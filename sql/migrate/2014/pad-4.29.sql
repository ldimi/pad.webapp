--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.29';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

INSERT INTO ART46.SCHULDVORDERING_STATUS (KEY,NAME,NAME_WEB_LOKER) VALUES ('BEOORDEELD','BEOORDEELD','VERZONDEN');

INSERT INTO ART46.SCHULDVORDERING_STATUS (KEY,NAME,NAME_WEB_LOKER) VALUES ('ONDERTEKEND','ONDERTEKEND','VERZONDEN');


update ART46.SCHULDVORDERING_STATUS_HISTORY
set status = 'BEOORDEELD'
WHERE status = 'GEACCEPTEERD'
;

update ART46.SCHULDVORDERING_STATUS_HISTORY
set status = 'ONDERTEKEND'
WHERE status = 'GOEDGEKEURD'
;


update ART46.SCHULDVORDERING
set status = 'BEOORDEELD'
WHERE status = 'GEACCEPTEERD'
;

update ART46.SCHULDVORDERING
set status = 'ONDERTEKEND'
WHERE status = 'GOEDGEKEURD'
;



delete from ART46.SCHULDVORDERING_STATUS where key = 'AFGEKEURD';

delete from ART46.SCHULDVORDERING_STATUS where key LIKE 'GOEDGEKEUR%';
delete from ART46.SCHULDVORDERING_STATUS where key LIKE 'GEACC%';
delete from ART46.SCHULDVORDERING_STATUS where key LIKE 'IN OPMAAK';



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.29');




























