package be.ovam.art46.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by koencorstjens on 13-9-13.
 */
@Entity
@Table(schema = "ART46", name = "MEETSTAAT_EENHEID_MAPPING")
public class MeetstaatEenheidMapping {

    @Id
    String naam;

    @Column(name = "eenheid_naam")
    String eenheidCode;

    public String getEenheidCode() {
        return eenheidCode;
    }

    public void setEenheidCode(String eenheidCode) {
        this.eenheidCode = eenheidCode;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
}
