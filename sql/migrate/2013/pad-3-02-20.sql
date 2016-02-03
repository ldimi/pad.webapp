
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

  ALTER TABLE ART46.JAARBUDGET_MIJLPAAL
     drop constraint CC_MIJLPAAL_D
  ;



  ALTER TABLE ART46.DOSSIER_HOUDER    
    DROP CONSTRAINT FK_DOHO_PROGRAMMA_TYPE
    DROP CONSTRAINT FK_DOHO_KLANT_AMBTENAAR
    DROP CONSTRAINT FK_DOHO_VERVANGER
 ;

  ALTER TABLE ART46.DOSSIER_HOUDER
    DROP column programma_type_code;
    
call sysproc.admin_cmd('reorg table ART46.DOSSIER_HOUDER')
;
    
  ALTER TABLE ART46.DOSSIER_HOUDER
    DROP column vervanger;           

call sysproc.admin_cmd('reorg table ART46.DOSSIER_HOUDER')
;
  

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.20';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;
  
  ALTER TABLE ART46.DOSSIER_HOUDER
    ADD programma_type_code VARCHAR(5);

call sysproc.admin_cmd('reorg table ART46.DOSSIER_HOUDER')
;


  ALTER TABLE ART46.DOSSIER_HOUDER    
    ADD CONSTRAINT FK_DOHO_PROGRAMMA_TYPE FOREIGN KEY (programma_type_code)
    REFERENCES ART46.PROGRAMMA_TYPE(code);
    
  ALTER TABLE ART46.DOSSIER_HOUDER    
    ADD CONSTRAINT FK_DOHO_KLANT_AMBTENAAR FOREIGN KEY (doss_hdr_id)
    REFERENCES SMEG.KLANT_AMBTENAAR(uid);
    
  ALTER TABLE ART46.DOSSIER_HOUDER
    ADD vervanger VARCHAR(8);
    
call sysproc.admin_cmd('reorg table ART46.DOSSIER_HOUDER')
;

  ALTER TABLE ART46.DOSSIER_HOUDER    
    ADD CONSTRAINT FK_DOHO_VERVANGER FOREIGN KEY (vervanger)
    REFERENCES ART46.DOSSIER_HOUDER(DOSS_HDR_ID);
    
    
-- jaarbudget_mijlpaal
  ALTER TABLE ART46.JAARBUDGET_MIJLPAAL
     add constraint CC_MIJLPAAL_D  check( year(mijlpaal_d) = jaar)
  ;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.20');
