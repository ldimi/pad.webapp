

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '6.19';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier
   drop column EXTERN_HISTORIEK_L
   drop column EXTERN_HISTORIEK_OVAM_L
   drop column EXTERN_HISTORIEK_D
   drop column EXTERN_HISTORIEK_OVAM_D
   drop column EXTERN_HISTORIEK_S
   drop column EXTERN_VERONTREINIGING_L
   drop column EXTERN_VERONTREINIGING_OVAM_L
   drop column EXTERN_VERONTREINIGING_D
   drop column EXTERN_VERONTREINIGING_OVAM_D
   drop column EXTERN_VERONTREINIGING_S
   drop column EXTERN_AANPAK_OVAM_L
   drop column EXTERN_AANPAK_D
   drop column EXTERN_AANPAK_OVAM_D
   drop column EXTERN_AANPAK_S
   drop column EXTERN_STAND_OVAM_L
   drop column EXTERN_STAND_D
   drop column EXTERN_STAND_OVAM_D
   drop column EXTERN_STAND_S
   drop column EXTERN_STAND_L
   drop column EXTERN_AANPAK_L
;

call sysproc.admin_cmd('reorg table art46.dossier')
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('6.19');
