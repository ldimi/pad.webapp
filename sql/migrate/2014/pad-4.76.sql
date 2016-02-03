--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '4.76';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table art46.dossier
      add constraint uc_id_type unique(id, dossier_type)
;

alter table art46.bestek
    add column dossier_type char(1) not null default 'z'
;

drop trigger art46.bu_bestek_ts;

update art46.BESTEK b1
set b1.dossier_type = (select dos.DOSSIER_TYPE
                    from art46.BESTEK be
                            inner join art46.DOSSIER dos
                            on be.DOSSIER_ID = dos.id
                    where b1.bestek_id = be.bestek_id
                )
;

create trigger art46.bu_bestek_ts
no cascade before update on art46.bestek
referencing
    old as pre
    new as post
for each row mode db2sql
set
        post.wijzig_ts = current timestamp
;

create trigger art46.bi_bestek_dossier_type
before insert on art46.bestek
referencing new as n
for each row
set
     n.dossier_type =  (select dos.dossier_type
                        from art46.dossier dos
                        where dos.id = n.dossier_id)
;

alter table art46.bestek
      add constraint uc_bestek_id_dossier_type unique(bestek_id, dossier_type)
;

alter table art46.bestek
    drop constraint fk_be_dossier
;

alter table art46.bestek
   add constraint fk_be_dossier foreign key (dossier_id, dossier_type)
      references art46.dossier (id, dossier_type)
      on delete restrict on update restrict
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.76');

