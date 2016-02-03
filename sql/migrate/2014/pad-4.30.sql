--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.schuldvordering
    drop column vordering_correct_bedrag
;

alter table art46.schuldvordering
    drop column herziening_correct_bedrag
;

alter table art46.schuldvordering
    drop column brief_categorie
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');


alter table art46.schuldvordering
   drop constraint FK_sv_brief_categorie
;

alter table art46.schuldvordering
    drop constraint cc_brief_categorie_id
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.30';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.schuldvordering
    add column vordering_correct_bedrag    double
;

alter table art46.schuldvordering
    add column herziening_correct_bedrag   double
;

alter table art46.schuldvordering
    add column brief_categorie_id             integer
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.SCHULDVORDERING');


alter table art46.schuldvordering
   add constraint FK_sv_brief_categorie foreign key (brief_categorie_id)
      references art46.brief_categorie (brief_categorie_id)
      on delete restrict on update restrict
;

alter table art46.schuldvordering
    add constraint cc_brief_categorie CHECK(brief_categorie_id in (12, 13, 14, 15))
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.30');




























