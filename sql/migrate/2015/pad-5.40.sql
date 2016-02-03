

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.offerte_abonnee;


delete from  art46.db_versie
where db_versie = '5.40';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.offerte_abonnee
(
  offerte_id int not null,
  email varchar(120) not null,
  primary key (offerte_id, email),
  foreign key (offerte_id) references art46.offerte (id)
);

create table art46.dossier_onderneming_email
(
  dossier_id int not null,
  ondernemingsnummer varchar(12) not null,
  email varchar(120) not null,
  primary key (dossier_id, ondernemingsnummer, email),
  foreign key fk_dosondlog_dossier (dossier_id)
      references art46.dossier (id)
      on delete restrict
);


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.40');