

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.01';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create trigger art46.bu_dossier_overdracht_versie_nr no cascade before
update on art46.dossier_overdracht referencing old as o new as n for each row mode db2sql when
(
   n.versie_nr != o.versie_nr + 1
)
signal sqlstate '75000' set message_text='ongeldig versie_nr. (stale data)'
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.01');
