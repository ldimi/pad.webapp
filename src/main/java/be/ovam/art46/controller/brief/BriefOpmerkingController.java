package be.ovam.art46.controller.brief;

import be.ovam.art46.model.BriefOpmerking;
import be.ovam.art46.util.Application;
import be.ovam.web.Response;

import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class BriefOpmerkingController {

    @Autowired
    private SqlSession sqlSession;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/brief/{brief_id}/opmerkingen", method = RequestMethod.GET)
    public @ResponseBody
    Response getOpmerkingen(@PathVariable Integer brief_id) {
        List result = sqlSession.selectList("be.ovam.art46.mappers.BriefMapper.getBriefOpmerkingen", brief_id);
        return new Response(result, true, null);
    }

    
    @RequestMapping(value = "/brief/opmerking/update", method = RequestMethod.POST)
    public @ResponseBody Response update(@RequestBody BriefOpmerking opmerking) {
        sqlSession.update("be.ovam.art46.mappers.BriefMapper.updateBriefOpmerking", opmerking);     
        return getOpmerkingen(opmerking.getBrief_id());
    }
    
    @RequestMapping(value = "/brief/opmerking/insert", method = RequestMethod.POST)
    public @ResponseBody Response insert(@RequestBody BriefOpmerking opmerking) {
        
        opmerking.setAuteur(Application.INSTANCE.getUser_id());
        opmerking.setWijzig_user(Application.INSTANCE.getUser_id());
        opmerking.setCreatie_d(new Date());
        opmerking.setVerwijderd_jn("N");
        
        sqlSession.update("be.ovam.art46.mappers.BriefMapper.insertBriefOpmerking", opmerking);     
        return getOpmerkingen(opmerking.getBrief_id());
    }

    @RequestMapping(value = "/brief/opmerking/{opmerking_id}/behandeld", method = RequestMethod.POST)
    public @ResponseBody Response briefOpmerkingBehandeld(@PathVariable Integer opmerking_id){
        BriefOpmerking opm = sqlSession.selectOne("be.ovam.art46.mappers.BriefMapper.getBriefOpmerking", opmerking_id);
        
        opm.setBehandeld_d(new Date());
        this.update(opm);
        
        return new Response(true, null);
        
    }
    
    

}
