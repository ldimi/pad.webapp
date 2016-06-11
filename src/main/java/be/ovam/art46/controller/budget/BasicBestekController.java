package be.ovam.art46.controller.budget;

import be.ovam.art46.model.BestekDO;
import be.ovam.art46.service.BestekService;
import be.ovam.art46.service.dossier.DossierService;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BasicBestekController {
    public static final String MODEL_ATTRIBUTE_NAME_BINDING_RESULT = "bindingResult";
    public static final String MODEL_ATTRIBUTE_NAME_BESTEK_NR = "bestekNr";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_ID = "dossierId";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_TYPE = "dossier_type";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_NR = "dossierNr";
    public static final String MODEL_ATTRIBUTE_NAME_DOSSIER_URL = "dossierUrl";

    private final int autoGrowCollectionLimit = 2048;

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
    
    public void startBasic(Long bestek_id, Model model) throws Exception {
        
        bestekDO = sql.selectOne("be.ovam.art46.mappers.BestekMapper.getBestek", bestek_id);
        
        
        String bestek_nr = bestekDO.getBestek_nr();
        String dossier_nr = bestekDO.getDossier_nr();
        
        model.addAttribute(MODEL_ATTRIBUTE_NAME_BESTEK_NR, bestek_nr);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_ID, bestekDO.getDossier_id());
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_TYPE, bestekDO.getDossier_type());
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_NR, dossier_nr);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_DOSSIER_URL, "dossierdetailsArt46.do?dossier_nr=" + dossier_nr);
                
        model.addAttribute("bestekDO", bestekDO);
        model.addAttribute("custom_title", "Bestek " + bestek_nr);
    }

}
