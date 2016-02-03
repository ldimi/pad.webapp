package be.ovam.art46.dto;

/**
 * Created by Koen on 16/03/2015.
 */
public class DossierGebruikerDto {
    private String email;
    private String dossierRol;
    private Long id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDossierRol() {
        return dossierRol;
    }

    public void setDossierRol(String dossierRol) {
        this.dossierRol = dossierRol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
