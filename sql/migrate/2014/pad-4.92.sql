--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_offerte;

delete from  art46.db_versie
where db_versie = '4.92';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


create view art46.v_offerte as
select
    off.id,
    off.id as offerte_id,
    off.bestek_id,
    be.bestek_nr,
    be.omschrijving bestek_omschrijving,
    be.dossier_id,
    be.dossier_nr,
    be.dossier_type,
    be.dossier_b,
    be.dossier_b_l,
    be.doss_hdr_id,
    be.raamcontract_jn,
    off.offerte_brief_id,
    br.brief_nr,
    br.naam_voornaam as inzender_naam_voornaam,
    off.inzender,
    off.status,
    off.btw_tarief,
    off.totaal,
    off.key,
    off.offerte_origineel_id,
    off.opdrachtgever_id,
    case when ba.contact_id is not null then  coalesce(ac.naam, '') || ' ' || coalesce(ac.voornaam, '')
         when ba.adres_id is not null then coalesce(a.naam, '') || ' ' || coalesce(a.voornaam, '')
         else ''
    end as opdrachtgever_naam_voornaam
from  art46.offerte off
        inner join art46.v_bestek be
        on off.bestek_id = be.bestek_id
    left join art46.bestek_adres ba
    on off.opdrachtgever_id = ba.bestek_adres_id
        left join art46.adres a
        on ba.adres_id = a.adres_id
            left join art46.adres_contact ac
            on ba.contact_id = ac.contact_id
    inner join art46.v_brief br
    on off.offerte_brief_id = br.brief_id
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.92');

