--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

drop view art46.v_bestek;



delete from  art46.db_versie
where db_versie = '4.89';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;


drop trigger art46.bi_brief_ltst_wzg_d;
drop trigger art46.bu_brief_ltst_wzg_d;

update art46.brief br
    set br.CONTACT_ID = null
where 1 = 1
    and br.CONTACT_ID is not null
    and not exists (select contact_id from art46.ADRES_CONTACT ac where br.CONTACT_ID = ac.contact_id)

create trigger art46.bi_brief_ltst_wzg_d
no cascade before insert on art46.brief
referencing
    new as post
for each row mode db2sql
set
        post.ltst_wzg_d = current timestamp
;

create trigger art46.bu_brief_ltst_wzg_d
no cascade before update on art46.brief
referencing
    old as pre
    new as post
for each row mode db2sql
set
        post.ltst_wzg_d = current timestamp
;

alter table art46.brief
   add constraint fk_br_contact foreign key (contact_id)
      references art46.adres_contact (contact_id)
      on delete restrict on update restrict
;







drop view art46.v_brief;

create view art46.v_brief as
select
    br.brief_id,
    br.brief_nr,
    br.adres_id,
    a.naam as adres_naam,
    a.voornaam as adres_voornaam,
    br.contact_id,
    ac.naam as contact_naam,
    ac.voornaam as contact_voornaam,
    case when br.contact_id is null then coalesce(a.naam, '') || ' ' || coalesce(a.voornaam, '')
         else coalesce(ac.naam, '') || ' ' || coalesce(ac.voornaam, '')
    end as naam_voornaam,
    br.dossier_id,
    d.dossier_nr,
    d.dossier_b,
    d.dossier_id_boa,
    d.doss_hdr_id,
    d.dossier_fase_id,
    d.nis_id,
    d.raamcontract_jn,
    d.gemeente_b,
    br.dienst_id,
    br.inschrijf_d,
    br.betreft,
    br.commentaar,
    br.ltst_wzg_user_id,
    br.ltst_wzg_d,
    br.in_aard_id,
    bai.brief_aard_b as in_aard_b,
    br.in_type_id,
    bti.brief_type_b as in_type_b,
    br.in_d,
    br.in_stuk_d,
    br.in_referte,
    br.in_bijlage,
    br.uit_aard_id,
    bau.brief_aard_b as uit_aard_b,
    br.uit_type_id,
    btu.brief_type_b as uit_type_b,
    br.uit_type_id_vos,
    typ.type_b as uit_type_vos_b,
    br.uit_d,
    br.uit_referte,
    br.uit_bijlage,
    br.volgnummer,
    br.auteur_id,
    br.dms_id,
    br.dms_filename,
    br.dms_folder,
    br.categorie_id,
    bc.brief_categorie_b,
    br.reactie_d,
    br.reactie_voor_d,
    br.opnemen_export_s,
    br.parent_brief_id,
    br.bestek_id,
    be.bestek_nr,
    br.check_afd_hfd_d,
    br.opmerking_afd_hfd,
    br.check_auteur_d,
    br.qr_code,
    br.teprinten_jn,
    br.print_d
from art46.brief br
        inner join art46.v_dossier d on br.dossier_id = d.id
        inner join art46.adres a on br.adres_id = a.adres_id
        left join art46.adres_contact ac on br.contact_id = ac.contact_id
        left join art46.brief_type_vos typ on typ.type_id = br.uit_type_id_vos
        left join art46.brief_aard bai on bai.brief_aard_id = br.in_aard_id
        left join art46.brief_aard bau on bau.brief_aard_id = br.uit_aard_id
        left join art46.brief_categorie bc on br.categorie_id = bc.brief_categorie_id
        left join art46.bestek be on br.bestek_id = be.bestek_id
        left join art46.brief_type bti on br.in_type_id = bti.brief_type_id
        left join art46.brief_type btu on br.uit_type_id = btu.brief_type_id
;

select 'drop view ' || rtrim(v.VIEWSCHEMA) || '.' || rtrim(v.VIEWNAME) || ';' ||
       replace(cast(v.text as varchar(32000)), chr(10) , ' ') || ';'
from syscat.VIEWS v
where v.viewschema = 'ART46'
     and v.valid = 'N'
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.89');





























