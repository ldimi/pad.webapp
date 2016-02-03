package be.ovam.art46.common.mail;

import be.ovam.art46.dao.MailDAO;
import be.ovam.pad.model.OvamMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Service
public class MailServiceImpl implements MailService {

    @Autowired
	private JavaMailSenderImpl mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;
    @Autowired
    private MailDAO mailDAO;

	
	public void sendMail(String to, String subject, String from, String message) throws Exception {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(to);
        msg.setFrom(from);
        msg.setSubject(subject);
		msg.setText(message);
        mailSender.send(msg);	
	}
    public void sendHTMLMail(String to, String subject, String from, String bericht) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(bericht, true);
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

    public void sendMail(OvamMail ovamMail) throws MessagingException {
        sendHTMLMail(ovamMail.getTo(), ovamMail.getSubject(),ovamMail.getFrom(), ovamMail.getMessage());
    }

}
