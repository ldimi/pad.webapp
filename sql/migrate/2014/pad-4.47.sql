--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.47';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.brief
    add column qr_code integer
;

alter table art46.brief
    drop column inkomende_qrcode
;

call sysproc.admin_cmd('reorg table art46.brief');


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.47');