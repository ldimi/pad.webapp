package be.ovam.art46.service;

import be.ovam.art46.dao.BriefDAO;
import be.ovam.pad.model.Brief;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TestService {
    
    @Autowired
    private BriefDAO briefDAO;

    public void updateBrief(Integer briefId) throws Exception {
        Brief brief = briefDAO.get(briefId);
        brief.setCommentaar("updated:" + brief.getCommentaar());
        briefDAO.save(brief);
    }
    
}
