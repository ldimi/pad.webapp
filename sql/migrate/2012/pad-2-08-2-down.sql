


alter table ART46.BRIEF
   drop constraint FK_BR_IN_AARD
   drop constraint FK_BR_UIT_AARD
   drop constraint FK_BR_IN_TYPE
   drop constraint FK_BR_UIT_TYPE
   drop constraint FK_BR_CATEGORIE
   drop constraint FK_BR_UIT_TYPE_VOS
;

alter table ART46.BRIEF_TYPE
    drop primary key
;

alter table ART46.BRIEF_TYPE_VOS
    drop primary key
;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '2.08.2';
