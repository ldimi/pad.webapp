

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.06';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier_instrument
    drop primary key
;

alter table art46.dossier_instrument
    alter column dossier_id set not null
;

call sysproc.admin_cmd('reorg table ART46.dossier_instrument');


alter table art46.dossier_instrument
    add primary key (dossier_id, instrument_type_id)
;

alter table art46.dossier_instrument
    drop column id
    drop column overdracht_id
;

call sysproc.admin_cmd('reorg table ART46.dossier_instrument');

alter table art46.dossier_instrument
    drop foreign key FK_DOSINSTR_DOSSIER
;

alter table art46.dossier_instrument
    add foreign key FK_DOSINSTR_DOSSIER (dossier_id, dossier_type)
         references art46.dossier (id, dossier_type)
         on delete restrict
;




create table art46.dossier_verontreinig_activiteit (
    dossier_id int not null,
    activiteit_type_id int not null,
    dossier_type char(1) not null,
    primary key (dossier_id, activiteit_type_id),
    foreign key fk_verontact_type (activiteit_type_id)
         references art46.verontreinig_activiteit_type (activiteit_type_id)
         on delete restrict,
    foreign key fk_verontact_dossier (dossier_id, dossier_type)
         references art46.dossier (id, dossier_type)
         on delete restrict,
    foreign key fk_verontact_dossier_type (dossier_type)
         references art46.dossier_type (dossier_type)
         on delete restrict
);

insert into art46.dossier_verontreinig_activiteit
(dossier_id, activiteit_type_id, dossier_type)
select distinct dossier_id, activiteit_type_id, dossier_type
from art46.verontreinig_activiteit
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.06');
