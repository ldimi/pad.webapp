package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.dao.OfferteDao;
import be.ovam.art46.dao.OfferteRegelDao;
import be.ovam.art46.model.OfferteForm;
import be.ovam.art46.model.OffertesExport;
import be.ovam.art46.model.OffertesExportRegel;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.art46.service.meetstaat.MeetstaatService;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.OfferteRegel;
import be.ovam.pad.service.MeetstaatBasicService;
import be.ovam.pad.util.MeetstaatUtil;
import be.ovam.util.mybatis.SqlSession;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by Koen Corstjens on 29-8-13.
 */
@Service
@Transactional
public class MeetstaatOfferteServiceImpl extends MeetstaatBasicService<OfferteRegel> implements MeetstaatOfferteService {

    public static final Mapper MAPPER = new DozerBeanMapper();

    @Autowired
    @Qualifier("sqlSession")
    SqlSession sqlSession;
    
    @Autowired
    BriefService briefService;
    
    private final SecureRandom random = new SecureRandom();
    
    @Autowired
    private OfferteDao offerteDao;
    @Autowired
    private OfferteRegelDao offerteRegelDao;
    @Autowired
    private MeetstaatService meetstaatService;
    
    private final Logger log = Logger.getLogger(MeetstaatOfferteServiceImpl.class);

    public List<OfferteRegel> getEmptyOfferte(Long bestekId) {
        List<MeetstaatRegel> meetstaatRegels = meetstaatService.getAll(bestekId);
        return getEmptyOfferte(meetstaatRegels);
    }

    public List<OfferteRegel> getEmptyOfferte(List<MeetstaatRegel> meetstaatRegels) {
        List<OfferteRegel> offerteRegels = new ArrayList<OfferteRegel>();
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            OfferteRegel offerteRegel = new OfferteRegel();
            offerteRegel.setMeetstaatRegel(meetstaatRegel);
            if (StringUtils.equals(MeetstaatRegel.REGEL_TYPE_SPM, meetstaatRegel.getType())) {
                offerteRegel.setRegelTotaal(meetstaatRegel.getRegelTotaal());
            }
            offerteRegels.add(offerteRegel);
        }
        return offerteRegels;
    }


    public Long save(OfferteForm offerteForm, Long bestekId) throws Exception {
        offerteDao.save(offerteForm.getOfferte());
        for (OfferteRegel offerteRegel : offerteForm.getOfferteRegels()) {
            if (offerteRegel.isExtraRegel()) {
                offerteRegel.setOfferte(offerteDao.get(offerteForm.getOfferte().getId()));
                offerteRegelDao.save(offerteRegel);
            } else if (offerteRegel.getMeetstaatRegel().getId() != null) {
                offerteRegel.setOfferte(offerteForm.getOfferte());
                offerteRegelDao.save(offerteRegel);
            }
        }
        return offerteForm.getOfferte().getId();
    }

    public List<Offerte> getOrCreateForBestek(Long bestekId) throws Exception {
        List<Brief> offerteBrieven = briefService.getOfferteBrieven(bestekId);
        List<Offerte> result = new ArrayList<Offerte>();
        for (Brief brief : offerteBrieven) {
            Offerte offerte = offerteDao.getForBriefId(brief.getBrief_id());
            if (offerte == null) {
                offerte = new Offerte();
                offerte.setBrief(brief);
                offerte.setInzender(brief.getAdresContactNaam());
                offerte.setBestekId(bestekId);
                offerte.setBtwTarief(21);
                Offerte origineel = offerteDao.getOrgineelHerlanceringForNewOfferte(offerte);
                if (origineel != null) {
                    offerte.setOrgineel(origineel);
                }
                offerteDao.save(offerte);
            }
            result.add(offerte);
        }
        return result;
    }
    
    public List getOrCreateOffertesForBestek(Long bestekId) {
        return null;
    }
    
    public OfferteForm getOfferte(Offerte offerte) {
        List<MeetstaatRegel> meetstaatRegels = meetstaatService.getAll(offerte.getBestekId());
        return getOfferte(offerte, meetstaatRegels);
    }

    public OfferteForm getOfferte(Offerte offerte, List<MeetstaatRegel> meetstaatRegels) {
        OfferteForm offerteForm = new OfferteForm();
        offerteForm.setOfferte(offerte);
        List<OfferteRegel> offerteRegels = offerte.getOfferteRegels();
        if (CollectionUtils.isEmpty(offerteRegels)) {
            offerteRegels = getEmptyOfferte(meetstaatRegels);
        }
        offerteForm.setOfferteRegels(fill(offerteRegels, meetstaatRegels));
        offerteForm.setOfferteRegels(bereken(offerteForm.getOfferteRegels()));
        for (OfferteRegel offerteRegel : offerteForm.getOfferteRegels()) {
            if (offerteRegel.getMeetstaatRegel() != null && POSTNR_TOTAAL.equals(offerteRegel.getMeetstaatRegel().getPostnr())) {
                offerteForm.getOfferte().setTotaal(offerteRegel.getRegelTotaal());
                offerteDao.save(offerteForm.getOfferte());
            }
            if (offerteRegel.getMeetstaatRegel() != null && POSTNR_TOTAAL_BTW.equals(offerteRegel.getMeetstaatRegel().getPostnr())) {
                offerteForm.getOfferte().setTotaalInclBtw(offerteRegel.getRegelTotaal());
                offerteDao.save(offerteForm.getOfferte());
            }
        }
        return offerteForm;
    }

    public OfferteForm getOfferte(Long offerteId) {
        return getOfferte(offerteDao.get(offerteId));
    }

    public List<Brief> getOfferteBrieven(Long bestekId) {
        return briefService.getOfferteBrieven(bestekId);
    }

    public List<String> uploudMeetstaatCSV(InputStreamReader inputStreamReader, Long bestekId, Long offerteId) throws IOException {
        Offerte offerte = offerteDao.get(offerteId);
        List<String> fouten = new ArrayList<String>();
        List<MeetstaatRegel> meetstaatRegels = meetstaatService.getAll(bestekId);
        List<OfferteRegel> offerteRegels = getRegelsFromCsv(inputStreamReader, offerteId, meetstaatRegels, fouten);
        HashMap<String, OfferteRegel> offerteRegelHashMap = new HashMap<String, OfferteRegel>();
        int hoogsteRootPostnr = 0;
        List<OfferteRegel> extraRegels = new ArrayList<OfferteRegel>();
        for (OfferteRegel offerteRegel : offerteRegels) {
            offerteRegelHashMap.put(StringUtils.trim(offerteRegel.getPostnr()), offerteRegel);
            if (offerteRegel.isExtraRegel()) {
                extraRegels.add(offerteRegel);
                if (!offerteRegel.isTotaal() && offerteRegel.getLevel() > 1) {
                    OfferteRegel rootOfferteRegel = offerteRegelHashMap.get("" + MeetstaatUtil.getRootPostnr(offerteRegel.getPostnr()));
                    rootOfferteRegel.setChilds(rootOfferteRegel.getChilds() + 1);
                }
            } else {
                int rootPostnr = MeetstaatUtil.getRootPostnr(offerteRegel.getPostnr());
                if (rootPostnr > hoogsteRootPostnr) {
                    hoogsteRootPostnr = rootPostnr;
                }
            }
        }
        List<OfferteRegel> orgineleRegels = validateOrgineleRegels(bestekId, offerte, fouten, offerteRegelHashMap);
        if (offerte.getOrgineel() == null) {
            foutenExtraRegelsNietToegestaan(extraRegels, fouten);
        } else {
            validateExtraRegels(extraRegels, fouten, ++hoogsteRootPostnr);
        }
        if (CollectionUtils.isEmpty(fouten)) {
            if (CollectionUtils.isNotEmpty(extraRegels)) {
                orgineleRegels.addAll(extraRegels);
            }
            offerteRegelDao.deleteWhereOfferteId(offerteId);
            for (OfferteRegel orgineleRegel : orgineleRegels) {
                offerteRegelDao.save(orgineleRegel);
            }
        }
        return fouten;
    }

    private void foutenExtraRegelsNietToegestaan(List<OfferteRegel> extraRegels, List<String> fouten) {
        if (CollectionUtils.isNotEmpty(extraRegels)) {
            for (OfferteRegel extraregel : extraRegels) {
                fouten.add("Geen extra regels toegestaan op deze offerte: " + extraregel.getPostnr());
            }
        }
    }

    private void validateExtraRegels(List<OfferteRegel> extraRegels, List<String> errors, int extraRootPostNr) {
        if (CollectionUtils.isNotEmpty(extraRegels)) {
            for (OfferteRegel extraregel : extraRegels) {
                if (MeetstaatUtil.getRootPostnr(extraregel.getPostnr()) != extraRootPostNr) {
                    errors.add("Extra regel " + extraregel.getPostnr() + " post nr start niet met " + extraRootPostNr + "!!!");
                }
                String error = extraregel.validateRegel();
                if (StringUtils.isNotEmpty(error)) {
                    errors.add(error);
                }
            }
        }
    }

    private List<OfferteRegel> validateOrgineleRegels(Long bestekId, Offerte offerte, List<String> errors, HashMap<String, OfferteRegel> offerteRegelHashMap) {
        List<OfferteRegel> orgineleRegels = getEmptyOfferte(bestekId);
        for (OfferteRegel orgineleRegel : orgineleRegels) {
            if (!orgineleRegel.isTotaal()) {
                orgineleRegel.setOfferte(offerte);
                OfferteRegel geimporteerdeOfferteRegel = offerteRegelHashMap.get(StringUtils.trim(orgineleRegel.getPostnr()));
                if (geimporteerdeOfferteRegel == null) {
                    errors.add("Regel " + orgineleRegel.getPostnr() + " bestaat niet in import bestand!!!");
                } else if (orgineleRegel.getType() == null) {
                    orgineleRegel.setType("Totaal");
                } else if (orgineleRegel.getType().equals(geimporteerdeOfferteRegel.getType())) {
                    orgineleRegel.getRegelTotaal();
                    if (StringUtils.equals(MeetstaatRegel.REGEL_TYPE_SPM, orgineleRegel.getType())) {
                        if (orgineleRegel.getRegelTotaal() == null && geimporteerdeOfferteRegel.getRegelTotaal() != null) {
                            errors.add("Regel: " + orgineleRegel.getPostnr() + " het bedrag van de meetstaatregel(" + orgineleRegel.getRegelTotaal() + ") komt niet overeen met het bedrag van de offerteregel(" + geimporteerdeOfferteRegel.getRegelTotaal() + ")!!!");
                        } else if (orgineleRegel.getRegelTotaal() != null && geimporteerdeOfferteRegel.getRegelTotaal() == null) {
                            errors.add("Regel: " + orgineleRegel.getPostnr() + " het bedrag van de meetstaatregel(" + orgineleRegel.getRegelTotaal() + ") komt niet overeen met het bedrag van de offerteregel(" + geimporteerdeOfferteRegel.getRegelTotaal() + ")!!!");
                        } else if (!orgineleRegel.getRegelTotaal().setScale(2).equals(geimporteerdeOfferteRegel.getRegelTotaal().setScale(2))) {
                            errors.add("Regel: " + orgineleRegel.getPostnr() + " het bedrag van de meetstaatregel(" + orgineleRegel.getRegelTotaal() + ") komt niet overeen met het bedrag van de offerteregel(" + geimporteerdeOfferteRegel.getRegelTotaal() + ")!!!");
                        } else {
                            orgineleRegel.setAantal(geimporteerdeOfferteRegel.getAantal());
                            orgineleRegel.setEenheidsprijs(geimporteerdeOfferteRegel.getEenheidsprijs());
                            orgineleRegel.setRegelTotaal(geimporteerdeOfferteRegel.getRegelTotaal());
                        }
                    } else {
                        orgineleRegel.setAantal(geimporteerdeOfferteRegel.getAantal());
                        orgineleRegel.setEenheidsprijs(geimporteerdeOfferteRegel.getEenheidsprijs());
                        orgineleRegel.setRegelTotaal(geimporteerdeOfferteRegel.getRegelTotaal());
                    }
                    String error = orgineleRegel.validateRegel();
                    if (StringUtils.isNotEmpty(error)) {
                        errors.add(error);
                    }
                } else {
                    errors.add("Type van Regel " + orgineleRegel.getPostnr() + " is niet gelijk aan het geimporteerde type");
                }
            }
        }
        return orgineleRegels;
    }

    public void toekenen(Long offerteId) throws Exception {

        Offerte offerte = offerteDao.get(offerteId);
        Brief brief = offerte.getBrief();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestekId", offerte.getBestekId());
        params.put("adresId", brief.getAdres_id());
        params.put("contactId", brief.getContact_id());
        Map map = sqlSession.selectOne("be.ovam.art46.mappers.BestekAdresMapper.getBestekAdres", params);

        Integer bestek_adres_id;
        if (MapUtils.isEmpty(map)) {
            sqlSession.insert("be.ovam.art46.mappers.BestekAdresMapper.insertBestekAdres", params);
            bestek_adres_id = (Integer) params.get("bestek_adres_id");
        } else {
            bestek_adres_id = (Integer) map.get("bestek_adres_id");
        }
        offerte.setOpdrachtgever_id(bestek_adres_id);
        offerte.setStatus(Offerte.STATUS_TOEGEWEZEN);
        offerteDao.save(offerte);
    }


    public void afsluiten(Long offerteId) {
        Offerte offerte = offerteDao.get(offerteId);
        offerte.setStatus(Offerte.STATUS_AFGESLOTEN);
        offerteDao.save(offerte);
    }

    public Offerte get(Long offerteId) {
        return offerteDao.get(offerteId);
    }

    public List<Offerte> getToegekendeOffertes(Long bestekId) {
        return offerteDao.getToegekendForBestekId(bestekId);
    }

    public void toekenningverwijderen(Long offerteId) throws Exception {
        Offerte offerte = offerteDao.get(offerteId);

        Integer opdrachtgever_id = offerte.getOpdrachtgever_id();

        offerte.setOpdrachtgever_id(null);
        offerte.setStatus(null);
        offerte.setOrganisatie_id(null);
        offerteDao.save(offerte);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestek_adres_id", opdrachtgever_id);
        sqlSession.delete("be.ovam.art46.mappers.BestekAdresMapper.deleteBestekAdres", params);

    }

    public OffertesExport getOffertesRegelsForBestek(Long bestekId) throws Exception {
        OffertesExport offertesExport = new OffertesExport();
        List<OffertesExportRegel> offertesExportRegels = new ArrayList<OffertesExportRegel>();
        
        //List<Offerte> offertes = getOrCreateForBestek(bestekId);
        List<Offerte> offertes = offerteDao.getForBestekId(bestekId);
        
        
        List<MeetstaatRegel> meetstaatRegels = meetstaatService.getAll(bestekId);
        List<OfferteRegel> emptyOfferteRegels = getEmptyOfferte(meetstaatRegels);
        for (OfferteRegel emptyOfferteRegel : emptyOfferteRegels) {
            OffertesExportRegel offertesExportRegel = new OffertesExportRegel();
            MAPPER.map(emptyOfferteRegel, offertesExportRegel);
            offertesExportRegel.setOfferteRegels(new ArrayList<OfferteRegel>());
            for (Offerte offerte : offertes) {
                OfferteForm offerteForm = getOfferte(offerte, meetstaatRegels);
                List<OfferteRegel> offerteRegels = offerteForm.getOfferteRegels();
                Boolean gevonden = Boolean.FALSE;
                for (OfferteRegel offerteRegel : offerteRegels) {
                    if (StringUtils.equals(emptyOfferteRegel.getPostnr(), offerteRegel.getPostnr())) {
                        offertesExportRegel.getOfferteRegels().add(offerteRegel);
                        gevonden = Boolean.TRUE;
                        break;
                    }
                }
                if (!gevonden) {
                    offertesExportRegel.getOfferteRegels().add(new OfferteRegel());
                }
            }
            offertesExportRegels.add(offertesExportRegel);
        }
        offertesExport.setOffertes(offertes);
        offertesExport.setOffertesExportRegels(offertesExportRegels);
        return offertesExport;
    }

    private List<OfferteRegel> fill(List<OfferteRegel> offerteRegels, List<MeetstaatRegel> meetstaatRegels) {
        HashMap<Long, OfferteRegel> offerteRegelMeetstaatRegelHashMap = new HashMap<Long, OfferteRegel>();
        List<OfferteRegel> extraregels = new ArrayList<OfferteRegel>();
        for (OfferteRegel offerteRegel : offerteRegels) {
            if (offerteRegel.isExtraRegel()) {
                extraregels.add(offerteRegel);
            } else {
                offerteRegelMeetstaatRegelHashMap.put(offerteRegel.getMeetstaatRegel().getId(), offerteRegel);
            }
        }
        List<OfferteRegel> result = new ArrayList<OfferteRegel>();
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            OfferteRegel offerteRegel = offerteRegelMeetstaatRegelHashMap.get(meetstaatRegel.getId());
            if (offerteRegel == null) {
                offerteRegel = new OfferteRegel();
            }
            offerteRegel.setMeetstaatRegel(meetstaatRegel);
            result.add(offerteRegel);
        }
        result.addAll(extraregels);
        return result;
    }

    @Override
    protected OfferteRegel getRegelFrom(ICsvListReader listReader, Long bestekId, List<String> fouten) {
        OfferteRegel offerteRegel = null;
        if (listReader.length() >= 8) {
            offerteRegel = new OfferteRegel();
            offerteRegel.setPostnr(StringUtils.trim(listReader.get(1)));
            offerteRegel.setType(StringUtils.trim(listReader.get(4)));
            if (MeetstaatRegel.REGEL_TYPE_SPM.equals(offerteRegel.getType()) || MeetstaatRegel.REGEL_TYPE_TP.equals(offerteRegel.getType())) {
                if (StringUtils.isNotEmpty(StringUtils.trim(listReader.get(8)))) {
                    offerteRegel.setRegelTotaal(StringUtils.trim(listReader.get(8)));
                } else if (StringUtils.isNotEmpty(StringUtils.trim(listReader.get(9)))) {
                    offerteRegel.setRegelTotaal(StringUtils.trim(listReader.get(9)));
                } else if (StringUtils.isNotEmpty(StringUtils.trim(listReader.get(10)))) {
                    offerteRegel.setRegelTotaal(StringUtils.trim(listReader.get(10)));
                }
            }

            if (listReader.get(6) != null) {
                offerteRegel.setOfferteAantalString(StringUtils.trim(listReader.get(6)));
            }
            if (listReader.get(7) != null) {
                offerteRegel.setOfferteEenheidsprijsString(StringUtils.trim(listReader.get(7)));
            }
        }
        return offerteRegel;
    }

    @Override
    public void sort(List<OfferteRegel> regels) {
        Collections.sort(regels);
    }

    @Override
    public OfferteRegel getNewInstance() {
        OfferteRegel offerteRegel = new OfferteRegel();
        offerteRegel.setMeetstaatRegel(new MeetstaatRegel());
        return offerteRegel;
    }


    public List<OfferteRegel> getRegelsFromCsv(InputStreamReader inputStreamReader, Long offerteId, List<MeetstaatRegel> meetstaatRegels, List<String> fouten) throws IOException {
        HashMap<String, MeetstaatRegel> meetstaatRegelHashMap = new HashMap<String, MeetstaatRegel>();
        for (MeetstaatRegel meetstaatRegel : meetstaatRegels) {
            if (StringUtils.isNotEmpty(meetstaatRegel.getPostnr())) {
                meetstaatRegelHashMap.put(meetstaatRegel.getPostnr(), meetstaatRegel);
            }
        }

        if (fouten == null) {
            fouten = new ArrayList<String>();
        }
        ArrayList<OfferteRegel> regels = new ArrayList<OfferteRegel>();
        ICsvListReader listReader = null;
        try {
            listReader = new CsvListReader(inputStreamReader, CsvPreference.EXCEL_PREFERENCE);
            listReader.getHeader(true); // skip the header (can't be used with CsvListReader)
            while ((listReader.read()) != null) {
                OfferteRegel regel = getRegelFrom(listReader, offerteId, fouten, meetstaatRegelHashMap);
                if (regel != null) {
                    regels.add(regel);
                }
            }
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (listReader != null) {
                listReader.close();
            }
        }
        return regels;
    }

    private OfferteRegel getRegelFrom(ICsvListReader listReader, Long offerteId, List<String> fouten, HashMap<String, MeetstaatRegel> meetstaatRegelHashMap) {
        HashMap<String, String> postNummers = new HashMap<String, String>();
        OfferteRegel offerteRegel = null;
        if (listReader.length() >= 8) {
            String postnr = StringUtils.trim(listReader.get(1));
            if (StringUtils.isNotEmpty(postnr) && !StringUtils.contains("Postnr".toUpperCase(), postnr.toUpperCase()) && IsCoreectPostNr(postnr, fouten, postNummers)) {
                offerteRegel = new OfferteRegel();
                offerteRegel.setMeetstaatRegel(meetstaatRegelHashMap.get(postnr));
                String type = StringUtils.trim(listReader.get(4));
                String eenheid = StringUtils.trim(listReader.get(5));
                String titel = StringUtils.trim(listReader.get(2));
                String details = StringUtils.trim(listReader.get(3));
                if (offerteRegel.getMeetstaatRegel() == null) {
                    offerteRegel.setExtraRegelPostNr(postnr);
                    offerteRegel.setType(StringUtils.trim(type));
                    offerteRegel.setEenheid(StringUtils.trim(eenheid));
                    offerteRegel.setExtraRegelTaak(StringUtils.trim(titel));
                    offerteRegel.setExtraRegelDetails(StringUtils.trim(details));
                } else if (!StringUtils.equals(offerteRegel.getType(), type)) {
                    fouten.add("Type van Regel " + postnr + " is niet gelijk aan het geimporteerde type");
                }
                if (MeetstaatRegel.REGEL_TYPE_SPM.equals(offerteRegel.getType()) || MeetstaatRegel.REGEL_TYPE_TP.equals(offerteRegel.getType())) {
                    if (StringUtils.isNotEmpty(StringUtils.trim(listReader.get(8)))) {
                        offerteRegel.setRegelTotaal(StringUtils.trim(listReader.get(8)));
                    } else if (listReader.length() > 8) {
                        if (StringUtils.isNotEmpty(StringUtils.trim(listReader.get(9)))) {
                            offerteRegel.setRegelTotaal(StringUtils.trim(listReader.get(9)));
                        } else if (StringUtils.isNotEmpty(StringUtils.trim(listReader.get(10)))) {
                            offerteRegel.setRegelTotaal(StringUtils.trim(listReader.get(10)));
                        }
                    }
                } else if (MeetstaatRegel.REGEL_TYPE_VH.equals(offerteRegel.getType())) {
                    if (listReader.get(6) != null) {
                        offerteRegel.setOfferteAantalString(StringUtils.trim(listReader.get(6)));
                    }
                    if (listReader.get(7) != null) {
                        offerteRegel.setOfferteEenheidsprijsString(StringUtils.trim(listReader.get(7)));
                    }
                }
                offerteRegel.setOfferte(offerteDao.get(offerteId));
            }
        }
        return offerteRegel;
    }

}
