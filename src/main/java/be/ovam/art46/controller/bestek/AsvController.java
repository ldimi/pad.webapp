package be.ovam.art46.controller.bestek;

import be.ovam.art46.controller.BasicBestekController;
import be.ovam.art46.service.BriefCategorieService;
import be.ovam.art46.service.schuldvordering.AsvService;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.art46.util.OvamCustomNumberEditor;
import be.ovam.pad.model.AsvDO;
import be.ovam.pad.model.BriefCategorie;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Koen on 25/01/14.
 */
@Controller
public class AsvController extends BasicBestekController {
    private static final String MODEL_ATTRIBUTE_ASV = "asvDO";
    private static final String MODEL_ATTRIBUTE_NAME_BTW_TARIEVEN = "btwTarieven";
    private static final String MODEL_ATTRIBUTE_SCHULDVORDERING_FASEN = "schuldvorderingFasen";
    private static final String MODEL_ATTRIBUTE_STATUS_HISTORIEK = "status_historiek";
    private static final String MODEL_ATTRIBUTE_BIJLAGEN = "bijlagen";

    @Autowired
    private AsvService asvService;

    @Autowired
    @Qualifier("sqlSession")
    SqlSession sql;

    @Autowired
    private BriefCategorieService briefcategorieService;
    @Autowired
    protected Validator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BriefCategorie.class, "briefCategorie", new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) {
                BriefCategorie ch = briefcategorieService.get(Integer.parseInt(text));
                setValue(ch);
            }
        });
        binder.setValidator(validator);
        binder.registerCustomEditor(BigDecimal.class, new OvamCustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(Double.class, new OvamCustomNumberEditor(Double.class, true));
    }
    
    @ModelAttribute
    public void addModelAttributes(@PathVariable Long aanvraagId, Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_SCHULDVORDERING_FASEN, briefcategorieService.getFasenForSchuldvordering());
        model.addAttribute(MODEL_ATTRIBUTE_NAME_BTW_TARIEVEN, DropDownHelper.getBtwTarieven());
        
        AsvDO asv = asvService.getAsv(aanvraagId);
        model.addAttribute(MODEL_ATTRIBUTE_ASV, asv);
        
        model.addAttribute(MODEL_ATTRIBUTE_STATUS_HISTORIEK,
                sql.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getSchuldvorderingStatusHistoryLijst", asv.getVordering_id()));
        model.addAttribute(MODEL_ATTRIBUTE_BIJLAGEN,
                sql.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getAanvrSchuldvorderingBijlagen", asv.getAanvr_schuldvordering_id()));
    }


    @RequestMapping(value = "/bestek/{bestekId}/aanvraagSchuldvordering/{aanvraagId}", method = RequestMethod.POST)
    public String save(@ModelAttribute AsvDO asvDO,
                        @PathVariable Long bestekId,
                        @PathVariable Long aanvraagId,
                        @RequestParam String action,
                        BindingResult result,
                        Principal principal) throws Exception {
        ValidationUtils.rejectIfEmptyOrWhitespace(result, "schuldvordering_fase_id", "schuldvordering_fase_id.empty");
        if (result.hasErrors()) {
            return "bestek.meetstaat.aanvraag.schuldvordering";
        }
//        if (action.equals("Accepteren")) {
//            if(StringUtils.isNotEmpty(aanvraagSchuldvordering.getSchuldvordering().getWbs_nr())){
//                aanvraagSchuldvordering.getSchuldvordering().setStatus(SchuldvorderingStatusEnum.BEOORDEELD.getValue());
//            }
//        } else if (action.equals("Afkeuren")) {
//            aanvraagSchuldvordering.getSchuldvordering().setAfgekeurd_jn("J");
//            aanvraagSchuldvordering.getSchuldvordering().setStatus(SchuldvorderingStatusEnum.BEOORDEELD.getValue());
//        }
        
        long aanvr_schuldvordering_id = asvService.save(action, asvDO, principal.getName());
        if(action.equals("Accepteren") || action.equals("Afkeuren") ){
            return "redirect:/s//bestek/"+bestekId+"/schuldvorderingen/" + asvDO.getVordering_id();
        }
        return "redirect:/s/bestek/{bestekId}/aanvraagSchuldvordering/" + aanvr_schuldvordering_id;
    }


    @RequestMapping(value = "/bestek/{bestekId}/aanvraagSchuldvordering/{aanvraagId}", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, @PathVariable Long aanvraagId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        model.addAttribute("aanvraagId", aanvraagId);
        return "bestek.meetstaat.aanvraag.schuldvordering";
    }
    
}
