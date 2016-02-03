--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop function art46.round_2_dec
#


delete from  art46.db_versie
where db_versie = '5.13'#

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create or replace function art46.round_to_dec_31_2(delta decfloat)
      returns decimal(31,2)
begin
    declare alpha decimal(31,2);
            set alpha = round(cast(delta as decimal(31,3)),2);
    return alpha;
end
#




-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.13')#



