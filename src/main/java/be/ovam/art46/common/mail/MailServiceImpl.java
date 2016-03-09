package be.ovam.art46.common.mail;

import be.ovam.art46.dao.MailDAO;
import be.ovam.pad.model.OvamMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
	private JavaMailSenderImpl mailSender;

    @Autowired
    private MailDAO mailDAO;

    @Value("${ovam.omgeving}")
    private String ovam_omgeving;

	
	public void sendMail(String to, String subject, String from, String message) throws Exception {
        MailServiceImpl.this.sendHTMLMail(to, subject, from, message);
	}
    
    public void sendHTMLMail(OvamMail ovamMail) throws MessagingException {
        MailServiceImpl.this.sendHTMLMail(ovamMail.getTo(), ovamMail.getSubject(),ovamMail.getFrom(), ovamMail.getMessage());
    }
    
    public void sendHTMLMail(String to, String subject, String from, String bericht) throws MessagingException {
        String[] toArr =  new String[] {to};
        MailServiceImpl.this.sendHTMLMail(toArr, subject, from, bericht);
    }

    public void sendHTMLMail(String[] toArr, String subject, String from, String bericht) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(getToArr(toArr));
        helper.setFrom(from);
        helper.setSubject(getSubjectPrefix() + subject);
        helper.setText(getTextPrefix(toArr, "<br>") + bericht, true);
        mailSender.send(message);
    }

    public OvamMail save(String aan, String onderwerp, String van, String bericht) {
        OvamMail ovamMail = new OvamMail();
        ovamMail.setTo(aan);
        ovamMail.setFrom(van);
        ovamMail.setMessage(bericht);
        ovamMail.setSubject(onderwerp);
        mailDAO.save(ovamMail);
        return ovamMail;
    }

    private String[] getToArr (String[] toArr) {
        if ("productie".equals(ovam_omgeving)) {
            return toArr;
        } else {
            return new String[] {"ivstest120@gmail.com"};
        }
    }
    
    private String getSubjectPrefix() {
        if ("productie".equals(ovam_omgeving)) {
            return "";
        } else {
            return "Omgeving " + ovam_omgeving + " - ";
        }
    }
    
    
    private String getTextPrefix(String[] toList, String linebreak) {
        if ("productie".equals(ovam_omgeving)) {
            return "";
        } else {
            String toStr = "Bericht zou gestuurd worden naar de volgende adressen : " + linebreak;
            toStr = toStr + StringUtils.join(toList, ", ");
            return toStr + linebreak + linebreak;
        }
        
    }

}
