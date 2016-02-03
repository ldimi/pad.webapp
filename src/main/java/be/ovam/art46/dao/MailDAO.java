package be.ovam.art46.dao;

import be.ovam.pad.model.OvamMail;
import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 10/09/2014.
 */
@Repository
public class MailDAO extends GenericDAO<OvamMail> {
    public MailDAO() {
        super(OvamMail.class);
    }
}
