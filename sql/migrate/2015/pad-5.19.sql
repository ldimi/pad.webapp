--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.19';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create view art46.v_dossier_overdracht_historiek as
    with doh as (
        select *
        from art46.dossier_overdracht_hist
        union
        select *
        from art46.dossier_overdracht
    )
    select
        doh.*
    from  doh
            left join doh as doh_next
            on doh.overdracht_id = doh_next.overdracht_id
            and doh.versie_nr + 1 = doh_next.versie_nr
    where 1 = 1
        and (doh.status != doh_next.status or doh_next.status is null)
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.19');
