

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '5.35';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



alter table  art46.adres
    add constraint cc_voornaam  check ( trim(voornaam) != '' )
;

alter table  art46.adres
    add constraint cc_straat  check ( trim(straat) != '' )
;

alter table  art46.adres
    add constraint cc_postcode check ( trim(postcode) != '' )
;

alter table  art46.adres
    add constraint cc_gemeente  check ( trim(gemeente) != '' )
;

alter table  art46.adres
    add constraint cc_tel  check ( trim(tel) != '' )
;

alter table  art46.adres
    add constraint cc_fax  check ( trim(fax) != '' )
;

alter table  art46.adres
    add constraint cc_email  check ( trim(email) != '' )
;

alter table  art46.adres
    add constraint cc_website  check ( trim(website) != '' )
;

alter table  art46.adres
    add constraint cc_gsm  check ( trim(gsm) != '' )
;

alter table  art46.adres
    add constraint cc_referentie_postcodes  check ( trim(referentie_postcodes) != '' )
;







alter table art46.adres
    alter column stop_s set not null
;

call sysproc.admin_cmd('reorg table art46.adres')
;

alter table art46.adres
    alter column maatsch_zetel set not null
;

call sysproc.admin_cmd('reorg table art46.adres')
;

alter table art46.adres
    alter column land set not null
;

call sysproc.admin_cmd('reorg table art46.adres')
;



alter table art46.adres
    drop column derde_id
;
call sysproc.admin_cmd('reorg table art46.adres')
;

alter table art46.adres
    drop column huis_nr
;
call sysproc.admin_cmd('reorg table art46.adres')
;

alter table art46.adres
    drop column straat_zonder_nr
;
call sysproc.admin_cmd('reorg table art46.adres')
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.35');