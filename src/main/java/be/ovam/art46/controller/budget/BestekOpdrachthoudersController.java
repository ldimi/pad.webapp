package be.ovam.art46.controller.budget;

import be.ovam.art46.controller.BasicBestekController;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by koencorstjens on 20-8-13.
 */
@Controller
public class BestekOpdrachthoudersController extends BasicBestekController{

    @Autowired
    @Qualifier("sqlSession")
    SqlSession sqlSession;

    @RequestMapping(value = "/bestek/{bestekId}/opdrachthouders/", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId, model);
        model.addAttribute("bestekadressen", sqlSession.selectList("be.ovam.art46.mappers.BestekAdresMapper.getBestekOpdrachthouders", bestekId));
        return "bestek.opdrachthouders";
    }
    @RequestMapping(value = "/bestek/{bestekId}/opdrachthouders/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String bestekId, @RequestParam("adres_id") String adresId, @RequestParam("contact_id") String contactId, Model model) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("bestekId",bestekId);
        params.put("adresId", adresId);
        params.put("contactId", contactId);
        sqlSession.delete("be.ovam.art46.mappers.BestekAdresMapper.deleteBestekAdres", params);
        return "redirect:/s/bestek/"+bestekId+"/opdrachthouders/";
    }

}
