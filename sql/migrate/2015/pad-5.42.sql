

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.42';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.dossier_organisatie_email
(
  dossier_id int not null,
  organisatie_id int not null,
  email varchar(120) not null,
  primary key (dossier_id, organisatie_id, email),
  foreign key fk_dosorgema_dossier (dossier_id)
      references art46.dossier (id)
      on delete restrict
);


alter table art46.offerte
    add column organisatie_id int
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.42');