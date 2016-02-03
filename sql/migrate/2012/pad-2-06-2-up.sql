update art46.SCHULDVORDERING
set COMMENTAAR = null
where COMMENTAAR = ''
;

update art46.SCHULDVORDERING
set vordering_nr = null
where vordering_nr = ''
;

-- TRIGGER schuldv_comment;

CREATE TRIGGER art46_bi_schuldv_comment
NO CASCADE 
BEFORE INSERT ON art46.SCHULDVORDERING
REFERENCING
  NEW AS n
FOR EACH ROW MODE DB2SQL 
WHEN (n.commentaar = '')
       SET n.commentaar = null


CREATE TRIGGER art46_bu_schuldv_comment
NO CASCADE 
BEFORE UPDATE ON art46.SCHULDVORDERING
REFERENCING
  OLD AS o
  NEW AS n
FOR EACH ROW MODE DB2SQL 
WHEN (n.commentaar = '')
       SET n.commentaar = null

-- TRIGGER schuldv_vordnr;

CREATE TRIGGER art46_bi_schuldv_vordnr
NO CASCADE 
BEFORE INSERT ON art46.SCHULDVORDERING
REFERENCING
  NEW AS n
FOR EACH ROW MODE DB2SQL 
WHEN (n.vordering_nr = '')
       SET n.vordering_nr = null


CREATE TRIGGER art46_bu_schuldv_vordnr
NO CASCADE 
BEFORE UPDATE ON art46.SCHULDVORDERING
REFERENCING
  OLD AS o
  NEW AS n
FOR EACH ROW MODE DB2SQL 
WHEN (n.vordering_nr = '')
       SET n.vordering_nr = null
