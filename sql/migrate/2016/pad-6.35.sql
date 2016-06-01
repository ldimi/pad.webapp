

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.35';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.dossier_organisatie
(
  dossier_id int not null,
  organisatie_id int not null,
  primary key (dossier_id, organisatie_id),
  foreign key fk_dosorg_dossier (dossier_id)
      references art46.dossier (id)
      on delete restrict
);


insert into art46.dossier_organisatie (dossier_id, organisatie_id)
select distinct dossier_id, organisatie_id
from art46.dossier_organisatie_email
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.35', 'create table art46.dossier_organisatie');
