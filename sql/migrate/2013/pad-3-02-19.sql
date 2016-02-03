
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.BUDGET_CODE
    DROP CONSTRAINT FK_BUDGETAIR_ARTIKEL
    DROP  artikel_b
;

call sysproc.admin_cmd('reorg table ART46.BUDGET_CODE')
;

alter table sap.BUDGETAIR_ARTIKEL
   DROP CONSTRAINT uc_artikel_b
;

call sysproc.admin_cmd('reorg table sap.BUDGETAIR_ARTIKEL')
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.19';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table sap.BUDGETAIR_ARTIKEL
   ADD CONSTRAINT uc_artikel_b UNIQUE(ARTIKEL_B)
;

call sysproc.admin_cmd('reorg table sap.BUDGETAIR_ARTIKEL')
;


ALTER TABLE ART46.BUDGET_CODE
  	ADD artikel_b VARCHAR(20);

ALTER TABLE ART46.BUDGET_CODE
	ADD CONSTRAINT FK_BUDGETAIR_ARTIKEL FOREIGN KEY (artikel_b)
	REFERENCES SAP.BUDGETAIR_ARTIKEL(artikel_b);

call sysproc.admin_cmd('reorg table ART46.BUDGET_CODE')
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.19');
