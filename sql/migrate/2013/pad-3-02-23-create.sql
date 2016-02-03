--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


DROP TABLE ART46.DEELOPDRACHT_BRIEF;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.23';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

CREATE TABLE ART46.DEELOPDRACHT_BRIEF(
	BRIEF_ID int NOT NULL,
	DEELOPDRACHT_ID  int NOT NULL,
	PRIMARY KEY (BRIEF_ID,DEELOPDRACHT_ID)
)
;

alter table ART46.DEELOPDRACHT_BRIEF
   add constraint FK_DOBR_BRIEF foreign key (BRIEF_ID)
      references ART46.BRIEF (BRIEF_ID)
      on delete restrict on update restrict
;

alter table ART46.DEELOPDRACHT_BRIEF
   add constraint FK_DOBR_DEELOPDRACHT foreign key (DEELOPDRACHT_ID)
      references ART46.DEELOPDRACHT (DEELOPDRACHT_ID)
      on delete restrict on update restrict
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.23');
