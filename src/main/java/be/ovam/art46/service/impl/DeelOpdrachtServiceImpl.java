package be.ovam.art46.service.impl;

import be.ovam.art46.dao.DeelOpdrachtDao;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.art46.util.Application;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.DeelOpdrachtDO;
import be.ovam.pad.model.VoorstelDeelopdracht;

import be.ovam.util.mybatis.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Koen on 6/05/2014.
 */
@Service
@Transactional
public class DeelOpdrachtServiceImpl implements DeelOpdrachtService {
    
    @Autowired
    private DeelOpdrachtDao deelOpdrachtDao;
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;
    @Autowired
    private BriefService briefService;
    @Autowired
    private SqlSession sqlSession;


    private Logger log = Logger.getLogger(DeelOpdrachtServiceImpl.class);

    public List<DeelOpdracht> getMogelijkeDeelopdrachtenVoorVoorstel(Long voorstelId) {
        if(voorstelId==null){
            return new ArrayList<DeelOpdracht>();
        }
        VoorstelDeelopdracht voorstelDeelopdracht = voorstelDeelopdrachtService.get(voorstelId);
        if(voorstelDeelopdracht.getOfferte()==null){
            return new ArrayList<DeelOpdracht>();
        }
        return deelOpdrachtDao.getDeelopdrachten(voorstelDeelopdracht.getOfferte().getId(), voorstelDeelopdracht.getDossier().getId());

    }

    public DeelOpdracht get(Integer id) {
        return deelOpdrachtDao.get(id);
    }

    public Integer uploadBrief(Integer deelopdracht_id, String name, byte[] content)  throws Exception {
        int briefId = 0;
        try {
            DeelOpdrachtDO deelopdracht = getDeelopdrachtBy(deelopdracht_id);
            String commentaar = deelopdracht.getBestek_nr()+" "+" "+deelopdracht.getDossier_b_l()+": "+name;
            Brief brief = briefService.makeDocument(deelopdracht.getAnder_dossier_id(), BriefService.CATEGORIE_ID_VOORSTEL_DEELOPDRACHTEN, Application.INSTANCE.getUser_id(), commentaar, deelopdracht.getBestek_id());
            briefService.uploadBrief(brief.getBrief_id(), content, name);
            briefId = brief.getBrief_id();
            if (deelopdracht_id!=0) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("deelopdracht_id", deelopdracht_id);
                params.put("brief_id", briefId);
                sqlSession.insert("be.ovam.art46.mappers.DeelopdrachtBriefMapper.insert",params);
            }
        } catch (IOException e) {
            log.error("error when uploading file  " + e, e);
            throw e;
        }
        return briefId;
        
    };


    public void save(DeelOpdracht deelOpdracht) {
        if(deelOpdracht!=null) {
            deelOpdrachtDao.save(deelOpdracht);
        }
    }

    public DeelOpdrachtDO getDeelopdrachtBy(int id) {
        DeelOpdrachtDO deelOpdrachtDO = sqlSession.selectOne("be.ovam.art46.mappers.DeelopdrachtMapper.getDeelopdrachtById", id);
        deelOpdrachtDO.setCurrent_doss_hdr_id(Application.INSTANCE.getUser_id());
        return deelOpdrachtDO;
    }

}
