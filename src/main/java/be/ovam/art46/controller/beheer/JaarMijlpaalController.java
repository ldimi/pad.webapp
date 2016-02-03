package be.ovam.art46.controller.beheer;

import be.ovam.web.Response;
import be.ovam.art46.model.planning.Jaarmijlpaal;
import be.ovam.art46.model.planning.JaarmijlpaalProgramma;
import be.ovam.art46.util.Application;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.util.mybatis.SqlSession;
import static be.ovam.web.util.JsView.jsview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JaarMijlpaalController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping(value = "/beheer/mijlpalen", method = RequestMethod.GET)
	public String start(Model model) {
        model.addAttribute("jaren", DropDownHelper.INSTANCE.getJaren());
		model.addAttribute("programmaTypes",DropDownHelper.INSTANCE.getProgrammaTypes());
        
        model.addAttribute("isAdminArt46", Application.INSTANCE.isUserInRole("adminArt46"));
        model.addAttribute("title", "Beheer Mijlpalen");
        model.addAttribute("menuId", "m_toepassingsbeheer.mijlpalen");
        
        return jsview("beheer/mijlpalen", model);
	}

	
	@RequestMapping(value = "/beheer/getJaarMijlpalen", method = RequestMethod.GET)
	public @ResponseBody List getJaarMijlpalenList(@RequestParam int jaar) {
		return sqlSession.selectList("be.ovam.art46.mappers.JaarMijlpaalMapper.getMijlpalenList", jaar);
	}
	
	@RequestMapping(value = "/beheer/jaarmijlpaal/save", method = RequestMethod.POST)
	public @ResponseBody List save(@RequestBody Jaarmijlpaal jaarmijlpaal) throws Exception {
        sqlSession.saveInTable("art46", "jaarmijlpaal", jaarmijlpaal);
        clearCache();
		return getJaarMijlpalenList(jaarmijlpaal.getJaar());
	}

	
	
	@RequestMapping(value = "/beheer/getJaarMijlpalenProgramma", method = RequestMethod.GET)
	public @ResponseBody List getJaarMijlpalenProgrammaList(@RequestParam int jaar) throws Exception {
		return sqlSession.selectList("be.ovam.art46.mappers.JaarMijlpaalMapper.getMijlpalenProgrammaList", jaar);
	}	
	
	@RequestMapping(value = "/beheer/jaarmijlpaalProgramma/save", method = RequestMethod.POST)
	public @ResponseBody List save(@RequestBody JaarmijlpaalProgramma jaarmijlpaalProgramma) throws Exception {
		sqlSession.saveInTable("art46", "jaarmijlpaal_programma", jaarmijlpaalProgramma);
        clearCache();
		return getJaarMijlpalenProgrammaList(jaarmijlpaalProgramma.getJaar());
	}

	private void clearCache() {
	    sqlSession.getConfiguration().getCache("be.ovam.art46.mappers.JaarMijlpaalMapper").clear();
	}

}
