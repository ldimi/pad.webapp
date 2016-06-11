package be.ovam.art46.controller.budget;

import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.util.mybatis.SqlSession;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class DeelopdrachtVoorstellenController extends BasicBestekController {
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;

    @Autowired
    private SqlSession sqlSession;


    @RequestMapping(value = "/bestek/{bestek_id}/deelopdrachtvoorstellen", method = RequestMethod.GET)
    public String start(@PathVariable Long bestek_id, Model model) throws Exception {
        super.startBasic(bestek_id, model);
        model.addAttribute("voorstellen", voorstelDeelopdrachtService.getAllForBestek(bestek_id));
        return "bestek.deelopdracht.voorstellen";
    }

    @RequestMapping(value = "/bestek/{bestek_id}/voorstellen_opm/update", method = RequestMethod.POST)
    public String start(@PathVariable Long bestek_id, @RequestParam String voorstellen_opm) throws Exception {
        
        Map bestek = new HashMap();
        bestek.put("bestek_id", bestek_id);
        bestek.put("voorstellen_opm", voorstellen_opm);
        
        sqlSession.updateInTable("art46", "bestek", bestek);
        return "redirect:/s/bestek/" + bestek_id + "/deelopdrachtvoorstellen";
    }

}
