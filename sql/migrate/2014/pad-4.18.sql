--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.18';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING ADD bedrag_prijsherziening FLOAT(53);
ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING ADD "OPMERKING" VARCHAR(1024);
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.MEETSTAAT_SCHULDVORDERING');

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING ADD "VAN_DATUM" date;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.MEETSTAAT_SCHULDVORDERING');

ALTER TABLE ART46.MEETSTAAT_SCHULDVORDERING ADD "TOT_DATUM" date;
CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.MEETSTAAT_SCHULDVORDERING');
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.18');
