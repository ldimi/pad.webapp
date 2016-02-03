
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.ADRES_CONTACT
   drop constraint cc_id CHECK
;


alter table art46.ADRES_TYPE
   drop constraint cc_id CHECK
;

alter table art46.BESTEK
   drop constraint cc_id CHECK
;

alter table art46.DEELOPDRACHT
   drop constraint cc_id CHECK
;

alter table art46.SCHULDVORDERING
   drop constraint cc_id CHECK
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.08';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

--select *
--from art46.adres a
--where a.ADRES_ID = 0;
--
--select *
--from art46.ADRES_CONTACT ac
--where ac.CONTACT_ID = 0
--;
--
--select *
--from art46.ADRES_TYPE at
--where at.ADRES_TYPE_ID = 0
--;
--
--select *
--from art46.Bestek b
--where b.BESTEK_ID = 0
--;
--
--select *
--from art46.DEELOPDRACHT d
--where d.DEELOPDRACHT_ID = 0
--;
--
--select *
--from art46.SCHULDVORDERING sv
--WHERE 1 = 1
--    and  sv.VORDERING_ID = 0;


alter table art46.ADRES_CONTACT
   add constraint cc_id CHECK(contact_id > 0)
;


alter table art46.ADRES_TYPE
   add constraint cc_id CHECK(ADRES_TYPE_ID > 0)
;

alter table art46.BESTEK
   add constraint cc_id CHECK(bestek_id > 0)
;

alter table art46.DEELOPDRACHT
   add constraint cc_id CHECK(deelopdracht_id > 0)
;

alter table art46.SCHULDVORDERING
   add constraint cc_id CHECK(vordering_id > 0)
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.08');
