


create view ART46.V_SMEG_BUNDEL_KADASTER as
select bl.BUNDEL_ID,
    kv.*
from smeg.BUNDEL_LOCATIE bl
    inner join SMEG.LOCATIE l
    on bl.LOCATIE_ID = l.ID
        inner join SMEG.LOCATIE_GROND lg
        on l.id = lg.LOCATIE_ID
    inner join SMEG.GROND g
    on lg.GROND_ID = g.id
        inner join art46.RS_KADASTER_VIEW kv
        on g.KADASTER_ID = kv.KADASTER_ID
;


CREATE VIEW ART46.V_SMEG_DOSSIER_KADASTER AS
SELECT
    d.dossier_id,
    bk.*
from SMEG.DOSSIER d
        inner join SMEG.BUNDEL_BUNDEL bb
        on d.id = bb.parent_bundel_id
    inner join ART46.V_SMEG_BUNDEL_KADASTER bk
    on bb.CHILD_BUNDEL_ID = bk.BUNDEL_ID
;


-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('2.08.1');
