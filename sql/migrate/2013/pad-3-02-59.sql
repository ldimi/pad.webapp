
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

INSERT INTO "ART46"."BRIEF_CATEGORIE" (BRIEF_CATEGORIE_ID,BRIEF_CATEGORIE_B,SORT)
VALUES (17,'cd-rom',1);


INSERT INTO "ART46"."BRIEF_CATEGORIE_BRIEF_CATEGORIE" (PARENT_BRIEF_CATEGORIE_ID,CHILD_BRIEF_CATEGORIE_ID) 
VALUES (16, 17);

delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.59';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

delete 
from art46.BRIEF_CATEGORIE_BRIEF_CATEGORIE bcbc
where bcbc.CHILD_BRIEF_CATEGORIE_ID = 17
;

delete
from art46.BRIEF_CATEGORIE bc
where bc.BRIEF_CATEGORIE_ID = 17
;   

-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.59');
