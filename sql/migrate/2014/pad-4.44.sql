--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.44';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;



ALTER TABLE ART46.BRIEF_AARD
    add termijn int,
    add controle_afdelingshoofd_jn CHAR(1) not null default 'N'
;

CALL SYSPROC.ADMIN_CMD('REORG TABLE ART46.BRIEF_AARD');


ALTER TABLE ART46.BRIEF_AARD
    ADD CONSTRAINT FK_BA_CONTROLE_AH_JN FOREIGN KEY (controle_afdelingshoofd_jn)
    REFERENCES ART46.JA_NEE_CODE(code)
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.44');