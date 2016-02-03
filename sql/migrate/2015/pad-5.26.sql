--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '5.26';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- type wordt bij insert overgenomen van gekoppelde meetstaatregel

alter table art46.offerte_regel
    add column type varchar(24)
;

-- foreign key

alter table ART46.OFFERTE_REGEL
   drop constraint FK_OFR_MEETSTAATREGEL_TYPE
;

alter table ART46.offerte_regel
   add constraint FK_OFR_TYPE foreign key (TYPE)
      references ART46.MEETSTAATREGEL_TYPE (TYPE)
      on delete restrict on update restrict
;

alter table ART46.OFFERTE_REGEL
   add constraint FK_OFR_EXTRA_REGEL_TYPE foreign key (EXTRA_REGEL_TYPE)
      references ART46.MEETSTAATREGEL_TYPE (TYPE)
      on delete restrict on update restrict
;

-- type invullen voor alle reeds bestaande offert regels.

update art46.offerte_regel offr
set offr.type =  (select mr.type
              from art46.meetstaatregel mr
              where mr.id = offr.meetstaat_regel_id)
;

update art46.offerte_regel offr
set eenheidsprijs = 0
where eenheidsprijs is null and
      type =  'VH'
;


-- bi trigger die type invult op basis van type gekoppelde meetstaatregel

CREATE TRIGGER ART46.bi_offerte_regel_type NO CASCADE BEFORE
INSERT ON art46.offerte_regel REFERENCING NEW AS n FOR EACH ROW MODE DB2SQL
SET n.type = (select mr.type
              from art46.meetstaatregel mr
              where mr.id = n.meetstaat_regel_id)
;

CREATE TRIGGER ART46.bu_offerte_regel_type NO CASCADE BEFORE
UPDATE ON art46.offerte_regel REFERENCING NEW AS n FOR EACH ROW MODE DB2SQL
SET n.type = (select mr.type
              from art46.meetstaatregel mr
              where mr.id = n.meetstaat_regel_id)
;


-- check constraint : als type = 'VH' dan moet eenheidsprijs ingevuld zijn
alter table ART46.OFFERTE_REGEL
   add constraint cc_type_eenheidsprijs CHECK (
                                               (type = 'VH' and eenheidsprijs is not null) or
                                               type != 'VH'
                                              )
;





-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.26');


