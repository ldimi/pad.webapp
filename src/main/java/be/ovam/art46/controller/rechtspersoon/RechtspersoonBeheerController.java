package be.ovam.art46.controller.rechtspersoon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import be.ovam.art46.dao.RechtsPersoonDAO;
import be.ovam.art46.model.Adres_1;
import be.ovam.art46.model.Adres_1_Rechtspersoon;
import be.ovam.art46.model.Adres_1rpid;
import be.ovam.art46.model.Contactpersoon;
import be.ovam.art46.model.RechtsPersoon;
import be.ovam.art46.service.RechtsPersoonService;

@Controller
public class RechtspersoonBeheerController {
	
	@Autowired
	private RechtsPersoonService rechtspersoonService;
	
	@Autowired
	private SqlSession sqlSession;

	@ModelAttribute
	public void SetLinkModel(
			@RequestParam(required = false) Adres_1 adres, 
			@RequestParam(required = false) RechtsPersoon rechtspersoon , 
			@RequestParam(required = false) String rechtspersoonid ,
			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
			Model model
			){
		
		if (adres==null){
			adres=new Adres_1();
		}
		model.addAttribute("adres", adres);
		
		if (contactpersoon==null){
			contactpersoon=new Contactpersoon();
		}
		model.addAttribute("contactpersoon", contactpersoon);
	}

	@RequestMapping(value="/nieuwerechtspersoon",method= RequestMethod.GET)	
	public static String nieuwePersoon(Model model) {			
		RechtsPersoon rechtspersoon=new RechtsPersoon();	
		model.addAttribute("rechtspersoon", rechtspersoon);				
		return "rechtspersoon.nieuwrechtspersoonformulier";		
	}
	
	@RequestMapping(value="/nieuwerechtspersoon",method= RequestMethod.POST)	
	public String nieuwePersoonCheck(
			@RequestParam(required=false,value="rechtspersoonid") Integer rechtspersoonid,
			@ModelAttribute(value="rechtspersoon")@Valid RechtsPersoon rechtspersoon,			
			BindingResult bindingResult, 
			Model model) {		

		
		if (bindingResult.hasErrors()) {
		
			return "rechtspersoon.nieuwrechtspersoonformulier";
		}
		// kijk of deze persoon al in de db zit.
		List<RechtsPersoon> rechtspersonen = rechtspersoonService.getExistingRechtsPersonen(rechtspersoon);		
		
		
		
		//Als de Rechtspersoon al bestaat, alle gegevens, incl adresgegeven teru
		if (!rechtspersonen.isEmpty()) {			
			List<Adres_1_Rechtspersoon> rechtspersonenadressen=new ArrayList<Adres_1_Rechtspersoon>();
			List<Adres_1_Rechtspersoon> rechtspersonenadressen2=new ArrayList<Adres_1_Rechtspersoon>();			
			
			for (RechtsPersoon onerechtspersoon : rechtspersonen) {				
				Integer RPid=onerechtspersoon.getId();
				System.out.println("################################"+RPid.toString());
				rechtspersonenadressen= sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.listrechtspersoon_adres",RPid);					
				for(int i=0;i<rechtspersonenadressen.size(); i++){
					rechtspersonenadressen2.add(rechtspersonenadressen.get(i));
				}
				rechtspersonenadressen.clear();
			}	
			
			
			model.addAttribute("bestaanderechtspersonen", rechtspersonenadressen2);
			model.addAttribute("confirm", true);
			return "rechtspersoon.nieuwrechtspersoonformulier";
			
			
			
			
		}else{				
				try {
					rechtspersoonService.save(rechtspersoon);
					model.addAttribute("rechtspersoon", rechtspersoon);
					rechtspersoonid=rechtspersoon.getId();
					model.addAttribute("rechtspersoonid", rechtspersoonid);
					System.out.println("#############################"+rechtspersoonid.toString()+ "@AND"+rechtspersoon.getId());
//					return "rechtspersoon.rechtspersoonbeheer";
					return "beheer.rechtspersoonbeheer";
					//return "redirect:/s/nieuwerechtspersoon";
					} catch (Exception e) {
						System.out.println(" er ging iets fout bij het opslaan "+ e);
						return "rechtspersoon.nieuwrechtspersoonformulier";
					}					
			}
		}
	
	@RequestMapping(value="/rechtspersoonbeheer/confirmdouble",method= RequestMethod.POST)	
	public String nieuwePersoondoubleCheck(
			@RequestParam(required=false,value="rechtspersoonid") Integer rechtspersoonid,
			@ModelAttribute(value="rechtspersoon")@Valid RechtsPersoon rechtspersoon,			
			BindingResult bindingResult, 
			Model model) {		

		
		if (bindingResult.hasErrors()) {		
			return "rechtspersoon.nieuwrechtspersoonformulier";
		}
							
				try {
					rechtspersoonService.save(rechtspersoon);
					model.addAttribute("rechtspersoon", rechtspersoon);
					rechtspersoonid=rechtspersoon.getId();
					model.addAttribute("rechtspersoonid", rechtspersoonid);
					System.out.println("#############################"+rechtspersoonid.toString()+ "@AND"+rechtspersoon.getId());
//					return "rechtspersoon.rechtspersoonbeheer";
					return "beheer.rechtspersoonbeheer";
					//return "redirect:/s/nieuwerechtspersoon";
					} catch (Exception e) {
						System.out.println(" er ging iets fout bij het opslaan "+ e);
						return "rechtspersoon.nieuwrechtspersoonformulier";
					}					
			
		}
	
	
	@RequestMapping(value="/rechtspersoonbeheer/updaterechtspersoon",method= RequestMethod.POST)	
	public String updateRechtspersoon(		
			@ModelAttribute(value="rechtspersoon")@Valid RechtsPersoon rechtspersoon,
			BindingResult bindingResult, 
			Model model) {	
		
		model.addAttribute("rechtspersoon", rechtspersoon);
			
			
			if (bindingResult.hasErrors()) {				
				return "rechtspersoon.nieuwrechtspersoonformulier";
			}else{
				try {
						rechtspersoonService.save(rechtspersoon);
						model.addAttribute("rechtspersoon", rechtspersoon);
						model.addAttribute("geselecteerdepersoon", rechtspersoon);
						model.addAttribute("rechtspersoonid", rechtspersoon.getId());
//						System.out.println("#############################"+rechtspersoonid.toString()+ "@AND"+rechtspersoon.getId());
						return "rechtspersoon.rechtspersoonbeheer";
//						//return "redirect:/s/nieuwerechtspersoon";
						} catch (Exception e) {
							System.out.println(" er ging iets fout bij het opslaan "+ e);
							return "rechtspersoon.nieuwrechtspersoonformulier";
						}			
			}
			
		}
	
	
		
	@RequestMapping(value="/rechtspersoonadresLink",method= RequestMethod.GET)	
	public String getCleanRechtspersoonadresLink() {
		return "rechtspersoon.rechtspersoon_adresLink";		
	}
	
	@RequestMapping(value="/rechtspersoonadresLink",method= RequestMethod.POST)	
	public String rechtspersoonadresLink(		
			@ModelAttribute(value="adres") Adres_1 adres,
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			Model model){
	
		
		model.addAttribute("rechtspersoonid", rechtspersoonid);
		model.addAttribute("adres", adres);
		
		return "rechtspersoon.rechtspersoon_adresLink";		
	}
	
	
	@RequestMapping(value="/rechtspersoon/zoeken",method= RequestMethod.POST)	
	public String zoekRechtspersoon(
			@RequestParam(value="adresid")String adresid,
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			BindingResult bindingresult,
			Model model) {
		List<RechtsPersoon> gevondenpersonen=rechtspersoonService.find(rechtspersoon);
		model.addAttribute("gevondenpersonen", gevondenpersonen);
		model.addAttribute("adresid", adresid);
		return "adres.adres_rechtspersoonLink";		
	}
	
	@RequestMapping(value="/rechtspersoon/linkrechtspersoonadres", method=RequestMethod.POST)
	public String linkRechtspersoonAdres(
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			@RequestParam(value="adresid") String adresid,
			@ModelAttribute(value="adres") Adres_1 adres,
			Model model){
		
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("rechtspersoonid", rechtspersoonid);
		map.put("adresid", adresid);
		
		try {
			//we proberen eerst gewoon een save te doen naar de databank
			Object SQLanswer=sqlSession.insert("be.ovam.art46.mappers.AdresMapper.setRechtspersoon_Adres",map);	
			List gelinkteAdressen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteAdressenViaRP_ID",rechtspersoonid);
			model.addAttribute("gelinkteAdressen", gelinkteAdressen);
	
			System.out.println("#############dit ging er mis#################"+SQLanswer.toString());
			model.addAttribute("gelinkt", true);
			model.addAttribute("rechtspersoonid", rechtspersoonid);
			return "rechtspersoon.rechtspersoon_adresLink";
		} catch (Exception e) {
			System.out.println("#############dit ging er mis#################"+e);
			return "foutje";
		}
	}
	
	
	
	
//	@RequestMapping(value="/rechtspersoon/rechtspersoonbeheer",method=RequestMethod.GET)
//	public String getBeheerScherm(
//			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
//			Model model
//			){
//		return "rechtspersoon.rechtspersoonbeheer";
//	}
//	
	
	
//	@RequestMapping(value="/rechtspersoon/rechtspersoonbeheer",method=RequestMethod.POST)
//	public String getBeheerScherm(			
//			//@RequestParam(value="gevondenpersonen") String gevondenpersonen,
//			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
//			Model model){			
//		//model.addAttribute("rechtspersoonid", rechtspersoonid);	
//		//model.addAttribute("gevondenpersonen", gevondenpersonen);		
//		
//		
//		if (rechtspersoonid !=null){
//			//get het object
//			Integer rechtspersoonid2=(Integer)Integer.parseInt(rechtspersoonid);
//			RechtsPersoon geselecteerdepersoon=rechtspersoonService.getByID(rechtspersoonid2);
//			RechtsPersoon rechtspersoon=geselecteerdepersoon;
//			model.addAttribute("geselecteerdepersoon", geselecteerdepersoon);
//			model.addAttribute("rechtspersoon",rechtspersoon);
//			//get gelinkte adressen
//			List gelinkteAdressen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteAdressenViaRP_ID",rechtspersoonid);
//			model.addAttribute("gelinkteAdressen", gelinkteAdressen);
//			
//			//get gelinkte contactpersonen
//			List gelinkteContactpersonen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteContactpersonen",rechtspersoonid);
//			model.addAttribute("gelinkteContactpersonen", gelinkteContactpersonen);
//		}		
//		return "rechtspersoon.rechtspersoonbeheer";
//	}
	
	
//	@RequestMapping(value="/rechtspersoon/zoeken2",method= RequestMethod.POST)	
//	public String zoekRechtspersoon2(		
//			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
//			BindingResult bindingresult,
//			Model model) {
//		List<RechtsPersoon> gevondenpersonen=rechtspersoonService.find(rechtspersoon);
//		model.addAttribute("gevondenpersonen", gevondenpersonen);		
//		return "rechtspersoon.rechtspersoonbeheer";		
//	}
	
	
	
	
//	@RequestMapping(value="/rechtspersoon/selecteerRechtspersoon",method= RequestMethod.POST)	
//	public String selecteerRechtspersoon(
//			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
//			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
//			BindingResult bindingresult,
//			Model model) {
////		List<RechtsPersoon> gevondenpersonen=rechtspersoonService.find(rechtspersoon);
//		model.addAttribute("rechtspersoonid", rechtspersoonid);	
//		
//		if (rechtspersoonid !=null){
//			//get het object
//			Integer rechtspersoonid2=(Integer)Integer.parseInt(rechtspersoonid);
//			RechtsPersoon geselecteerdepersoon=rechtspersoonService.getByID(rechtspersoonid2);
//			model.addAttribute("geselecteerdepersoon", geselecteerdepersoon);
//			model.addAttribute("rechtspersoon", geselecteerdepersoon);
//			//get gelinkte adressen
//			List gelinkteAdressen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteAdressenViaRP_ID",rechtspersoonid);
//			model.addAttribute("gelinkteAdressen", gelinkteAdressen);
//			
//			//get gelinkte contactpersonen
//			List gelinkteContactpersonen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteContactpersonen",rechtspersoonid);
//			model.addAttribute("gelinkteContactpersonen", gelinkteContactpersonen);
//		}	
//		return "rechtspersoon.rechtspersoonbeheer";		
//	}
		

}
