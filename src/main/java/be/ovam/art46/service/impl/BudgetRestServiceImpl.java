package be.ovam.art46.service.impl;

import be.ovam.art46.model.AanvraagVastlegging;
import be.ovam.art46.model.rest.SchuldvorderingBudget;
import be.ovam.art46.service.BudgetRestService;
import be.ovam.pad.model.SchuldVorderingSapProject;
import be.ovam.pad.model.Schuldvordering;
import be.ovam.util.mybatis.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Koen on 1/07/2014.
 */
@Service
@Transactional
public class BudgetRestServiceImpl implements BudgetRestService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SqlSession sqlSession;

    @Value("${ovam.budget.rest.url}/budgetreservering/aanvraagVastlegging.do")
    private String urlAanvraagvastlegging="";
    
    @Value("${ovam.budget.rest.url}/schuldvordering/in.do")
    private String urlSchuldvorderingIn;
    
    @Value("${ovam.budget.rest.url}/schuldvordering/annuleer.do")
    private String urlSchuldvorderingAnnuleer;

    public long verzend(AanvraagVastlegging aanvraagVastlegging) {
        return restTemplate.postForObject(urlAanvraagvastlegging, aanvraagVastlegging, Long.class);
    }


    public void verzendSchuldvordering(Integer vordering_id) {
        List<SchuldvorderingBudget> lijst =  sqlSession.selectList("be.ovam.art46.mappers.SchuldvorderingMapper.getSchuldvorderingDataForBudget", vordering_id);
        for (SchuldvorderingBudget schuldvorderingBudget : lijst) {
            verzend(schuldvorderingBudget);
        }
    }
    
    public long annuleer(Long schuldvorderingId){
        ResponseEntity<Long> forEntity = restTemplate.getForEntity(urlSchuldvorderingAnnuleer + "?schuldvorderingId=" + schuldvorderingId, Long.class);
        return forEntity.getBody();
    }

    public void verzend(SchuldvorderingBudget schuldvorderingBudget){
        if(schuldvorderingBudget.getWbs()!=null && schuldvorderingBudget.getBedrag()!=null) {
            restTemplate.postForObject(urlSchuldvorderingIn, schuldvorderingBudget, Long.class);
        }
    }

    public void setUrlSchuldvorderingIn(String urlSchuldvorderingIn) {
        this.urlSchuldvorderingIn = urlSchuldvorderingIn;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
