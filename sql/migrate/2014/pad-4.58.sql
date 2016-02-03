--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.58';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- foreign key aanvraagvastlegging -- bestek_adres;
--------------------------------------------------;
ALTER TABLE ART46.AANVRAAGVASTLEGGING
    ADD CONSTRAINT FK_AV_BESTEK_ADRES FOREIGN KEY (opdrachthouder_id)
    REFERENCES ART46.BESTEK_ADRES(bestek_adres_id)
;

-- bestek_adres -->  unique constraint;
--------------------------------------;

alter table art46.bestek_adres
    add column b_a_c_key varchar(25) not null default ''
;

update art46.bestek_adres
    set b_a_c_key = bestek_id || '-' || adres_id || coalesce('-' || contact_id, '')
;

alter table art46.bestek_adres
   add constraint b_a_c_key_uniek unique(b_a_c_key)
;

alter table art46.bestek_adres
   add constraint cc_b_a_c_key check ( b_a_c_key = (bestek_id || '-' || adres_id || coalesce('-' || contact_id, '')) )
;

create trigger art46.bi_bestek_adres_key
before insert on art46.bestek_adres
referencing new as n
for each row
    set n.b_a_c_key = bestek_id || '-' || adres_id || coalesce('-' || contact_id, '')
;


-- foreign key  bestek_adres  --> adres en contact
select *
--delete
from art46.BESTEK_ADRES ba
where not exists (select * from art46.adres ad where ad.adres_id = ba.adres_id)


ALTER TABLE ART46.bestek_adres
    ADD CONSTRAINT FK_BA_ADRES FOREIGN KEY (adres_id) REFERENCES ART46.ADRES(adres_id)
    ADD CONSTRAINT FK_BA_ADRES_CONTACT FOREIGN KEY (contact_id) REFERENCES ART46.ADRES_CONTACT(contact_id)
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.58');