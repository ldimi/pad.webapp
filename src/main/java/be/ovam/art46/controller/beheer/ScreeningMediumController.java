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

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Controller
public class ScreeningMediumController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/beheer/screeningMedium", method = RequestMethod.GET)
    public String start(Model model) throws Exception {

        model.addAttribute("mediums", getScreeningMediumList());
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        
        model.addAttribute("title", "Beheer Screening Medium");
        model.addAttribute("menuId", "m_toepassingsbeheer.screeningMedium");
        
        return jsview("beheer/screeningMedium", model);
    }
    
    public List getScreeningMediumList() throws Exception {
		return sqlSession.selectList("be.ovam.art46.mappers.ScreeningMediumMapper.getScreeningMediumList", null);
	}

    @RequestMapping(value = "/beheer/screeningMedium/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody Map mediun) throws Exception {
        Response response;
        
        String status_crud = (String) mediun.get("status_crud");
        try{
            sqlSession.saveInTable("art46", "screening_medium_code", mediun);
            clearCache();
            return new Response(getScreeningMediumList(), true, null);
        } catch (DataIntegrityViolationException de) {
            if ("D".equals(status_crud)) {
                response = new Response(null, false, "Record kan niet verwijderd worden omdat er al naar verwezen wordt.");
            } else {
                response = new Response(null, false, "Error ivm. dataIntegriteit.");
            }
        } catch (Exception ex) {
            response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
        }
        return response;
    }
    
    private void clearCache() {
        sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.ScreeningMediumMapper").clear();
    }

    
}
