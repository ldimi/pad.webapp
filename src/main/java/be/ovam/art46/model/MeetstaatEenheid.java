package be.ovam.art46.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by koencorstjens on 13-9-13.
 */
@Entity
@Table(schema = "ART46", name = "MEETSTAAT_EENHEID")
public class MeetstaatEenheid {
    @Id
    private String naam;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
