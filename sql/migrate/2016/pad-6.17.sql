

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.17';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.dossier_parameter
(
    dossier_id integer not null,
    medium_code varchar(5) not null,
    parameter_id int not null,
    attest_aard_id int not null,
    primary key (dossier_id, medium_code, parameter_id, attest_aard_id),
    foreign key fk_dospar_dossier (dossier_id)
         references art46.dossier (id)
         on delete restrict,
    foreign key fk_dospar_parameter (parameter_id)
         references smeg_ref.parameter (id)
         on delete restrict,
    foreign key fk_dospar_medium (medium_code)
         references art46.screening_medium_code (medium_code)
         on delete restrict,
    foreign key fk_dospar_aard (attest_aard_id)
         references smeg_ref.attest_aard (id)
         on delete restrict
);

create table art46.dossier_stofgroep
(
    dossier_id integer not null,
    medium_code varchar(5) not null,
    stofgroep_code varchar(25) not null,
    bepalend_jn char(1) not null default 'N',
    primary key (dossier_id, medium_code, stofgroep_code),
    foreign key fk_dosstfg_dossier (dossier_id)
         references art46.dossier (id)
         on delete restrict,
    foreign key fk_dosstfg_medium (medium_code)
         references art46.screening_medium_code (medium_code)
         on delete restrict,
    foreign key fk_dosstfg_stfg_code (stofgroep_code)
         references art46.screening_stofgroep_code (stofgroep_code)
         on delete restrict,
    foreign key fk_dosstfg_bepalend_jn (bepalend_jn)
         references art46.ja_nee_code (code)
         on delete restrict
);

insert into art46.dossier_parameter
(dossier_id, medium_code, parameter_id, attest_aard_id)
select
     doso.DOSSIER_ID,
     sp.MEDIUM_CODE,
     sp.PARAMETER_ID,
     sp.ATTEST_AARD_ID 
from art46.SCREENING_PARAMETER sp
		left join  art46.DOSSIER_OVERDRACHT doso
		on sp.OVERDRACHT_ID = doso.OVERDRACHT_ID
;

insert into art46.dossier_stofgroep
(dossier_id, medium_code, stofgroep_code, bepalend_jn)
select
     doso.DOSSIER_ID,
     sp.MEDIUM_CODE,
     sp.stofgroep_code,
     sp.bepalend_jn 
from art46.SCREENING_stofgroep sp
		left join  art46.DOSSIER_OVERDRACHT doso
		on sp.OVERDRACHT_ID = doso.OVERDRACHT_ID
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.17');
