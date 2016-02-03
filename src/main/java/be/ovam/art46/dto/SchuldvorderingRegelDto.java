package be.ovam.art46.dto;

import java.math.BigDecimal;

/**
 * Created by Koen on 12/01/2015.
 */
public class SchuldvorderingRegelDto {
    private String postnr;
    private String taak;
    private String details;
    private BigDecimal eenheidsprijs;
    private BigDecimal regelTotaal = new BigDecimal(0);
    private String schuldvorderingNr;

    public String getPostnr() {
        return postnr;
    }

    public void setPostnr(String postnr) {
        this.postnr = postnr;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTaak() {
        return taak;
    }

    public void setTaak(String taak) {
        this.taak = taak;
    }

    public BigDecimal getEenheidsprijs() {
        return eenheidsprijs;
    }

    public void setEenheidsprijs(BigDecimal eenheidsprijs) {
        this.eenheidsprijs = eenheidsprijs;
    }

    public BigDecimal getRegelTotaal() {
        return regelTotaal;
    }

    public void setRegelTotaal(BigDecimal regelTotaal) {
        this.regelTotaal = regelTotaal;
    }

    public void setSchuldvorderingNr(String schuldvorderingNr) {
        this.schuldvorderingNr = schuldvorderingNr;
    }

    public String getSchuldvorderingNr() {
        return schuldvorderingNr;
    }
}
