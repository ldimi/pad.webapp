

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT CC_DOSSIER_B ;

ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT CC_DOSSIER_B  CHECK (
    (dossier_id not like '\_%' ESCAPE '\' and dossier_b is not null and dossier_b <> '')
    OR
    (dossier_id like '\_%' ESCAPE '\' and dossier_b is null and ( dossier_type = 'B' ) )
)
;


delete from  art46.db_versie
where db_versie = '6.02';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT CC_DOSSIER_B ;

ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT CC_DOSSIER_B  CHECK (
    (dossier_id not like '\_%' ESCAPE '\' and dossier_b is not null and dossier_b <> '')
    OR
    (dossier_id like '\_%' ESCAPE '\' and dossier_b is null and dossier_type = 'B' )
    OR
    (dossier_id like '\_a\_%' ESCAPE '\' and dossier_b is null and dossier_type = 'A' )
)
;

drop trigger ART46.BI_DOSSIER_DOSSIER_ID ;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.02');
