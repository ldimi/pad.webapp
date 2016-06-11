package be.ovam.art46.controller;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
