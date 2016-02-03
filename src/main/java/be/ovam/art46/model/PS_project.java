package be.ovam.art46.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PS_project implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;
    private String description;
    private String bodemNumber;
    private String ivsNumber;
    private String jdNumber;
    private String ovamNumber;

    private String responsibleBodem;
    private String responsibleIvs;
    private String responsibleJd;
    private String responsibleOvam;
    private String projectType;

    private List<WbsNode> children = new ArrayList<WbsNode>();

    public void koppelChildnode(WbsNode node) {
        getChildren().add(node);
    }

    public String getLabel() {
        return "Project " + this.id + " - " + this.description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getBodemNumber() {
        return bodemNumber;
    }

    public void setBodemNumber(String bodemNumber) {
        this.bodemNumber = bodemNumber;
    }


    public String getIvsNumber() {
        return ivsNumber;
    }

    public void setIvsNumber(String ivsNumber) {
        this.ivsNumber = ivsNumber;
    }


    public String getJdNumber() {
        return jdNumber;
    }

    public void setJdNumber(String jdNumber) {
        this.jdNumber = jdNumber;
    }


    public String getResponsibleBodem() {
        return responsibleBodem;
    }

    public void setResponsibleBodem(String responsibleBodem) {
        this.responsibleBodem = responsibleBodem;
    }


    public String getResponsibleIvs() {
        return responsibleIvs;
    }

    public void setResponsibleIvs(String responsibleIvs) {
        this.responsibleIvs = responsibleIvs;
    }


    public String getResponsibleJd() {
        return responsibleJd;
    }

    public void setResponsibleJd(String responsibleJd) {
        this.responsibleJd = responsibleJd;
    }


    public List<WbsNode> getChildren() {
        return children;
    }

    public void setChildren(List<WbsNode> children) {
        this.children = children;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getOvamNumber() {
        return ovamNumber;
    }

    public void setOvamNumber(String ovamNumber) {
        this.ovamNumber = ovamNumber;
    }

    public String getResponsibleOvam() {
        return responsibleOvam;
    }

    public void setResponsibleOvam(String responsibleOvam) {
        this.responsibleOvam = responsibleOvam;
    }

}
