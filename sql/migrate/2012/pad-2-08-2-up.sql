


alter table ART46.BRIEF
   add constraint FK_BR_IN_AARD foreign key (in_aard_id)
      references ART46.BRIEF_AARD (BRIEF_AARD_ID)
      on delete restrict on update restrict
;

alter table ART46.BRIEF
   add constraint FK_BR_UIT_AARD foreign key (uit_aard_id)
      references ART46.BRIEF_AARD (BRIEF_AARD_ID)
      on delete restrict on update restrict
;


alter table ART46.BRIEF_TYPE
    add primary key (BRIEF_TYPE_ID)
;

alter table ART46.BRIEF
   add constraint FK_BR_IN_TYPE foreign key (in_type_id)
      references ART46.BRIEF_TYPE (BRIEF_TYPE_ID)
      on delete restrict on update restrict
;

alter table ART46.BRIEF
   add constraint FK_BR_UIT_TYPE foreign key (uit_type_id)
      references ART46.BRIEF_TYPE (BRIEF_TYPE_ID)
      on delete restrict on update restrict
;

alter table ART46.BRIEF
   add constraint FK_BR_CATEGORIE foreign key (categorie_id)
      references ART46.BRIEF_CATEGORIE (BRIEF_CATEGORIE_ID)
      on delete restrict on update restrict
;

alter table ART46.BRIEF_TYPE_VOS
    add primary key (TYPE_ID)
;

alter table ART46.BRIEF
   add constraint FK_BR_UIT_TYPE_VOS foreign key (uit_type_id_vos)
      references ART46.BRIEF_TYPE_VOS (TYPE_ID)
      on delete restrict on update restrict
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('2.08.2');
