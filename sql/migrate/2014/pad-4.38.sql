--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.38';


--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.jaarbudget add column effectief_budget int ;

CALL SYSPROC.ADMIN_CMD('REORG TABLE art46.jaarbudget');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.38 ');




























