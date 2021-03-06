package be.ovam.art46.service.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    ScheduledOverdrachtService scheduledOverdrachtService;

    @Autowired
    ScheduledWebloketService scheduledWebloketService;

    private final static Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    
    @Scheduled(cron = "0 30 7-19 * * 1-5")
    public void registreerOverdrachtsFichesBijBestemmeling() {
        // nieuwe overdrachten aanmaken
        // wordt om het uur (op het half uur) getriggerd tijdens werkdagen.
        scheduledOverdrachtService.registreerOverdrachtsFichesBijBestemmeling();
    }


    //@Scheduled(cron = "0 */2 7-19 * * 1-5")
    @Scheduled(cron = "0 0 20 * * *")
    public void syncWebloketMedewerkersRol() {
        scheduledWebloketService.syncWebloketMedewerkersRol();
    }

    @Scheduled(cron = "0 30 6 * * *")
    public void verwijderDossierAbonnees() {
        scheduledWebloketService.verwijderDossierAbonnees();
    }

}