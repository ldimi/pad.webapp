--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


alter table art46.brief
    drop constraint cc_dms_velden
;


drop trigger sap.bi_brief_dms_path_uniek
;

drop trigger art46.bu_brief_dms_path_uniek
;



drop trigger sap.bi_brief_dms_id_uniek
;

drop trigger art46.bu_brief_dms_id_uniek
;



delete from  art46.db_versie
where db_versie = '4.85';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.brief
    add constraint cc_dms_velden check (
        (dms_id is null and dms_filename is null and dms_folder is null) or
        (dms_id is not null and dms_filename is not null and dms_folder is not null)
    )
;


create trigger sap.bi_brief_dms_path_uniek
before insert on art46.brief
referencing new as n
for each row
when (n.dms_filename is not null and
      (n.dms_folder, n.dms_filename) in (select distinct dms_folder, dms_filename from art46.brief)
     )
       signal sqlstate '75000' set message_text='(dms_folder, dms_filename) moet uniek zijn.'
;

create trigger art46.bu_brief_dms_path_uniek
before update on art46.brief
referencing new as n old as o
for each row
when ((n.dms_filename is not null and (n.dms_filename != o.dms_filename or o.dms_filename is null or
                                       n.dms_folder != o.dms_folder or o.dms_folder is null
                                      )
       ) and
      ((n.dms_folder, n.dms_filename) in (select distinct dms_folder, dms_filename from art46.brief))
     )
       signal sqlstate '75000' set message_text='(dms_folder, dms_filename) moet uniek zijn.'
;







create trigger sap.bi_brief_dms_id_uniek
before insert on art46.brief
referencing new as n
for each row
when (n.dms_id is not null and
      n.dms_id in (select dms_id from art46.brief)
     )
       signal sqlstate '75000' set message_text='dms_id moet uniek zijn.'
;

create trigger art46.bu_brief_dms_id_uniek
before update on art46.brief
referencing new as n old as o
for each row
when ((n.dms_id is not null and (n.dms_id != o.dms_id or o.dms_id is null)
       ) and
      ( n.dms_id in (select dms_id from art46.brief))
     )
       signal sqlstate '75000' set message_text='dms_id moet uniek zijn.'
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.85');

