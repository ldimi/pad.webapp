

-- Fase codes
INSERT INTO "ART46"."PLANNING_FASE" (DOSSIER_TYPE,FASE_CODE,BUDGET_CODE)
VALUES
	('A','VVWR','afval'),
	('A','AVWR','afval'),
	('B','BBP','werken'),
	('B','BSP','werken'),
	('B','BSW','werken'),
	('B','NZ','werken'),
	('B','VZM','werken'),
	('B','VEI','werken'),
	('B','BBO','onderzoek'),
	('B','OBO','onderzoek')
;



INSERT INTO "ART46"."PLANNING_FASE_DETAIL" (FASE_CODE, FASE_DETAIL_CODE)
VALUES
	('VVWR', 'uitvoering'),
	('AVWR', 'uitvoering'),
	('BBP', 'uitvoering'),
	('BSP', 'uitvoering'),
	('BSW', 'werken'),
	('BSW', 'VC'),
	('BSW', 'begeleiding'),
	('NZ', 'uitvoering'),
	('VZM', 'uitvoering'),
	('VEI', 'uitvoering'),
	('BBO', 'uitvoering'),
	('OBO', 'uitvoering')
;

-- VERSIE 1
-----------;
INSERT INTO ART46.PLANNING
	(DOSS_HDR_ID, WIJZIG_USER)
VALUES ('ctodts', 'dvdveken');

INSERT INTO ART46.PLANNING_VERSIE
	(DOSS_HDR_ID, PLANNING_VERSIE, WIJZIG_USER)
VALUES ('ctodts',   1,             'dvdveken');

-- lijn 1;
INSERT INTO ART46.PLANNING_LIJN
	(DOSS_HDR_ID, LIJN_ID, WIJZIG_USER)
VALUES ('ctodts',    1,      'dvdveken');

INSERT INTO ART46.PLANNING_LIJN_VERSIE
	(DOSS_HDR_ID, LIJN_ID, PLANNING_VERSIE, DOSSIER_ID, DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE, IG_BEDRAG, WIJZIG_USER)
VALUES ('ctodts',    1,       1,             23039,     'B',           'BSP',      null,             1000,      'dvdveken');

-- lijn 2;
INSERT INTO ART46.PLANNING_LIJN
	(DOSS_HDR_ID, LIJN_ID, WIJZIG_USER)
VALUES ('ctodts',    2,      'dvdveken');

INSERT INTO ART46.PLANNING_LIJN_VERSIE
	(DOSS_HDR_ID, LIJN_ID, PLANNING_VERSIE, DOSSIER_ID, DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE, IG_BEDRAG, WIJZIG_USER)
VALUES ('ctodts',    2,       1,             23039,     'B',          'BSW',     'werken',         2000,     'dvdveken');

-- lijn 3;
INSERT INTO ART46.PLANNING_LIJN
	(DOSS_HDR_ID, LIJN_ID, WIJZIG_USER)
VALUES ('ctodts',    3,      'dvdveken');

INSERT INTO ART46.PLANNING_LIJN_VERSIE
	(DOSS_HDR_ID, LIJN_ID, PLANNING_VERSIE, DOSSIER_ID, DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE, IG_BEDRAG, WIJZIG_USER)
VALUES ('ctodts',    3,       1,             23039,     'B',          'BSW',      'VC',             3000,     'dvdveken');


-- VERSIE 2 : data eerste lijn editeren.
----------------------------------------;
INSERT INTO ART46.PLANNING_VERSIE
	(DOSS_HDR_ID, PLANNING_VERSIE, WIJZIG_USER)
VALUES ('ctodts',   2,             'dvdveken');

-- lijn 1 data 2 
INSERT INTO ART46.PLANNING_LIJN_VERSIE
	(DOSS_HDR_ID, LIJN_ID, PLANNING_VERSIE, DOSSIER_ID, DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE, IG_BEDRAG, WIJZIG_USER)
VALUES ('ctodts',    1,       2,             23039,     'B',           'BSP',     null,             1010,     'dvdveken');


-- VERSIE 3 : lijn toevoegen
----------------------------;
INSERT INTO ART46.PLANNING_VERSIE
	(DOSS_HDR_ID, PLANNING_VERSIE, WIJZIG_USER)
VALUES ('ctodts',   3,             'dvdveken');

INSERT INTO ART46.PLANNING_LIJN
	(DOSS_HDR_ID, LIJN_ID, WIJZIG_USER)
VALUES ('ctodts',    4,      'dvdveken');

INSERT INTO ART46.PLANNING_LIJN_VERSIE
	(DOSS_HDR_ID, LIJN_ID, PLANNING_VERSIE, DOSSIER_ID, DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE, IG_BEDRAG, WIJZIG_USER)
VALUES ('ctodts',    4,       3,             23039,     'B',          'BSP',      'uitvoering',     4000,     'dvdveken');


-- VERSIE 4 : lijn 2 verwijderen
-------------------------------------------;
INSERT INTO ART46.PLANNING_VERSIE
	(DOSS_HDR_ID, PLANNING_VERSIE, WIJZIG_USER)
VALUES ('ctodts',   4,             'dvdveken');

INSERT INTO ART46.PLANNING_LIJN_VERSIE
	(DOSS_HDR_ID, LIJN_ID, PLANNING_VERSIE, DOSSIER_ID, DOSSIER_TYPE, FASE_CODE, FASE_DETAIL_CODE, IG_BEDRAG,  DELETED_JN,  WIJZIG_USER)
VALUES ('ctodts',    2,       4,             23039,     'B',          'BSW',      'werken',         2000,       'J',        'dvdveken');


