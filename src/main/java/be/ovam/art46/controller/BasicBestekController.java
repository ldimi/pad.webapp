package be.ovam.art46.controller;

import be.ovam.art46.model.BestekDO;
import be.ovam.art46.service.BestekService;
import be.ovam.art46.service.dossier.DossierService;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Created by Koen Corstjens on 29-8-13.
 */
public class BasicBestekController {
    public static final String MODEL_ATTRIBUTE_NAME_BINDING_RESULT = "bindingResult";
    public static final String MODEL_ATTRIBUTE_NAME_BESTEK_ID = "bestekId";
    public static final String MODEL_ATTRIBUTE_NAME_BESTEK_NR = "bestekNr";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_ID = "dossierId";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_TYPE = "dossier_type";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_NR = "dossierNr";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_URL = "dossierUrl";

    private int autoGrowCollectionLimit = 2048;

    @InitBinder
    public void initListBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(autoGrowCollectionLimit);
    }


    @Autowired
    protected BestekService bestekService;

    @Autowired
    protected DossierService dossierService;
    
    @Autowired
    @Qualifier("sqlSession")
    protected SqlSession sql;

    protected BestekDO bestekDO;
    
    protected Integer dossierId;
    protected String dossier_type;
    protected String dossierNr;
    protected Long bestekId;
    protected String dossierUrl;
    protected String bestekNr;


    public void startBasic(Long bestekId, Model model) throws Exception {
        
        bestekDO = sql.selectOne("getBestek", bestekId);
        
        
        this.bestekId = bestekId;
        dossierId = bestekDO.getDossier_id();
        bestekNr = bestekDO.getBestek_nr();
        dossier_type = bestekDO.getDossier_type();
        
        dossierNr = bestekDO.getDossier_nr();
        dossierUrl = "dossierdetailsArt46.do?dossier_nr=" + dossierNr;
        
        model.addAttribute(MODEL_ATTRIBUTE_NAME_BESTEK_ID, bestekId);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_BESTEK_NR, bestekNr);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_ID, dossierId);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_TYPE,dossier_type);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_NR, dossierNr);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_URL, dossierUrl);
                
        model.addAttribute("bestekDO", bestekDO);
        model.addAttribute("custom_title", "Bestek " + bestekNr);
    }

}
