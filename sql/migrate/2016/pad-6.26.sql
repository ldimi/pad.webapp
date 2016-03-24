

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  art46.db_versie
where db_versie = '6.26';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

select dos2.id, dos2.dossier_nr, dos2.PROGRAMMA_CODE,
       (select doh2.PROGRAMMA_TYPE_CODE
       from art46.DOSSIER_HOUDER doh2
       where dos2.DOSS_HDR_ID = doh2.DOSS_HDR_ID)
from art46.dossier dos2
where dos2.id in (
          select dos.id
		from art46.dossier dos
				inner join art46.DOSSIER_HOUDER doh
				on dos.DOSS_HDR_ID = doh.DOSS_HDR_ID
		where 1 = 1
		    and dos.dossier_nr not like '+_%' escape '+'
		    and dos.afsluit_d is null
		    and dos.PROGRAMMA_CODE != doh.PROGRAMMA_TYPE_CODE
	     )
;



update art46.dossier dos2
set dos2.programma_code =
       (select doh2.PROGRAMMA_TYPE_CODE
       from art46.DOSSIER_HOUDER doh2
       where dos2.DOSS_HDR_ID = doh2.DOSS_HDR_ID)
where dos2.id in (
          select dos.id
		from art46.dossier dos
				inner join art46.DOSSIER_HOUDER doh
				on dos.DOSS_HDR_ID = doh.DOSS_HDR_ID
		where 1 = 1
		    and dos.dossier_nr not like '+_%' escape '+'
		    and dos.afsluit_d is null
		    and dos.PROGRAMMA_CODE != doh.PROGRAMMA_TYPE_CODE
	     )
;

-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie, omschrijving) values ('6.26', 'programma_code van dossier updaten <-- doss_hdr.programma');
