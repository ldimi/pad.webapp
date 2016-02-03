--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop trigger art46.bi_adres_contact_id;

alter table art46.adres_contact
    drop constraint a_c_key_uniek
    drop constraint cc_a_c_key
    drop column a_c_key
;

call sysproc.admin_cmd('reorg table art46.adres_contact');


delete from  art46.db_versie
where db_versie = '4.50';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.adres_contact
    add column a_c_key varchar(11) not null default ''
;

update art46.adres_contact
set a_c_key = (adres_id || ',' || contact_id)
;

alter table art46.adres_contact
   add constraint a_c_key_uniek unique(a_c_key)
;

alter table art46.adres_contact
   add constraint cc_a_c_key check(a_c_key = (adres_id || ',' || contact_id))
;

create trigger art46.bi_adres_contact_id
before insert on art46.adres_contact
referencing new as n
for each row
    set n.a_c_key = (n.adres_id || ',' || n.contact_id)
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.50');