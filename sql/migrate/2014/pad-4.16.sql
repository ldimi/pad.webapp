--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

ALTER TABLE ART46.schuldvordering
    DROP CONSTRAINT UC_brief_of_aanvraag_id
;



drop TRIGGER ART46.BI_SCHULDV_BRIEF_OF_AANVRAAG;

drop TRIGGER ART46.BU_SCHULDV_BRIEF_OF_AANVRAAG;

alter table art46.schuldvordering
    drop column brief_of_aanvraag_id
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING');

ALTER TABLE ART46.schuldvordering
    ADD CONSTRAINT UC_BRIEF_ID UNIQUE (BRIEF_ID)
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING');

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.16';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


alter table ART46.SCHULDVORDERING
    drop UNIQUE UC_BRIEF_ID
;

ALTER TABLE ART46.SCHULDVORDERING
    alter column brief_id drop not null
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING')
;

ALTER TABLE ART46.SCHULDVORDERING
    add constraint cc_brief_of_aanvraag check ( (brief_id is null and aanvraag_schuldvordering_id is not null) or
                                                (brief_id is not null and aanvraag_schuldvordering_id is null)  )
;



alter table art46.schuldvordering
    add column brief_of_aanvraag_id varchar(11)
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING');


update art46.schuldvordering
set brief_of_aanvraag_id  =  'B' || rtrim(cast(brief_id as char(10)))
;


CREATE TRIGGER ART46.BI_SCHULDV_BRIEF_OF_AANVRAAG
NO CASCADE BEFORE INSERT ON ART46.SCHULDVORDERING
REFERENCING
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.brief_of_aanvraag_id = case when post.brief_id is not null then 'B' || rtrim(cast(post.brief_id as char(10)))
                                         else 'A' || rtrim(cast(post.aanvraag_schuldvordering_id as char(10)))
                                    end
;


CREATE TRIGGER ART46.BU_SCHULDV_BRIEF_OF_AANVRAAG
NO CASCADE BEFORE UPDATE ON ART46.SCHULDVORDERING
REFERENCING
    OLD AS pre
    NEW AS post
FOR EACH ROW MODE DB2SQL
SET
        post.brief_of_aanvraag_id = case when post.brief_id is not null then 'B' || rtrim(cast(post.brief_id as char(10)))
                                         else 'A' || rtrim(cast(post.aanvraag_schuldvordering_id as char(10)))
                                    end
;


ALTER TABLE ART46.SCHULDVORDERING
    alter column brief_of_aanvraag_id set not null
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING');


ALTER TABLE ART46.schuldvordering
    ADD CONSTRAINT UC_brief_of_aanvraag_id UNIQUE (brief_of_aanvraag_id)
;

call sysproc.admin_cmd('reorg table ART46.SCHULDVORDERING');



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.16');



