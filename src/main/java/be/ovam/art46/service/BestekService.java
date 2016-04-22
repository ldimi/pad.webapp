package be.ovam.art46.service;

import be.ovam.art46.common.mail.MailService;
import be.ovam.art46.dao.BestekDAO;
import be.ovam.art46.dao.SAPDAO;
import be.ovam.art46.dao.SchuldvorderingDAO;
import be.ovam.art46.model.BestekDO;
import be.ovam.art46.model.Raming;
import be.ovam.art46.struts.actionform.SAPFactuurDossierForm;
import be.ovam.pad.model.Bestek;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class BestekService {

    private static String pathToAlfresco = System.getProperty("ovam.dms.webdrive.base");
    
    @Autowired
    @Qualifier("sqlSession")
    protected SqlSession sql;

    @Autowired
    private BestekDAO bestekDAO;
    
    @Autowired
    private SchuldvorderingDAO schuldvorderingDAO;
    
    @Autowired
    private SAPDAO sapDAO;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private EsbService esbService;

    public void addFactuurDossier(SAPFactuurDossierForm form) throws Exception {
        sapDAO.addFactuurDossier(form);
    }

    public BigDecimal calculateHerziening(BigDecimal s_klein, BigDecimal s_groot, BigDecimal i_klein, BigDecimal i_groot, BigDecimal a, BigDecimal b) {
        if (s_klein.toString().equals("0.00") && s_groot.toString().equals("0.00") && i_klein.toString().equals("0.00") && i_groot.toString().equals("0.00") && a.toString().equals("0.00") && b.toString().equals("0.00")) {
            return BigDecimal.ONE;
        } else {
            if (!a.toString().equals("0.00") && !b.toString().equals("0.00")) {
                return b.divide(a);
            }
            return BigDecimal.valueOf(0.4).multiply(s_klein).divide(s_groot).add(BigDecimal.valueOf(0.4).multiply(i_klein).divide(i_groot)).add(BigDecimal.valueOf(0.2));
        }
    }

    public void saveOrUpdate(Bestek bestek) throws Exception {
        // update bestaand bestek
        if (bestek.getBestek_id() == null) {
            throw new RuntimeException("creatie van bestek moet via method 'saveBestek(bestekDO)' gebeuren.");
        }
        bestekDAO.saveObject(bestek);
    }


    private String getPadMailAdres() {
        String mailadres = System.getProperty("pad.mailadres");
        if (mailadres == null || mailadres.length() == 0) {
            mailadres = "pad@ovam.be";
        }
        return mailadres;
    }

    private String getMailAdresAfsluitenBestek() {
        String mailadres = System.getProperty("pad.mail.afsluitenBestek");
        if (mailadres == null || mailadres.length() == 0) {
            mailadres = "ctodts@ovam.be";
        }
        return mailadres;
    }

    private void sendEmailAfsluitenBestek(BestekDO bestek) throws Exception {
        List<Map> projecten = sql.selectList("getSAPProjectByBestekId", bestek.getBestek_id());
        String message = "Bestek " + bestek.getBestek_nr() + " werd afgesloten, de volgende projectnummers mogen daarom worden afgesloten: ";
        for (Map project : projecten) {
            message += "<br>   - " + project.get("project_id") + " " + project.get("project_b");
        }
        mailService.sendHTMLMail(this.getMailAdresAfsluitenBestek(), "Afsluiting bestek " + bestek.getBestek_nr(), this.getPadMailAdres(), message);
    }


    public void saveBestekRaming(Integer bestekId, Integer ramingId) throws Exception {
        bestekDAO.saveBestekRaming(bestekId, ramingId);
    }

    public List<Raming> getRamingByBestekId(Long bestekId) {
        return bestekDAO.getRamingByBestekId(bestekId);
    }


    public SchuldvorderingDAO getSchuldvorderingDAO() {
        return schuldvorderingDAO;
    }

    public void setSchuldvorderingDAO(SchuldvorderingDAO schuldvorderingDao) {
        this.schuldvorderingDAO = schuldvorderingDao;
    }

    public SAPDAO getSapDAO() {
        return sapDAO;
    }

    public void setSapDAO(SAPDAO sapDAO) {
        this.sapDAO = sapDAO;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public Bestek getBestek(Long bestekId) {
        return bestekDAO.get(bestekId);
    }

    public Integer saveBestek(BestekDO bestekDO) throws Exception {

        // if (bestekDO.getBestek_nr() != null) {
        if (bestekDO.getBestek_id() != null) {
            //update bestaand bestek
            
            BestekDO bestekDO_org = sql.selectOne("getBestek", bestekDO.getBestek_id());
            if (bestekDO.getAfsluit_d() != null) {
                if (bestekDO_org.getAfsluit_d() == null) {
                    sendEmailAfsluitenBestek(bestekDO);
                }
            }
            sql.updateInTable("art46", "bestek", bestekDO);
        } else {
            // nieuw bestek
            bestekDO.setBestek_nr(bestekDAO.generateBestekNr("J".equals(bestekDO.getRaamcontract_jn())));
            sql.insertInTable("art46", "bestek", bestekDO);
        }
        
        return bestekDO.getBestek_id();
    }

    public void addControle(Long bestekId, MultipartFile multipartFile) throws Exception {
        Bestek bestek = getBestek(bestekId);
        String dossier_nr = bestek.getDossier().getDossier_nr();
        String path = "/Toepassingen/ivs/" + dossier_nr.substring(0, 2) + "/" + dossier_nr + "/" + bestekId;
        File tmpFile = File.createTempFile("upload" + UUID.randomUUID().toString(), "tmp");
        multipartFile.transferTo(tmpFile);
        String dmsId = esbService.uploadFile("admin", tmpFile, path, multipartFile.getOriginalFilename());
        bestek.setControleDmsFolder(pathToAlfresco + path);
        bestek.setControleDmsFileName(multipartFile.getOriginalFilename());
        bestek.setControleDmsId(dmsId);
        saveOrUpdate(bestek);
        tmpFile.delete();
    }
}
