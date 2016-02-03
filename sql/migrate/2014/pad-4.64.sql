--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.63';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

--select  off.*,
--        (select ba2.bestek_adres_id
--         from art46.OFFERTE off2
--                left  join art46.BRIEF br2
--                on off2.OFFERTE_BRIEF_ID = br2.BRIEF_ID
--            left join art46.BESTEK_ADRES ba2
--            on off2.BESTEK_ID = ba2.BESTEK_ID
--            and br2.ADRES_ID = ba2.ADRES_ID
--            and (br2.CONTACT_ID = ba2.CONTACT_ID or (br2.CONTACT_ID is null and ba2.CONTACT_ID is null))
--         where off.id = off2.id
--        ) as v2,
--      ba.*
--from art46.OFFERTE off
--        left  join art46.BRIEF br
--        on off.OFFERTE_BRIEF_ID = br.BRIEF_ID
--    left join art46.BESTEK_ADRES ba
--    on off.BESTEK_ID = ba.BESTEK_ID
--    and br.ADRES_ID = ba.ADRES_ID
--    and (br.CONTACT_ID = ba.CONTACT_ID or (br.CONTACT_ID is null and ba.CONTACT_ID is null))
--where 1 = 1
--    and off.key is not null


update art46.OFFERTE off
set off.OPDRACHTGEVER_ID =
        (select ba2.bestek_adres_id
         from art46.OFFERTE off2
                left  join art46.BRIEF br2
                on off2.OFFERTE_BRIEF_ID = br2.BRIEF_ID
            left join art46.BESTEK_ADRES ba2
            on off2.BESTEK_ID = ba2.BESTEK_ID
            and br2.ADRES_ID = ba2.ADRES_ID
            and (br2.CONTACT_ID = ba2.CONTACT_ID or (br2.CONTACT_ID is null and ba2.CONTACT_ID is null))
         where off.id = off2.id
        )
where 1 = 1
    and off.key is not null
    and off.OPDRACHTGEVER_ID is null
;

create table art46.offerte_status (
    code varchar(24) not null,
    primary key(code)
);

insert into  art46.offerte_status (code)
values
('Toegewezen'),
('Afgesloten')
;

alter table art46.OFFERTE
   add FOREIGN KEY FK_OFF_STATUS (status)
           REFERENCES ART46.offerte_status (code) ON DELETE RESTRICT
;

alter table art46.OFFERTE
    add constraint cc_key_opdrachtgever check(
            (key is null and opdrachtgever_id is null)
            OR
            (key is not null and opdrachtgever_id is not null)
        )
;


alter table art46.OFFERTE
    add constraint cc_key_status check( NOT	(key is not null and STATUS is null) )
;



alter table art46.SCHULDVORDERING
   add FOREIGN KEY FK_SV_STATUS (status)
           REFERENCES ART46.SCHULDVORDERING_STATUS (key) ON DELETE RESTRICT
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.64');