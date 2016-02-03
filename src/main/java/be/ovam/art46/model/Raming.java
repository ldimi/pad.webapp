package be.ovam.art46.model;

public abstract class Raming {

    private Integer raming_id;
    private Integer fase_id;
    private Integer prioriteit_id;
    private Integer jaartal;
    private Integer maand;
    private Double raming;
    private Double budget_vastgelegd;
    private String commentaar;
    private String afgesloten_s;

    public String getAfgesloten_s() {
        return afgesloten_s;
    }

    public void setAfgesloten_s(String afgesloten_s) {
        this.afgesloten_s = afgesloten_s;
    }

    public Double getBudget_vastgelegd() {
        return budget_vastgelegd;
    }

    public void setBudget_vastgelegd(Double budget_vastgelegd) {
        this.budget_vastgelegd = budget_vastgelegd;
    }

    public String getCommentaar() {
        return commentaar;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public Integer getFase_id() {
        return fase_id;
    }

    public void setFase_id(Integer fase_id) {
        this.fase_id = fase_id;
    }

    public Integer getJaartal() {
        return jaartal;
    }

    public void setJaartal(Integer jaartal) {
        this.jaartal = jaartal;
    }

    public Integer getMaand() {
        return maand;
    }

    public void setMaand(Integer maand) {
        this.maand = maand;
    }

    public Integer getPrioriteit_id() {
        return prioriteit_id;
    }

    public void setPrioriteit_id(Integer prioriteit_id) {
        this.prioriteit_id = prioriteit_id;
    }

    public Double getRaming() {
        return raming;
    }

    public void setRaming(Double raming) {
        this.raming = raming;
    }

    public Integer getRaming_id() {
        return raming_id;
    }

    public void setRaming_id(Integer raming_id) {
        this.raming_id = raming_id;
    }

    abstract public Object getDossier_id();

    abstract public void setDossier_id(Object dossier_id);


}
