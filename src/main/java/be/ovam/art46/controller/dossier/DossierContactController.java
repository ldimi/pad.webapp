package be.ovam.art46.controller.dossier;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DossierContactController extends BasisDossierController {
    
    @RequestMapping(value = "/dossier/{dossier_id}/contact", method = RequestMethod.GET)
    public String startContact(@PathVariable Integer dossier_id, Model model, HttpSession session) throws Exception {
        
		putDossierInModelAndSession(dossier_id, model, session);
        
		model.addAttribute("dossierhouders", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierhouders", dossier_id));
		model.addAttribute("dossiercontactlijst", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierContactLijst", dossier_id));
		model.addAttribute("alle_adressen", sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getDossierAlleAdressen", dossier_id));

        return "dossier.contact";
    }


    @RequestMapping(value = "/dossier/{dossier_id}/contact/insert", method = RequestMethod.GET)
    public String addContact(@PathVariable Integer dossier_id,
            @RequestParam Integer adres_id,
            @RequestParam(required = false) Integer contact_id) throws Exception {
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("dossier_id", dossier_id);
        params.put("adres_id", adres_id);
        
        if (contact_id == null) {
            contact_id = 0;
        }
        params.put("contact_id", contact_id);
        
        sqlSession.insert("be.ovam.art46.mappers.AdresMapper.insertDossierAdres", params);

        return "redirect:/s/dossier/" + dossier_id + "/contact";
    }
        
    @RequestMapping(value = "/dossier/{dossier_id}/contact/delete", method = RequestMethod.GET)
    public String deleteContact(@PathVariable Integer dossier_id,
            @RequestParam Integer adres_id,
            @RequestParam(required = false) Integer contact_id) throws Exception {
        
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("dossier_id", dossier_id);
        params.put("adres_id", adres_id);
        params.put("contact_id", contact_id);

        sqlSession.delete("be.ovam.art46.mappers.AdresMapper.deleteDossierAdres", params);

        return "redirect:/s/dossier/" + dossier_id + "/contact";
    }
        
}
