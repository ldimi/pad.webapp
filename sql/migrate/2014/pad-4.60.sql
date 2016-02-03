--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

alter table ART46.OFFERTE_REGEL
    drop constraint ofr_mr_uniek
;

alter table ART46.OFFERTE_REGEL
    alter column offerte_id set null
    alter column meetstaat_regel_id set null
;
call sysproc.admin_cmd('reorg table art46.OFFERTE_REGEL');


delete from  art46.db_versie
where db_versie = '4.60';



--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

--select *
----delete
--from art46.OFFERTE_REGEL ofr1
--where 1 = 1
--    and (ofr1.OFFERTE_ID, ofr1.MEETSTAAT_REGEL_ID) in (
--                                            select ofr.OFFERTE_ID, ofr.MEETSTAAT_REGEL_ID
--                                            from art46.OFFERTE_REGEL ofr
--                                            group by ofr.OFFERTE_ID, ofr.MEETSTAAT_REGEL_ID
--                                            having count(*) > 1
--                                            )
--    and not exists ( select id from art46.OFFERTE_REGEL ofr2
--                    where ofr1.OFFERTE_ID = ofr2.OFFERTE_ID
--                             and ofr1.MEETSTAAT_REGEL_ID = ofr2.MEETSTAAT_REGEL_ID
--                           and ofr1.id > ofr2.id
--                   )
--;
--
--select *
----delete
--from art46.OFFERTE_REGEL ofr
--where ofr.MEETSTAAT_REGEL_ID is null or ofr.OFFERTE_ID is null



alter table ART46.OFFERTE_REGEL
    alter column offerte_id set not null
    alter column meetstaat_regel_id set not null
;
call sysproc.admin_cmd('reorg table art46.OFFERTE_REGEL');

alter table ART46.OFFERTE_REGEL
    add constraint ofr_mr_uniek unique(offerte_id, meetstaat_regel_id)
;


-- deze versie van de wijzigingen in db registreren.
insert into art46.db_versie(db_versie) values ('4.60');