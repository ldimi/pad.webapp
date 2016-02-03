

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;


delete from  art46.db_versie
where db_versie = '5.46';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

-- dubbele QR-codes verwijderen.
update  art46.brief br
set qr_code =  null
where 1 = 1
    and br.QR_CODE is not null
    and br.PARENT_BRIEF_ID is not null
    and br.CATEGORIE_ID = 5


create trigger art46.bi_brief_qr_code_uniek before
insert on art46.brief referencing new as n for each row when
(
    n.qr_code is not null
    and n.qr_code in (select qr_code from art46.brief)
)
signal sqlstate '75000' set message_text='De qr_code moet uniek zijn.'
;

create trigger art46.bu_brief_qr_code_uniek before
update on art46.brief referencing new as n old as o for each row when
(
   (n.qr_code is not null and (n.qr_code != o.qr_code or o.qr_code is null) )
   and ( n.qr_code in (select qr_code from art46.brief))
)
signal sqlstate '75000' set message_text='De qr_code moet uniek zijn.'
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.46');
