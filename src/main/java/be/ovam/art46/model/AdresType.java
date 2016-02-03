package be.ovam.art46.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "ART46", name = "ADRES_TYPE")
public class AdresType implements Serializable {

    private static final long serialVersionUID = -8872003523861078595L;

    @Id
    @GenericGenerator(name = "adrestypeid", strategy = "increment", parameters = {@org.hibernate.annotations.Parameter(name = "schema", value = "ART46")})
    @GeneratedValue(generator = "adrestypeid")
    @Column(name = "ADRES_TYPE_ID")
    private Integer adrestype_id;

    @Column(name = "adres_type_b")
    private String adrestype_b;

    public String getAdrestype_b() {
        return adrestype_b;
    }

    public void setAdrestype_b(String adrestype_b) {
        this.adrestype_b = adrestype_b;
    }

    public Integer getAdrestype_id() {
        return adrestype_id;
    }

    public void setAdrestype_id(Integer adrestype_id) {
        this.adrestype_id = adrestype_id;
    }


}
