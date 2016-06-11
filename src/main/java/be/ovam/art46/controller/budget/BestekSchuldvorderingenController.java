package be.ovam.art46.controller.budget;

import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by koencorstjens on 21-8-13.
 */
@Controller
public class BestekSchuldvorderingenController extends BasicBestekController{
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;
    @Autowired
    private SchuldvorderingService schuldvorderingService;


    @RequestMapping(value = "/bestek/{bestekId}/schuldvorderingen", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        basicSetup(model, bestekId);
        model.addAttribute("bestekschuldvorderinglijst",sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.bestekschuldvorderinglijst", bestekId));
        return "bestek.schuldvorderingen";
    }

    @RequestMapping(value = "/bestek/{bestekId}/schuldvorderingen/{vordering_id}", method = RequestMethod.GET)
    public String openVordering(@PathVariable Long bestekId,@PathVariable Long vordering_id , Model model) throws Exception {
        model.addAttribute("vordering_id", vordering_id);
        return this.start(bestekId, model);
    }

    private void basicSetup(Model model, Long bestekId) throws Exception {
        super.startBasic(bestekId, model);

    }

    @RequestMapping(value = "/bestek/{bestekId}/schuldvorderingen/verwijder", method = RequestMethod.GET)
    public String verwijder(@PathVariable String bestekId, @RequestParam("vordering_id") Integer vorderingId) throws Exception {
        schuldvorderingService.deleteSchuldvordering(vorderingId);
        return "redirect:/s/bestek/"+bestekId+"/schuldvorderingen/";
    }


    @RequestMapping(value = "/bestek/{bestekId}/schuldvorderingen/verwijderWbs", method = RequestMethod.GET)
    public String verwijderWbs(@PathVariable String bestekId, @RequestParam("vordering_id") Integer vordering_id) throws Exception {
        schuldvorderingService.verwijderWbs(vordering_id);
        return "redirect:/s/bestek/"+bestekId+"/schuldvorderingen/";
    }

}
