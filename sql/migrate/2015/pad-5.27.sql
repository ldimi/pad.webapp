--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.27';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table art46.dossier_rol
    add primary key (key)
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.DOSSIER_ROL');


alter table art46.webloket_gebruiker_dossier
    add foreign key fk_wlgd_dossier_rol (rol) REFERENCES ART46.dossier_rol (key) ON DELETE RESTRICT
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.27');
