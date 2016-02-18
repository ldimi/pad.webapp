package be.ovam.art46.controller.sluis;

import be.ovam.pad.model.dossieroverdracht.DossierOverdrachtDTO;
import be.ovam.art46.service.dossier.DossierService;
import be.ovam.art46.service.sluis.SluisService;
import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.pad.service.BasisDossierOverdrachtService;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.*;

import static be.ovam.web.util.JsView.jsview;
import org.springframework.beans.factory.annotation.Qualifier;

@Controller
public class  SluisController {

    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sql;

    @Autowired
    private HttpSession session;

    @Autowired
    protected DossierService dossierService;
    
    @Autowired
    protected SluisService sluisService;
    
    @ModelAttribute
    public void addModelAttributes(Model model) {
        
        model.addAttribute("mistral2Url", System.getProperty("pad.mistral2Url"));
        model.addAttribute("dmsUrl", System.getProperty("ovam.dms.url"));
        model.addAttribute("rechtsgronden", DropDownHelper.INSTANCE.getDossierRechtsgronden());
        
        model.addAttribute("verontreinig_activiteiten_dd", sql.selectList("be.ovam.pad.common.mappers.DossierDDMapper.verontreinig_activiteiten_dd"));
        model.addAttribute("instrumenten_dd", sql.selectList("be.ovam.pad.common.mappers.DossierDDMapper.instrumenten_dd"));

        model.addAttribute("overdrachtstatussen_dd", sql.selectList("be.ovam.art46.mappers.DossierOverdrachtMapper.getOverdrachtStatussenDD"));
        model.addAttribute("screenBestekken_dd", sql.selectList("be.ovam.pad.common.mappers.DossierOverdrachtMapper.getScreenBestekkenDD"));
        
        model.addAttribute("dossierhouders", DropDownHelper.INSTANCE.getDossierhouders());
        model.addAttribute("fusiegemeenten", DropDownHelper.INSTANCE.getFusiegemeenten()); 
        model.addAttribute("programmaTypes", DropDownHelper.INSTANCE.getProgrammaTypes());
        model.addAttribute("fasen", DropDownHelper.INSTANCE.getDossierFasen_dd());
        model.addAttribute("doelgroepen_dd", DropDownHelper.INSTANCE.getDoelgroepen_dd());

        model.addAttribute("screening_parameter_dd", sql.selectList("screening_parameter_dd"));
        model.addAttribute("screening_medium_dd", sql.selectList("screening_medium_dd"));
        model.addAttribute("screening_attest_aard_dd", sql.selectList("screening_attest_aard_dd"));
        model.addAttribute("screening_stofgroepen_dd", sql.selectList("screening_stofgroepen_dd"));

        model.addAttribute("screening_actueel_risico_dd", sql.selectList("screening_actueel_risico_dd"));
        model.addAttribute("screening_beleidsmatig_risico_dd", sql.selectList("screening_beleidsmatig_risico_dd"));
        model.addAttribute("screening_integratie_risico_dd", sql.selectList("screening_integratie_risico_dd"));
        model.addAttribute("screening_potentieel_risico_dd", sql.selectList("screening_potentieel_risico_dd"));

        model.addAttribute("screening_prioriteit_gewichten", sql.selectList("screening_prioriteit_gewichten"));
        
        model.addAttribute("isAdminSluis", Application.INSTANCE.isUserInRole("adminSluis"));
        model.addAttribute("currentDate", new Date());
    }

    @RequestMapping(value = "/sluis", method = RequestMethod.GET)
    public String start(Model model) throws Exception {
        return this.overdrachtBeheer(model);
    }

    @RequestMapping(value = "/sluis/registratie", method = RequestMethod.GET)
    public String registratie(Model model) throws Exception {

        model.addAttribute("title", "Sluis Registratie");
        model.addAttribute("menuId", "m_sluis_overdracht.registratie");
        model.addAttribute("page_status_list", Arrays.asList("registrati"));
        model.addAttribute("page_key", "registratie");

        return jsview("sluis", "sluis/overdrachtBeheer", model);
    }

    @RequestMapping(value = "/sluis/instroom", method = RequestMethod.GET)
    public String instroom(Model model) throws Exception {

        model.addAttribute("title", "Sluis Instroom");
        model.addAttribute("menuId", "m_sluis_overdracht.instroom");
        model.addAttribute("page_status_list", Arrays.asList("instroom"));
        model.addAttribute("page_key", "instroom");

        return jsview("sluis", "sluis/overdrachtBeheer", model);
    }

    @RequestMapping(value = "/sluis/screening", method = RequestMethod.GET)
    public String screening(Model model) throws Exception {

        model.addAttribute("title", "Sluis Screening");
        model.addAttribute("menuId", "m_sluis_overdracht.screening");
        model.addAttribute("page_status_list", Arrays.asList("screening", "na_screen", "na_scr_afg"));
        model.addAttribute("page_key", "screening");

        return jsview("sluis", "sluis/overdrachtBeheer", model);
    }

    @RequestMapping(value = "/sluis/bibliotheek", method = RequestMethod.GET)
    public String bibliotheek(Model model) throws Exception {

        model.addAttribute("title", "Sluis Bibliotheek");
        model.addAttribute("menuId", "m_sluis_overdracht.bibliotheek");
        model.addAttribute("page_status_list", Arrays.asList("bib"));
        model.addAttribute("page_key", "bibliotheek");

        return jsview("sluis", "sluis/overdrachtBeheer", model);
    }

    @RequestMapping(value = "/sluis/uitstroom", method = RequestMethod.GET)
    public String uitstroom(Model model) throws Exception {

        model.addAttribute("title", "Sluis Uitstroom");
        model.addAttribute("menuId", "m_sluis_overdracht.uitstroom");
        model.addAttribute("page_status_list", Arrays.asList("uitstroom", "naar_ivs"));

        model.addAttribute("page_key", "uitstroom");

        return jsview("sluis", "sluis/overdrachtBeheer", model);
    }

    @RequestMapping(value = "/sluis/overdracht/beheer", method = RequestMethod.GET)
    public String overdrachtBeheer(Model model) throws Exception {

        model.addAttribute("title", "Sluis overzicht");
        model.addAttribute("menuId", "m_sluis_overdracht.overzicht");
        model.addAttribute("page_status_list", Arrays.asList("registrati", "instroom", "screening", "na_screen", "na_scr_afg", "bib", "uitstroom", "naar_ivs", "geklassrd"));
        model.addAttribute("page_key", "overzicht");

        return jsview("sluis", "sluis/overdrachtBeheer", model);
    }

    @RequestMapping(value = "/sluis/overdracht/historiek", method = RequestMethod.GET)
    public String overdrachtHistoriek(Model model) throws Exception {

        model.addAttribute("title", "Overdracht historiek");
        model.addAttribute("menuId", "m_sluis_overdracht.historiek");

        return jsview("sluis", "sluis/overdrachtHistoriek", model);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/sluis/overdracht/historiek/lijst", method = RequestMethod.POST)
    public
    @ResponseBody
    Response getOverdrachtHistoriekLijst(@RequestBody Map<String, Object> params) throws Exception {
        return new Response(sql.selectList("getOverdrachtHistoriek", params), true, null);
    }


    @RequestMapping(value = "/sluis/overdrachten", method = RequestMethod.POST)
    public
    @ResponseBody
    Response getOverdrachtLijst(@RequestBody HashMap params) throws Exception {
        session.setAttribute("sluis_beheer_selectie_params", params);
        return getOverdrachten();
    }

    @RequestMapping(value = "/sluis/overdracht/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Response save(@RequestBody DossierOverdrachtDTO overdracht) throws Exception {
        sluisService.save(overdracht);
        return getOverdrachten();
    }

    @RequestMapping(value = "/sluis/overdracht/maakZip", method = RequestMethod.POST)
    public
    @ResponseBody
    Response maakZip(@RequestBody Integer dossier_id) throws Exception {
        sluisService.maakZip(dossier_id);
        return new Response(true);
    }

    @RequestMapping(value = "/sluis/smegdata/{dossier_id_boa}", method = RequestMethod.GET)
    public
    @ResponseBody
    Response getSmegData(@PathVariable String dossier_id_boa) throws Exception {
        return new Response(sql.selectOne("be.ovam.art46.mappers.DossierOverdrachtMapper.getSmegDataByDossierIdBoa", dossier_id_boa), true, null);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Response getOverdrachten() {
        HashMap params = (HashMap) session.getAttribute("sluis_beheer_selectie_params");

        Map result = sluisService.getOverdrachtenMap(params);
        result.put("params", params);
        return new Response(result, true, null);
    }


}
