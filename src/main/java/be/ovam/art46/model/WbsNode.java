package be.ovam.art46.model;

import be.ovam.sap.common.WbsType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class WbsNode {

    private String id;
    private String description;
    private WbsType wbsType;
    private String coupledWbsId;
    private String externalReference;
    private String wbsUp;

    private WbsNode parentNode;

    private List<WbsNode> children = new ArrayList<WbsNode>();


    public void koppelChildnode(WbsNode andereNode) {
        getChildren().add(andereNode);
        andereNode.setParentNode(this);
    }

    public String getLabel() {
        return this.description + " " + this.id;
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


    public WbsType getWbsType() {
        return wbsType;
    }

    public void setWbsType(WbsType wbsType) {
        this.wbsType = wbsType;
    }


    public String getCoupledWbsId() {
        return coupledWbsId;
    }

    public void setCoupledWbsId(String coupledWbsId) {
        this.coupledWbsId = coupledWbsId;
    }


    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getWbsUp() {
        return wbsUp;
    }

    public void setWbsUp(String wbsUp) {
        this.wbsUp = wbsUp;
    }

    @XmlTransient
    @JsonIgnore
    public WbsNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(WbsNode parentNode) {
        this.parentNode = parentNode;
    }


    public List<WbsNode> getChildren() {
        return children;
    }

    public void setChildren(List<WbsNode> children) {
        this.children = children;
    }

}
