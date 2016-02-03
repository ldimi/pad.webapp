
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;



delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.47';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING
    add column eenheid_naam varchar(24)
;


update ART46.MEETSTAAT_EENHEID_MAPPING em
set em.EENHEID_NAAM = (select m.naam from art46.MEETSTAAT_EENHEID m where em.EENHEIDCODE = m.CODE)
;

ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING   
    drop CONSTRAINT FK_MEM_MEETSTAAT_EENHEID
;

ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING   
    ADD CONSTRAINT FK_MEM_MEETSTAAT_EENHEID FOREIGN KEY (eenheid_naam)
    REFERENCES ART46.MEETSTAAT_EENHEID(naam)
;

ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING
    alter column eenheidcode drop not null
;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID_MAPPING')
;


ALTER TABLE ART46.MEETSTAAT_EENHEID
      drop CONSTRAINT CODE_UNIEK
;

ALTER TABLE ART46.MEETSTAAT_EENHEID
    alter column code drop not null
;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID')
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.47');
