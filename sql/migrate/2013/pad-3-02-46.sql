
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


ALTER TABLE ART46.DOSSIER    
    drop CONSTRAINT CC_BOA_ID
;
ALTER TABLE ART46.DOSSIER    
    add CONSTRAINT CC_BOA_ID CHECK(
        (dossier_type = 'B' and (dossier_id_boa is not null and dossier_id_boa != 0) )
        OR
        (dossier_type != 'B' and (dossier_id_boa is null or dossier_id_boa = 0) )
    )
;

ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT CC_DOSSIER_B
;
ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT C_DOSSIER_B  CHECK (DOSSIER_B <> '')
;


ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT CC_DOSSIER_TYPE
;
ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT C_DOSSIER_TYPE  CHECK (DOSSIER_TYPE in ('A', 'B', 'X'))
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.46';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


ALTER TABLE ART46.DOSSIER    
    drop CONSTRAINT CC_BOA_ID
;
ALTER TABLE ART46.DOSSIER    
    add CONSTRAINT CC_BOA_ID CHECK(
        (dossier_type = 'B' and (dossier_id_boa is not null and dossier_id_boa != 0) )
        OR
        (dossier_type != 'B' and (dossier_id_boa is null) )
    )
;


ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT CC_DOSSIER_B
;
ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT CC_DOSSIER_B  CHECK (
    (dossier_id not like '\_%' ESCAPE '\' and dossier_b is not null and dossier_b <> '')
    OR
    (dossier_id like '\_%' ESCAPE '\' and dossier_b is null and dossier_type = 'B')
)
;

-- rename van bestaande constraint
ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT C_DOSSIER_TYPE
;
ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT CC_DOSSIER_TYPE  CHECK (DOSSIER_TYPE in ('A', 'B', 'X'))
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.46');
