package be.ovam.art46.controller.taken.secretariaat;

import static be.ovam.web.util.JsView.jsview;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PrintBriefTakenController {
    
    @Autowired
    private SqlSession sqlSession;

    @RequestMapping(value = "/brief/afgedrukt/{brief_id}", method = RequestMethod.GET)
    public String afgedrukt(@PathVariable("brief_id") Integer brief_id){
        sqlSession.update("be.ovam.art46.mappers.BriefMapper.updateAfgedrukt", brief_id);
        return "redirect:/s/taken/brief/printen";
    }

    @RequestMapping(value = "/taken/brief/printen", method = RequestMethod.GET)
    public String get(Model model){
        
        model.addAttribute("tePrintenBrieven", tePrintenBrieven());
        model.addAttribute("dms_webdrive_base", System.getProperty("ovam.dms.webdrive.base"));
        
        model.addAttribute("title","te printen brieven");
        model.addAttribute("menuId","m_taken.printBrief");
        return jsview("taken/secretariaat/printBriefTaken", model);
    }

    @SuppressWarnings({ "rawtypes"})
    private List tePrintenBrieven(){
        return sqlSession.selectList("be.ovam.art46.mappers.BriefMapper.tePrintenBrieven", null);
    }

}

