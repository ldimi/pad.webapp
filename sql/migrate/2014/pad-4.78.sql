--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table art46.SCHULDVORDERING_STATUS_HISTORY
   drop constraint fk_svsh_gebruiker
;



delete from  art46.db_versie
where db_versie = '4.78';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table art46.schuldvordering_status_history
   alter column gebruiker_id set data type integer
;

call sysproc.admin_cmd('reorg table art46.schuldvordering_status_history')
;


alter table art46.SCHULDVORDERING_STATUS_HISTORY
   add constraint fk_svsh_gebruiker foreign key (gebruiker_id)
      references art46.webloket_gebruiker (id)
      on delete restrict on update restrict
;

create view art46.v_schuldvordering_status_laatste as
    select
        ssh.id,
        ssh.schuldvordering_id,
        ssh.gebruiker_id,
        ssh.dossierhouder_id,
        ssh.datum,
        ssh.motivatie,
        ssh.status
    from art46.schuldvordering_status_history ssh
    where ssh.id = (select max(ssh2.id)
                      from art46.schuldvordering_status_history  ssh2
                      where ssh2.schuldvordering_id = ssh.schuldvordering_id)
;




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.78');

