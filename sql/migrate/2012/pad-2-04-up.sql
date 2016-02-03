


-- foreign keys ;
----------------------------------------------------------------;

alter table ART46.DOSSIER 
   add constraint FK_FASE foreign key (DOSSIER_FASE_ID)
      references ART46.DOSSIER_FASE (FASE_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT C_DOSSIER_TYPE  CHECK (DOSSIER_TYPE in ('A', 'B', 'X'))
;

ALTER TABLE ART46.DOSSIER
      ADD CONSTRAINT C_DOSSIER_B  CHECK (DOSSIER_B <> '')
;


alter table ART46.DEELOPDRACHT
   add constraint FK_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;

alter table ART46.BESTEK
   add constraint FK_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;


alter table ART46.SCHULDVORDERING
   add constraint FK_BESTEK foreign key (BESTEK_ID)
      references ART46.BESTEK (BESTEK_ID)
      on delete restrict on update restrict
;




-- schuldvordering - factuur;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   add constraint FK_FACTUUR foreign key (PROJECT_ID, FACTUUR_ID)
      references SAP.PROJECT_FACTUUR (PROJECT_ID, FACTUUR_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.SCHULDVORDERING_SAP_PROJECT_FACTUUR
   add constraint FK_SCHULDV foreign key (VORDERING_ID)
      references ART46.SCHULDVORDERING (VORDERING_ID)
      on delete restrict on update restrict
;


-- dossier - factuur;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   add constraint FK_FACTUUR foreign key (PROJECT_ID, FACTUUR_ID)
      references SAP.PROJECT_FACTUUR (PROJECT_ID, FACTUUR_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.DOSSIER_SAP_PROJECT_FACTUUR
   add constraint FK_DOSSIER foreign key (DOSSIER_ID)
      references ART46.DOSSIER (ID)
      on delete restrict on update restrict
;


-- bestek - sap_project;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   add constraint FK_PROJECT foreign key (PROJECT_ID)
      references SAP.PROJECT (PROJECT_ID)
      on delete restrict on update restrict
;

ALTER TABLE ART46.BESTEK_SAP_PROJECT
   add constraint FK_BESTEK foreign key (BESTEK_ID)
      references ART46.BESTEK (BESTEK_ID)
      on delete restrict on update restrict
;



--  OVAM_AMBTENAAR_VIEW;
-----------------------------------------------------------------------------------------------------------


create view ART46.OVAM_AMBTENAAR_VIEW as
(
   select
	   ambtenaar_id,
	   ambtenaar_b,
	   tel1,
	   functie,
	   email,
	   uid,
	   max(afdeling_id) as afdeling_id ,
	   max(dienst_id) as dienst_id
   from
   (
      SELECT
	      k.id,
	      ka.uid AS ambtenaar_id,
	      k.naam_1 as ambtenaar_b,
	      k.tel as tel1,
	      ka.functie,
	      k.email,
	      ka.uid,
	      coalesce(a1.code , a2.code, a3.code) as afdeling_id,
	      coalesce(d2.code , d3.code) AS dienst_id
      FROM SMEG.KLANT k
	      INNER JOIN SMEG.KLANT_AMBTENAAR ka ON ka.id = k.id
	      	LEFT OUTER JOIN SMEG.KLANT_AMBTENAAR_AFDELING kaa ON kaa.klant_ambtenaar_id = ka.id
	      LEFT OUTER JOIN SMEG_REF.AFDELING a1 ON a1.id = kaa.afdeling_id
	      	left JOIN SMEG.KLANT_AMBTENAAR_DIENST kad ON kad.klant_ambtenaar_id = ka.id
	      LEFT OUTER JOIN SMEG_REF.DIENST d2 ON d2.id = kad.dienst_id
	      	LEFT OUTER JOIN SMEG_REF.AFDELING a2 ON a2.id = d2.afdeling_id
	      left JOIN SMEG.KLANT_AMBTENAAR_CEL kac ON kac.klant_ambtenaar_id = ka.id
	      	left JOIN SMEG_REF.CEL c ON c.id = kac.cel_id
	      LEFT OUTER JOIN SMEG_REF.DIENST d3 ON d3.id = c.dienst_id
	      	LEFT OUTER JOIN SMEG_REF.AFDELING a3 ON a3.id = d3.afdeling_id
   )
   GROUP BY id, ambtenaar_id, ambtenaar_b, tel1, functie, email, uid
)
;


create view ART46.V_DOSSIERHOUDERS_BOA as
(
select
   distinct o.DOSSIER_ID,
		   a.AMBTENAAR_ID,
		   a.AMBTENAAR_B,
		   'BOA' as AFDELING,
		   'O' as TYPE
from SMEG.RS_ONDERZOEK_VIEW o 
   		inner join ART46.OVAM_AMBTENAAR_VIEW a
   		on o.DOSS_HDR_ID = a.UID
   union
select
   distinct p.DOSSIER_ID,
   a.AMBTENAAR_ID,
   a.AMBTENAAR_B,
   'BOA' as AFDELING,
   'P' as TYPE
from SMEG.RS_PROJECT_VIEW p
		inner join ART46.OVAM_AMBTENAAR_VIEW a
		on p.DOSS_HDR_ID = a.UID
)
;


create view ART46.V_DOSSIERHOUDERS as
(
   select
   d.ID DOSSIER_ID,  dh.AMBTENAAR_ID, dh.AMBTENAAR_B , dh.AFDELING, dh.TYPE
   from ART46.DOSSIER d
   inner join ART46.V_DOSSIERHOUDERS_BOA dh on dh.DOSSIER_ID = d.dossier_id_boa
 union
   select
   a.ID DOSSIER_ID, b.AMBTENAAR_ID, b.AMBTENAAR_B , 'IVS' AFDELING, 'IVS' TYPE
   from ART46.DOSSIER a
   inner join ART46.OVAM_AMBTENAAR_VIEW b on a.DOSS_HDR_ID = b.AMBTENAAR_ID
 union
   select
   a.DOSSIER_ID DOSSIER_ID, b.AMBTENAAR_ID, b.AMBTENAAR_b, 'JD' AFDELING, 'JD' TYPE
   from ART46.DOSSIER_JD a
   inner join ART46.OVAM_AMBTENAAR_VIEW b on a.DOSS_HDR_ID = b.AMBTENAAR_ID
)
;


CREATE VIEW ART46.RS_KADASTER_AFD_VIEW AS
SELECT
	a.id AS KADASTER_AFD_ID,
	cg.nis_id AS NIS_ID,
	a.omschrijving AS KADASTER_AFD_B,
	a.toestandsdatum AS TOESTAND_D,
	ka.ambtenaar_code AS AMBTENAAR_ID
FROM SMEG_REF.KADASTER_AFDELING a
		INNER JOIN SMEG_REF.CRAB_GEMEENTE cg ON cg.id = a.crab_gemeente_id
	INNER JOIN SMEG.KLANT_AMBTENAAR ka ON ka.id = a.klant_ambtenaar_id
;



CREATE VIEW ART46.RS_KADASTER_VIEW AS
SELECT
	g.kadaster_id AS KADASTER_ID,
	g.kadaster_afdeling_id AS KADASTER_AFD_ID,
	g.sectie AS SECTIE,
	trim(CHAR(g.grond_nr)) AS GRONDNUMMER,
	trim(CHAR(g.bis_nr)) AS BISNUMMER,
	g.exponent1 AS EXPONENT1,
	trim(CHAR(g.exponent2)) AS EXPONENT2,
	g.oppervlakte AS OPPERVLAKTE,
	g.toestandsdatum AS TOESTAND_D,
	g.straat || coalesce(' ' || g.huis_nr, '') || coalesce(' bus ' || g.bus_nr, '') AS ADRES,
	trim(CHAR(g.gem_inv)) AS GEM_INV_S,
	g.opmerking AS COMMENTAAR
FROM SMEG.GROND g
;


CREATE VIEW ART46.V_DOSSIER_OPDRACHT_BOA AS
(
SELECT
	o.id AS ONDERZOEK_ID,
	d.dossier_id AS DOSSIER_ID,
	trim(CHAR(o.type_id)) AS TYPE_ONDERZOEK,
	o.rapport_datum AS RAPPORT_D,
	o.titel AS RAPPORT_TITEL_O,
	o.ontvangst_datum AS ONTVANGST_D,
	o.straat || coalesce(' ' || o.huis_nr, '') || coalesce(' bus ' || o.bus_nr, '') AS ADRES,
	o.postcode AS POSTCODE,
	o.gemeente AS STAD
FROM SMEG.ONDERZOEK ond
	INNER JOIN SMEG.OPDRACHT o ON o.id = ond.id
	INNER JOIN SMEG.BUNDEL b ON b.id = o.id
	INNER JOIN SMEG.BUNDEL_BUNDEL bb ON bb.child_bundel_id = o.id
	INNER JOIN SMEG.DOSSIER d ON d.id = bb.parent_bundel_id
);


create view ART46.RS_KADASTER_ONDERZOEK_VIEW as
(
SELECT
	g.kadaster_id AS KADASTER_ID, o.id AS ONDERZOEK_ID
FROM SMEG.GROND g
	INNER JOIN SMEG.LOCATIE_GROND lg ON lg.grond_id = g.id
	INNER JOIN SMEG.LOCATIE l ON l.id = lg.locatie_id
	INNER JOIN SMEG.BUNDEL_LOCATIE bl ON bl.locatie_id = l.id
	INNER JOIN SMEG.OPDRACHT o ON o.id = bl.bundel_id
	INNER JOIN SMEG.ONDERZOEK ond ON ond.id = o.id
)
;


CREATE VIEW ART46.RS_KADASTER_PROJECT_VIEW AS
SELECT
g.kadaster_id AS KADASTER_ID, o.id AS PROJECT_ID
FROM SMEG.GROND g
INNER JOIN SMEG.LOCATIE_GROND lg ON lg.grond_id = g.id
INNER JOIN SMEG.LOCATIE l ON l.id = lg.locatie_id
INNER JOIN SMEG.BUNDEL_LOCATIE bl ON bl.locatie_id = l.id
INNER JOIN SMEG.OPDRACHT o ON o.id = bl.bundel_id
INNER JOIN SMEG.BSP p ON p.id = o.id
;


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
   		inner join ART46.RS_KADASTER_ONDERZOEK_VIEW ko on o.ONDERZOEK_ID = ko.ONDERZOEK_ID
   left outer join ART46.VOOR_GOEDK a
   on a.DOSSIER_ID = d.ID
   and a.KADASTER_ID = ko.KADASTER_ID
--
   union
--
select
   d.id,
   d.DOSSIER_ID_BOA,
   kp.KADASTER_ID,
   a.ARTIKEL_ID,
   a.GOEDKEURING_S,
   a.COMMENTAAR COMMENTAAR_VOOR
from ART46.DOSSIER d
   inner join SMEG.RS_PROJECT_VIEW p on d.DOSSIER_ID_BOA = p.DOSSIER_ID
   		inner join ART46.RS_KADASTER_PROJECT_VIEW kp on p.PROJECT_ID = kp.PROJECT_ID
	left outer join ART46.VOOR_GOEDK a
	on a.DOSSIER_ID = d.ID
	and a.KADASTER_ID = kp.KADASTER_ID
)
;

CREATE VIEW ART46.V_SMEG_DOSSIER_ONDERZOEK AS
SELECT
	d.dossier_id AS DOSSIER_ID,
	o.id AS ONDERZOEK_ID
from SMEG.DOSSIER d
		INNER JOIN SMEG.BUNDEL_BUNDEL bb
		ON d.id = bb.parent_bundel_id	
	inner join SMEG.ONDERZOEK o
	on bb.child_bundel_id = o.id
;


CREATE VIEW ART46.V_SMEG_DOSSIER_PROJECT AS
SELECT
	d.dossier_id AS DOSSIER_ID,
	p.id AS PROJECT_ID
from SMEG.DOSSIER d
		INNER JOIN SMEG.BUNDEL_BUNDEL bb
		ON d.id = bb.parent_bundel_id	
	inner join SMEG.BSP p
	on bb.child_bundel_id = p.id
;

