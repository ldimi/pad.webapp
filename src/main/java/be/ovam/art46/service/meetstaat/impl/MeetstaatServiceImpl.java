package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.dao.MeetstaatRegelDao;
import be.ovam.art46.dao.OfferteDao;
import be.ovam.art46.service.BestekService;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.meetstaat.*;
import be.ovam.art46.service.meetstaat.response.ResponseReadMeetstaatCSV;
import be.ovam.art46.util.Application;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.util.MeetstaatUtil;
import com.google.common.io.Files;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.ICsvListReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Koen Corstjens on 29-7-13.
 */
@Service
@Transactional
public class MeetstaatServiceImpl extends MeetstaatBasicService<MeetstaatRegel> implements MeetstaatService {

    public static final List<String> TYPE_LIST = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(MeetstaatRegel.REGEL_TYPE_SPM);
                add(MeetstaatRegel.REGEL_TYPE_VH);
                add(MeetstaatRegel.REGEL_TYPE_TP);
                add("POST");
                add("(1)");
            }});
    private static final String PDF_EXTENTIE = ".pdf";
    private static final String EXCEL_EXTENTIE = ".xls";
    @Autowired
    private BestekService bestekService;
    @Autowired
    private MeetstaatEenheidService meetstaatEenheidService;
    @Autowired
    private MeetstaatExportExcelService meetstaatExportExcelService;
    @Autowired
    private MeetstaatExportPdfService meetstaatExportPdfService;
    @Autowired
    private BriefService briefService;
    @Autowired
    private MeetstaatOfferteService meetstaatOfferteService;
    @Autowired
    private MeetstaatRegelDao meetstaatRegelDao;
    @Autowired
    private OfferteDao offerteDao;
    private Logger log = Logger.getLogger(MeetstaatServiceImpl.class);

    public ResponseReadMeetstaatCSV readMeetstaatCSV(Reader reader, Bestek bestek) throws IOException {
        ResponseReadMeetstaatCSV responseReadMeetstaatCSV = new ResponseReadMeetstaatCSV();
        List<String> errors = new ArrayList<String>();
        Long id =null;
        if(bestek !=null) {
            id = bestek.getBestek_id();
        }
        List<MeetstaatRegel> meetstaatRegels = getMeetstaatRegelsFromCsv(reader,id , errors);
        completeMeetstaatRegels(meetstaatRegels);
        toevoegenOnbrekendeRegels(meetstaatRegels, bestek);
        meetstaatRegels = bereken(meetstaatRegels, errors);
        responseReadMeetstaatCSV.setErrors(errors);
        responseReadMeetstaatCSV.setMeetstaatRegels(meetstaatRegels);
        return responseReadMeetstaatCSV;
    }


    private boolean isMeetstaatRij(ICsvListReader listReader) {
        if (listReader.get(1) != null && StringUtils.contains(listReader.get(1).toUpperCase(), "TOTAAL")) {
            return false;
        }
        if (listReader.get(1) != null && StringUtils.contains(listReader.get(1).toUpperCase(), "POSTNR")) {
            return false;
        }
        Boolean result = !(listReader.get(4) == null && listReader.get(3) == null) && !StringUtils.contains(listReader.get(2), "ALGEMEEN TOTAAL (");
        result = listReader.get(1) != null || (result);
        result = result && !(listReader.get(1) != null && TYPE_LIST.contains(StringUtils.replace(listReader.get(1).trim(), ":", "")));
        return result;
    }


    public void delete(Long bestekId) {
        meetstaatRegelDao.deleteBestekRegels(bestekId);
    }

    public List<MeetstaatRegel> save(List<MeetstaatRegel> nieuweMeetstaatRegelList, Long bestekId) {
        Bestek bestek = bestekService.getBestek(bestekId);
        if (nieuweMeetstaatRegelList != null && nieuweMeetstaatRegelList.size() > 0) {
            for (MeetstaatRegel meetstaatRegel : nieuweMeetstaatRegelList) {
                meetstaatRegel.setBestek(bestek);
                meetstaatRegel.setMeetstaatTemplate(null);
            }
            List<MeetstaatRegel> oudeMeetstaatRegels = meetstaatRegelDao.getBestekRegels(bestekId);
            List<MeetstaatRegel> deleteMeetstaatRegels = new ArrayList<MeetstaatRegel>();
            for (MeetstaatRegel oudeMeestaatRegel : oudeMeetstaatRegels) {
                boolean gevonden = Boolean.FALSE;
                for (MeetstaatRegel meetstaatRegel : nieuweMeetstaatRegelList) {
                    if (meetstaatRegel.getId() != null && oudeMeestaatRegel.getId().compareTo(meetstaatRegel.getId()) == 0) {
                        gevonden = Boolean.TRUE;
                        break;
                    }
                }
                if (!gevonden) {
                    deleteMeetstaatRegels.add(oudeMeestaatRegel);
                }
            }
            meetstaatRegelDao.delete(deleteMeetstaatRegels);
            meetstaatRegelDao.save(nieuweMeetstaatRegelList);

        }
        return getAll(bestekId);
    }

    public List<MeetstaatRegel> getAll(Long bestekId) {
        List<MeetstaatRegel> meetstaatRegelList = meetstaatRegelDao.getBestekRegels(bestekId);
        bereken(meetstaatRegelList);
        return meetstaatRegelList;
    }

    public MeetstaatRegel get(Long bestekId, String postnr) {
        return meetstaatRegelDao.get(bestekId, postnr);
    }

    public List<MeetstaatRegel> herbereken(List<MeetstaatRegel> meetstaatRegels, BigDecimal btwTarief) {
        meetstaatRegels = cleanUpMeetstaat(meetstaatRegels);
        meetstaatRegels = bereken(meetstaatRegels);
        return meetstaatRegels;
    }

    public List<MeetstaatRegel> addNewMeetstaat(MeetstaatRegel newMeetstaatRegel, List<MeetstaatRegel> meetstaatRegels, BigDecimal btwTarief) {
        meetstaatRegels.add(newMeetstaatRegel);
        meetstaatRegels = cleanUpMeetstaat(meetstaatRegels);
        meetstaatRegels = bereken(meetstaatRegels);
        return meetstaatRegels;
    }

    public List<MeetstaatRegel> replace(List<MeetstaatRegel> meetstaatRegelList, String tevervangenPostnr, String resultPostnr, BigDecimal btwTarief) {
        sort(meetstaatRegelList);
        List<MeetstaatRegel> replaceMeetstaatRegelList = new ArrayList<MeetstaatRegel>();
        List<MeetstaatRegel> resultMeetstaatRegels = new ArrayList<MeetstaatRegel>();
        MeetstaatRegel meetstaatRegelParent = null;
        String startPostNr = tevervangenPostnr + ".";
        for (MeetstaatRegel meetstaatRegel : meetstaatRegelList) {
            if (StringUtils.equals(meetstaatRegel.getPostnr(), tevervangenPostnr)) {
                meetstaatRegelParent = meetstaatRegel;
            } else if (StringUtils.startsWith(meetstaatRegel.getPostnr(), startPostNr)) {
                replaceMeetstaatRegelList.add(meetstaatRegel);
            } else {
                resultMeetstaatRegels.add(meetstaatRegel);
            }
        }
        resultMeetstaatRegels = cleanUpMeetstaat(resultMeetstaatRegels);
        meetstaatRegelParent.setPostnr(resultPostnr);
        meetstaatRegelParent.setCrudStatus("V");
        resultMeetstaatRegels.add(meetstaatRegelParent);
        resultMeetstaatRegels = cleanUpMeetstaat(resultMeetstaatRegels);
        for (MeetstaatRegel meetstaatRegel : replaceMeetstaatRegelList) {
            meetstaatRegel.setPostnr(StringUtils.replaceOnce(meetstaatRegel.getPostnr(), startPostNr, meetstaatRegelParent.getPostnr() + "."));
            resultMeetstaatRegels.add(meetstaatRegel);
        }
        resultMeetstaatRegels = herbereken(resultMeetstaatRegels, btwTarief);
        return resultMeetstaatRegels;

    }

    public List<String> definitiefmaken(Long bestekId) {
        List<String> errors = validate(getAll(bestekId));
        if (CollectionUtils.isNotEmpty(errors)) {
            return errors;
        }
        try {
            ontgrendelDefinitieveMeetstaat(bestekId);
        } catch (Exception e) {
            log.error(e, e);
            throw new RuntimeException(e);
        }
        File tempdir = Files.createTempDir();
        FileOutputStream fileOutputStreamPdf = null;
        File pdfFile = null;
        try {
            pdfFile = File.createTempFile("pdf", String.valueOf(bestekId), tempdir);
            fileOutputStreamPdf = new FileOutputStream(pdfFile);
            meetstaatExportPdfService.createFinalExport(bestekId, fileOutputStreamPdf);
        } catch (IOException e) {
            log.error(e, e);
            throw new RuntimeException(e);
        } finally {
            if (fileOutputStreamPdf != null) {
                try {
                    fileOutputStreamPdf.flush();
                    fileOutputStreamPdf.close();
                } catch (IOException ioe) {
                    log.error(ioe, ioe);
                    throw new RuntimeException(ioe);
                }
            }
        }
        saveMeetstaatDoc(bestekId, pdfFile, PDF_EXTENTIE);

        FileOutputStream fileOutputStreamExcel = null;
        File excelFile = null;
        try {
            excelFile = File.createTempFile("xls", String.valueOf(bestekId), tempdir);
            fileOutputStreamExcel = new FileOutputStream(excelFile);
            meetstaatExportExcelService.createFinalExport(bestekId, fileOutputStreamExcel);
            fileOutputStreamExcel.close();
        } catch (IOException e) {
            log.error(e, e);
            throw new RuntimeException(e);
        } finally {
            if (fileOutputStreamExcel != null) {
                try {
                    fileOutputStreamExcel.flush();
                    fileOutputStreamExcel.close();
                } catch (IOException ioe) {
                    log.error(ioe, ioe);
                    throw new RuntimeException(ioe);
                }
            }
        }
        saveMeetstaatDoc(bestekId, excelFile, EXCEL_EXTENTIE);
        tempdir.delete();
        return null;
    }

    public void ontgrendelDefinitieveMeetstaat(Long bestekId) throws Exception {
        Bestek bestek = bestekService.getBestek(bestekId);
        if (CollectionUtils.isNotEmpty(offerteDao.getForBestekId(bestekId))) {
            log.error("Gebruiker " + Application.INSTANCE.getUser_id() + " probeert een meetstaat te verwijderen waar al offertes aanhangen. Bestek:" + bestekId);
            return;
        }
        Brief meetstaatPDF = bestek.getMeetstaatPDF();
        Brief meetstaatExcel = bestek.getMeetstaatExcel();
        bestek.setMeetstaatPDF(null);
        bestek.setMeetstaatExcel(null);
        bestekService.saveOrUpdate(bestek);
        if (meetstaatPDF != null) {
            briefService.deleteBrief(meetstaatPDF);
        }
        if (meetstaatExcel != null) {
            briefService.deleteBrief(meetstaatExcel);
        }
    }

    public void save(MeetstaatRegel meetstaatRegel) {
        meetstaatRegelDao.save(meetstaatRegel);
    }


    private void saveMeetstaatDoc(Long bestekId, File file, String extentie) {
        try {
            Bestek bestek = bestekService.getBestek(bestekId);
            String omschrijving = bestek.getOmschrijving();
            if (StringUtils.isEmpty(omschrijving)) {
                omschrijving = StringUtils.EMPTY;
            }
            String commentaar = "Definitieve meetstaat bestek: " + bestek.getBestek_nr() + ", " + omschrijving + " (" + extentie + ")";
            String naam = "Definitieve_meetstaat_" + bestek.getBestek_nr() + extentie;
            Brief brief = briefService.makeDocument(bestek.getDossier_id(), BriefService.CATEGORIE_ID_MEETSTAAT, Application.INSTANCE.getUser_id(), commentaar, bestekId);
            try {
                briefService.uploadBrief(brief.getBrief_id(), Files.toByteArray(file), naam);
            } catch (Exception e) {
                log.error(e, e);
                throw new RuntimeException(e);
            }
            if (PDF_EXTENTIE.equals(extentie)) {
                bestek.setMeetstaatPDF(brief);
            } else {
                bestek.setMeetstaatExcel(brief);
            }
            bestekService.saveOrUpdate(bestek);
        } catch (InvocationTargetException ite) {
            log.error(ite, ite);
            throw new RuntimeException(ite);
        } catch (IllegalAccessException iae) {
            log.error(iae, iae);
            throw new RuntimeException(iae);
        } catch (Exception e) {
            log.error(e, e);
            throw new RuntimeException(e);
        }
    }


    private MeetstaatRegel fillMeetstaatRegelMetDetails(ICsvListReader listReader, Bestek bestek, List<String> fouten) {
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        if (bestek != null) {
            meetstaatRegel.setBestek(bestek);
        }
        meetstaatRegel.setPostnr(StringUtils.trim(listReader.get(1)));
        meetstaatRegel.setTaak(StringUtils.trim(listReader.get(2)));
        meetstaatRegel.setDetails(StringUtils.trim(listReader.get(3)));
        meetstaatRegel.setType(StringUtils.trim(listReader.get(4)));
        if (StringUtils.equals(MeetstaatRegel.REGEL_TYPE_VH, meetstaatRegel.getType())) {
            if (listReader.get(5) != null) {
                meetstaatRegel.setEenheid(mapEenheid(StringUtils.trim(listReader.get(5)), fouten));
            }
            if (listReader.get(6) != null) {
                meetstaatRegel.setAantalString(listReader.get(6));
            }
            if (listReader.get(7) != null) {
                meetstaatRegel.setEenheidsprijsString(listReader.get(7));
            }
        } else if (MeetstaatRegel.REGEL_TYPE_SPM.equals(meetstaatRegel.getType()) || MeetstaatRegel.REGEL_TYPE_TP.equals(meetstaatRegel.getType())) {
            if (StringUtils.isNotEmpty(StringUtils.trim(listReader.get(8)))) {
                meetstaatRegel.setRegelTotaalString(StringUtils.trim(listReader.get(8)));
            } else if (listReader.length() > 8 && StringUtils.isNotEmpty(StringUtils.trim(listReader.get(9)))) {
                meetstaatRegel.setRegelTotaalString(StringUtils.trim(listReader.get(9)));
            } else if (listReader.length() > 9 && StringUtils.isNotEmpty(StringUtils.trim(listReader.get(10)))) {
                meetstaatRegel.setRegelTotaalString(StringUtils.trim(listReader.get(10)));
            }
        }
        return meetstaatRegel;
    }


    private MeetstaatRegel fillMeetstaatRegelZonderDetail(ICsvListReader listReader, Bestek bestek, List<String> fouten) {
        MeetstaatRegel meetstaatRegel = new MeetstaatRegel();
        if (bestek != null) {
            meetstaatRegel.setBestek(bestek);
        }
        meetstaatRegel.setPostnr(StringUtils.trim(listReader.get(1)));
        meetstaatRegel.setTaak(StringUtils.trim(listReader.get(2)));
        meetstaatRegel.setDetails(StringUtils.EMPTY);
        meetstaatRegel.setType(StringUtils.trim(listReader.get(3)));
        if (StringUtils.equals(MeetstaatRegel.REGEL_TYPE_VH, meetstaatRegel.getType())) {
            if (listReader.get(4) != null) {
                meetstaatRegel.setEenheid(mapEenheid(StringUtils.trim(listReader.get(4)), fouten));
            }
            if (listReader.get(5) != null) {
                meetstaatRegel.setAantalString(StringUtils.trim(listReader.get(5)));
            }
            if (listReader.get(6) != null) {
                meetstaatRegel.setEenheidsprijsString(StringUtils.trim(listReader.get(6)));
            }
        }
        if (MeetstaatRegel.REGEL_TYPE_SPM.equals(StringUtils.trim(listReader.get(3))) || MeetstaatRegel.REGEL_TYPE_TP.equals(StringUtils.trim(listReader.get(3)))) {
            meetstaatRegel.setRegelTotaalString(StringUtils.trim(listReader.get(7)));
        }

        return meetstaatRegel;
    }

    protected void completeMeetstaatRegels(List<MeetstaatRegel> meetstaatRegels) {
        String laatstePostnr = "";
        int postnrIndex = 1;
        MeetstaatRegel vorigeMeetstaatRegel = null;
        List<MeetstaatRegel> toetevoegenMeetstaatRegel = new ArrayList<MeetstaatRegel>();
        boolean inSpecialSub = false;
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            if (StringUtils.isNotEmpty(laatstePostnr) && StringUtils.isEmpty(meetstaatRegel.getPostnr())) {
                if (vorigeMeetstaatRegel != null && StringUtils.isNotEmpty(vorigeMeetstaatRegel.getType()) && StringUtils.isEmpty(meetstaatRegel.getTaak()) && StringUtils.isNotEmpty(vorigeMeetstaatRegel.getDetails()) && StringUtils.isNotEmpty(vorigeMeetstaatRegel.getTaak())) {
                    laatstePostnr = vorigeMeetstaatRegel.getPostnr();
                    MeetstaatRegel meetstaatRegelSubTitle = new MeetstaatRegel();
                    meetstaatRegelSubTitle.setPostnr(laatstePostnr);
                    meetstaatRegelSubTitle.setTaak(vorigeMeetstaatRegel.getTaak());
                    meetstaatRegelSubTitle.setBestek(vorigeMeetstaatRegel.getBestek());
                    toetevoegenMeetstaatRegel.add(meetstaatRegelSubTitle);
                    vorigeMeetstaatRegel.setPostnr(laatstePostnr + "." + 1);
                    vorigeMeetstaatRegel.setTaak(StringUtils.EMPTY);
                    postnrIndex = 2;
                    inSpecialSub = true;
                } else if (vorigeMeetstaatRegel != null && StringUtils.isEmpty(meetstaatRegel.getTaak()) && StringUtils.isNotEmpty(vorigeMeetstaatRegel.getTaak()) && StringUtils.isEmpty(vorigeMeetstaatRegel.getType())) {
                    postnrIndex = 1;
                    laatstePostnr = vorigeMeetstaatRegel.getPostnr();
                    inSpecialSub = true;
                }
                if (inSpecialSub && StringUtils.isNotEmpty(meetstaatRegel.getTaak())) {
                    postnrIndex = MeetstaatUtil.getLaatsteNummer(laatstePostnr) + 1;
                    laatstePostnr = MeetstaatUtil.getParent(laatstePostnr);
                    inSpecialSub = false;
                }
                meetstaatRegel.setPostnr(laatstePostnr + "." + postnrIndex);
                postnrIndex++;
            } else {
                inSpecialSub = false;
                laatstePostnr = meetstaatRegel.getPostnr();
                postnrIndex = 1;
            }
            vorigeMeetstaatRegel = meetstaatRegel;
        }
        meetstaatRegels.addAll(toetevoegenMeetstaatRegel);
    }

    protected List<MeetstaatRegel> toevoegenOnbrekendeRegels(List<MeetstaatRegel> meetstaatRegels, Bestek bestek) {
        sort(meetstaatRegels);
        List<MeetstaatRegel> toetevoegenMeetstaatRegels = new ArrayList<MeetstaatRegel>();
        String laatstePostnr = "0";
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            String postNr = meetstaatRegel.getPostnr();
            while (!StringUtils.equals(laatstePostnr, postNr) && !MeetstaatUtil.isVolgendPostnr(laatstePostnr, postNr)) {
                MeetstaatRegel toetevoegenmeetstaatRegel = new MeetstaatRegel();
                toetevoegenmeetstaatRegel.setPostnr(MeetstaatUtil.berekenVorigPostNr(postNr));
                toetevoegenmeetstaatRegel.setBestek(bestek);
                toetevoegenMeetstaatRegels.add(toetevoegenmeetstaatRegel);
                postNr = toetevoegenmeetstaatRegel.getPostnr();
                log.debug("toevoegen:" + postNr);
            }
            laatstePostnr = meetstaatRegel.getPostnr();
            log.debug(meetstaatRegel.getPostnr() + "'" + meetstaatRegel.getTaak() + "' '" + meetstaatRegel.getDetails() + "'");
        }

        meetstaatRegels.addAll(toetevoegenMeetstaatRegels);
        sort(meetstaatRegels);
        return meetstaatRegels;
    }

    private ArrayList<MeetstaatRegel> cleanUpMeetstaat(List<MeetstaatRegel> meetstaatRegels) {
        sort(meetstaatRegels);
        ArrayList<MeetstaatRegel> newMeetstaatRegels = new ArrayList<MeetstaatRegel>();

        MeetstaatRegel vorigeMeetstaatRegel = new MeetstaatRegel();
        vorigeMeetstaatRegel.setPostnr("0");

        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            if (!meetstaatRegel.isTotaal()) {
                if (!MeetstaatUtil.isVolgendPostnr(vorigeMeetstaatRegel.getPostnr(), meetstaatRegel.getPostnr())) {
                    if (StringUtils.isNotEmpty(meetstaatRegel.getCrudStatus()) && meetstaatRegel.getPostnr().equals(vorigeMeetstaatRegel.getPostnr())) {
                        vorigeMeetstaatRegel.setPostnr(MeetstaatUtil.herstelPostnr(vorigeMeetstaatRegel.getPostnr(), meetstaatRegel.getPostnr()));
                    } else {
                        meetstaatRegel.setPostnr(MeetstaatUtil.herstelPostnr(vorigeMeetstaatRegel.getPostnr(), meetstaatRegel.getPostnr()));
                        vorigeMeetstaatRegel = meetstaatRegel;
                    }
                } else {
                    vorigeMeetstaatRegel = meetstaatRegel;
                }
                newMeetstaatRegels.add(meetstaatRegel);
            }
        }
        return newMeetstaatRegels;
    }

    @Override
    protected MeetstaatRegel getRegelFrom(ICsvListReader listReader, Long bestekId, List<String> fouten) {
        Bestek bestek =null;
        if(bestekId!=null) {
            bestek = bestekService.getBestek(bestekId);
        }
        if (listReader.length() >= 8) {
            if (isMeetstaatRij(listReader)) {
                return fillMeetstaatRegelMetDetails(listReader, bestek, fouten);
            }
        } else if (listReader.length() > 6) {
            if (listReader.get(1) != null && listReader.get(2) != null) {
                if (!(TYPE_LIST.contains(StringUtils.replace(listReader.get(1).trim().toUpperCase(), ":", "")))) {
                    return fillMeetstaatRegelZonderDetail(listReader, bestek, fouten);
                }
            }
        }
        return null;
    }

    @Override
    public void sort(List<MeetstaatRegel> regels) {
        Collections.sort(regels);
    }

    public String mapEenheid(String eenheid, List<String> fouten) {
        eenheid = StringUtils.trim(eenheid);
        if (StringUtils.isNotEmpty(eenheid)) {
            Map<String, String> eenheden = meetstaatEenheidService.getAllEenheden();
            String upperCaseEenheid = eenheid.toUpperCase();
            if (eenheden.containsKey(upperCaseEenheid)) {
                return eenheden.get(upperCaseEenheid);
            } else {
                fouten.add("Eenheid : " + eenheid + " is onbekend\n");
                log.info("Eenheid : " + eenheid + " is onbekend\n");
            }
        }
        return null;
    }

    public void setMeetstaatEenheidService(MeetstaatEenheidService meetstaatEenheidService) {
        this.meetstaatEenheidService = meetstaatEenheidService;
    }

    public void setBestekService(BestekService bestekService) {
        this.bestekService = bestekService;
    }

    @Override
    public MeetstaatRegel getNewInstance() {
        return new MeetstaatRegel();
    }
}
