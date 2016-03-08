package be.ovam.art46.common.mail;

import be.ovam.pad.model.OvamMail;

public interface MailService {

	public void sendMail(String to, String subject, String from, String message) throws Exception;

    void sendHTMLMail(String aan, String onderwerp, String van, String s)throws Exception;
    void sendHTMLMail(String[] aan, String onderwerp, String van, String s)throws Exception;

    OvamMail save(String aan, String onderwerp, String van, String bericht);

    void sendMail(OvamMail ovamMail) throws Exception;

}
