--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop TABLE ART46.SCHULDVORDERING_STATUS_HISTORY;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.24';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


CREATE TABLE ART46.SCHULDVORDERING_STATUS_HISTORY (
  id int PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY,
  schuldvordering_id int NOT NULL,
  gebruiker_id VARCHAR (120),
  dossierHouder_id VARCHAR(8),
  datum timestamp,
  motivatie VARCHAR(1024),
  status VARCHAR (30)
);


alter table ART46.SCHULDVORDERING_STATUS_HISTORY
   add constraint FK_svsh_dossierhouder foreign key (dossierhouder_id)
      references art46.dossier_houder (doss_hdr_id)
      on delete restrict on update restrict
;

alter table ART46.SCHULDVORDERING_STATUS_HISTORY
   add constraint FK_svsh_schuldvordering foreign key (schuldvordering_id)
      references art46.schuldvordering (vordering_id)
      on delete cascade on update restrict
;

alter table art46.schuldvordering
    add column status varchar(30)
;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');

alter table art46.schuldvordering
   add constraint FK_sv_aanvraag_id foreign key (aanvraag_schuldvordering_id)
      references art46.meetstaat_schuldvordering (id)
      on delete restrict on update restrict
;


update ART46.SCHULDVORDERING_STATUS
   set (NAME,NAME_WEB_LOKER) = ('VERZONDEN', 'VERWERKT')
where key = 'VERZONDEN';

INSERT INTO ART46.SCHULDVORDERING_STATUS (KEY,NAME,NAME_WEB_LOKER) VALUES ('INGEDIEND','INGEDIEND','VERZONDEN');


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.24');





























