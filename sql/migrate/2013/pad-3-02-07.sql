
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.7';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table ART46.ACTIE_SUB_TYPE 
   add constraint FK_AST_ACTIE_TYPE foreign key (actie_type_id)
      references ART46.ACTIE_TYPE (actie_type_id)
      on delete restrict on update restrict
;

alter table ART46.DOSSIER_ACTIE
   add constraint FK_DA_ACTIE_TYPE foreign key (actie_type_id)
      references ART46.ACTIE_TYPE (actie_type_id)
      on delete restrict on update restrict
;


ALTER TABLE ART46.ACTIE_SUB_TYPE
      ADD CONSTRAINT UC_actie_sub_actie UNIQUE(actie_type_id, actie_sub_type_id)
;

alter table ART46.DOSSIER_ACTIE
   add constraint FK_DA_ACTIE_SUB_TYPE foreign key (actie_type_id, actie_sub_type_id)
      references ART46.ACTIE_SUB_TYPE (actie_type_id, actie_sub_type_id)
      on delete restrict on update restrict
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.7');
