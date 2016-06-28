package be.ovam.art46.service.sluis;

import be.ovam.util.mybatis.SqlSession;


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

    @Scheduled(cron = "0 30 7-19 * * 1-5")
    public void registreerOverdrachtsFichesBijBestemmeling() {
        // nieuwe overdrachten aanmaken
        // wordt om het uur (op het half uur) getriggerd tijdens werkdagen.
        sql.insert("registreerOverdrachtsFichesBijBestemmeling");
    }


//    @Scheduled(cron = "0 30 7-19 * * 1-5")
//    public void registreerOverdrachtsFichesBijBestemmeli() {
//        // nieuwe overdrachten aanmaken
//        // wordt om het uur (op het half uur) getriggerd tijdens werkdagen.
//        sql.insert("registreerOverdrachtsFichesBijBestemmeling");
//    }




}