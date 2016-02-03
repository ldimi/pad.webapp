package be.ovam.art46.service.impl;

import be.ovam.art46.model.*;
import be.ovam.art46.service.AanvraagVastleggingsService;
import be.ovam.art46.service.BudgetRestService;
import be.ovam.art46.service.BudgetairArtikelService;
import be.ovam.art46.util.Application;

import org.apache.commons.lang3.StringUtils;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by koencorstjens on 25-7-13.
 */
@Service
@Transactional
public class AanvraagVastleggingsServiceImpl implements AanvraagVastleggingsService {
    
    @Autowired
    @Qualifier("sqlSession")
    SqlSession sqlSession;
    
    @Autowired
    BudgetairArtikelService budgetairArtikelService;
    
    @Value("${pad.vastleggingsaanvraag.watcher}")
    private String watcherUid="";

    @Autowired
    private BudgetRestService budgetRestService;

    @SuppressWarnings("rawtypes")
    public void verzend(AanvraagVastlegging aanvraagVastlegging) throws Exception {
        
        aanvraagVastlegging = saveAanvraag(aanvraagVastlegging);
                
        aanvraagVastlegging.setGunningsbeslissingDms(getDmsFor(aanvraagVastlegging.getGunningsbeslissing()));
        aanvraagVastlegging.setGunningsverslagDms(getDmsFor(aanvraagVastlegging.getGunningsverslag()));
        aanvraagVastlegging.setOvereenkomstDms(getDmsFor(aanvraagVastlegging.getOvereenkomst()));

        aanvraagVastlegging.setBudgetair_artikel_b(budgetairArtikelService.getBudgetairArtikel(aanvraagVastlegging.getBudgetairartikel()));
        
        Map schuldeiser = (Map) sqlSession.selectOne("be.ovam.art46.mappers.BestekAdresMapper.getBestekOpdrachtHouder", aanvraagVastlegging.getOpdrachthouder_id());
        aanvraagVastlegging.setSchuldeiserNaam((String) schuldeiser.get("label"));
        aanvraagVastlegging.setSchuldeiserGemeente((String) schuldeiser.get("gemeente"));
        aanvraagVastlegging.setSchuldeiserAdres((String) schuldeiser.get("straat"));
        aanvraagVastlegging.setSchuldeiserPostcode((String) schuldeiser.get("postcode"));
        
        aanvraagVastlegging.setUid(Application.INSTANCE.getUser_id());
        aanvraagVastlegging.setWatcher(watcherUid);
        
        aanvraagVastlegging.setAanvraagid(budgetRestService.verzend(aanvraagVastlegging));
        aanvraagVastlegging.setGeweigerd(StringUtils.EMPTY);
        sqlSession.update("be.ovam.art46.mappers.AanvraagVastleggingMapper.update", aanvraagVastlegging);
    }

    private String getDmsFor(Integer briefId) {
        return sqlSession.selectOne("be.ovam.art46.mappers.BriefMapper.getDMSid",briefId);
    }

    public void weigerVastlegging(RequestWeigerVastlegging requestWeigerVastlegging) {
        if (StringUtils.isEmpty(requestWeigerVastlegging.getComentaar())){
            requestWeigerVastlegging.setComentaar(StringUtils.EMPTY);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("aanvraagId", requestWeigerVastlegging.getId());
        params.put("commentaar", requestWeigerVastlegging.getComentaar());
        sqlSession.update("be.ovam.art46.mappers.AanvraagVastleggingMapper.weiger", params);
    }

    public AanvraagVastlegging saveAanvraag(AanvraagVastlegging aanvraagVastlegging) throws Exception {
        if (aanvraagVastlegging.getId() == null || aanvraagVastlegging.getId() == 0){
            sqlSession.insert("be.ovam.art46.mappers.AanvraagVastleggingMapper.insert", aanvraagVastlegging);
        } else {
            sqlSession.update("be.ovam.art46.mappers.AanvraagVastleggingMapper.update", aanvraagVastlegging);
        }
        saveSpreiding(aanvraagVastlegging.getId(), aanvraagVastlegging.getSpreiding());
        saveBrieven(aanvraagVastlegging.getId(), aanvraagVastlegging.getOptineleBestanden());
        return aanvraagVastlegging;
    }

    public void saveSpreiding(int aanvraagId, List<Spreiding> spreidings) throws Exception{
        if (spreidings==null){
            return;
        }
        for (Spreiding spreiding: spreidings) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("bedrag", spreiding.getBedrag());
            params.put("jaar", spreiding.getJaar());
            params.put("aanvraagId", aanvraagId);
            if(spreiding.getAanvraagid()==0){
                sqlSession.insert("be.ovam.art46.mappers.AanvraagVastleggingSpreidingMapper.insert", params);
            } else {
                sqlSession.update("be.ovam.art46.mappers.AanvraagVastleggingSpreidingMapper.update", params);
            }
        }
    }

    public void saveBrieven(int aanvraagId, List<OptioneelBestand> bestanden) throws Exception{
        if (bestanden==null){
            return;
        }
        for (OptioneelBestand bestand: bestanden) {
            if(bestand!=null && bestand.getBrief_id()!=null) {
                if(bestand.getAanvraagid()==null || bestand.getAanvraagid()==0){
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("aanvraagId", aanvraagId);
                    params.put("briefId", bestand.getBrief_id());
                    sqlSession.insert("be.ovam.art46.mappers.AanvraagVastleggingBrievenMapper.insert", params);
                }
            }
        }
    }

}
