package be.ovam.art46.service.scheduled;

import be.ovam.util.mybatis.SqlSession;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ScheduledWebloketService {
	
    @Autowired
    @Qualifier("sqlSession")
    protected SqlSession sql;

    @Autowired
    private SqlSession ovamcore_sqlSession;

    private final static Logger log = LoggerFactory.getLogger(ScheduledWebloketService.class);
    
    
    public void syncWebloketMedewerkersRol() {
        // nieuwe overdrachten aanmaken
        // wordt om het uur (op het half uur) getriggerd tijdens werkdagen.
        
        log.info("start syncWebloketMedewerkersRol");
        
        List<Map> medewerkersRollen = ovamcore_sqlSession.selectList("medewerkersrol_actief_alle");
        
        if (medewerkersRollen.isEmpty()) {
            throw new RuntimeException("Geen wedewerkersRollen gevonden in ovamcore");
        }
        
        sql.delete("delete_all_medewerkersrollen");
        
        for (Map medewerkersRol : medewerkersRollen) {
            sql.insertInTable("art46", "webloket_medewerkersrol", medewerkersRol);
        }
    }
    
    public void verwijderDossierAbonnees () {
        log.info("start removeDossierAbonnees");
        Integer deleted = sql.delete("be.ovam.art46.mappers.DossierMapper.verwijderDossierAbonnees");
        log.info("stop removeDossierAbonnees : aantal verwijderde abonnees = " + deleted);
    }
    
}
