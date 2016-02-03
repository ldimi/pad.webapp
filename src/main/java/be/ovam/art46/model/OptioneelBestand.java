package be.ovam.art46.model;

/**
 * Created by koencorstjens on 17-7-13.
 */
public class OptioneelBestand {

    private Integer brief_id;
    private Integer aanvraagid;
    private String dms_id;

    public String getDms_id() {
        return dms_id;
    }

    public void setDms_id(String dms_id) {
        this.dms_id = dms_id;
    }

    public Integer getBrief_id() {
        return brief_id;
    }

    public void setBrief_id(Integer brief_id) {
        this.brief_id = brief_id;
    }

    public Integer getAanvraagid() {
        return aanvraagid;
    }

    public void setAanvraagid(Integer aanvraagid) {
        this.aanvraagid = aanvraagid;
    }
}
