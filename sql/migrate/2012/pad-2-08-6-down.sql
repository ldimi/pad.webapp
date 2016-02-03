

drop TRIGGER ART46.BI_DOSSIER_NIS_ID; 

drop TRIGGER ART46.BU_DOSSIER_NIS_ID;   

alter table ART46.DOSSIER
	drop constraint cc_nis_id_int
;

alter table ART46.DOSSIER
	drop column NIS_ID_INT
;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '2.08.6';
