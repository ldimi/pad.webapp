
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

DROP VIEW ART46.V_DOSSIER_KADASTER;
create view ART46.V_DOSSIER_KADASTER as
(
   select
   d.id,
   d.DOSSIER_ID_BOA,
   ko.KADASTER_ID,
   a.ARTIKEL_ID,
   a.GOEDKEURING_S,
   a.COMMENTAAR COMMENTAAR_VOOR
   from ART46.DOSSIER d
   inner join SMEG.RS_ONDERZOEK_VIEW o on d.DOSSIER_ID_BOA = o.DOSSIER_ID
   inner join ART46.RS_KADASTER_ONDERZOEK_VIEW ko on o.ONDERZOEK_ID = ko.ONDERZOEK_ID left outer
   join ART46.VOOR_GOEDK a on a.DOSSIER_ID = d.ID
   and a.KADASTER_ID = ko.KADASTER_ID
   union
   select
   d.id,
   d.DOSSIER_ID_BOA,
   kp.KADASTER_ID,
   a.ARTIKEL_ID,
   a.GOEDKEURING_S,
   a.COMMENTAAR COMMENTAAR_VOOR
   from ART46.DOSSIER d
   inner join SMEG.RS_PROJECT_VIEW p on d.DOSSIER_ID_BOA = p.DOSSIER_ID
   inner join ART46.RS_KADASTER_PROJECT_VIEW kp on p.PROJECT_ID = kp.PROJECT_ID left outer
   join ART46.VOOR_GOEDK a on a.DOSSIER_ID = d.ID
   and a.KADASTER_ID = kp.KADASTER_ID
)
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.9';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop view ART46.V_DOSSIER_KADASTER;
create view ART46.V_DOSSIER_KADASTER as
(
   select distinct
	   d.id,
	   d.DOSSIER_ID_BOA,
	   g.KADASTER_ID,
	   a.ARTIKEL_ID,
	   a.GOEDKEURING_S,
	   a.COMMENTAAR COMMENTAAR_VOOR
   from ART46.DOSSIER d
			INNER JOIN SMEG.DOSSIER sd ON d.dossier_id_boa = sd.dossier_id
		INNER JOIN SMEG.BUNDEL_BUNDEL bb ON sd.id = bb.parent_bundel_id
	   		INNER JOIN SMEG.BUNDEL_LOCATIE chbl ON bb.CHILD_BUNDEL_ID = chbl.bundel_id
		INNER JOIN SMEG.LOCATIE l ON chbl.locatie_id = l.id
	     	INNER JOIN SMEG.LOCATIE_GROND lg ON l.id = lg.locatie_id
	     INNER JOIN SMEG.GROND g on lg.grond_id = g.id
			left outer join ART46.VOOR_GOEDK a
			on a.DOSSIER_ID = d.ID
			and a.KADASTER_ID = g.KADASTER_ID
		left JOIN SMEG.ONDERZOEK ond ON bb.CHILD_BUNDEL_ID = ond.id
			left join SMEG.BSP p on bb.CHILD_BUNDEL_ID = p.id
);



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.9');
