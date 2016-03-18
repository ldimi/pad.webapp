

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.dossier_taak;

delete from  art46.db_versie
where db_versie = '6.24';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.taak_type (
	taak_type varchar(50) not null,
    taak_b varchar(50) not null,
	taak_type_volgnr int not null,
    primary key (taak_type)
);

insert into art46.taak_type
    (taak_type, taak_b, taak_type_volgnr)
values
    ('schuldvordering goedkeuren', 'schuldvordering goedkeuren', 10),
    ('ondertekenen schuldvorderingen', 'ondertekenen schuldvorderingen', 20),
    ('scannen schuldvorderingen', 'scannen schuldvorderingen', 40),
    ('printen schuldvorderingen', 'printen schuldvorderingen', 50),
    ('printen brieven', 'printen brieven', 51),
    ('voorstel beoordelen', 'voorstel beoordelen', 60),
    ('brief_in_check_afd_hfd', 'inkomende brief', 80),
    ('brief_in_check_auteur', 'inkomende brief', 90),
    ('deelopdracht goedkeuren', 'deelopdracht goedkeuren', 100),
    ('nieuw PAD dossier', 'nieuw PAD dossier', 110)
;


alter table art46.dossier_taak
    add foreign key fk_dostaak_taak (taak_type)
         references art46.taak_type (taak_type)
         on delete restrict
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.24');
