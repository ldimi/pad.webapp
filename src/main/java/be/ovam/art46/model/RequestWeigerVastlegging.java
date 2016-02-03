package be.ovam.art46.model;

/**
 * Created by koencorstjens on 23-7-13.
 */
public class RequestWeigerVastlegging {
    private Long id;
    private String comentaar;

    public RequestWeigerVastlegging() {
        super();
    }

    public RequestWeigerVastlegging(Long id, String comentaar) {
        this.id = id;
        this.comentaar = comentaar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentaar() {
        return comentaar;
    }

    public void setComentaar(String comentaar) {
        this.comentaar = comentaar;
    }
}
