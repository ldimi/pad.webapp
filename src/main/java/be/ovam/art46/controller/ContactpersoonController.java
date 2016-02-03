package be.ovam.art46.controller;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.ovam.art46.model.Contactpersoon;
import be.ovam.art46.service.ContactpersoonService;

@Controller
public class ContactpersoonController {
	
	@Autowired
	private ContactpersoonService contactpersoonService;
	
	@Autowired
	private SqlSession sqlSession;
	
	// @RequestMapping(value="/nieuwecontactpersoon", method=RequestMethod.GET)
	// public String getNieuwFormulier(Model model){
	// 	return "contactpersoon.nieuwcontactpersoonformulier";
	// }
	// 
	// 
	// @RequestMapping(value="/contactpersoon/nieuweContactpersoon", method=RequestMethod.POST)
	// public String maakEnLinkNieuweContactpersoon(
	// 		@RequestParam(value="rechtspersoonid") String rechtspersoonid,
	// 		@ModelAttribute(value="contactpersoon") Contactpersoon contactpersoon,
	// 		Model model
	// 		){
	// 	return null;
	// }
	
}
