package be.ovam.art46.controller.budget;

import be.ovam.art46.controller.BasicBestekController;
import be.ovam.art46.decorator.BigDecimalDecorator;
import be.ovam.art46.decorator.CurrencyDecorator;
import be.ovam.art46.model.SelectionBoxValue;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.meetstaat.MeetstaatOfferteService;
import be.ovam.art46.util.Application;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.Offerte;
import be.ovam.web.Response;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;

@Controller
public class DeelOpdrachtController extends BasicBestekController{

    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;

    @Autowired
    private BriefService briefService;
    @Autowired
    private DeelOpdrachtService deelOpdrachtService;

    @Autowired
    private MeetstaatOfferteService meetstaatOfferteService;

    
    @RequestMapping(value = "/bestek/{bestek_id}/deelopdrachten", method = RequestMethod.GET)
    public String start(@PathVariable Long bestek_id, Model model) throws Exception {
        return start(bestek_id, null, model);
    }
    
    @RequestMapping(value = "/bestek/{bestek_id}/offerte/{offerte_id}/deelopdrachten", method = RequestMethod.GET)
    public String start(@PathVariable Long bestek_id, @PathVariable Long offerte_id, Model model) throws Exception {
        
        if (offerte_id != null && offerte_id == -1 ) {
            return "redirect:/s/bestek/" + bestek_id + "/deelopdrachten";
        }
        
        super.startBasic(bestek_id, model);
        model.addAttribute("besteksaldo", sqlSession.selectOne("be.ovam.art46.mappers.BestekMapper.besteksaldo", bestek_id));
        model.addAttribute("bestekOffertesDD", sqlSession.selectList("getBestekOffertesDD", bestek_id));
        model.addAttribute("doss_hdr_id", Application.INSTANCE.getUser().getUser_id());
        
        model.addAttribute("offerte_id" , offerte_id);
        
        Map<String, Object> params =  new HashMap<String, Object>();
        params.put("bestek_id", bestek_id);
        params.put("offerte_id", offerte_id);
        List<Map<String,Object>> bestekdeelopdrachtlijst = sqlSession.selectList("bestekdeelopdrachtlijst", params);
        model.addAttribute("bestekdeelopdrachtlijst", bestekdeelopdrachtlijst);
        
        Double som_geraamd_bedrag = 0.0;
        BigDecimal som_totaal_schuldvorderingen = new BigDecimal(0.00);
        BigDecimal som_totaal_facturen = new BigDecimal(0.00);  
        
        for (Map<String, Object> map : bestekdeelopdrachtlijst) {
            if (map.get("bedrag") != null) {
                som_geraamd_bedrag += (Double) map.get("bedrag");
            }
            som_totaal_schuldvorderingen = som_totaal_schuldvorderingen.add((BigDecimal) map.get("totaal_schuldvorderingen"));
            som_totaal_facturen = som_totaal_facturen.add((BigDecimal) map.get("totaal_facturen"));
        }
        model.addAttribute("som_geraamd_bedrag", CurrencyDecorator.format(som_geraamd_bedrag));
        model.addAttribute("som_totaal_schuldvorderingen", CurrencyDecorator.format(som_totaal_schuldvorderingen));
        model.addAttribute("som_totaal_facturen", CurrencyDecorator.format(som_totaal_facturen));

        
        List<Map<String,Object>> openstaandePlanningslijnen = sqlSession.selectList("getOpenstaandePlanningslijnenVoorBestek", bestek_id);
        model.addAttribute("openstaandePlanningslijnen", openstaandePlanningslijnen);
        Integer som_openstaand_gepland = 0;
        for (Map<String, Object> map : openstaandePlanningslijnen) {
            if (map.get("ig_bedrag") != null) {
                som_openstaand_gepland += (Integer) map.get("ig_bedrag");
            }
        }
        model.addAttribute("som_openstaand_gepland", CurrencyDecorator.format(som_openstaand_gepland));
                
        return "bestek.deelopdrachten";
    }
    
    @RequestMapping(value = "/bestek/{bestek_id}/deelopdrachten/{deelopdracht_id}", method = RequestMethod.GET)
    public String openDeelopdracht(@PathVariable Long bestek_id,@PathVariable Integer deelopdracht_id, Model model) throws Exception {
        model.addAttribute("deelopdracht_id", deelopdracht_id);
        return start(bestek_id, model);
    }
    
    
    @RequestMapping(value = "/bestek/{bestek_id}/deelopdrachten/{deelopdrachtId}/verwijder", method = RequestMethod.GET)
    public String delete(@PathVariable String bestek_id,@PathVariable Integer deelopdrachtId, Model model) throws Exception {
        sqlSession.delete("be.ovam.art46.mappers.DeelopdrachtMapper.deleteDeelopdracht", deelopdrachtId);
        return "redirect:/s/bestek/" + bestek_id + "/deelopdrachten";
    }

    @RequestMapping(value = "deelopdracht/historiek", method = RequestMethod.GET)
    public @ResponseBody
    Response getGeschiedenisDeelopdracht(@RequestParam("id") String id) throws Exception {      
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.DeelopdrachtMapper.getHistoriekById", id), true, null);        
    }

    
    

    @RequestMapping(value = "/deelopdracht", method = RequestMethod.GET)
    public
    @ResponseBody
    Response getDeelopdracht(@RequestParam("id") int id) throws Exception {
        return new Response(deelOpdrachtService.getDeelopdrachtBy(id), true, null);
    }

    @RequestMapping(value = "/deelopdracht/dossiers", method = RequestMethod.GET)
    public
    @ResponseBody
    Response getDeelopdrachtDossiers(@RequestParam("deelopdracht_id") String deelopdracht_id) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("doss_hdr_id", Application.INSTANCE.getUser().getUser_id());
        params.put("deelopdracht_id", deelopdracht_id);
        params.put("adminArt46_JN", Application.INSTANCE.isUserInRole("adminArt46") ? "J" : "N");
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.BestekMapper.deelopdrachtloaddossiers", params), true, null);
    }


    @RequestMapping(value = "/deelopdracht/planning_lijnen", method = RequestMethod.GET)
    public
    @ResponseBody
    Response getDeelopdrachtPlanningLijnen(@RequestParam("bestek_id") int bestek_id, @RequestParam("deelopdracht_id") int deelopdrachtId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestek_id", bestek_id);
        params.put("deelopdracht_id", deelopdrachtId);
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.BestekMapper.getPlanningsItemsVoorDeelOpdracht", params), true, null);
    }

    @RequestMapping(value = "/deelopdracht/offertes", method = RequestMethod.GET)
    public
    @ResponseBody
    Response getOffertes(@RequestParam("bestek_id") Long bestek_id) throws Exception {
        List<Offerte> offertes = meetstaatOfferteService.getToegekendeOffertes(bestek_id);
        ArrayList<SelectionBoxValue> selectionBoxValues = new ArrayList<SelectionBoxValue>();
        for(Offerte offerte : offertes){
            SelectionBoxValue selectionBoxValue = new SelectionBoxValue();
            selectionBoxValue.setValue(""+offerte.getId());
            selectionBoxValue.setLabel(offerte.getInzender());
            selectionBoxValues.add(selectionBoxValue);
        }
        return new Response(selectionBoxValues, true, null);
    }


    @RequestMapping(value = "/budget/deelopdracht/insert", method = RequestMethod.POST)
    public @ResponseBody Response insert(@RequestBody DeelOpdracht deelOpdracht) throws Exception {
        sqlSession.update("be.ovam.art46.mappers.DeelopdrachtMapper.insertDeelopdracht", deelOpdracht);
        return new Response(null, true, null);
    }

    @RequestMapping(value = "/budget/deelopdracht/update", method = RequestMethod.POST)
    public @ResponseBody Response update(@RequestBody DeelOpdracht deelOpdracht) throws Exception {
        sqlSession.update("be.ovam.art46.mappers.DeelopdrachtMapper.updateDeelopdracht", deelOpdracht);
        return new Response(null, true, null);
    }

    @RequestMapping(value = "/budget/deelopdracht/upload", method = RequestMethod.POST)
    public @ResponseBody Integer uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("deelopdrachtId") int deelopdracht_id) throws Exception {
        return deelOpdrachtService.uploadBrief(deelopdracht_id, name, file.getBytes());
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/budget/deelopdracht/getBrieven", method = RequestMethod.GET)
    public @ResponseBody Response getBrieven(@RequestParam("deelopdrachtId") int deelopdrachtId) throws Exception {
        List brieven = sqlSession.selectList("be.ovam.art46.mappers.DeelopdrachtBriefMapper.brieven", deelopdrachtId);
        return new Response(brieven, true, null);
    }

    @RequestMapping(value = "/budget/deelopdracht/updateBrief", method = RequestMethod.POST)
    public @ResponseBody Response UpdateBrief(@RequestBody Brief brief) throws Exception {
        sqlSession.update("be.ovam.art46.mappers.BriefMapper.updateCommentaarBrief", brief);
        return new Response(null, true, null);
    }

}
