package be.ovam.art46.service.sluis;

import be.ovam.art46.controller.brief.BriefServiceController;
import be.ovam.util.mybatis.SqlSession;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    @Qualifier("sqlSession")
    protected SqlSession sql;

    @Autowired
    private SqlSession ovamcore_sqlSession;

    private final static Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    
    @Scheduled(cron = "0 30 7-19 * * 1-5")
    public void registreerOverdrachtsFichesBijBestemmeling() {
        // nieuwe overdrachten aanmaken
        // wordt om het uur (op het half uur) getriggerd tijdens werkdagen.
        sql.insert("registreerOverdrachtsFichesBijBestemmeling");
    }


    @Scheduled(cron = "0 */1 7-19 * * 1-5")
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




}