package be.ovam.art46.service.schuldvordering;

import be.ovam.art46.common.mail.MailService;
import be.ovam.art46.dao.SchuldvorderingDAO;
import be.ovam.art46.model.SchuldvorderingDO;
import be.ovam.art46.model.SchuldvorderingData;
import be.ovam.art46.service.BriefCategorieService;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.BudgetRestService;
import be.ovam.art46.service.EsbService;
import be.ovam.art46.util.Application;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.Schuldvordering;
import be.ovam.pad.model.SchuldvorderingStatusEnum;
import be.ovam.pad.model.SchuldvorderingStatusHistoryDO;
import be.ovam.util.mybatis.SqlSession;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;


@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SchuldvorderingServiceImpl implements SchuldvorderingService {

    @Autowired
    protected SessionFactory sessionFactory;
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;
    @Autowired
    private SchuldvorderingDAO schuldvorderingDao;
    @Autowired
    private SchuldvorderingExportService schuldvorderingExportService;
    @Autowired
    private AanvraagSchuldvorderingExportService aanvraagSchuldvorderingExportService;
    @Autowired
    private BriefCategorieService briefCategorieService;
    @Autowired
    private BriefService briefService;
    @Autowired
    private MailService mailService;
    @Autowired
    private EsbService esbService;
    @Autowired
    private BudgetRestService budgetRestService;

    @Value("${ovam.webloket.url}")
    private String urlWebloket;

    private Logger log = Logger.getLogger(SchuldvorderingServiceImpl.class);


    public SchuldvorderingData save(SchuldvorderingDO schuldvorderingDO) throws Exception {
        if (schuldvorderingDO.getVordering_id() == null) {
            sqlSession.insertInTable("art46", "schuldvordering", schuldvorderingDO);

            createStatusHistory(schuldvorderingDO.getVordering_id(), SchuldvorderingStatusEnum.INGEDIEND.getValue(), null);
        } else {
            sqlSession.updateInTable("art46", "schuldvordering", schuldvorderingDO);
        }
        return this.getSchuldvorderingData(schuldvorderingDO.getVordering_id());
    }

    public SchuldvorderingData afkeuren(SchuldvorderingDO schuldvorderingDO) throws Exception {
        schuldvorderingDO.setAfgekeurd_jn("J");
        schuldvorderingDO.setGoedkeuring_bedrag(null);
        schuldvorderingDO.setAcceptatie_d(new Date());
        schuldvorderingDO.setStatus(SchuldvorderingStatusEnum.BEOORDEELD.getValue());

        //sqlSession.update("be.ovam.art46.mappers.SchuldvorderingMapper.updateSvDO", schuldvorderingDO);
        sqlSession.updateInTable("art46", "schuldvordering", schuldvorderingDO);
        createStatusHistory(schuldvorderingDO.getVordering_id(), SchuldvorderingStatusEnum.BEOORDEELD.getValue(), null);

        return this.getSchuldvorderingData(schuldvorderingDO.getVordering_id());
    }


    public SchuldvorderingData goedkeuren(SchuldvorderingDO schuldvorderingDO) throws Exception {
        schuldvorderingDO.setAcceptatie_d(new Date());
        schuldvorderingDO.setStatus(SchuldvorderingStatusEnum.BEOORDEELD.getValue());

        sqlSession.updateInTable("art46", "schuldvordering", schuldvorderingDO);
        //sqlSession.update("be.ovam.art46.mappers.SchuldvorderingMapper.updateSvDO", schuldvorderingDO);
        createStatusHistory(schuldvorderingDO.getVordering_id(), SchuldvorderingStatusEnum.BEOORDEELD.getValue(), null);

        return this.getSchuldvorderingData(schuldvorderingDO.getVordering_id());
    }


    public void deleteSchuldvordering(Integer vordering_id) {
        sqlSession.deleteInTable("art46", "schuldvordering", "vordering_id", vordering_id);
    }

    public void verwijderWbs(Integer vordering_id) throws Exception {
        SchuldvorderingDO svDO = this.getSchuldvorderingDO(vordering_id);
        sqlSession.delete("be.ovam.art46.mappers.SchuldvorderingMapper.verwijderWbs", vordering_id);
        sqlSession.update("be.ovam.art46.mappers.SchuldvorderingMapper.updateBijVerwijderWbs", vordering_id);
        // verwijderen en delete van uitgaande brieven.
        if (svDO.getAntwoord_pdf_brief_id() != null) {
            briefService.deleteBrief(svDO.getAntwoord_pdf_brief_id());
        }
        budgetRestService.annuleer(new Long(vordering_id));

    }

    public List<Schuldvordering> getForBestek(Long bestekId) {
        return schuldvorderingDao.getForBestek(bestekId);
    }


    public List<Schuldvordering> getTeKeurenSchuldvorderingen() {
        return schuldvorderingDao.getTeKeurenSchuldvorderingen();
    }

    public void ondertekenen(Integer schuldvorderingId, String ondertekenaar) throws Exception {

        log.info("start Ondertekenen van schuldvordering :" + schuldvorderingId + ", ondertekenaar : " + ondertekenaar);

        SchuldvorderingDO svDO = this.getSchuldvorderingDO(schuldvorderingId);

        log.info("status van schuldvordering wordt 'ONDERTEKEND'");
        svDO.setGoedkeuring_d(new Date());
        svDO.setStatus(SchuldvorderingStatusEnum.ONDERTEKEND.getValue());
        createStatusHistory(schuldvorderingId, svDO.getStatus(), null);

        // hibernate mag alleen lezen, save met mybatis ...
        //   beter zou immutable data doorgegegeven worden.
        sessionFactory.getCurrentSession().setDefaultReadOnly(true);

        Schuldvordering schuldvordering = schuldvorderingDao.get(schuldvorderingId);
        Integer antwoord_pdf_brief_id = this.generatePdf(schuldvordering, ondertekenaar);
        svDO.setAntwoord_pdf_brief_id(antwoord_pdf_brief_id);

        sqlSession.updateInTable("art46", "schuldvordering", svDO);

        if (svDO.getAanvr_schuldvordering_id() != null) {

            log.info("status van schuldvordering wordt 'VERZONDEN' (digitaal traject)");
            svDO.setStatus(SchuldvorderingStatusEnum.VERZONDEN.getValue());
            sqlSession.updateInTable("art46", "schuldvordering", svDO);
            createStatusHistory(schuldvorderingId, svDO.getStatus(), null);

            log.info("schulvordering gegevens worden naar buget verzonden");
            budgetRestService.verzendSchuldvordering(svDO.getVordering_id());

            if (svDO.getWebloket_gebruiker_email() != null) {
                log.info("Er wordt een mail naar de inzender van de aanvraag verstuurd");
                String aan = svDO.getWebloket_gebruiker_email();
                String onderwerp = "Uw schuldvordering " + svDO.getSchuldvordering_nr() + " is beoordeeld.";
                String van = svDO.getContact_doss_hdr_email();
                String link = urlWebloket + "webloket/offerte/" + svDO.getOfferte_id() +
                        "/aanvraagSchuldvordering/" + svDO.getAanvr_schuldvordering_id();
                StringBuilder inhoud = new StringBuilder()
                        .append(getOmgevingWarning())
                        .append("Uw schuldvordering ").append(svDO.getSchuldvordering_nr()).append(" is beoordeeld: \n")
                        .append(link);

                // mail wordt niet verzonden in ontwikkel omgeving ...
                String omgeving = System.getProperty("ovam.omgeving");
                if (!"ontwikkeling".equals(omgeving)) {
                    mailService.sendHTMLMail(aan, onderwerp, van, inhoud.toString());
                    log.info("mail verzonden aan : " + aan);
                } else {
                    log.info("[omgeving = ontwikkeling] geen mail verzonden van : " + van + ",  aan : " + aan + ", link : " + link);
                }
            } else {
                throw new RuntimeException("Geen webloket_gebruiker gekoppeld aan " + svDO.getSchuldvordering_nr());
            }


        }
    }

    private Integer generatePdf(Schuldvordering schuldvordering, String ondertekenaar) throws Exception {

        String commentaar = "Antwoord schuldvordering " + schuldvordering.getVordering_id();
        String auteur;
        if (schuldvordering.getDeelOpdracht() != null) {
            auteur = schuldvordering.getDeelOpdracht().getDossier().getDoss_hdr_id();
        } else {
            auteur = schuldvordering.getBestek().getDossier().getDoss_hdr_id();
        }

        if (schuldvordering.getSchuldvordering_fase_id() == null) {
            schuldvordering.setSchuldvordering_fase_id(12);
        }

        Brief antwoordPDF = briefService.makeDocument(schuldvordering, auteur, commentaar);


        schuldvordering.setAntwoordPDF(antwoordPDF);

        String filenaam = "Antwoord_schuldvordering_" + schuldvordering.getNummer() + ".pdf";
        byte[] pdfBytes = schuldvorderingExportService.makeSchuldvorderingPdf(schuldvordering, ondertekenaar);
        briefService.uploadBrief(antwoordPDF.getBrief_id(), pdfBytes, filenaam);

        return antwoordPDF.getBrief_id();
    }


    public Schuldvordering get(Integer schuldvorderingId) {
        return schuldvorderingDao.get(schuldvorderingId);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public SchuldvorderingData getSchuldvorderingData(Integer vordering_id) {
        SchuldvorderingData result = new SchuldvorderingData();

        SchuldvorderingDO schuldvorderingDO = this.getSchuldvorderingDO(vordering_id);
        result.setSchuldvordering(schuldvorderingDO);

        // 0, 1 of 2 vastleggingen waaraan de schuldvordering gekoppeld is.
        List svProjecten = (List) sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getSvProjectDOs", vordering_id);
        result.setProjecten(svProjecten);

        // alleen voor een schuldvordering van type 'X' (ander dossier) moet eventueel een deelOpdracht geselecteerd worden.
        if ("X".equals(schuldvorderingDO.getDossier_type())) {
            result.setDeelopdrachten(this.getDeelopdrachten(schuldvorderingDO.getBestek_id(), schuldvorderingDO.getVordering_id()));
        }

        // lijst van vastleggingen waaraan de schuldvordering kan gekoppeld worden.
        if (svProjecten == null || svProjecten.size() == 0) {
            // alleen indien geen vastleggingen gekoppeld aan de schuldvordering moet de selectie lijst opgehaald worden.
            result.setVastleggingen(sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getVastleggingenCombo", vordering_id));
        }

        result.setFacturen((List) sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getSvFactuurDOs", vordering_id));

        result.setBriefcategorieLijst(sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.briefcategorieLijst", null));

        return result;
    }

    private SchuldvorderingDO getSchuldvorderingDO(Integer vordering_id) {
        return (SchuldvorderingDO) sqlSession.selectOne("be.ovam.art46.mappers.SchuldvorderingMapper.getSvDO", vordering_id);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public SchuldvorderingData getNieuwSchuldvorderingData(Integer brief_id, Date vordering_d) {
        SchuldvorderingData result = new SchuldvorderingData();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("brief_id", brief_id);
        params.put("vordering_d", vordering_d);
        SchuldvorderingDO schuldvorderingDO = (SchuldvorderingDO) sqlSession.selectOne("be.ovam.art46.mappers.SchuldvorderingMapper.getNieuwSvDO", params);
        result.setSchuldvordering(schuldvorderingDO);
        result.setProjecten(new ArrayList());
        // alleen voor een schuldvordering van type 'X' (ander dossier) moet eventueel een deelOpdracht geselecteerd worden.
        if ("X".equals(schuldvorderingDO.getDossier_type())) {
            result.setDeelopdrachten(this.getDeelopdrachten(schuldvorderingDO.getBestek_id(), schuldvorderingDO.getVordering_id()));
        }

        result.setFacturen(new ArrayList());

        result.setBriefcategorieLijst(sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.briefcategorieLijst", null));

        return result;
    }

    @SuppressWarnings("rawtypes")
    private List getDeelopdrachten(Long bestek_id, Integer vordering_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestek_id", bestek_id);
        params.put("vordering_id", vordering_id);
        return sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getDeelOpdrachtenCombo", params);
    }

    public void terugNaarDossierhouder(Integer id, String motivatie) {
        Schuldvordering schuldvordering = schuldvorderingDao.get(id);
        schuldvordering.setGoedkeuring_d(null);
        schuldvordering.setAcceptatieDatum(null);
        schuldvordering.setStatus(SchuldvorderingStatusEnum.INGEDIEND.getValue());
        schuldvordering.setAfgekeurd_jn("N");
        schuldvorderingDao.save(schuldvordering);
        createStatusHistory(schuldvordering.getVordering_id(), schuldvordering.getStatus(), motivatie);
    }

    private void createStatusHistory(Integer vordering_id, String status, String motivatie) {
        SchuldvorderingStatusHistoryDO sshdo = new SchuldvorderingStatusHistoryDO();
        sshdo.setSchuldvordering_id(vordering_id);
        sshdo.setDossierhouder_id(Application.INSTANCE.getUser().getUser_id());
        sshdo.setDatum(new Date());
        sshdo.setMotivatie(motivatie);
        sshdo.setStatus(status);

        sqlSession.insertInTable("art46", "schuldvordering_status_history", sshdo);
    }

    public List<Schuldvordering> getTeScannenSchuldvorderingen() {
        List<Schuldvordering> schuldvorderingen = schuldvorderingDao.getTeScannenSchuldvorderingen();
        return schuldvorderingen;
    }

    public String addScan(Integer schuldvorderingId, MultipartFile multipartFile) throws Exception {
        if (!StringUtils.equals(multipartFile.getContentType(), "application/pdf")) {
            return multipartFile.getOriginalFilename() + " is geen PDF bestand";
        }
        Schuldvordering schuldvordering = get(schuldvorderingId);
        Brief brief = schuldvordering.getBrief();
        String commentaar = "Scan aanvraag schuldvordering " + schuldvordering.getNummer();
        brief.setCommentaar(commentaar);
        briefService.uploadBrief(brief.getBrief_id(), multipartFile.getBytes(), "scan" + schuldvordering.getNummer() + ".pdf");
        return multipartFile.getOriginalFilename() + " is correct opgeladen voor schuldvordering " + schuldvordering.getNummer();
    }

    public List<Schuldvordering> getTePrintenSchuldvorderingen() {
        List<Schuldvordering> schuldvorderingen = schuldvorderingDao.getTePrintenSchuldvorderingen();
        return schuldvorderingen;
    }

    public void isAfgedrukt(Integer id) {
        Schuldvordering schuldvordering = schuldvorderingDao.get(id);
        if (schuldvordering.getBrief().getDms_id() != null) {
            schuldvordering.setStatus(SchuldvorderingStatusEnum.VERZONDEN.getValue());
            schuldvordering.setPrintDate(Calendar.getInstance());
            budgetRestService.verzendSchuldvordering(schuldvordering.getVordering_id());
            createStatusHistory(schuldvordering.getVordering_id(), schuldvordering.getStatus(), null);
        }
        // else : no action , blijft in lijst zitten,
        //                    Er is geen meetstaat opgeladen, en de oorzaak hiervan moet best onderzocht worden.
    }

    public void save(Schuldvordering schuldvordering) {
        schuldvorderingDao.save(schuldvordering);
    }

    public String handtekenningBeschikbaar() {
        String result;
        try {
            result = schuldvorderingExportService.handtekenningBeschikbaar();
        } catch (Exception e) {
            log.error(e, e);
            result = e.getStackTrace().toString();
        }
        return result;
    }

    public boolean print(Integer vordering_id) {
        Schuldvordering schuldvordering = get(vordering_id);
        return esbService.print(schuldvordering.getAntwoordPDF().getDms_id(), Application.INSTANCE.getUser_id(), schuldvordering.getNummer());
    }

    private String getOmgevingWarning() {
        String omgeving = System.getProperty("ovam.omgeving");
        if (omgeving == null || omgeving.equals("productie")) {
            // default  productie , geen waarschuwings text. 
            return "";
        } else {
            return "[DIT IS EEN TEST ! vanuit de " + omgeving + " omgeving] ";
        }
    }


}
