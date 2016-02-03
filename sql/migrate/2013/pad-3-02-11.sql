
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table ART46.BESTEK
   drop constraint FK_BE_FASE
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.11';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table ART46.BESTEK
   add constraint FK_BE_FASE foreign key (fase_id)
      references ART46.BESTEK_FASE (fase_id)
      on delete restrict on update restrict
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.11');
