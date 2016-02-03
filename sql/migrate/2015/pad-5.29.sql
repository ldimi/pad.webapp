--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.screening_stofgroep;

drop table art46.screening_parameter;



delete from  art46.db_versie
where db_versie = '5.29';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


create table art46.screening_parameter
(
    overdracht_id integer not null,
    medium_code varchar(5) not null,
    parameter_id int not null,
    attest_aard_id int not null,
    primary key (overdracht_id, medium_code, parameter_id, attest_aard_id),
    foreign key fk_scrpar_parameter (parameter_id)
         references smeg_ref.parameter (id)
         on delete restrict,
    foreign key fk_scrpar_medium (medium_code)
         references art46.screening_medium_code (medium_code)
         on delete restrict,
    foreign key fk_scrpar_aard (attest_aard_id)
         references smeg_ref.attest_aard (id)
         on delete restrict
);

create table art46.screening_stofgroep
(
    overdracht_id integer not null,
    medium_code varchar(5) not null,
    stofgroep_code varchar(25) not null,
    bepalend_jn char(1) not null default 'N',
    primary key (overdracht_id, medium_code, stofgroep_code),
    foreign key fk_scrstfg_medium (medium_code)
         references art46.screening_medium_code (medium_code)
         on delete restrict,
    foreign key fk_scrstfg_stfg_code (stofgroep_code)
         references art46.screening_stofgroep_code (stofgroep_code)
         on delete restrict,
    foreign key fk_scrstfg_bepalend_jn (bepalend_jn)
         references art46.ja_nee_code (code)
         on delete restrict
);





-- insert into art46.screening_parameter
-- (overdracht_id, medium_code, parameter_id, attest_aard_id)
-- values
-- (121, 'GW', 72, 1),
-- (121, 'GW', 73, 1),
-- (121, 'GW', 74, 3),
-- (121, 'VDA', 72, 5),
-- (121, 'VDA', 75, 5),
-- (121, 'ANDER', 159, 2),
-- (122, 'VDA', 134, 2),
-- (122, 'ZKL', 135, 2)
-- ;

-- insert into art46.screening_stofgroep
-- (overdracht_id, medium_code, stofgroep_code, bepalend_jn)
-- values
-- (121, 'GW', 'BTEX', 'N'),
-- (121, 'GW', 'Teer', 'J'),
-- (121, 'VDA', 'Zware metalen', 'N'),
-- (122, 'GW', 'Pesticiden', 'N'),
-- (122, 'ZKL', 'Stortmateriaal', 'J')
-- ;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.29');
