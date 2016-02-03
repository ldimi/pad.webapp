
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view ART46.V_FUSIEGEMEENTE;

drop view ART46.V_PROVINCIE_VLAANDEREN;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.07';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create view ART46.V_PROVINCIE_VLAANDEREN as
select id, omschrijving
from SMEG_REF.PROVINCIE
where id in (6,7,8,9,12)
;

create view ART46.V_FUSIEGEMEENTE as
        select
            g.NIS_ID,
            g.NAAM as gemeente_b,
            g.provincie_id
        from SMEG_REF.CRAB_GEMEENTE g
                inner join art46.V_PROVINCIE_VLAANDEREN p
                on g.PROVINCIE_ID = p.id
;

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.07');
