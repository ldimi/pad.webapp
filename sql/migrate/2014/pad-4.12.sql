--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.12';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


drop table art46.dossier_type;

CREATE TABLE ART46.DOSSIER_TYPE
(
	DOSSIER_TYPE CHAR(1) not null,
	DOSSIER_TYPE_B VARCHAR(5) not null,
	PRIMARY KEY (DOSSIER_TYPE)
);

insert into ART46.DOSSIER_TYPE
values
('A', 'AFVAL'),
('B', 'BODEM'),
('X', 'ANDER')
;

alter table ART46.DOSSIER
   add constraint FK_DO_DOSSIER_TYPE foreign key (DOSSIER_TYPE)
      references ART46.DOSSIER_TYPE (DOSSIER_TYPE)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_FASE
   add constraint FK_DOSF_DOSSIER_TYPE foreign key (DOSSIER_TYPE)
      references ART46.DOSSIER_TYPE (DOSSIER_TYPE)
      on delete restrict on update restrict
;



CREATE TABLE ART46.DOSSIER_RECHTSGROND
(
	DOSSIER_TYPE CHAR(1) not null,
	RECHTSGROND_CODE VARCHAR(5) not null,
	RECHTSGROND_B VARCHAR(40),
	PRIMARY KEY (DOSSIER_TYPE, RECHTSGROND_CODE),
	foreign key FK_DOSRG_DOSSIER_TYPE (DOSSIER_TYPE)
        references ART46.DOSSIER_TYPE (DOSSIER_TYPE)
        on delete restrict on update restrict
);


ALTER TABLE ART46.DOSSIER
    ADD RECHTSGROND_CODE VARCHAR(5);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.DOSSIER ');

alter table ART46.DOSSIER
   add constraint FK_DO_RECHTSGROND foreign key (DOSSIER_TYPE, RECHTSGROND_CODE)
      references ART46.DOSSIER_RECHTSGROND (DOSSIER_TYPE, RECHTSGROND_CODE)
      on delete restrict on update restrict
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.12');

