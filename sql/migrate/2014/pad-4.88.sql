--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_bestek;



delete from  art46.db_versie
where db_versie = '4.88';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.webloket_bijlage
   drop constraint fk_wlb_schuldvordering
;


alter table art46.webloket_bijlage
   add constraint fk_wlb_aanvr_schuldvordering foreign key (schuldvordering_id)
      references art46.meetstaat_schuldvordering (id)
      on delete restrict on update restrict
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.88');





























