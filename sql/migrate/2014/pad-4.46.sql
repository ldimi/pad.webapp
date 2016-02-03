--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.dossier_overdracht_hist;

drop table art46.dossier_overdracht;

drop table art46.dossier_overdracht_status;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.46';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.dossier_overdracht_status
(
    code varchar(10) not null,
    omschrijving varchar(50),
    volg_nr smallint,
    primary key (code)
);


create table art46.dossier_overdracht
(
   overdracht_id smallint not null generated by default as identity (start with 100),
   versie_nr smallint not null,
   dossier_type char(1) not null,
   dossier_id_boa integer,
   dossier_nr_afval varchar(10),
   rechtsgrond_code varchar(5),
   commentaar varchar(1000),
   screener varchar(25),
   screen_bestek_nr varchar(8),
   overdracht_d date not null,
   status_start_d date not null default current date,
   status varchar(10),
   deleted_jn char(1) not null default 'N',
   wijzig_ts timestamp not null default current timestamp,
   wijzig_user varchar(25) not null default 'user',
   primary key (overdracht_id),
   foreign key fk_dov_rechtsgrond(rechtsgrond_code, dossier_type)
           references art46.dossier_rechtsgrond (rechtsgrond_code, dossier_type) on delete restrict,
   foreign key fk_dov_dossier_type(dossier_type)
           references art46.dossier_type (dossier_type) on delete restrict,
   foreign key fk_dov_status(status)
           references art46.dossier_overdracht_status (code) on delete restrict,
   foreign key fk_dov_deleted_jn(deleted_jn)
           references art46.ja_nee_code(code) on delete restrict
);

create trigger art46.dossier_overdracht_ts
no cascade before update on art46.dossier_overdracht
referencing
    old as pre
    new as post
for each row mode db2sql
set
        post.wijzig_ts = current timestamp
;



create table art46.dossier_overdracht_hist
(
   overdracht_id smallint not null generated by default as identity (start with 100),
   versie_nr smallint not null,
   dossier_type char(1) not null,
   dossier_id_boa integer,
   dossier_nr_afval varchar(10),
   rechtsgrond_code varchar(5),
   commentaar varchar(1000),
   screener varchar(25),
   screen_bestek_nr varchar(8),
   overdracht_d date not null,
   status_start_d date not null,
   status varchar(10) not null,
   deleted_jn char(1) not null,
   wijzig_ts timestamp not null default current timestamp,
   wijzig_user varchar(25) not null,
   primary key (overdracht_id, versie_nr),
   foreign key FK_dovh_overdracht (overdracht_id)
         references ART46.dossier_overdracht (overdracht_id)
         on delete cascade,
   foreign key fk_dov_deleted_jn(deleted_jn)
           references art46.ja_nee_code(code) on delete restrict
);

create trigger art46.au_dossier_overdr_hist
after update on art46.dossier_overdracht
referencing
    old as pre
    new as post
for each row mode db2sql
insert into art46.dossier_overdracht_hist (
       overdracht_id,
       versie_nr,
       dossier_type,
       dossier_id_boa,
       dossier_nr_afval,
       rechtsgrond_code,
       commentaar,
       screener,
       screen_bestek_nr,
       overdracht_d,
       status_start_d,
       status,
       deleted_jn,
       wijzig_user
    )
values (
       pre.overdracht_id,
       pre.versie_nr,
       pre.dossier_type,
       pre.dossier_id_boa,
       pre.dossier_nr_afval,
       pre.rechtsgrond_code,
       pre.commentaar,
       pre.screener,
       pre.screen_bestek_nr,
       pre.overdracht_d,
       pre.status_start_d,
       pre.status,
       pre.deleted_jn,
       pre.wijzig_user
)
;


----------------------------------------------------------------------------------------------


insert into art46.dossier_overdracht_status (code, omschrijving, volg_nr)
values
    ('registrati','registratie (voorstel)', 1),
    ('instroom','instroom (acceptatie)', 2),
    ('screening','in screening', 3),
    ('na_screen','screening afgerond (nascreen)', 4),
    ('bib','bibliotheek', 5),
    ('uitstroom','uitstroom', 6),
    ('naar_ivs','naar ivs (toegewezen)', 7),
    ('geklassrd','geklasseerd (afgewezen)', 8)
;


--insert into art46.dossier_overdracht
--(overdracht_id, versie_nr, dossier_type, dossier_id_boa, overdracht_d, status, wijzig_user)
--values
--(1,1,'B', 97, current date, 'voorstel', 'registratie')
--;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.46');