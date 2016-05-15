package be.ovam.art46.controller.beheer;

import static be.ovam.web.util.JsView.jsview;
import be.ovam.web.Response;
import be.ovam.art46.util.Application;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import org.apache.ibatis.cache.Cache;

@SuppressWarnings("rawtypes")
@Controller
public class DossierRechtsgrondController {
	
	@Autowired
	private SqlSession sqlSession;
	
    @RequestMapping(value = "/beheer/dossierRechtsgrondlijst", method = RequestMethod.GET)
    public String start(Model model) throws Exception {

        model.addAttribute("rechtsgronden", getDossierRechtsgrondList());
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        
        model.addAttribute("title", "Beheer Rechtsgrond");
        model.addAttribute("menuId", "m_toepassingsbeheer.dossierRechtsgronden");
        
        return jsview("beheer/rechtsgrondLijst", model);
    }
    
    @RequestMapping(value = "/beheer/dossierRechtsgrond/save", method = RequestMethod.POST)
    public @ResponseBody Response save(@RequestBody DossierRechtsgrondDO rechtsgrond) throws Exception {
        Response response;
        
        String status_crud = rechtsgrond.getStatus_crud();
        try{
            sqlSession.saveInTable("art46", "dossier_rechtsgrond", rechtsgrond);
            clearCache();
            return new Response(getDossierRechtsgrondList(), true, null);
        } catch (DataIntegrityViolationException de) {
            if ("D".equals(status_crud)) {
                response = new Response(null, false, "Deze rechtsgrond kan niet verwijderd worden omdat er al naar verwezen wordt.");
            } else {
                response = new Response(null, false, "Error ivm. dataIntegriteit.");
            }
        } catch (Exception ex) {
            response = new Response(null, false, ex.getClass().getSimpleName() + " : " + ex.getMessage());
        }
        return response;
    }
    
    private void clearCache() {
        // er is geen cache !
        
        // Cache cache = sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.DossierRechtsgrondMapper");
    }

    private List getDossierRechtsgrondList() throws Exception {
		return sqlSession.selectList("be.ovam.art46.mappers.DossierRechtsgrondMapper.getDossierRechtsgrondList", null);
	}

    
}
