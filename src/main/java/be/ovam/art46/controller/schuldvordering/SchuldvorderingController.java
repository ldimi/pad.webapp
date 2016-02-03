package be.ovam.art46.controller.schuldvordering;

import be.ovam.art46.model.SchuldvorderingDO;
import be.ovam.art46.model.SchuldvorderingData;
import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
public class SchuldvorderingController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private SchuldvorderingService schuldvorderingService;


	
	@RequestMapping(value = "/sv/get", method = RequestMethod.GET)
	public @ResponseBody
	SchuldvorderingData get(@RequestParam("vordering_id") Integer vordering_id) throws Exception {
	    return schuldvorderingService.getSchuldvorderingData(vordering_id);
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sv/nieuw", method = RequestMethod.GET)
    public @ResponseBody
    SchuldvorderingData getNieuw(@RequestParam("brief_id") Integer brief_id , @RequestParam("vordering_d") Date vordering_d  ) throws Exception {
        return schuldvorderingService.getNieuwSchuldvorderingData(brief_id, vordering_d);
    }

	@RequestMapping(value = "/sv/bewaar", method = RequestMethod.POST)
	public @ResponseBody SchuldvorderingData bewaar(@RequestBody SchuldvorderingData schuldvorderingData) throws Exception {
		return schuldvorderingService.save(schuldvorderingData.getSchuldvordering());
	}
	
    @RequestMapping(value = "/sv/afkeuren", method = RequestMethod.POST)
    public @ResponseBody SchuldvorderingData keurAf(@RequestBody SchuldvorderingData schuldvorderingData) throws Exception {
        SchuldvorderingDO schuldvorderingDO =  schuldvorderingData.getSchuldvordering();
        
        return schuldvorderingService.afkeuren(schuldvorderingDO);
    }

    @RequestMapping(value = "/sv/goedkeuren", method = RequestMethod.POST)
    public @ResponseBody SchuldvorderingData goedkeuren(@RequestBody SchuldvorderingData schuldvorderingData) throws Exception {
        SchuldvorderingDO schuldvorderingDO =  schuldvorderingData.getSchuldvordering();
        
        return schuldvorderingService.goedkeuren(schuldvorderingDO);
    }

		
	@SuppressWarnings({ "unche cked", "rawtypes" })
	@RequestMapping(value = "/sv/getVastleggingenCombo", method = RequestMethod.GET)
	public @ResponseBody
	List getVastleggingenCombo(@RequestParam("vordering_id") Integer vordering_id ,  Model model) throws Exception {
		return sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getVastleggingenCombo", vordering_id);
	}
	

}
