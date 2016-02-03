
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.18';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table ART46.DOSSIER_ADRES
   add primary key (DOSSIER_ID, ADRES_ID, CONTACT_ID)
;

alter table ART46.DOSSIER_ADRES
   add constraint FK_DA_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_ADRES
   add constraint FK_DA_ADRES foreign key (ADRES_ID)
      references ART46.ADRES (ADRES_ID)
      on delete restrict on update restrict
;


alter table ART46.ADRES_CONTACT
   add primary key (CONTACT_ID)
;

alter table ART46.ADRES_CONTACT
   add constraint FK_AC_ADRES foreign key (ADRES_ID)
      references ART46.ADRES (ADRES_ID)
      on delete restrict on update restrict
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.18');
