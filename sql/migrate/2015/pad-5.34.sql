

--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop table art46.brief_hist

delete from  art46.db_versie
where db_versie = '5.34';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

create table art46.brief_hist like art46.brief;

alter table art46.brief_hist
    alter column ltst_wzg_d set not null
;

call sysproc.admin_cmd('reorg table art46.brief_hist')
;


alter table art46.brief_hist
    add column deleted_jn  char(1) not null default 'N'
    add primary key (brief_id, ltst_wzg_d)
;

call sysproc.admin_cmd('reorg table art46.brief_hist')
;

--drop trigger art46.ad_brief_hist

create trigger art46.au_brief_hist
after update on art46.brief
referencing
    old as pre
    new as post
for each row mode db2sql
insert into art46.brief_hist (
	    brief_id,
	    brief_nr,
	    adres_id,
	    contact_id,
	    dossier_id,
	    dienst_id,
	    inschrijf_d,
	    betreft,
	    commentaar,
	    ltst_wzg_user_id,
	    ltst_wzg_d,
	    in_aard_id,
	    in_type_id,
	    in_d,
	    in_stuk_d,
	    in_referte,
	    in_bijlage,
	    uit_aard_id,
	    uit_type_id,
	    uit_type_id_vos,
	    uit_d,
	    uit_referte,
	    uit_bijlage,
	    volgnummer,
	    auteur_id,
	    dms_id,
	    dms_filename,
	    dms_folder,
	    categorie_id,
	    reactie_d,
	    reactie_voor_d,
	    opnemen_export_s,
	    parent_brief_id,
	    bestek_id,
	    check_afd_hfd_d,
	    opmerking_afd_hfd,
	    check_auteur_d,
	    qr_code,
	    teprinten_jn,
	    print_d,
	    gegenereerd_jn,
	    deleted_jn
    )
values (
	    pre.brief_id,
	    pre.brief_nr,
	    pre.adres_id,
	    pre.contact_id,
	    pre.dossier_id,
	    pre.dienst_id,
	    pre.inschrijf_d,
	    pre.betreft,
	    pre.commentaar,
	    pre.ltst_wzg_user_id,
	    current timestamp,
	    pre.in_aard_id,
	    pre.in_type_id,
	    pre.in_d,
	    pre.in_stuk_d,
	    pre.in_referte,
	    pre.in_bijlage,
	    pre.uit_aard_id,
	    pre.uit_type_id,
	    pre.uit_type_id_vos,
	    pre.uit_d,
	    pre.uit_referte,
	    pre.uit_bijlage,
	    pre.volgnummer,
	    pre.auteur_id,
	    pre.dms_id,
	    pre.dms_filename,
	    pre.dms_folder,
	    pre.categorie_id,
	    pre.reactie_d,
	    pre.reactie_voor_d,
	    pre.opnemen_export_s,
	    pre.parent_brief_id,
	    pre.bestek_id,
	    pre.check_afd_hfd_d,
	    pre.opmerking_afd_hfd,
	    pre.check_auteur_d,
	    pre.qr_code,
	    pre.teprinten_jn,
	    pre.print_d,
	    pre.gegenereerd_jn,
	    'N'
)
;


create trigger art46.ad_brief_hist
after delete on art46.brief
referencing
    old as pre
for each row mode db2sql
insert into art46.brief_hist (
	    brief_id,
	    brief_nr,
	    adres_id,
	    contact_id,
	    dossier_id,
	    dienst_id,
	    inschrijf_d,
	    betreft,
	    commentaar,
	    ltst_wzg_user_id,
	    ltst_wzg_d,
	    in_aard_id,
	    in_type_id,
	    in_d,
	    in_stuk_d,
	    in_referte,
	    in_bijlage,
	    uit_aard_id,
	    uit_type_id,
	    uit_type_id_vos,
	    uit_d,
	    uit_referte,
	    uit_bijlage,
	    volgnummer,
	    auteur_id,
	    dms_id,
	    dms_filename,
	    dms_folder,
	    categorie_id,
	    reactie_d,
	    reactie_voor_d,
	    opnemen_export_s,
	    parent_brief_id,
	    bestek_id,
	    check_afd_hfd_d,
	    opmerking_afd_hfd,
	    check_auteur_d,
	    qr_code,
	    teprinten_jn,
	    print_d,
	    gegenereerd_jn,
	    deleted_jn
    )
values (
	    pre.brief_id,
	    pre.brief_nr,
	    pre.adres_id,
	    pre.contact_id,
	    pre.dossier_id,
	    pre.dienst_id,
	    pre.inschrijf_d,
	    pre.betreft,
	    pre.commentaar,
	    pre.ltst_wzg_user_id,
	    current timestamp,
	    pre.in_aard_id,
	    pre.in_type_id,
	    pre.in_d,
	    pre.in_stuk_d,
	    pre.in_referte,
	    pre.in_bijlage,
	    pre.uit_aard_id,
	    pre.uit_type_id,
	    pre.uit_type_id_vos,
	    pre.uit_d,
	    pre.uit_referte,
	    pre.uit_bijlage,
	    pre.volgnummer,
	    pre.auteur_id,
	    pre.dms_id,
	    pre.dms_filename,
	    pre.dms_folder,
	    pre.categorie_id,
	    pre.reactie_d,
	    pre.reactie_voor_d,
	    pre.opnemen_export_s,
	    pre.parent_brief_id,
	    pre.bestek_id,
	    pre.check_afd_hfd_d,
	    pre.opmerking_afd_hfd,
	    pre.check_auteur_d,
	    pre.qr_code,
	    pre.teprinten_jn,
	    pre.print_d,
	    pre.gegenereerd_jn,
	    'J'
)
;



-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('5.34');