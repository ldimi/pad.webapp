package be.ovam.art46.common.mail;

import be.ovam.pad.model.OvamMail;

public interface MailService {

    void sendHTMLMail(String aan, String onderwerp, String van, String s)throws Exception;
    void sendHTMLMail(String[] aan, String onderwerp, String van, String s)throws Exception;
    void sendHTMLMail(OvamMail ovamMail) throws Exception;

    OvamMail save(String aan, String onderwerp, String van, String bericht);
}
