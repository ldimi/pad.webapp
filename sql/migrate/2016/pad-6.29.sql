

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  art46.db_versie
where db_versie = '6.29';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.bestek
    add column doss_hdr_id varchar(8);
;

call sysproc.admin_cmd('reorg table art46.bestek')
;

select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;

update art46.bestek be
set be.doss_hdr_id =  ( select doss_hdr_id from art46.dossier dos where dos.id = be.dossier_id)

alter table art46.bestek
    add foreign key FK_BE_DOS_HDR (doss_hdr_id)
         references art46.dossier_houder (doss_hdr_id)
         on delete restrict
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.29', 'toevoegen bestek.doss_hdr_id');
