--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.77';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


drop trigger art46.bu_schuldvordering_ts;

update art46.SCHULDVORDERING sv
set sv.DOSSIER_TYPE = (select be.dossier_type
                        from art46.bestek be
                        where sv.BESTEK_ID = be.bestek_id)
where 1 = 1
    and sv.DOSSIER_TYPE != (select be.dossier_type
                        from art46.bestek be
                        where sv.BESTEK_ID = be.bestek_id)
;

create trigger art46.bu_schuldvordering_ts
no cascade before update on art46.schuldvordering
referencing
    old as pre
    new as post
for each row mode db2sql
set
        post.wijzig_ts = current timestamp
;

alter table art46.schuldvordering
   drop constraint fk_sv_bestek
;

alter table art46.schuldvordering
   add constraint fk_sv_bestek foreign key (bestek_id, dossier_type)
      references art46.bestek (bestek_id, dossier_type)
      on delete restrict on update restrict
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.77');

