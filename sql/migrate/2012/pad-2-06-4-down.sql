

-- ART46.DOSSIER

DROP TRIGGER ART46.bi_dossier_ts
;


DROP TRIGGER ART46.bu_dossier_ts  
;

-- SAP.PROJECT

DROP TRIGGER SAP.BI_PROJECT_TS  
;


DROP TRIGGER SAP.BU_PROJECT_TS   
;


-- ART46.SCHULDVORDERING

DROP TRIGGER ART46.BI_SCHULDVORDERING_TS  
;


DROP TRIGGER ART46.BU_SCHULDVORDERING_TS   
;


-- ART46.BESTEK

DROP TRIGGER ART46.BI_BESTEK_TS  
;


DROP TRIGGER ART46.BU_BESTEK_TS   
;


-- ART46.DEELOPDRACHT

DROP TRIGGER ART46.BI_DEELOPDRACHT_TS  
;


DROP TRIGGER ART46.BU_DEELOPDRACHT_TS   
;

-- ART46.SCHULDVORDERING_SAP_PROJECT

DROP TRIGGER ART46.BI_SVSP_TS  
;


DROP TRIGGER ART46.BU_SVSP_TS   
;


-- tabel met versie nummer van db schema

DROP table ART46.DB_VERSIE
;


DROP TRIGGER ART46.BI_DB_VERSIE_TS  
;


DROP TRIGGER ART46.BU_DB_VERSIE_TS   
;

-- delete from  ART46.DB_VERSIE where DB_VERSIE = '2.06.4';
