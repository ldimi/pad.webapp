package be.ovam.art46.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String user_id;
    private String ambtenaar_id;
    private String paswoord;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String string) {
        user_id = string;
    }

    public String getPaswoord() {
        return paswoord;
    }

    public void setPaswoord(String string) {
        paswoord = string;
    }

    public String getAmbtenaar_id() {
        return ambtenaar_id;
    }

    public void setAmbtenaar_id(String ambtenaar_id) {
        this.ambtenaar_id = ambtenaar_id;
    }
}
