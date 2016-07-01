package be.ovam.art46.service.scheduled;

import be.ovam.util.mybatis.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ScheduledOverdrachtService {
	
    @Autowired
    @Qualifier("sqlSession")
    protected SqlSession sql;

    private final static Logger log = LoggerFactory.getLogger(ScheduledOverdrachtService.class);
    

    public void registreerOverdrachtsFichesBijBestemmeling() {
        // nieuwe overdrachten aanmaken
        // wordt om het uur (op het half uur) getriggerd tijdens werkdagen.
        sql.insert("registreerOverdrachtsFichesBijBestemmeling");
    }
    
}
