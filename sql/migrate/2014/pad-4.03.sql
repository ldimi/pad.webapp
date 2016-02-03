
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.V_PLANNING_LIJN;

create view art46.V_PLANNING_LIJN as
select
	*
from art46.PLANNING_LIJN_VERSIE plv
where 1 = 1
	and plv.DELETED_JN = 'N'
	and plv.PLANNING_VERSIE =
	(
	   select
	   max(plv2.PLANNING_VERSIE)
	   from art46.PLANNING_LIJN_VERSIE plv2
	   where plv.LIJN_ID = plv2.LIJN_ID
	)
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.03';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop view art46.V_PLANNING_LIJN;

create view art46.V_PLANNING_LIJN as
select
	*
from art46.PLANNING_LIJN_VERSIE plv
where 1 = 1
	and plv.DELETED_JN = 'N'
	and plv.PLANNING_DOSSIER_VERSIE =
	(
	   select
	   max(plv2.PLANNING_DOSSIER_VERSIE)
	   from art46.PLANNING_LIJN_VERSIE plv2
	   where plv.LIJN_ID = plv2.LIJN_ID
	)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.03');
