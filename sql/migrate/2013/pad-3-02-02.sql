
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view SMEG.ART46_DIENST_VIEW;

create view SMEG.ART46_DIENST_VIEW as
	select
	int(d.id) as dienst_id, d.code as dienst_b
	from SMEG_REF.DIENST d
	inner join SMEG_REF.AFDELING a on a.id = d.afdeling_id
	where a.code = 'IVS'
union
	select
	10000 as dienst_id, 'AH' as dienst_b
	from SMEG_REF.DIENST d
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.2';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;
drop view SMEG.ART46_DIENST_VIEW;

create view SMEG.ART46_DIENST_VIEW as
	select
	int(d.id) as dienst_id, d.code as dienst_b
	from SMEG_REF.DIENST d
	inner join SMEG_REF.AFDELING a on a.id = d.afdeling_id
	where a.code = 'IVS'
union
	select
	10000 as dienst_id, 'AH' as dienst_b
	from SMEG_REF.DIENST d
union
	select
	1 as dienst_id, 'IVS' as dienst_b
	from SMEG_REF.DIENST d
;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.2');
