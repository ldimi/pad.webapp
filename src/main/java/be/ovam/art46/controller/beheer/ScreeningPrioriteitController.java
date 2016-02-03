package be.ovam.art46.controller.beheer;

import static be.ovam.web.util.JsView.jsview;

import be.ovam.art46.util.Application;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Controller
public class ScreeningPrioriteitController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/beheer/screeningPrioriteit", method = RequestMethod.GET)
    public String start(Model model) throws Exception {

        model.addAttribute("lijsten", getLijsten());
        
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        
        model.addAttribute("title", "Beheer Screening Prioriteiten");
        model.addAttribute("menuId", "m_toepassingsbeheer.screeningPrioriteit");
        
        return jsview("beheer/screeningPrioriteit", model);
    }
    
    @RequestMapping(value = "/beheer/screeningPrioriteitCriterium/save", method = RequestMethod.POST)
    public @ResponseBody Response saveCriterium(@RequestBody Map criterium) throws Exception {
        Response response;
        
        String status_crud = (String) criterium.get("status_crud");
        try{
            sqlSession.saveInTable("art46", "screening_prioriteit_criterium_code", criterium);
            clearCache();
            return new Response(getLijsten(), true, null);
        } catch (DataIntegrityViolationException de) {
            if ("D".equals(status_crud)) {
                response = new Response(null, false, "Dit Criterium kan niet verwijderd worden omdat er al naar verwezen wordt.");
            } else {
                response = new Response(null, false, "Error ivm. dataIntegriteit.");
            }
        } catch (Exception ex) {
            response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
        }
        return response;
    }

    
    @RequestMapping(value = "/beheer/screeningPrioriteitWaarde/save", method = RequestMethod.POST)
    public @ResponseBody Response saveWaarde(@RequestBody Map criterium) throws Exception {
        Response response;
        
        String status_crud = (String) criterium.get("status_crud");
        try{
            sqlSession.saveInTable("art46", "screening_prioriteit_waarde", criterium);
            clearCache();
            return new Response(getLijsten(), true, null);
        } catch (DataIntegrityViolationException de) {
            if ("D".equals(status_crud)) {
                response = new Response(null, false, "Dit Criterium kan niet verwijderd worden omdat er al naar verwezen wordt.");
            } else {
                response = new Response(null, false, "Error ivm. dataIntegriteit.");
            }
        } catch (Exception ex) {
            response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
        }
        return response;
    }
    
    
    private Map<String, List> getLijsten () {
        Map<String, List> lijsten = new HashMap<String, List>();
        lijsten.put("priotiteitCriteria", getScreeningPrioriteitCriteriumList());
        lijsten.put("priotiteitWaarden", getScreeningPrioriteitWaardeList());
        return lijsten;
    }

    
    
    private List getScreeningPrioriteitWaardeList() {
        return sqlSession.selectList("getScreeningPrioriteitWaardeList", null);
    }

    private List getScreeningPrioriteitCriteriumList() {
        return sqlSession.selectList("getScreeningPrioriteitCriteriumList", null);
    }

    
    
    
    private void clearCache() {
        sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.ScreeningPrioriteitMapper").clear();
    }

    
}
