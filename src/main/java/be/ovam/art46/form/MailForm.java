package be.ovam.art46.form;

/**
 * Created by Koen on 3/07/2014.
 */
public class MailForm {
    private String onderwerp;
    private String bericht;
    private String aan;
    private String van;

    public String getOnderwerp() {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    public String getAan() {
        return aan;
    }

    public void setAan(String aan) {
        this.aan = aan;
    }

    public void setVan(String van) {
        this.van = van;
    }

    public String getVan() {
        return van;
    }
}
