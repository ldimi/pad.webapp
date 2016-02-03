--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop trigger art46.biu_schulvordering_round
;


delete from  art46.db_versie
where db_versie = '4.62';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- multiple event trigger

-- NIET UITGEVOERD : afrondingen in databank geven geen afgeronde waarden in java double
--    Dit gaf  problemen bij de isDirty test in editeerscherm sschuldvordering.
--    De afronding is dus in de code opgenomen

--create trigger art46.biu_schulvordering_round
--no cascade
--before insert or update on art46.schuldvordering
--referencing new as n old as o
--for each row mode db2sql
--begin
--     set n.vordering_bedrag = round(n.vordering_bedrag, 2);
--     set n.goedkeuring_bedrag = round(n.goedkeuring_bedrag, 2);
--     set n.herziening_bedrag = round(n.herziening_bedrag, 2);
--     set n.boete_bedrag = round(n.boete_bedrag, 2);
--     set n.vordering_correct_bedrag = round(n.vordering_correct_bedrag, 2);
--     set n.herziening_correct_bedrag = round(n.herziening_correct_bedrag, 2);
--end
--#


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.62')#