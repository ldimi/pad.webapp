package be.ovam.art46.model;

/**
 * @author Tonny Bruckers
 * @version 0.1 (May 2, 2004)
 */
public class Lijst {

    private Integer lijst_id;
    private String lijst_b;
    private Integer jaar;
    private String afgesloten_s;

    /**
     * @return
     */
    public String getAfgesloten_s() {
        return afgesloten_s;
    }

    /**
     * @return
     */
    public String getLijst_b() {
        return lijst_b;
    }

    /**
     * @return
     */
    public Integer getLijst_id() {
        return lijst_id;
    }

    /**
     * @param string
     */
    public void setAfgesloten_s(String string) {
        afgesloten_s = string;
    }

    /**
     * @param string
     */
    public void setLijst_b(String string) {
        lijst_b = string;
    }

    /**
     * @param Integer
     */
    public void setLijst_id(Integer id) {
        lijst_id = id;
    }

    public Integer getJaar() {
        return jaar;
    }

    public void setJaar(Integer jaar) {
        this.jaar = jaar;
    }

}
