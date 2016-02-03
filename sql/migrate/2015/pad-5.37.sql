

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.dossier_onderneming
    drop constraint dossier_ondernem_uniek
;

alter table art46.dossier_onderneming
    add foreign key fk_dosond_dossier (dossier_id)
        references art46.dossier (id)
        on delete restrict
;

alter table art46.dossier_onderneming
    add foreign key fk_dosond_rol (rol)
        references art46.dossier_rol (key)
        on delete restrict
;

drop table art46.offerte_onderneming;


delete from  art46.db_versie
where db_versie = '5.37';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.offerte_onderneming
(
    offerte_id int not null,
    ondernemingsnummer varchar(12) not null,
    primary key (offerte_id, ondernemingsnummer),
    foreign key fk_ofond_offerte (offerte_id)
        references art46.offerte (id)
        on delete restrict
);


alter table art46.dossier_onderneming
    add constraint dossier_ondernem_uniek unique(dossier_id, ondernemingsnummer)
    add foreign key fk_dosond_dossier (dossier_id)
        references art46.dossier (id)
        on delete restrict
    add foreign key fk_dosond_rol (rol)
        references art46.dossier_rol (key)
        on delete restrict
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.37');