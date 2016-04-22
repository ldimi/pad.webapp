package be.ovam.art46.service;

import be.ovam.art46.common.mail.MailService;
import be.ovam.art46.dao.DeelOpdrachtDao;
import be.ovam.art46.struts.actionform.DeelopdrachtLijstForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class DeelOpdrachtService {
    
    @Autowired
    private DeelOpdrachtDao deelOpdrachtDao;
    
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;
    
    @Autowired
    private BriefService briefService;
    
    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    private MailService mailService;


    private Logger log = Logger.getLogger(DeelOpdrachtService.class);

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
    
//    public void saveDeelopdrachtGoedkeuring_d(DeelopdrachtLijstForm form) throws Exception {
//        Iterator iter = form.getGoedkeuring_ds().keySet().iterator();
//        String deelopdracht_id = null;
//        List<Object[]> paramList = new ArrayList<Object[]>();
//        while (iter.hasNext()) {
//            deelopdracht_id = (String) iter.next();
//            if (form.getGoedkeuring_ds().get(deelopdracht_id) != null && ((String) form.getGoedkeuring_ds().get(deelopdracht_id)).length() != 0) {
//                Object[] params = {((String) form.getGoedkeuring_ds().get(deelopdracht_id)).length() == 0 ? null : (String) form.getGoedkeuring_ds().get(deelopdracht_id), Integer.valueOf(deelopdracht_id)};
//                paramList.add(params);
//            }
//        }
//        saveDeelopdrachtGoedkeuring_d(paramList);
//        iter = paramList.iterator();
//        while (iter.hasNext()) {
//            sendEmailGoedkeuringDeelopdrachtDossierhouderDeelopdracht(Integer.valueOf(((Object[]) iter.next())[1].toString()));
//        }
//    }

//    public void saveDeelopdrachtGoedkeuring_d(List params) throws  Exception {
//        for (Object param : params) {
//            Object[] arr = (Object[]) param;
//            Map map = new HashMap();
//            map.put("goedkeuring_d", arr[0]);
//            map.put("deelopdracht_id", arr[1]);
//            
//            sqlSession.update("updateDeelopdracht_Goedkeuring_d", map);
//        }
//	}

    public void saveDeelopdrachtGoedkeuring_d(DeelopdrachtLijstForm form) throws Exception {
        Map deelopdrachtId_goedkeuring_d_map = form.getGoedkeuring_ds();
        for (Object keyObj : deelopdrachtId_goedkeuring_d_map.keySet()) {
            String deelopdracht_id = (String) keyObj;
            Object goedkeuring_d = deelopdrachtId_goedkeuring_d_map.get(deelopdracht_id); 
            if (goedkeuring_d != null && ((String) goedkeuring_d).length() != 0) {
                
                updateDeelopdracht_Goedkeuring_d(deelopdracht_id, (String) goedkeuring_d);
                sendEmailGoedkeuringDeelopdrachtDossierhouderDeelopdracht(new Integer(deelopdracht_id));
            }
        }
    }
    
	private void updateDeelopdracht_Goedkeuring_d(String deelopdracht_id, String goedkeuring_d) throws  Exception {
        Map map = new HashMap();
        map.put("deelopdracht_id", deelopdracht_id);
        map.put("goedkeuring_d", goedkeuring_d);

        sqlSession.update("updateDeelopdracht_Goedkeuring_d", map);
	}


    
    private void sendEmailGoedkeuringDeelopdrachtDossierhouderDeelopdracht(Integer deelopdracht_id) {
        try {
            DeelOpdrachtDO deelopdrachtDO = sqlSession.selectOne("getDeelopdrachtById", deelopdracht_id);
            String message = "In dossier  " + deelopdrachtDO.getAnder_dossier_b_l() + " (" + deelopdrachtDO.getAnder_dossier_nr() + ") werd voor " +
                    " bestek " + deelopdrachtDO.getBestek_nr() + " de volgende deelopdracht goedgekeurd: " + deelopdrachtDO.getDossier_b_l() + " (" + deelopdrachtDO.getDossier_nr() +
                    ").<br><br>" + 
                    " Meer info over het bestek op: " + LoadPlugin.url + "/s/bestek/" + deelopdrachtDO.getBestek_id();
            mailService.sendHTMLMail(deelopdrachtDO.getDoss_hdr_id() + "@ovam.be", "Goedkeuring deelopdracht", this.getPadMailAdres(), message);

            DeelOpdracht deelOpdracht = this.get(deelopdracht_id);
            VoorstelDeelopdracht voorstelDeelopdracht = deelOpdracht.getVoorstelDeelopdracht();
            if (voorstelDeelopdracht != null) {
                if (voorstelDeelopdracht.getOvamMail() != null) {
                    mailService.sendHTMLMail(voorstelDeelopdracht.getOvamMail());
                    voorstelDeelopdracht.setOvamMail(null);
                    this.save(deelOpdracht);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPadMailAdres() {
        String mailadres = System.getProperty("pad.mailadres");
        if (mailadres == null || mailadres.length() == 0) {
            mailadres = "pad@ovam.be";
        }
        return mailadres;
    }

}
