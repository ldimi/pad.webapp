package be.ovam.art46.controller.budget;

import be.ovam.art46.controller.BasicBestekController;
import be.ovam.art46.model.BestekDO;
import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;
import static be.ovam.web.util.JsView.jsview;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by koencorstjens on 20-8-13.
 */
@Controller
public class BestekController extends BasicBestekController {

    private static Logger logger = Logger.getLogger(BestekController.class);

    @Autowired
    private SqlSession ovamcore_sqlSession;


    @RequestMapping(value = "/bestek/{bestekId}", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        return startBasis(bestekId, model);
    }
        
    @RequestMapping(value = "/bestek/{bestekId}/basisgegevens", method = RequestMethod.GET)
    public String startBasis(@PathVariable Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);

        addModelAttrsForBasisScherm(model);
        
        return jsview("bestek.basisgegevens", "bestek/bestekBasisgegevens", model);
    }

    @RequestMapping(value = "/dossier/{dossierId}/bestek/aanmaken", method = RequestMethod.GET)
    public String aanmaken(@PathVariable String dossierId, Model model){
        BestekDO bestekDO = sql.selectOne("getNieuwBestekData", dossierId);
        model.addAttribute("bestekDO", bestekDO);
        
        addModelAttrsForBasisScherm(model);
        
        return jsview("bestek.basisgegevens", "bestek/bestekBasisgegevens", model);
    }

    private void addModelAttrsForBasisScherm(Model model) {
        model.addAttribute("isEditAllowed", (Application.INSTANCE.isUserInRole("adminArt46") ||
                Application.INSTANCE.isUserInRole("adminIVS")      ) );
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        
        model.addAttribute("bestekBodemType_dd", DropDownHelper.INSTANCE.getBestekBodemType());
        model.addAttribute("bestekBodemProcedure_dd", DropDownHelper.INSTANCE.getBestekBodemProcedure());
        model.addAttribute("bestekBodemFase_dd", DropDownHelper.INSTANCE.getBestekBodemFase());
        model.addAttribute("dossierhouders", DropDownHelper.INSTANCE.getDossierhouders());
        model.addAttribute("organisatiesVoorScreening_dd", ovamcore_sqlSession.selectList("organisatiesVoorScreening_dd"));
    }
   

    @RequestMapping(value = "/bestek/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody BestekDO bestekDO) throws Exception {
        Integer bestek_id = bestekService.saveBestek(bestekDO);
        return new Response(bestek_id);
    }

    @RequestMapping(value = "/{dossier_type}/bestek/{bestek_id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable Long bestek_id, @PathVariable String dossier_type) throws Exception {
        sql.deleteInTable("art46", "bestek", "bestek_id", bestek_id);
        return "redirect:/dossierdetails.do?selectedtab=Bestek";
    }
    
    
}
