package be.ovam.art46.service.schuldvordering;

import be.ovam.art46.model.SchuldvorderingExportData;
import be.ovam.art46.model.SchuldvorderingSapProjectExportData;
import be.ovam.art46.service.*;
import be.ovam.art46.service.impl.EsbServiceImpl;
import be.ovam.art46.util.PdfMerger;
import be.ovam.pad.model.*;
import be.ovam.pad.util.BigDecimalFormatter;
import com.google.common.io.Files;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by Koen on 19/03/14.
 */
@Service
@Transactional
public class SchuldvorderingExportServiceImpl implements SchuldvorderingExportService {

    private Logger log = Logger.getLogger(SchuldvorderingExportServiceImpl.class);

    @Autowired
    private BriefService briefService;
    @Autowired
    private SchuldvorderingService schuldvorderingService;
    @Autowired
    private BriefCategorieService briefCategorieService;
    @Autowired
    private AanvraagSchuldvorderingExportService aanvraagSchuldvorderingExportService;
    @Autowired
    private GebruikerService gebruikerService;
    @Autowired
    private HandtekeningenService handtekeningenService;
    @Autowired
    private BriefTemplateService briefTemplateService;
    @Autowired
    private EsbService esbService;


    @Value("${pad.uid.afdelingshoofd.ivs}")
    private CharSequence uidAfdelingshoofdIvs;

    public void createDraftExport(Integer schuldvorderingId, ServletOutputStream op) throws Exception {
        log.info("java.io.tmpdir : " + System.getProperty("java.io.tmpdir"));

        Schuldvordering schuldvordering = schuldvorderingService.get(schuldvorderingId);

        makeSchuldvorderingPdf(schuldvordering, SchuldvorderingExportService.DRAFT, op);
    }

    public String handtekenningBeschikbaar() throws ExecutionException {
        return handtekeningenService.handtekenningBeschikbaar();
    }

    public byte[] makeSchuldvorderingPdf(Schuldvordering schuldvordering, String handtekeningNaam) throws Exception {
        log.info("java.io.tmpdir : " + System.getProperty("java.io.tmpdir"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        makeSchuldvorderingPdf(schuldvordering, handtekeningNaam, out);
        return out.toByteArray();
    }


    private void makeSchuldvorderingPdf(Schuldvordering schuldvordering, String handtekeningNaam, OutputStream outputStream) throws Exception {

        SchuldvorderingExportData schuldvorderingData = createSchuldvorderingExportData(schuldvordering, handtekeningNaam);

        byte[] pdfBytes = buildPdf(schuldvorderingData);

        if (schuldvordering.getAanvraagSchuldvordering() != null) {
            byte[] meetstaatfile = aanvraagSchuldvorderingExportService.createMeetstaatExportFile(schuldvordering.getAanvraagSchuldvordering());
            PdfMerger.merge(new ByteArrayInputStream(pdfBytes), new ByteArrayInputStream(meetstaatfile), outputStream);
        } else if (StringUtils.isNotEmpty(schuldvordering.getBrief().getDms_id())) {
            InputStream inputStream = briefService.getFileBrief(schuldvordering.getBrief());
            PdfMerger.merge(new ByteArrayInputStream(pdfBytes), inputStream, outputStream);
        } else {
            StreamUtils.copy(new ByteArrayInputStream(pdfBytes), outputStream);
        }

    }

    private byte[] buildPdf(SchuldvorderingExportData schuldvorderingData) throws Exception {
        IImageProvider handtekening = getHandtekening(schuldvorderingData.isDraft());

        IXDocReport report = loadReportTemplate(schuldvorderingData.isGoedgekeurd());

        FieldsMetadata metadata = report.createFieldsMetadata();
        metadata.addFieldAsImage("handtekening1");
        metadata.addFieldAsImage("handtekening2");

        IContext context = report.createContext();
        context.put("schuldvordering", schuldvorderingData);
        context.put("projecten", schuldvorderingData.getProjecten());
        context.put("handtekening1", handtekening);
        context.put("handtekening2", handtekening);

        File tempdir = Files.createTempDir();

        File odtFile = File.createTempFile("odtFile", ".odt", tempdir);

        FileOutputStream fileOutputStreamOdt = new FileOutputStream(odtFile);
        report.process(context, fileOutputStreamOdt);
        fileOutputStreamOdt.close();

        File pdfFile = File.createTempFile("intro", ".pdf", tempdir);
        esbService.convertToPDF(odtFile, pdfFile);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamUtils.copy(new FileInputStream(pdfFile), baos);

        byte[] result = baos.toByteArray();

        tempdir.delete();

        return result;
    }

    private IImageProvider getHandtekening(Boolean isDraft) throws ExecutionException {
        IImageProvider handtekening;
        if (isDraft) {
            handtekening = handtekeningenService.getDraft();
        } else {
            handtekening = handtekeningenService.getHandtekenning();
        }
        handtekening.setUseImageSize(true);
        handtekening.setHeight(75f);
        handtekening.setResize(true);
        return handtekening;
    }

    private IXDocReport loadReportTemplate(boolean isGoedgekeurd) throws ExecutionException, IOException, XDocReportException {
        InputStream in;
        if (isGoedgekeurd) {
            in = briefTemplateService.getTemplate("IU1-schuldvordering_brief_pv_metWbs.odt");
        } else {
            in = briefTemplateService.getTemplate("IU1a-AfkeuringSchuldvordering.odt");
        }
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
        return report;
    }


    private SchuldvorderingExportData createSchuldvorderingExportData(Schuldvordering schuldvordering, String handtekeningNaam) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("nl"));
        SimpleDateFormat korteDateFormat = new SimpleDateFormat("dd.MM.yyyy", new Locale("nl"));

        SchuldvorderingExportData schuldvorderingExportData = new SchuldvorderingExportData();

        schuldvorderingExportData.setHandtekeningNaam(handtekeningNaam);
        schuldvorderingExportData.setAfgekeurd_jn(schuldvordering.getAfgekeurd_jn());

        if (schuldvordering.isGoedgekeurd()) {
            if (schuldvordering.isAangepast()) {
                schuldvorderingExportData.setStatus("gedeeltelijke goedkeuring");
            } else {
                schuldvorderingExportData.setStatus("goedkeuring");
            }
        }
        schuldvorderingExportData.setVorderingNr(schuldvordering.getNummer());
        
        schuldvorderingExportData.setGoedkeuringDatum(simpleDateFormat.format(schuldvordering.getVordering_d()));
        schuldvorderingExportData.setVorderingDatum(korteDateFormat.format(schuldvordering.getVordering_d()));
        
        schuldvorderingExportData.setUitesrsteBetalingsDatum(simpleDateFormat.format(schuldvordering.getUiterste_d()));

        schuldvorderingExportData.setOpmerking(schuldvordering.getCommentaar());
        if (schuldvordering.getVan_d() != null) {
            schuldvorderingExportData.setVanDatum(simpleDateFormat.format(schuldvordering.getVan_d()));
        }
        if (schuldvordering.getTot_d() != null) {
            schuldvorderingExportData.setTotDatum(simpleDateFormat.format(schuldvordering.getTot_d()));
        }
        
        
        schuldvorderingExportData.setBoetebedrag(BigDecimalFormatter.formatCurencyFull(schuldvordering.getBoete_bedrag()));
        
        // change request dd 08/09/2015 :  op vraag van ddamman
        //   op Proces-verbaal van gedeeltelijke schuldvordering
        //   met 'Bedrag van de schuldvordering'   =  vordering + herziening (zonder met correcties rekening te houden !!!)
        //    vb   vordering = 10     corr_vordering = 11
        //         herziening = 3     corr_herziening = 5
        //   --> 'Bedrag van de schuldvordering' = 13    en niet 16 zoals voor de aanpassing
                        
        //Double effectief_vordering_bedrag;
        //Double vordering_correct_bedrag = schuldvordering.getVordering_correct_bedrag();
        //if (vordering_correct_bedrag == null) {
        //    effectief_vordering_bedrag = schuldvordering.getVordering_bedrag();
        //} else {
        //    effectief_vordering_bedrag = vordering_correct_bedrag;
        //}
        //Double herziening_correct_bedrag = schuldvordering.getHerziening_correct_bedrag();
        //if (herziening_correct_bedrag != null) {
        //    effectief_vordering_bedrag = effectief_vordering_bedrag + herziening_correct_bedrag;
        //} else {
        //    Double herziening_bedrag = schuldvordering.getHerziening_bedrag();
        //    if (herziening_bedrag != null) {
        //        effectief_vordering_bedrag = effectief_vordering_bedrag + herziening_bedrag;
        //    }
        //}
        // schuldvorderingExportData.setVorderingBedrag(BigDecimalFormatter.formatCurencyFull(effectief_vordering_bedrag));

        Double vordering_bedrag = schuldvordering.getVordering_bedrag();
        Double herziening_bedrag = schuldvordering.getHerziening_bedrag();
        if (herziening_bedrag != null) {
            vordering_bedrag = vordering_bedrag + herziening_bedrag;
        }
        schuldvorderingExportData.setVorderingBedrag(BigDecimalFormatter.formatCurencyFull(vordering_bedrag));
        
        // einde change request dd 08/09/2015 
        
        
        schuldvorderingExportData.setGoedkeuringBedrag(BigDecimalFormatter.formatCurencyFull(schuldvordering.getGoedkeuring_bedrag()));

        schuldvorderingExportData.setLeveranciersReferentie(""); //voorlopig niet gebruikt. (dd. 2014-10-23)
        
        schuldvorderingExportData.setWbs(schuldvordering.getWbs_nr());
        
        
        BriefCategorie briefCategorie = schuldvordering.getBriefCategorie();
        if (briefCategorie == null) {
            briefCategorie = briefCategorieService.getBasicFase();
        }
        schuldvorderingExportData.setFase(briefCategorie.getBeschrijving());
        
        
        
        if (schuldvordering.getAntwoordPDF() != null) {
            schuldvorderingExportData.setBriefNr(schuldvordering.getAntwoordPDF().getBrief_nr());
        }

        
        
        // gegevens van Dossier
        //   (Indien deelopdracht , het dossier van de deelopdracht)
        /////////////////////////////////////////////////////////////////////////////////////////////
        
        
        Dossier dossier = null; 
        if (schuldvordering.getDeelOpdracht() != null) {
            dossier = schuldvordering.getDeelOpdracht().getDossier();
        } else {
            dossier = schuldvordering.getBestek().getDossier();
        }
        
        if (dossier.getGemeente() != null) {
            schuldvorderingExportData.setProjectGemeente(dossier.getGemeente().getNaam());
        }
        
        schuldvorderingExportData.setDossierOmschrijving(dossier.getDossier_b());

        Ambtenaar ambtenaar = null;
        if (dossier.getDossierhouder() != null) {
            ambtenaar = dossier.getDossierhouder().getAmbtenaar();
        }
        if (ambtenaar != null) {
            schuldvorderingExportData.setAuteur(ambtenaar.getAmbtenaar_b());
            schuldvorderingExportData.setAuteurEmail(ambtenaar.getEmail());
            schuldvorderingExportData.setAuteurTelefoonnummer(ambtenaar.getTel1());
        }

        
        // gegevens van bestek
        ////////////////////////////////////////////////////////////////////////////////////////////////
        
        schuldvorderingExportData.setBestekId(schuldvordering.getBestek().getBestek_id());
        schuldvorderingExportData.setBestekNr(schuldvordering.getBestek().getBestek_nr());
        schuldvorderingExportData.setBestekOmschrijving(schuldvordering.getBestek().getOmschrijving());
        schuldvorderingExportData.setDienstType(mapDienstTyp(schuldvordering.getBestek().getType_id()));

        
        
        if (schuldvordering.getBrief() != null) {
            addBriefInfo(schuldvorderingExportData, schuldvordering.getBrief());
        } else {
            addBriefInfo(schuldvorderingExportData, schuldvordering.getAanvraagSchuldvordering().getOfferte().getBrief());
        }
            
            
//        if (schuldvordering.getBrief() == null) {
//            addDigitaleAanvraagInfo(schuldvorderingExportData, schuldvordering.getAanvraagSchuldvordering());
//        }
        
        
        
        schuldvorderingExportData.setProjecten(copy(schuldvordering.getSchuldVorderingSapProjects()));
        
        
        
        
        Ambtenaar ondertekenaar = gebruikerService.getHuidigAmbtenaar();
        schuldvorderingExportData.setOndertekenaarNaam(ondertekenaar.getAmbtenaar_b());
        schuldvorderingExportData.setOndertekenaarFunctie(ondertekenaar.getFunctie());
        if (StringUtils.equals(uidAfdelingshoofdIvs, ondertekenaar.getAmbtenaar_id())) {
            schuldvorderingExportData.setOndertekendDoorDiensthoofd(Boolean.TRUE);
        } else {
            schuldvorderingExportData.setOndertekendDoorDiensthoofd(Boolean.FALSE);
        }
        Calendar vandaagPlus7 = Calendar.getInstance();
        vandaagPlus7.add(Calendar.DATE, 7);
        if (schuldvordering.getUiterste_d().before(vandaagPlus7.getTime())) {
            schuldvorderingExportData.setOpTijd(Boolean.FALSE);
        } else {
            schuldvorderingExportData.setOpTijd(Boolean.TRUE);
        }
        
        
        
        return schuldvorderingExportData;
    }

    private String mapOpdrachtnemersType(Integer type_id) {
        if (type_id == 1) return "Aannemer";
        if (type_id == 5) return "Leverancie";
        if (type_id == 6) return "Dienstverlener";
        return "Dienstverlener";
    }

    private String mapDienstTyp(Integer type_id) {
        if (type_id == 1) return "werken";
        if (type_id == 5) return "leveringen";
        if (type_id == 6) return "diensten";
        return "diensten";
    }

    private List<SchuldvorderingSapProjectExportData> copy(List<SchuldVorderingSapProject> schuldVorderingSapProjects) {
        List<SchuldvorderingSapProjectExportData> schuldvorderingSapProjectExportDatas = new ArrayList<SchuldvorderingSapProjectExportData>();
        int i = 1;
        for (SchuldVorderingSapProject schuldVorderingSapProject : schuldVorderingSapProjects) {
            SchuldvorderingSapProjectExportData schuldvorderingSapProjectExportData = new SchuldvorderingSapProjectExportData();
            schuldvorderingSapProjectExportData.setNummer(Integer.toString(i));
            schuldvorderingSapProjectExportData.setWbsNummer(schuldVorderingSapProject.getWbsNr());
            schuldvorderingSapProjectExportData.setBedrag(BigDecimalFormatter.formatCurencyFull(schuldVorderingSapProject.getBedrag()));
            if (schuldVorderingSapProject.getSapProject() != null) {
                schuldvorderingSapProjectExportData.setProjectnummer(schuldVorderingSapProject.getSapProject().getProjectId());
                schuldvorderingSapProjectExportData.setBudgetairArtikel(schuldVorderingSapProject.getSapProject().getBudgetairArtikel());
                schuldvorderingSapProjectExportData.setBoekJaar(schuldVorderingSapProject.getSapProject().getBoekJaar());
                schuldvorderingSapProjectExportData.setInitieelAchtNummer(schuldVorderingSapProject.getSapProject().getInitieelAchtNummer());
            }
            schuldvorderingSapProjectExportDatas.add(schuldvorderingSapProjectExportData);
            i++;
        }
        return schuldvorderingSapProjectExportDatas;
    }


    private void addBriefInfo(SchuldvorderingExportData schuldvorderingExportData, Brief brief) {
        schuldvorderingExportData.setPostcode(brief.getAdres().getPostcode());
        schuldvorderingExportData.setGemeente(brief.getAdres().getGemeente());
        if (brief.getAdres().getAdresLand() != null) {
            schuldvorderingExportData.setLand(brief.getAdres().getAdresLand().getLand_b());
        } else {
            schuldvorderingExportData.setLand(brief.getAdres().getLand());
        }
        schuldvorderingExportData.setStraat(brief.getAdres().getStraat());
        schuldvorderingExportData.setOpdrachtnemersNaam(brief.getAdres().getNaam());
    }

//    public void addDigitaleAanvraagInfo(SchuldvorderingExportData schuldvorderingExportData, AanvraagSchuldvordering aanvraagSchuldvordering) {
//        schuldvorderingExportData.setLeveranciersReferentie(aanvraagSchuldvordering.getBeschrijving());
//    }

    public void setBriefService(BriefService briefService) {
        this.briefService = briefService;
    }

    public void setAanvraagSchuldvorderingExportService(AanvraagSchuldvorderingExportService aanvraagSchuldvorderingExportService) {
        this.aanvraagSchuldvorderingExportService = aanvraagSchuldvorderingExportService;
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    public void setEsbService(EsbServiceImpl esbService) {
        this.esbService = esbService;
    }

}
