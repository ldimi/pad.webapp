
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop function ART46.FLOAT_FORMAT
;

CREATE FUNCTION ART46.FLOAT_FORMAT (V_DEC FLOAT)
    RETURNS  VARCHAR(20)
    DETERMINISTIC NO EXTERNAL ACTION READS SQL DATA
    RETURN
    case art46.strip(char(decimal( round(V_DEC, +2), 12,2),','), 'L','0') when ',00' then '0,00' else art46.strip(char(decimal( round(V_DEC, +2), 12,2),','), 'L','0') end

    
    
    
drop function ART46.DECIMAL_FORMAT;

CREATE FUNCTION ART46.DECIMAL_FORMAT (V_DEC DECIMAL(13,2))
    RETURNS  VARCHAR(20)
    DETERMINISTIC NO EXTERNAL ACTION READS SQL DATA
    RETURN
    case art46.strip(char(decimal( round(V_DEC, +2), 12,2),','), 'L','0') when ',00' then '0,00' else art46.strip(char(decimal( round(V_DEC, +2), 12,2),','), 'L','0') end;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.52';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

drop function ART46.FLOAT_FORMAT
;

CREATE FUNCTION ART46.FLOAT_FORMAT (V_DEC FLOAT)
    RETURNS  VARCHAR(20)
    DETERMINISTIC NO EXTERNAL ACTION READS SQL DATA
    RETURN
    case char(decimal( round(V_DEC, +2), 12,2),',') when ',00' then '0,00' else rtrim(char(decimal( round(V_DEC, +2), 12,2),',')) end
;



drop function ART46.DECIMAL_FORMAT;


CREATE FUNCTION ART46.DECIMAL_FORMAT (V_DEC DECIMAL(13,2))
    RETURNS  VARCHAR(20)
    DETERMINISTIC NO EXTERNAL ACTION READS SQL DATA
    RETURN
    case strip(char(decimal( round(V_DEC, +2), 12,2),','), L,'0') when ',00' then '0,00' else strip(char(decimal( round(V_DEC, +2), 12,2),','), L,'0') end;




-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.52');
