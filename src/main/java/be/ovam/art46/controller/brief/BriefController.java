package be.ovam.art46.controller.brief;

import be.ovam.art46.service.EsbService;
import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.dms.alfresco.DmsUserAlfresco;
import be.ovam.web.Response;
import be.ovam.util.mybatis.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class BriefController {

    private static Logger logger = LoggerFactory.getLogger(BriefController.class);

    @Autowired
    private SchuldvorderingService schuldvorderingService;

    @Autowired
    private SqlSession sqlSession;

	@Autowired
	private DmsUserAlfresco dmsAlfresco;
    @Autowired
    private EsbService esbService;

    private String dmsUrl = System.getProperty("ovam.dms.url");
    private String dmsUser = System.getProperty("ovam.dms.user");
    private String dmsPass = System.getProperty("ovam.dms.pass");

	@RequestMapping(value = "/dossier/{dossier_nr}/dms/oplaad", method = RequestMethod.GET)
	public String getDmsObject(@PathVariable String dossier_nr, Model model) throws Exception {
		
		String superFolder = dossier_nr.substring(0, 2);
		
		String host = dmsUrl.substring(7);
		
/*
        RestTemplate restTemplate = new RestTemplate(host, 80, dmsUser, dmsPass);

        URI uri = new URI(dmsUrl + "/service/be/ovam/pad/createOplaadDir_2?dossier_id=" + dossier_nr);
        String result = restTemplate.getForObject(uri, String.class);
*/
        String to = "/Toepassingen/ivs/" + superFolder + "/" + dossier_nr + "/Oplaad";
        String from = "/Toepassingen/ivs/template_oplaad/";
        esbService.copy(from, to, dmsUser);
        
		return "redirect:" + dmsUrl + "/share/page/repository#filter=path%7C%2FToepassingen%2Fivs%2F" + superFolder + "%2F" + dossier_nr + "%2FOplaad"; // + "&page=1";
	}

    @RequestMapping(value = "/dossier/{dossier_nr}/dms/webloket", method = RequestMethod.GET)
    public String getDmsObjectWebloket(@PathVariable String dossier_nr, Model model) throws Exception {
        String superFolder = dossier_nr.substring(0, 2);
        return "redirect:" + dmsUrl + "/share/page/repository#filter=path%7C%2FToepassingen%2Fivs%2F" + superFolder + "%2F" + dossier_nr + "%2FWEBLOKET"; // + "&page=1";
    }
	
    @RequestMapping(value = "/brief/{brief_id}/schuldvordering/verwijder/{vordering_id}", method = RequestMethod.GET)
    public String verwijderSchuldvordering(@PathVariable("brief_id") String brief_id, @PathVariable("vordering_id") Integer vordering_id) throws Exception {
       
        schuldvorderingService.deleteSchuldvordering(vordering_id);
        return "redirect:/briefdetails.do?brief_id=" + brief_id;
    }

    @RequestMapping(value = "/brief/afdHfd/checked", method = RequestMethod.POST)
    public @ResponseBody Response afdHfdChecked(@RequestBody Map params){        
        sqlSession.update("be.ovam.art46.mappers.BriefMapper.updateCheckedAfdelingsHoofd", params);        
        return new Response(true,null);        
    }

    @RequestMapping(value = "/brief/auteur/checked", method = RequestMethod.POST)
    public @ResponseBody Response auteurChecked(@RequestBody Map params){        
        sqlSession.update("be.ovam.art46.mappers.BriefMapper.updateCheckedAuteur", params);        
        return new Response(true,null);        
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/brief/{brief_id}/documenten", method = RequestMethod.GET)
    public @ResponseBody
    Response getDocumenten(@PathVariable Integer brief_id) {
        List result = sqlSession.selectList("be.ovam.art46.mappers.BriefMapper.getBriefDocumenten", brief_id);
        return new Response(result, true, null);
    }

    

}
