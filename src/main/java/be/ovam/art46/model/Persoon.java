package be.ovam.art46.model;

public class Persoon {

    private Integer persoon_id;
    private String persoon_b;
    private String firma;
    private String adres;
    private String postcode;
    private String stad;
    private String land;

    public Integer getPersoon_id() {
        return persoon_id;
    }

    public void setPersoon_id(Integer persoon_id) {
        this.persoon_id = persoon_id;
    }

    public String getPersoon_b() {
        return persoon_b;
    }

    public void setPersoon_b(String persoon_b) {
        this.persoon_b = persoon_b;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStad() {
        return stad;
    }

    public void setStad(String stad) {
        this.stad = stad;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

}
