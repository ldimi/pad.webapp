package be.ovam.art46.model;

/**
 * Created by koencorstjens on 8-8-13.
 */
public class SelectElement {
    private String lable;
    private String code;

    public SelectElement() {

    }

    public SelectElement(String code, String lable) {
        this.code = code;
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectElement that = (SelectElement) o;
        return code.equals(that.code);
    }

}
