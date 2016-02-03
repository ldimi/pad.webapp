
drop VIEW ART46.RS_KADASTER_VIEW;

CREATE VIEW ART46.RS_KADASTER_VIEW AS
SELECT
	g.id as grond_id,
	g.kadaster_id AS KADASTER_ID,
	g.kadaster_afdeling_id AS KADASTER_AFD_ID,
	g.sectie AS SECTIE,
	trim(CHAR(g.grond_nr)) AS GRONDNUMMER,
	trim(CHAR(g.bis_nr)) AS BISNUMMER,
	g.exponent1 AS EXPONENT1,
	trim(CHAR(g.exponent2)) AS EXPONENT2,
	g.toestandsdatum AS TOESTAND_D,
	g.straat || coalesce(' ' || g.huis_nr, '') || coalesce(' bus ' || g.bus_nr, '') AS ADRES,
	trim(CHAR(g.gem_inv)) AS GEM_INV_S
FROM SMEG.GROND g
;


drop VIEW ART46.RS_KADASTER_ONDERZOEK_VIEW;

create view ART46.RS_KADASTER_ONDERZOEK_VIEW as
(
   SELECT
   		g.id as GROND_ID,
   		g.kadaster_id AS KADASTER_ID,
   		o.id AS ONDERZOEK_ID
   FROM SMEG.GROND g
	   INNER JOIN SMEG.LOCATIE_GROND lg ON lg.grond_id = g.id
	   INNER JOIN SMEG.LOCATIE l ON l.id = lg.locatie_id
	   INNER JOIN SMEG.BUNDEL_LOCATIE bl ON bl.locatie_id = l.id
	   INNER JOIN SMEG.OPDRACHT o ON o.id = bl.bundel_id
	   INNER JOIN SMEG.ONDERZOEK ond ON ond.id = o.id
)
;

drop VIEW ART46.RS_KADASTER_PROJECT_VIEW;

CREATE VIEW ART46.RS_KADASTER_PROJECT_VIEW AS
(
	SELECT
   		g.id as GROND_ID,
		g.kadaster_id AS KADASTER_ID,
		o.id AS PROJECT_ID
	FROM SMEG.GROND g
		INNER JOIN SMEG.LOCATIE_GROND lg ON lg.grond_id = g.id
		INNER JOIN SMEG.LOCATIE l ON l.id = lg.locatie_id
		INNER JOIN SMEG.BUNDEL_LOCATIE bl ON bl.locatie_id = l.id
		INNER JOIN SMEG.OPDRACHT o ON o.id = bl.bundel_id
		INNER JOIN SMEG.BSP p ON p.id = o.id
)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('2.08.7');
