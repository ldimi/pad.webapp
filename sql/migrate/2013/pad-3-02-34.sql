
--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.SCHULDVORDERING    
    drop CONSTRAINT FK_SV_AFGEKEURD_JN
;

alter table ART46.SCHULDVORDERING
     drop column afgekeurd_jn
;

drop table art46.JA_NEE_CODE;



call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING')
;


delete from  ART46.DB_VERSIE
where DB_VERSIE = '3.02.34';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table ART46.SCHULDVORDERING
    add column afgekeurd_jn char(1) not null default 'N'
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING')
;

create table art46.JA_NEE_CODE
(
    code char(1) not null,
    code_b varchar(5) not null,
    primary key (code)
)
;

insert into art46.JA_NEE_CODE (code, code_b)
values
('J','Ja'),
('N','Nee')
;

ALTER TABLE ART46.SCHULDVORDERING    
    ADD CONSTRAINT FK_SV_AFGEKEURD_JN FOREIGN KEY (afgekeurd_JN)
    REFERENCES ART46.JA_NEE_CODE(code)
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('3.02.34');
