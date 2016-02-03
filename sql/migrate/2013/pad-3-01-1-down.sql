
alter table ART46.VOOR_GOEDK
   drop constraint FK_VOORGK_ARTIKEL
;

alter table ART46.NA_GOEDK
   drop constraint FK_NAGK_ARTIKEL
;

ALTER TABLE ART46.DOSSIER
      DROP CONSTRAINT DOSSIER_ID_UNIEK
;


alter table ART46.BRIEF
   drop constraint FK_BR_DIENST
;

alter table ART46.BESTEK
   drop constraint FK_BE_DIENST
;



alter table ART46.DOSSIER 
   drop constraint FK_DO_FASE 
;

ALTER TABLE ART46.DOSSIER_FASE
      DROP CONSTRAINT TYPE_FASE_UNIEK
;

alter table ART46.DOSSIER_FASE
	alter column DOSSIER_TYPE set null
;

call sysproc.admin_cmd('reorg table ART46.DOSSIER_FASE')
;

alter table ART46.DOSSIER 
   add constraint FK_DO_FASE foreign key (DOSSIER_FASE_ID)
      references ART46.DOSSIER_FASE (FASE_ID)
      on delete restrict on update restrict
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.01.1';
