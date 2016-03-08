

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.dossier
    drop constraint cc_dossier_raming
    drop constraint cc_dossier_raming_bsp
;


delete from  art46.db_versie
where db_versie = '6.18';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier
    add constraint cc_dossier_raming check
        ( dossier_type = 'B' OR
          (bbo_prijs is null and bbo_looptijd is null and
           bsp_jn is null and
           bsp_prijs is null and bsp_looptijd is null and
           bsw_prijs is null and bsw_looptijd is null
          )
        )
;

alter table art46.dossier
    add constraint cc_dossier_raming_bsp check
        ( bsp_jn = 'J' OR
          (bsp_prijs is null and bsp_looptijd is null and
           bsw_prijs is null and bsw_looptijd is null
          )
        )
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.18');
