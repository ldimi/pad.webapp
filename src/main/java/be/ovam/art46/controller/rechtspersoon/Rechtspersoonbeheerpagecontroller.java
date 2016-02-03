package be.ovam.art46.controller.rechtspersoon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import be.ovam.art46.model.Adres_1;
import be.ovam.art46.model.Contactpersoon;
import be.ovam.art46.model.RechtsPersoon;
import be.ovam.art46.service.Adres_1Service;
import be.ovam.art46.service.ContactpersoonService;
import be.ovam.art46.service.RechtsPersoonService;

@Controller
public class Rechtspersoonbeheerpagecontroller {
	
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
			@RequestParam(required=false,value="rechtspersoonid") String rechtspersoonid,
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			@ModelAttribute(value="adres")Adres_1 adres,
			@ModelAttribute(value="contactpersoon")Contactpersoon contactpersoon,
			Model model
			){
		if(rechtspersoonid==null){
			rechtspersoonid="";
			model.addAttribute("rechtspersoonid", rechtspersoonid);
		}
		if(rechtspersoonid!=""){
			Integer rechtspersoonid2=(Integer)Integer.parseInt(rechtspersoonid);
			RechtsPersoon geselecteerdepersoon=rechtspersoonService.getByID(rechtspersoonid2);
			model.addAttribute("geselecteerdepersoon", geselecteerdepersoon);
			model.addAttribute("rechtspersoon", geselecteerdepersoon);
			model.addAttribute("rechtspersoonid", rechtspersoonid);
			//get gelinkte adressen
			List gelinkteAdressen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteAdressenViaRP_ID",rechtspersoonid);
			model.addAttribute("gelinkteAdressen", gelinkteAdressen);
			
			//get gelinkte contactpersonen
			List gelinkteContactpersonen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteContactpersonen",rechtspersoonid);
			model.addAttribute("gelinkteContactpersonen", gelinkteContactpersonen);
		}
		
	}
	
	@RequestMapping(value="/beheer/rechtspersoonbeheer",method=RequestMethod.GET)
	public String getPage(
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			Model model) {		
		
		return "beheer.rechtspersoonbeheer";
	}	
	@RequestMapping(value="/beheer/zoek",method=RequestMethod.POST)
	public String zoekrechtspersoon(
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			Model model
			) {		
		List<RechtsPersoon> gevondenpersonen=rechtspersoonService.find(rechtspersoon);
		model.addAttribute("gevondenpersonen", gevondenpersonen);		
		return "beheer.rechtspersoonbeheer";
	}
	
	@RequestMapping(value="/beheer/selecteer",method=RequestMethod.POST)
	public String selecteerrechtspersoon(
			Model model
			) {
		
		return "beheer.rechtspersoonbeheer";
	}
	
	@RequestMapping(value="/beheer/update",method=RequestMethod.POST)
	public String updaterechtspersoon(
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			BindingResult bindingresult,
			Model model
			) {		
		
		if (bindingresult.hasErrors()) {	
			model.addAttribute("rechtspersoonid", rechtspersoonid);
			return "beheer.rechtspersoonbeheer";
		}else{
			try {
				rechtspersoonService.save(rechtspersoon);
				System.out.println("############################### Rechtspersoon werd aangepast"+rechtspersoonid);
				return "beheer.rechtspersoonbeheer";
			} catch (Exception e) {
			System.out.println("###############################"+e);
			return "beheer.rechtspersoonbeheer";
			}
		}
				
	}
	
	@RequestMapping(value="/beheer/zoekadressen",method=RequestMethod.POST)
	public String zoekadressen(
			@ModelAttribute(value="adres") Adres_1 adres,
			Model model
			){
		List<Adres_1> gevondenadressen=adres_1Service.find(adres);
		model.addAttribute("gevondenadressen", gevondenadressen);
		return "beheer.rechtspersoonbeheer";
	}
	
	
	
	
	
	
	
	@RequestMapping(value="/beheer/maakadres",method=RequestMethod.POST)
	public String maakadres(
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			@ModelAttribute(value = "adres") @Valid Adres_1 adres, 
			BindingResult bindingResult,
			Model model
			){
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("adres", adres);
			return "beheer.rechtspersoonbeheer";
		}else{
					// kijk of het adres al in de db zit.
			List<Adres_1> adressen = adres_1Service.compare(adres);
			if (!adressen.isEmpty()) {
				model.addAttribute("bestaandeAdressen", adressen);
				return "beheer.rechtspersoonbeheer";		
			}else{				
				try {
					adres_1Service.save(adres);
					try {	
							HashMap<String, String> map=new HashMap<String, String>();
							map.put("rechtspersoonid", rechtspersoonid);
							map.put("adresid", adres.getId().toString());
							sqlSession.insert("be.ovam.art46.mappers.AdresMapper.setRechtspersoon_Adres",map);
							List gelinkteAdressen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteAdressenViaRP_ID",rechtspersoonid);
							model.addAttribute("gelinkteAdressen", gelinkteAdressen);
					}catch(Exception e){
							System.out.println("#########################################  er ging iets fout bij het linken van adres en rechtspersoon "+ e);
					}
				} catch (Exception e) {
					System.out.println("######################################### er ging iets fout bij het opslaan van het nieuwe adres "+ e);
				}					
			}
		}			

		return "beheer.rechtspersoonbeheer";
	}
	
	@RequestMapping(value="beheer/linkrechtspersoonadres", method=RequestMethod.POST)
	public String linkRechtspersoonAdres(
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			@RequestParam(value="adresid") String adresid,
			@ModelAttribute(value="gelinkteAdressen") List gelinkteAdressen,
			Model model){
		
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("rechtspersoonid", rechtspersoonid);
		map.put("adresid", adresid);
		
		for (Object object : gelinkteAdressen) {
			Map adres=(Map)object;
			if((Integer)adres.get("id")==Integer.parseInt(adresid)){ 
				model.addAttribute("overbodigAdres", true);
			return "beheer.rechtspersoonbeheer";
			}
		}
		
		
		
		try {
			//we proberen eerst gewoon een save te doen naar de databank
			Object SQLanswer=sqlSession.insert("be.ovam.art46.mappers.AdresMapper.setRechtspersoon_Adres",map);	
//			List gelinkteAdressen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteAdressenViaRP_ID",rechtspersoonid);
//			model.addAttribute("gelinkteAdressen", gelinkteAdressen);
//			model.addAttribute("rechtspersoonid", rechtspersoonid);
			return "redirect:/s/beheer/rechtspersoonbeheer";
		} catch (Exception e) {
			System.out.println("#############dit ging er mis#################"+e);
			return "foutje";
		}
	}

	
	
	@RequestMapping(value="/beheer/unlinkrechtspersoonadres", method=RequestMethod.POST)
	public String unlinkRechtspersoonAdres(
			@RequestParam(value="rechtspersoonid")String rechtspersoonid,
			@RequestParam(value="adresid")String adresid,
			Model model
				){
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("rechtspersoonid", rechtspersoonid);
		map.put("adresid", adresid);
		try {
			//we proberen eerst gewoon een save te doen naar de databank
			sqlSession.delete("be.ovam.art46.mappers.AdresMapper.unsetRechtspersoon_Adres",map);	
			List gelinkteAdressen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteAdressenViaRP_ID",rechtspersoonid);
			model.addAttribute("gelinkteAdressen", gelinkteAdressen);
			model.addAttribute("rechtspersoonid", rechtspersoonid);		
		} catch (Exception e) {
			System.out.println("#############dit ging er mis#################"+e);			
		}
		return "beheer.rechtspersoonbeheer";
	}
	

	@RequestMapping(value="beheer/markeerMZ", method=RequestMethod.POST)
	public String markeerMZ(
			@RequestParam(value="rechtspersoonid")String rechtspersoonid,
			@RequestParam(value="adresid")String adresid,
			Model model
				){
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("rechtspersoonid", rechtspersoonid);
		map.put("adresid", adresid);
		try {
			//we updaten de rechtspersoon met de nieuwe mz
			sqlSession.insert("be.ovam.art46.mappers.AdresMapper.updateRPwithMZ",map);		
		} catch (Exception e) {
			System.out.println("#############dit ging er mis#################"+e);			
		}
		return "redirect:/s/beheer/rechtspersoonbeheer";
	}
	
	
	@RequestMapping(value="beheer/maakcontact",method=RequestMethod.POST)
	public String maakcontactpersoon(
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			@ModelAttribute(value = "contactpersoon")Contactpersoon contactpersoon,
			BindingResult bindingResult,
			Model model
			){
		
	
			try {
				contactpersoonService.save(contactpersoon);
				} catch (Exception e) {
					System.out.println("######################################### er ging iets fout bij het opslaan van de nieuwe contactpersoon "+ e);
				}					
			
		List gelinkteContactpersonen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteContactpersonen",rechtspersoonid);
		model.addAttribute("gelinkteContactpersonen", gelinkteContactpersonen);
		model.addAttribute("rechtspersoonid", rechtspersoonid);	
		Contactpersoon cp2=new Contactpersoon();
		model.addAttribute("contactpersoon", cp2);
		
		return "beheer.rechtspersoonbeheer";
	}
	
	
	
	@RequestMapping(value="beheer/verwijdercontactpersoon",method=RequestMethod.POST)
	public String verwijdercontactpersoon(
			@RequestParam(value="rechtspersoonid")String rechtspersoonid,
			@RequestParam(value="contactpersoonid")String contactpersoonid,
			Model model
			){
		Integer cpid=(Integer)Integer.parseInt(contactpersoonid);
		
		contactpersoonService.delete(cpid);
		List gelinkteContactpersonen=sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.getGelinkteContactpersonen",rechtspersoonid);
		model.addAttribute("gelinkteContactpersonen", gelinkteContactpersonen);
		model.addAttribute("rechtspersoonid", rechtspersoonid);	
			return "beheer.rechtspersoonbeheer";
	}	
}
