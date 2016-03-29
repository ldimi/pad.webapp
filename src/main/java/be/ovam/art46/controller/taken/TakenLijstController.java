package be.ovam.art46.controller.taken;

import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TakenLijstController {
    
    @Autowired
    private SqlSession sqlSession;



    @RequestMapping(value = "/takenlijst", method = RequestMethod.GET)
    public String startTakenLijst(Model model){
        String doss_hdr_id = Application.INSTANCE.getUser_id();
        model.addAttribute("dossierhouders",DropDownHelper.INSTANCE.getDossierhouders());
        model.addAttribute("doss_hdr_id", doss_hdr_id);
        model.addAttribute("dms_webdrive_base", System.getProperty("ovam.dms.webdrive.base"));
        
        model.addAttribute("title", "Takenlijst");
        model.addAttribute("menuId", "m_taken.lijst");
        model.addAttribute("page", "lijsten/takenlijst.jsp");
        return "pageView";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/sv/beoordelen", method = RequestMethod.POST)
    public @ResponseBody List schuldvorderingBeoordeelTaken(@RequestBody ParamsDO params){
        return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_schuldvorderingBeoordelen", params);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/sv/ondertekenen", method = RequestMethod.POST)
    public @ResponseBody List schuldvorderingOndertekenTaken(@RequestBody ParamsDO params){
        if (Application.INSTANCE.isUserInRole("ondertekenaar") &&
                Application.INSTANCE.getUser_id().equals(params.getDoss_hdr_id()) ) {
            return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_schuldvorderingOndertekenen", null);
        }
        return new ArrayList();
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/sv/scannen", method = RequestMethod.POST)
    public @ResponseBody List schuldvorderingScannenTaken(@RequestBody ParamsDO params){
        if (Application.INSTANCE.isUserInRole("secretariaat") &&
                Application.INSTANCE.getUser_id().equals(params.getDoss_hdr_id()) ) {
            return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_schuldvorderingScannen", null);
        }
        return new ArrayList();
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/sv/printen", method = RequestMethod.POST)
    public @ResponseBody List schuldvorderingPrintenTaken(@RequestBody ParamsDO params){
        if (Application.INSTANCE.isUserInRole("secretariaat") &&
                Application.INSTANCE.getUser_id().equals(params.getDoss_hdr_id()) ) {
            return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_schuldvorderingPrinten", null);
        }
        return new ArrayList();
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/brieven_printen", method = RequestMethod.POST)
    public @ResponseBody List brieven_printen(@RequestBody ParamsDO params){
        if (Application.INSTANCE.isUserInRole("secretariaat") &&
                Application.INSTANCE.getUser_id().equals(params.getDoss_hdr_id()) ) {
            return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_brieven_printen", null);
        }
        return new ArrayList();
    }
    
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/voorstelDeelopdracht/beoordelen", method = RequestMethod.POST)
    public @ResponseBody List voorstelDeelopdrachtBeoordeelTaken(@RequestBody ParamsDO params){
        return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_voorstelDeelopdrachtBeoordelen", params);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/deelopdracht/goedkeuren", method = RequestMethod.POST)
    public @ResponseBody List deelopdrachtGoedkeurTaken(@RequestBody ParamsDO params){
        return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_deelopdrachtGoedkeuren", params);
    }


    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/brief_in_check_auteur", method = RequestMethod.POST)
    public @ResponseBody List brief_in_check_auteur(@RequestBody ParamsDO params){
        return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_brief_in_check_auteur", params);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/brief_in_check_afd_hfd", method = RequestMethod.POST)
    public @ResponseBody List brief_in_check_afd_hfd(@RequestBody ParamsDO params){
        if (Application.INSTANCE.isUserInRole("ondertekenaar") &&
                Application.INSTANCE.getUser_id().equals(params.getDoss_hdr_id()) ) {
            return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_brief_in_check_afd_hfd", null);
        }
        return new ArrayList();
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/takenlijst/nieuw_pad_dossier", method = RequestMethod.POST)
    public @ResponseBody List nieuw_pad_dossier_taken(@RequestBody ParamsDO params){
        return sqlSession.selectList("be.ovam.art46.mappers.TakenMapper.get_taken_nieuw_pad_dossier", params);
    }

        
    public static class ParamsDO implements Serializable {

        private static final long serialVersionUID = 5690418591506088803L;
        
        private String doss_hdr_id;

        public String getDoss_hdr_id() {
            return doss_hdr_id;
        }

        public void setDoss_hdr_id(String doss_hdr_id) {
            this.doss_hdr_id = doss_hdr_id;
        }
    }

}
