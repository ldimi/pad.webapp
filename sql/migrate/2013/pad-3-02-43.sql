
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE 
where DB_VERSIE = '3.02.43';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

  
ALTER TABLE ART46.OFFERTE   
    ADD CONSTRAINT FK_OFF_BESTEK FOREIGN KEY (bestek_id)
    REFERENCES ART46.BESTEK(bestek_id)
    ADD CONSTRAINT FK_OFF_BRIEF FOREIGN KEY (offerte_brief_id)
    REFERENCES ART46.BRIEF(brief_id)
;


ALTER TABLE ART46.OFFERTE_REGEL   
    ADD CONSTRAINT FK_OFFR_OFFERTE FOREIGN KEY (offerte_id)
    REFERENCES ART46.OFFERTE(id)
;
    
ALTER TABLE ART46.OFFERTE_REGEL   
    ADD CONSTRAINT FK_OFFR_MEETSTAATREGEL FOREIGN KEY (meetstaat_regel_id)
    REFERENCES ART46.MEETSTAATREGEL(id)
;


	
	
	
ALTER TABLE ART46.MEETSTAAT_EENHEID
    alter column code drop not null
;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID')
;

ALTER TABLE ART46.MEETSTAAT_EENHEID
    alter column naam drop not null
;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID')
;

ALTER TABLE ART46.MEETSTAAT_EENHEID
    add PRIMARY KEY (naam)
;

ALTER TABLE ART46.MEETSTAAT_EENHEID
      ADD CONSTRAINT CODE_UNIEK UNIQUE(CODE)
;









ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING
    alter column eenheidcode drop not null
    alter column naam drop not null
;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID_MAPPING')
;

ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING
    add PRIMARY KEY (naam)
;

call sysproc.admin_cmd('reorg table ART46.MEETSTAAT_EENHEID_MAPPING')
;

ALTER TABLE ART46.MEETSTAAT_EENHEID_MAPPING   
    ADD CONSTRAINT FK_MEM_MEETSTAAT_EENHEID FOREIGN KEY (eenheidcode)
    REFERENCES ART46.MEETSTAAT_EENHEID(code)
;







ALTER TABLE ART46.MEETSTAATREGEL   
    ADD CONSTRAINT FK_MR_TEMPLATE FOREIGN KEY (template_id)
    REFERENCES ART46.MEETSTAAT_TEMPLATE(id)
;


-- Als beide velden samen ingevuld zijn, dan betekent dit
-- dat bestek meetstaat regel gegenereerd is op basis  van de template regel.
--   (eventueel in toekomst te refactoren naar twee kolommen : template_id , en references_template_id)
ALTER TABLE ART46.MEETSTAATREGEL   
    ADD CONSTRAINT CC_BESTEK_OR_TEMPLATE CHECK 
        (bestek_id is not null OR  template_id is not null) 
;



--TODO
--ART46.MEETSTAATREGEL
--  type


update
art46.MEETSTAATREGEL
set eenheid = null
where trim(eenheid) = ''
;



ALTER TABLE ART46.MEETSTAATREGEL   
    ADD CONSTRAINT FK_MR_EENHEID FOREIGN KEY (EENHEID)
    REFERENCES ART46.MEETSTAAT_EENHEID(naam)
;

	
--select TABSCHEMA, TABNAME from SYSIBMADM.ADMINTABINFO where REORG_PENDING = 'Y';	
	
-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.43');
