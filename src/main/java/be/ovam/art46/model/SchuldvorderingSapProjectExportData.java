package be.ovam.art46.model;

/**
 * Created by Koen on 15/04/2014.
 */
public class SchuldvorderingSapProjectExportData {
    private String nummer;
    private String wbsNummer;
    private String projectnummer;
    private String bedrag;
    private String budgetairArtikel;
    private String boekJaar;
    private String initieelAchtNummer;

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getWbsNummer() {
        return wbsNummer;
    }

    public void setWbsNummer(String wbsNummer) {
        this.wbsNummer = wbsNummer;
    }

    public String getProjectnummer() {
        return projectnummer;
    }

    public void setProjectnummer(String projectnummer) {
        this.projectnummer = projectnummer;
    }

    public void setBedrag(String bedrag) {
        this.bedrag = bedrag;
    }

    public String getBedrag() {
        return bedrag;
    }

    public void setBudgetairArtikel(String budgetairArtikel) {
        this.budgetairArtikel = budgetairArtikel;
    }

    public String getBudgetairArtikel() {
        return budgetairArtikel;
    }

    public void setBoekJaar(String boekJaar) {
        this.boekJaar = boekJaar;
    }

    public String getBoekJaar() {
        return boekJaar;
    }

    public void setInitieelAchtNummer(String initieelAchtNummer) {
        this.initieelAchtNummer = initieelAchtNummer;
    }

    public String getInitieelAchtNummer() {
        return initieelAchtNummer;
    }

    public String getVastleggingsnummer(){
        return getBoekJaar()+getInitieelAchtNummer();
    }
}
