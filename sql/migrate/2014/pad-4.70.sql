--------------------------------------------------------------------;
-- DOWN
--------------------------------------------------------------------;

delete from  ART46.DB_VERSIE
where DB_VERSIE = '4.70';

--------------------------------------------------------------------;
-- UP
--------------------------------------------------------------------;

alter table ART46.VOORSTEL_DEELOPDRACHT
   drop constraint FK_vdo_mail
;


drop table art46.mail;

CREATE TABLE ART46.MAIL (
  id int PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY,
  to VARCHAR (120) not null,
  subject VARCHAR(500) not null,
  from VARCHAR (120) not null,
  message VARCHAR(2028) not null
);


alter table ART46.VOORSTEL_DEELOPDRACHT
   add constraint FK_vdo_mail foreign key (mail_id)
      references art46.mail (id)
      on delete restrict on update restrict
;



-- deze versie van de wijzigingen in db registreren.
insert into ART46.DB_VERSIE(DB_VERSIE) values ('4.70');

