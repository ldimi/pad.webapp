package be.ovam.art46.controller.adres;

public class AdresDO {

    private Integer adres_id;
    private String naam;
    private String voornaam;
    private String straat;
    private String postcode;
    private String gemeente;
    private String land;
    private String tel;
    private String fax;
    private String email;
    private String website;
    private String stop_s;
    private String maatsch_zetel;
    private Integer type_id;
    private String gsm;
    private String referentie_postcodes;
    
    private String status_crud;
    
    // private Integer derde_id;
    // private String huis_nr;
    // private String straat_zonder_nr;

    public Integer getAdres_id() {
        return adres_id;
    }

    public void setAdres_id(Integer adres_id) {
        this.adres_id = adres_id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getGemeente() {
        return gemeente;
    }

    public void setGemeente(String gemeente) {
        this.gemeente = gemeente;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStop_s() {
        return stop_s;
    }

    public void setStop_s(String stop_s) {
        this.stop_s = stop_s;
    }

    public String getMaatsch_zetel() {
        return maatsch_zetel;
    }

    public void setMaatsch_zetel(String maatsch_zetel) {
        this.maatsch_zetel = maatsch_zetel;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getReferentie_postcodes() {
        return referentie_postcodes;
    }

    public void setReferentie_postcodes(String referentie_postcodes) {
        this.referentie_postcodes = referentie_postcodes;
    }

    public String getStatus_crud() {
        return status_crud;
    }

    public void setStatus_crud(String status_crud) {
        this.status_crud = status_crud;
    }

   
}
