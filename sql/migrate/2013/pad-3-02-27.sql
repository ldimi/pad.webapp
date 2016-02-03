
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.PLANNING_LIJN_VERSIE
    drop constraint cc_actie_code_contract
    drop constraint cc_contract_id_type
;

DROP TRIGGER ART46.BU_DOSSIER_DOSSIER_ID
;


ALTER TABLE ART46.SCHULDVORDERING    
    DROP CONSTRAINT FK_SV_DEELOPDRACHT
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.27';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING    
    ADD CONSTRAINT FK_SV_DEELOPDRACHT FOREIGN KEY (deelopdracht_id)
    REFERENCES ART46.DEELOPDRACHT(deelopdracht_id);

    
-- om te voorkomen dat dossier elkaar overschrijven    
-- vermoedelijk in het verleden voorgevallen (09016-6 vs 06138-8)    
CREATE TRIGGER ART46.BU_DOSSIER_DOSSIER_ID
BEFORE UPDATE ON ART46.DOSSIER
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
WHEN (O.DOSSIER_ID not like '\_%' ESCAPE '\' and 
      N.DOSSIER_ID <> O.DOSSIER_ID)
       SIGNAL SQLSTATE '75000' SET MESSAGE_TEXT='Dossier_id mag niet gewijzigd worden'
;


-- constraints op planning

alter table art46.PLANNING_LIJN_VERSIE
    add constraint cc_actie_code_contract CHECK
        ( ((actie_code is null or actie_code = 'N_B' or actie_code = 'H_B') and contract_id is null) or
           (actie_code = 'RC' or actie_code = 'GGO'))
;

alter table art46.PLANNING_LIJN_VERSIE
    add constraint cc_contract_id_type CHECK
        ( (contract_id is null and contract_type is null) OR
          (contract_id is not null and contract_type is not null))
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.27');
