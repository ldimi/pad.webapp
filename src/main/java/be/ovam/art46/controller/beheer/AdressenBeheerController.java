package be.ovam.art46.controller.beheer;

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
import org.springframework.web.bind.annotation.ResponseBody;


import be.ovam.art46.model.Adres_1;
import be.ovam.art46.model.RechtsPersoon;
import be.ovam.art46.service.Adres_1Service;

@Controller
public class AdressenBeheerController {
	
	@Autowired
	private Adres_1Service adres_1Service;
	
	@Autowired
	private SqlSession sqlSession;


	@ModelAttribute
	public void SetLinkModel(
			@RequestParam(required = false) Adres_1 adres, 
//			@RequestParam(required = false) RechtsPersoon rechtspersoon , 
			Model model){
		
		if (adres==null){
			adres=new Adres_1();
		}
		model.addAttribute("adres", adres);
		
//		if (rechtspersoon==null){
//			rechtspersoon=new RechtsPersoon();
//		}
//		model.addAttribute("rechtspersoon", rechtspersoon);
	}

	
	@RequestMapping(value = "/adressenbeheer", method = RequestMethod.GET)
	public String start(Model model)
								throws Exception {
		
	
		/*hier geven we een overzicht van de huidige status van de databank*/
//		Integer aantalAdresRecords = sqlSession.selectOne("be.ovam.art46.mappers.AdresMapper.aantalAdresRecords");
//		Integer aantalActieveAdresRecords = sqlSession.selectOne("be.ovam.art46.mappers.AdresMapper.aantalActieveAdresRecords");
//		Integer aantalRPRecords = sqlSession.selectOne("be.ovam.art46.mappers.AdresMapper.aantalRPRecords");
//		Integer dubbelerecordsOpNaam = sqlSession.selectOne("be.ovam.art46.mappers.AdresMapper.dubbelerecordsOpNaam");
//		List<Integer> dubbeleAdressenlijst = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.IDdubbeleAdressen");
//		Integer onvolledigeAdressen = sqlSession.selectOne("be.ovam.art46.mappers.AdresMapper.aantalonvolledigeAdressen");
//		
//		Integer dubbeleAdressen=dubbeleAdressenlijst.size();
//		
//		model.addAttribute("aantalAdresRecords", aantalAdresRecords.toString());
//		model.addAttribute("aantalActieveAdresRecords", aantalActieveAdresRecords.toString());
//		
//		model.addAttribute("aantalRPRecords", aantalRPRecords.toString());
//		model.addAttribute("dubbelerecordsOpNaam", dubbelerecordsOpNaam.toString());
//		model.addAttribute("dubbeleAdressen", dubbeleAdressen.toString());
//		model.addAttribute("onvolledigeAdressen", onvolledigeAdressen.toString());
		
		return "adres.beheer";		
	}
		
	@RequestMapping(value="/adres/stelgemeentenvoor",method=RequestMethod.GET)
	public @ResponseBody List zoekGemeenten(@RequestParam("term") String gemeente){	
		List gemeentenlijst = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.geefgemeenten", gemeente);
		return gemeentenlijst;		
	}

    @RequestMapping(value="/adres/gemeentenLijst",method=RequestMethod.GET)
    public @ResponseBody List getGemeentenLijst(){  
        return sqlSession.selectList("getGemeentenLijst", null);
    }
	
	
	@RequestMapping(value="/adres/stelgemeentenvoorviapostcode",method=RequestMethod.GET)
	public @ResponseBody List zoekGemeentenviapostcode(@RequestParam("term") String postcode){	
		List gemeentenlijst = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.geefgemeentenviapostcode", postcode);
		return gemeentenlijst;		
	}
	
	@RequestMapping(value="/adres/stelstratenvoor",method=RequestMethod.GET)
	public @ResponseBody List zoekStraten(@RequestParam("term") String straat){			
		List straten = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.geefstraten", straat);
		return straten;		
	}
	
	@RequestMapping(value="/adres/stelstratenvoor2",method=RequestMethod.GET)
	public @ResponseBody List zoekStraten2(@RequestParam("term") String straat,String gemeente){
		
		 Map<String, String> params = new HashMap<String, String>();
	        params.put("straat",straat);
	        params.put("gemeente", gemeente);
		List straten = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.geefstraten2", params);
		return straten;		
	}
	
	@RequestMapping(value="/adres/stellandvoor",method=RequestMethod.GET)
	public @ResponseBody List zoekland(@RequestParam("term") String land){			
		List straten = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.geeflanden", land);
		return straten;		
	}	

	
	@RequestMapping(value = "/nieuw_adres", method = RequestMethod.GET)
	public String nieuwAdres(Model model){
		Adres_1 adres = new Adres_1();
		model.addAttribute("adres", adres);
		return "adres.nieuwadresformulier";		
	}
	
	@RequestMapping(value = "/nieuw_adres", method = RequestMethod.POST)
	public String nieuwAdresCheck(
			
			@ModelAttribute(value = "adres") @Valid Adres_1 adres, 
			BindingResult bindingResult,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			return "adres.nieuwadresformulier";
		}else{
					// kijk of het adres al in de db zit.
			List<Adres_1> adressen = adres_1Service.compare(adres);
			if (!adressen.isEmpty()) {
				for (Adres_1 adresItem : adressen) {
					model.addAttribute("bestaandeAdressen", adressen);
				}
				return "adres.nieuwadresformulier";
		
			}else{				
				try {
					adres_1Service.save(adres);	
					model.addAttribute("adres", adres);
					model.addAttribute("adresid", adres.getId());
					return "adres.success";
					} catch (Exception e) {
						System.out.println(" er ging iets fout bij het opslaan "+ e);
						return "adres.nieuwadresformulier";
					}					
			}
		}			
	}		
	
	
	
	
	
	
//	@RequestMapping(value="/rechtspersoonadresLink",method= RequestMethod.POST)	
//	public String adresrechtspersoonLink(HttpServletRequest req, HttpServletResponse res, Model m){
////		try {
////			String test= req.getParameter("adresid");
////		} catch (Exception e) {
////		System.out.println("error gevangen: geen adres aanwezig wss: "+ e);
////		}
////		
////		String test= req.getParameter("adresid");
////		System.out.println(test+"##############################################################################");
////		//als er geen adres aanwezig is, ga je dat zoeken.
////		if(false){
////			this.getcleanadresrechtspersoonLink();
////		}
//		
//		m.addAttribute("test","test");
//		m.addAttribute("test2", req.getParameter("adresid"));
//		
//		return "rechtspersoon.rechtspersoon_adresLink";		
//	}

	
	@RequestMapping(value="/adresrechtspersoonLink",method= RequestMethod.GET)	
	public String getCleanAdresrechtspersoonLink(){
		return "adres.adres_rechtspersoonLink";
	}
	
	@RequestMapping(value="/adresrechtspersoonLink",method= RequestMethod.POST)	
	public String adresrechtspersoonLink(
			@ModelAttribute(value="rechtspersoon")RechtsPersoon rechtspersoon,
			@RequestParam(value="adresid") String adresid,
			Model model) {
		
		model.addAttribute("adresid", adresid);
		model.addAttribute("rechtspersoon", rechtspersoon);
		return "adres.adres_rechtspersoonLink";		
	}
	
	@RequestMapping(value="/adres_1/zoeken",method= RequestMethod.POST)	
	public String zoekAdres(
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			@ModelAttribute(value="adres") Adres_1 adres,
			BindingResult bindingResult,
			Model model){
	List<Adres_1> gevondenAdressen=adres_1Service.find(adres);
	model.addAttribute("gevondenadressen", gevondenAdressen);
	model.addAttribute("rechtspersoonid", rechtspersoonid);
	return "rechtspersoon.rechtspersoon_adresLink";	
	}
	
	
	@RequestMapping(value="/adres_1/linkadresrechtspersoon",method= RequestMethod.POST)	
	public String linkRechtspersoonAdres(
			@RequestParam(value="rechtspersoonid") String rechtspersoonid,
			@RequestParam(value="adresid") String adresid,
			@ModelAttribute(value="rechtspersoon") RechtsPersoon rechtspersoon,
			Model model){
		
		HashMap<String, String> map=new HashMap<String, String>();
		map.put("rechtspersoonid", rechtspersoonid);
		map.put("adresid", adresid);
		
		try {			
			sqlSession.insert("be.ovam.art46.mappers.AdresMapper.setRechtspersoon_Adres",map);
			model.addAttribute("gelinkt", true);
			model.addAttribute("adresid", adresid);
//			return "rechtspersoon.rechtspersoon_adresLink";
			return "adres.adres_rechtspersoonLink";
		} catch (Exception e) {
			System.out.println("#############dit ging er mis#################"+e);
			return "foutje";
		}
	}

	@RequestMapping(value="/adres/corrigeeradressen")
	public String corrigeerAdressenPage(
			@ModelAttribute(value="adres")Adres_1 adres,
			BindingResult bindingresult,
			Model model){
		//get de lijst van aan te passen id's
		List<Integer> IDonvolledigeAdressen = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.IDonvolledigeAdressen");
		Integer aantalOnvolledigeAdressen=IDonvolledigeAdressen.size();
		model.addAttribute("aantalOnvolledigeAdressen", aantalOnvolledigeAdressen);
		
		//get het object van de eerste id
		if(IDonvolledigeAdressen.size()>0){
			adres=adres_1Service.getAdresByID(IDonvolledigeAdressen.get(0));
			model.addAttribute("adres", adres);
		}
		
		
	
		
		
		
		
		return "adres.corrigeeradressen";
	}
	
	@RequestMapping(value="/adres/corrigeer")
	public String corrigeerAdressen(
			@ModelAttribute(value="adres")@Valid Adres_1 adres,
			BindingResult bindingresult,
			Model model){
		
//	model.addAttribute("adres", adres);
	
		if (bindingresult.hasErrors()) {
			List<Integer> IDonvolledigeAdressen = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.IDonvolledigeAdressen");
			Integer aantalOnvolledigeAdressen=IDonvolledigeAdressen.size();
			model.addAttribute("aantalOnvolledigeAdressen", aantalOnvolledigeAdressen);
			return "adres.corrigeeradressen";
		}else{		
			try {
				int id= adres.getId();
				sqlSession.update("be.ovam.art46.mappers.AdresMapper.correctControle",id);
				adres_1Service.save(adres);	
				System.out.println("######################adressenbeheer record met id: "+id+" werd geupdate.");
				return "redirect:/s/adres/corrigeeradressen";
			} catch (Exception e) {				
				System.out.println(e);	
				return "adres.corrigeeradressen";
			}
		}		
		
	}

	@RequestMapping(value="/adres/verwijderAdres")
	public String verwijderAdres(
			@ModelAttribute(value="adres") Adres_1 adres, //moet meegegeven worden om de pagina te kunnen herladen ->alhoewel? doet de methode @modelattribute dat neit?
			@RequestParam(value="adresid")Integer adresid,
			Model model){
		
		try {
			Object Answer= sqlSession.update("be.ovam.art46.mappers.AdresMapper.markTeVerwijderen", adresid);		
			System.out.println("######################adressenbeheer record met id: "+adresid+" werd geupdate, verwijderd: In totaal werden er "+Answer+ " rijen in de db aangepast (gemarkeerd als teVerwijdern)");
		} catch (Exception e) {			
			System.out.println(e);	
			return "adres.corrigeeradressen";
		}
		return "redirect:/s/adres/corrigeeradressen";
		
	}

	@RequestMapping(value="/adres/consolideeradressen")
	public String consolideerAdressenPage(
			Model model
			){
		//get de lijst van aan te passen id's
		List<Integer> IDonvolledigeAdressen = sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.IDdubbeleAdressen");
		//get het object van de eerste id
		
		if(IDonvolledigeAdressen.size()>0){
			Integer adresid=IDonvolledigeAdressen.get(0);
			List<Adres_1> dubbeleAdressen= sqlSession.selectList("be.ovam.art46.mappers.AdresMapper.dubbeleAdressen",adresid);	
			model.addAttribute("dubbeleAdressen", dubbeleAdressen);
		}
		Integer aantaldubbeleAdressen=IDonvolledigeAdressen.size();
		model.addAttribute("aantaldubbeleAdressen", aantaldubbeleAdressen);
		return "adres.consolideeradressen";
	}
	
	@RequestMapping(value="/adres/markeerprimairadres")
	public String markeerPrimairAdres(
			@RequestParam(value="adresid")Integer adresid,
			Model model
			){
		
		try {
			System.out.println("###############################"+adresid.toString());
			
			sqlSession.update("be.ovam.art46.mappers.AdresMapper.markeerprimairadres",adresid);
		} catch (Exception e) {
			System.out.println("####################################dit ging er fout bij het zetten van het geconsolideerde adres"+e);
		}
		
				
		return "redirect:/s/adres/consolideeradressen";
	}
}
