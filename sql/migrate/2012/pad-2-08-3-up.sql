
drop VIEW ART46.RS_KADASTER_VIEW;

CREATE VIEW ART46.RS_KADASTER_VIEW AS
SELECT 
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

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('2.08.3');
