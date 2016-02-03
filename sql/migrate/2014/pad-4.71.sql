--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.MEETSTAATREGEL
    drop CONSTRAINT CC_BESTEK_OR_TEMPLATE
;

ALTER TABLE ART46.MEETSTAATREGEL
    ADD CONSTRAINT CC_BESTEK_OR_TEMPLATE CHECK
        (bestek_id is not null OR  template_id is not null)
;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.71';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.MEETSTAATREGEL
    drop CONSTRAINT CC_BESTEK_OR_TEMPLATE
;

ALTER TABLE ART46.MEETSTAATREGEL
    ADD CONSTRAINT CC_BESTEK_OR_TEMPLATE CHECK (
        (bestek_id is null and  template_id is not null)
        OR
        (bestek_id is not null and  template_id is null)
     )
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.71');

