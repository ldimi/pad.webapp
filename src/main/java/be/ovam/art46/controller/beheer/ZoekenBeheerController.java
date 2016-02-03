package be.ovam.art46.controller.beheer;

import java.util.ArrayList;
import java.util.List;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.ovam.art46.model.Adres_1;
import be.ovam.art46.model.Adres_1rpid;
import be.ovam.art46.model.Contactpersoon;
import be.ovam.art46.model.Contactpersoon_RP;
import be.ovam.art46.model.RechtsPersoon;
import be.ovam.art46.service.Adres_1Service;
import be.ovam.art46.service.ContactpersoonService;
import be.ovam.art46.service.RechtsPersoonService;

@Controller
public class ZoekenBeheerController {
	
	@Autowired
	private RechtsPersoonService rechtspersoonService;
	
	@Autowired
	private Adres_1Service adres_1Service;
	
	@Autowired
	private ContactpersoonService contactpersoonService;
	
	@Autowired
	private SqlSession sqlSession;

	@ModelAttribute
	public void setPage(
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			@ModelAttribute(value="adres")Adres_1 adres,
			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
			Model model
			){
		
		if (rechtspersoon!= null){
			model.addAttribute("rechtspersoon", rechtspersoon);
		}else{
			rechtspersoon= new RechtsPersoon();
			model.addAttribute("rechtspersoon", rechtspersoon);
		}
		if (adres!= null){
			model.addAttribute("adres", adres);
		}else{
			adres= new Adres_1();
			model.addAttribute("adres", adres);
		}
		if (contactpersoon!= null){
			model.addAttribute("contactpersoon", contactpersoon);
		}else{
			contactpersoon= new Contactpersoon();
			model.addAttribute("contactpersoon", contactpersoon);
		}
		
	}
	
	@RequestMapping(value="/zoeken/beheer",method=RequestMethod.GET)
	public String getPage(
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			@ModelAttribute(value="adres")Adres_1 adres,
			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
			Model model
			) {		
		return "zoeken.beheer";
	}	
	
	@RequestMapping(value="/zoeken/beheer/rechtspersoon",method=RequestMethod.POST)
	public String getresultsrechtspersoon(
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			@ModelAttribute(value="adres")Adres_1 adres,
			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
			Model model
			) {
		
		if (rechtspersoon != null) {
			List<RechtsPersoon> gevondenpersonen = rechtspersoonService
					.find(rechtspersoon);
			model.addAttribute("gevondenpersonen", gevondenpersonen);
		
		}

	
		return "zoeken.beheer";
	}	
	
//	@RequestMapping(value="/zoeken/beheer/adres",method=RequestMethod.POST)
//	public String getresultsadres(
//			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
//			@ModelAttribute(value="adres")Adres_1 adres,
//			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
//			Model model
//			) {
//		if (adres != null) {
//			List<Adres_1> gevondenadressen = adres_1Service.find(adres);
//			//voor elk adres in gevonden adressen
//			//query op rechtspersoon_adres met adresid
//			//gewoon de join op List gevondenadressen2 zetten.
//			List<Adres_1rpid> gevondenadressen2=new ArrayList<Adres_1rpid>();
//			List<Adres_1rpid> gevondenadressen3=new ArrayList<Adres_1rpid>();
//			
//			for (Adres_1 oneadres : gevondenadressen) {
//				
//				Integer adresid=oneadres.getId();
//				gevondenadressen2= sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getAdres_rpid",adresid);	
//				for(int i=0;i<gevondenadressen2.size(); i++){
//					gevondenadressen3.add(gevondenadressen2.get(i));
//				}
//				gevondenadressen2.clear();
//			}
//			
//			model.addAttribute("gevondenadressen", gevondenadressen3);
//		}
//
//		return "zoeken.beheer";
//	}
	
    @RequestMapping(value="/zoeken/beheer/adres",method=RequestMethod.POST)
    public String getresultsadres(
            @ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
            @ModelAttribute(value="adres")Adres_1 adres,
            @ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
            Model model
            ) {
        if (adres != null) {
            List<Adres_1rpid> gevondenadressen =  sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getAdres_rp",adres);    
            model.addAttribute("gevondenadressen", gevondenadressen);
        }

        return "zoeken.beheer";
    }
    
	@RequestMapping(value="/zoeken/beheer/contactpersoon",method=RequestMethod.POST)
	public String getresultscp(
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			@ModelAttribute(value="adres")Adres_1 adres,
			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
			Model model
			) {		
		if (contactpersoon != null) {
			List<Contactpersoon> gevondencontactpersonen = contactpersoonService.find(contactpersoon);
			List<Contactpersoon_RP> gevondencontactpersonen2=new ArrayList<Contactpersoon_RP>();
			List<Contactpersoon_RP> gevondencontactpersonen3=new ArrayList<Contactpersoon_RP>();
				
			for (Contactpersoon onecontact : gevondencontactpersonen) {						
						Integer contactid=onecontact.getId();
//						System.out.println("#################################"+adresid.toString());
						gevondencontactpersonen2=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getContact_RP",contactid);	
						for(int i=0;i<gevondencontactpersonen2.size(); i++){
							gevondencontactpersonen3.add(gevondencontactpersonen2.get(i));
						}
						gevondencontactpersonen2.clear();
					}
			model.addAttribute("gevondencontactpersonen",gevondencontactpersonen3);			
		}
		return "zoeken.beheer";
	}
	
	
//	@RequestMapping(value="/zoeken/beheer",method=RequestMethod.POST)
//	public String getresults(
//			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
//			@ModelAttribute(value="adres")Adres_1 adres,
//			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
//			Model model
//			) {
//		
//		if (rechtspersoon != null) {
//			List<RechtsPersoon> gevondenpersonen = rechtspersoonService
//					.find(rechtspersoon);
//			model.addAttribute("gevondenpersonen", gevondenpersonen);
//			
//		}
//
//		if (adres != null) {
//			List<Adres_1> gevondenadressen = adres_1Service.find(adres);
//			model.addAttribute("gevondenadressen", gevondenadressen);
//			
//		}
//
//		if (contactpersoon != null) {
//
//			List<Contactpersoon> gevondencontactpersonen = contactpersoonService
//					.find(contactpersoon);
//			model.addAttribute("gevondencontactpersonen",
//					gevondencontactpersonen);
//			
//		}
//		return "zoeken.beheer";
//	}
	
//	@RequestMapping(value="/naarbeheer",method=RequestMethod.POST)
//	public String naarBeheer(
//			@RequestParam(value="rechtspersoonid")String rechtspersoonid,
//			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
//			@ModelAttribute(value="adres")Adres_1 adres,
//			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
//			Model model
//			) {
//		model.addAttribute("rechtspersoonid", rechtspersoonid);
//		return "beheer.rechtspersoonbeheer";
//	}
	
	
}