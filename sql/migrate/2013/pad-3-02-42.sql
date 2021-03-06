
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.42';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('AANTAL','Aantal');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('CM','cm');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('DAG','Dag');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('DEELOPDRACHT','Deelopdracht');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('FASE','Fase');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('HALVE WERKDAG','Halve werkdag');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('KADASTRAAL PERCEEL','Kadastraal perceel');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('KALENDERDAG','Kalenderdag');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('KILOGRAM','Kilogram');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('KILOMETER','Kilometer');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('LITER','Liter');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('M','m');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('M²','m²');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('M².KALENDERDAGEN','m².kalenderdagen');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('M³','m³');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('MOB/DEMOB','Mob/demob');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('SITE','Site');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('STELPOST','Stelpost');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('STUK','Stuk');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('TON','Ton');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('TON.DAGEN','Ton.dagen');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('TON.KILOMETER','Ton.kilometer');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('UUR','Uur');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('VERZAMELSTAAL','Verzamelstaal');
INSERT INTO "ART46"."MEETSTAAT_EENHEID" (CODE,NAAM) VALUES ('WERKDAG','Werkdag');



INSERT INTO "ART46"."MEETSTAAT_EENHEID_MAPPING" (EENHEIDCODE,NAAM) VALUES ('AANTAL','Totaalanalyse');
INSERT INTO "ART46"."MEETSTAAT_EENHEID_MAPPING" (EENHEIDCODE,NAAM) VALUES ('AANTAL','interventie');
INSERT INTO "ART46"."MEETSTAAT_EENHEID_MAPPING" (EENHEIDCODE,NAAM) VALUES ('AANTAL','per site');
INSERT INTO "ART46"."MEETSTAAT_EENHEID_MAPPING" (EENHEIDCODE,NAAM) VALUES ('CM','Centimeter');
INSERT INTO "ART46"."MEETSTAAT_EENHEID_MAPPING" (EENHEIDCODE,NAAM) VALUES ('M','meter');
INSERT INTO "ART46"."MEETSTAAT_EENHEID_MAPPING" (EENHEIDCODE,NAAM) VALUES ('UUR','uren');

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.42');
