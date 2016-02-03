package be.ovam.art46.model;

import java.io.Serializable;

/**
 * @author Tonny Bruckers
 * @version 0.1 (May 22, 2004)
 */
public class UserRolComponentId implements Serializable {

    private String user_id;
    private String user_rol;

    /**
     * @return
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @return
     */
    public String getUser_rol() {
        return user_rol;
    }

    /**
     * @param string
     */
    public void setUser_id(String string) {
        user_id = string;
    }

    /**
     * @param string
     */
    public void setUser_rol(String string) {
        user_rol = string;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof UserRolComponentId) {
            UserRolComponentId urcId = (UserRolComponentId) obj;
            return this.getUser_id().equals(urcId.getUser_id()) && this.getUser_rol().equals(urcId.getUser_rol());
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return user_id.hashCode() + user_rol.hashCode();
    }

}
