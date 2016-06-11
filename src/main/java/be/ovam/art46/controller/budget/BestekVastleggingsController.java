package be.ovam.art46.controller.budget;

import be.ovam.art46.model.*;
import be.ovam.art46.service.AanvraagVastleggingsService;
import be.ovam.web.Response;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by koencorstjens on 4-7-13.
 */

@Controller
public class BestekVastleggingsController extends BasicBestekController{
    
    @Autowired
    @Qualifier("sqlSession")
    SqlSession sqlSession;

    @Autowired
    AanvraagVastleggingsService aanvraagVastleggingsService;

    @RequestMapping(value = "/bestek/{bestekId}/vastleggingen", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestekId", bestekId);

        model.addAttribute("budgetairartikels", sqlSession.selectList("be.ovam.art46.mappers.BudgetairArtikelMapper.budgetairArtikels")) ;
        model.addAttribute("kostenplaatsen", sqlSession.selectList("be.ovam.art46.mappers.KostenPlaatsMapper.getAllen")) ;
        super.startBasic(bestekId, model);
        return "bestek.individueel.vastleggingen";
    }

    @RequestMapping(value = "/rest/vastlegging/weiger", method = RequestMethod.POST)
    public @ResponseBody String weigerAanvraagVastlegging(@RequestBody RequestWeigerVastlegging requestWeigerVastlegging) throws IOException {
        aanvraagVastleggingsService.weigerVastlegging(requestWeigerVastlegging);
        return "ok";
    }

    @RequestMapping(value = "/vastlegging/save", method = RequestMethod.POST)
    public @ResponseBody Response createVastlegging(@RequestBody AanvraagVastlegging aanvraagVastlegging) throws Exception {
        aanvraagVastlegging = aanvraagVastleggingsService.saveAanvraag(aanvraagVastlegging);
        return getAll(aanvraagVastlegging.getBestekid());
    }
    @RequestMapping(value = "/vastlegging/delete", method = RequestMethod.POST)
    public @ResponseBody Response delete(@RequestBody AanvraagVastlegging aanvraagVastlegging) throws Exception {
        sqlSession.delete("be.ovam.art46.mappers.AanvraagVastleggingMapper.delete", aanvraagVastlegging.getId());
        return getAll(aanvraagVastlegging.getBestekid());
    }

    @RequestMapping(value = "/vastlegging/verzend", method = RequestMethod.POST)
    public @ResponseBody Response verzenden(@RequestBody AanvraagVastlegging aanvraagVastlegging) throws Exception {
        aanvraagVastleggingsService.verzend(aanvraagVastlegging);
        return getAll(aanvraagVastlegging.getBestekid());
    }

    @RequestMapping(value = "/lijsten/spreidingen", method = RequestMethod.GET)
    public @ResponseBody Response getSpreidingen(@RequestParam("aanvraagId") int aanvraagId) throws Exception{
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.AanvraagVastleggingSpreidingMapper.getAll", aanvraagId), true, null);
    }

    @RequestMapping(value = "/vastlegging/brief", method = RequestMethod.GET)
    public @ResponseBody Response getAanvraagOptioneleBrief(@RequestParam("briefId") int briefId) throws Exception{
        return new Response(sqlSession.selectOne("be.ovam.art46.mappers.AanvraagVastleggingBrievenMapper.getBrief", briefId), true, null);
    }

    @RequestMapping(value = "/lijsten/optioneleBrieven", method = RequestMethod.GET)
    public @ResponseBody Response getBrieven(@RequestParam("aanvraagId") int aanvraagId) throws Exception{
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.AanvraagVastleggingBrievenMapper.getAll", aanvraagId), true, null);
    }

    @RequestMapping(value = "/spreiding/delete", method = RequestMethod.POST)
    public @ResponseBody Response deleteSpreiding(@RequestBody Spreiding spreiding) throws Exception {
        sqlSession.delete("be.ovam.art46.mappers.AanvraagVastleggingSpreidingMapper.delete", spreiding);
        return getSpreidingen(spreiding.getAanvraagid());
    }

    @RequestMapping(value = "/brief/delete", method = RequestMethod.POST)
    public @ResponseBody void deleteBrief(@RequestBody OptioneelBestand bestand) throws Exception {
        sqlSession.delete("be.ovam.art46.mappers.AanvraagVastleggingBrievenMapper.delete", bestand);
    }

    @RequestMapping(value = "/lijsten/nieuwVastleggingsData", method = RequestMethod.GET)
    public @ResponseBody Response getNieuwVastleggingsData(@RequestParam("bestekId") int bestekId){
        return new Response(sqlSession.selectOne("be.ovam.art46.mappers.BestekMapper.getNieuwVastleggingsData", bestekId), true, null);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/lijsten/planning_lijnen", method = RequestMethod.GET)
    public @ResponseBody Response getPlanningLijnen(@RequestParam("bestekId") int bestekId, @RequestParam("aanvraagId") int aanvraagId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestekId", bestekId);
        params.put("aanvraagId", aanvraagId);
        List list = sqlSession.selectList("be.ovam.art46.mappers.BestekMapper.getPlanningsItemsVoorBestekVastleggingsAanvraag", params);
        return new Response(list, true, null);
    }

    @RequestMapping(value = "/lijsten/allScans", method = RequestMethod.GET)
    public @ResponseBody Response getAllScans(@RequestParam("bestekId") int bestekId, @RequestParam("aanvraagId") Integer aanvraagId) throws Exception {
        return getScansForVastleggingsAanvraag(bestekId, aanvraagId, null);
    }

    @RequestMapping(value = "/lijsten/gunningsverslagScans", method = RequestMethod.GET)
    public @ResponseBody Response getGunningsverslagScans(@RequestParam("bestekId") int bestekId, @RequestParam("aanvraagId") Integer aanvraagId)
            throws Exception {
        int[] typeIds = { 65, 91, 92, 93, 103, 104 };
        return getScansForVastleggingsAanvraag(bestekId, aanvraagId, typeIds);
    }

    @RequestMapping(value = "/lijsten/gunningsbeslissingsScans", method = RequestMethod.GET)
    public @ResponseBody Response getGunningsbeslissingsScans(@RequestParam("bestekId") int bestekId, @RequestParam("aanvraagId") Integer aanvraagId)
            throws Exception {
        int[] typeIds = { 64, 120 };
        return getScansForVastleggingsAanvraag(bestekId, aanvraagId, typeIds);
    }

    @RequestMapping(value = "/lijsten/overeenkomstScans", method = RequestMethod.GET)
    public @ResponseBody Response getOvereenkomstScans(@RequestParam("bestekId") int bestekId, @RequestParam("aanvraagId") Integer aanvraagId) throws Exception {
        int[] typeIds = { 80 };
        return getScansForVastleggingsAanvraag(bestekId, aanvraagId, typeIds);
    }

    private Response getScansForVastleggingsAanvraag(int bestekId, Integer aanvraagId, int[] typeIds) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestekId", bestekId);
        params.put("aanvraagId", aanvraagId);
        params.put("typeIds", typeIds);
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.BriefMapper.getScansForVastleggingsAanvraag", params), true, null);
    }

    @RequestMapping(value = "/lijsten/getAllVastleggingen", method = RequestMethod.GET)
    public @ResponseBody Response getAll(@RequestParam("bestekId") Integer bestekId) throws Exception {
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.AanvraagVastleggingMapper.getAll", bestekId), true, null);
    }

    @RequestMapping(value = "/lijsten/opdrachthouders", method = RequestMethod.GET)
    public @ResponseBody Response getOpdrachthouders(@RequestParam("bestekId") long bestekId) throws Exception {
        return new Response(sqlSession.selectList("be.ovam.art46.mappers.BestekAdresMapper.getBestekOpdrachthouders", bestekId), true, null);
    }
    
}
