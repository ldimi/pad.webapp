--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.brief
   drop column teprinten_jn
   drop column print_d
;

call sysproc.admin_cmd('reorg table art46.brief');


delete from  art46.db_versie
where db_versie = '4.53';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.brief
   add column teprinten_jn char(1) not null default 'N'
   add column print_d date
   foreign key fk_br_teprinten_jn(teprinten_jn)
           references art46.ja_nee_code (code) on delete restrict
;

call sysproc.admin_cmd('reorg table art46.brief');




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.53');