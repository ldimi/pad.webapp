****************************************************************
*  Nieuwe versie aanmaken
****************************************************************

- lokale code is volledig gesynchroniseerd met SVN
- lokale testen zijn ok
- release build naar tomcat server 
     mvn clean install -P release,deploy-to-applicationserver
  ( op http://test-in.ovam.be/pad kan je dan de release versie testen)
  ( Het 'release' profiel is noodzakelijk (ivm browser caching !!! )
    De release build copieert de javascript files naar een dir, met build.timestamp in het path.
    Deze build.timestamp zorgt ervoor dat de script zonder probleem in browsers kunnen gecached worden.)

  ( NB: de expireTime wordt op de server geconfigureerd in /conf/web.xml   > Filter customisation  )

- in pom.xml : -SNAPSHOT uit versie verwijderen  (4.XX-SNAPSHOT wordt 4.XX)
- commit pom.xml (er is dus geen veschil lokaal en svn-repository meer)

- tag version in SVN:
    - op projectniveau contextmenu
    - Team > Branch/Tag ...
        copy to url : http://10.1.11.15/svn/ovam/pad.webapp/tags/4.XX
        create copy in the repository from 
            * Head revision in the repository
        Edit the Branch/tag comment
        finish
        
- deploy versie naar NEXUS
    mvn clean deploy -P release -DskipTests=true
        ( Dit deployt versie 4.XX naar nexus,
          release profiel verzorgt de javascript build : 
             copieert de javascript files naar een dir, met build.timestamp in het path. 
        ) 
          
- in pom.xml : versie verhogen (vb : 4.31 wordt 4.32-SNAPSHOT)
- commit pom.xml

*****************************************************************
* versie vanuit BAMBOO deployen
*****************************************************************

- alle nodige DB-migrate-scripts (manueel) runnen op productie database;

- (PAD-Accept / PAD-PROD) > padDeploy job editeren
        Default Job > Builder > Target :  versie juist zetten (, en Save drukken)
- vanuit dashboard de deploy job runnen.

- even checken of alles gelukt is door pad te openen.




*****************************************************************
*  enkele MAVEN commando's
*****************************************************************

- lokale build
    mvn clean package
    
- lokale build, waarbij de web.xml in target dir getouched wordt om hotDeploy te triggeren.
    mvn clean package -P ant_dodeploy_exploded
    
- lokale release build (copieert de javascript files naar een dir, met build.timestamp in het path)
    mvn clean package -P release
    
- release build naar acceptatie server 
    mvn clean install -P release,deploy-to-applicationserver

- deploy versie naar NEXUS
    mvn clean deploy -P release 
   

******************************************************************
* allerlei
******************************************************************


    