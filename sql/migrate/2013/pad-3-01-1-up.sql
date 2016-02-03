
alter table ART46.VOOR_GOEDK
   add constraint FK_VOORGK_ARTIKEL foreign key (ARTIKEL_ID)
      references ART46.ARTIKEL (ARTIKEL_ID)
      on delete restrict on update restrict
;

alter table ART46.NA_GOEDK
   add constraint FK_NAGK_ARTIKEL foreign key (ARTIKEL_ID)
      references ART46.ARTIKEL (ARTIKEL_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT DOSSIER_ID_UNIEK UNIQUE(DOSSIER_ID)
;


alter table ART46.BRIEF
   add constraint FK_BR_DIENST foreign key (dienst_id)
      references ART46.dienst (dienst_id)
      on delete restrict on update restrict
;

alter table ART46.BESTEK
   add constraint FK_BE_DIENST foreign key (dienst_id)
      references ART46.dienst (dienst_id)
      on delete restrict on update restrict
;


-- FK_DO_FASE  moet ook dossier_type bevatten.

alter table ART46.DOSSIER 
   drop constraint FK_DO_FASE 
;

alter table ART46.DOSSIER_FASE
	alter column DOSSIER_TYPE drop not null
;

call sysproc.admin_cmd('reorg table ART46.DOSSIER_FASE')
;

ALTER TABLE ART46.DOSSIER_FASE
      ADD CONSTRAINT TYPE_FASE_UNIEK UNIQUE(DOSSIER_TYPE, FASE_ID)
;


alter table ART46.DOSSIER 
   add constraint FK_DO_FASE foreign key (DOSSIER_TYPE, DOSSIER_FASE_ID)
      references ART46.DOSSIER_FASE (DOSSIER_TYPE, FASE_ID)
      on delete restrict on update restrict
;





-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.01.1');
