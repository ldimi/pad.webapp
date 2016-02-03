package be.ovam.art46.dto;

import be.ovam.pad.util.MeetstaatUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koen on 14/01/2015.
 */
public class ReportViewRegelDto{
    private String postnr;
    private String taak;
    private BigDecimal regelTotaalOfferte = new BigDecimal(0);
    private BigDecimal totaalViewRegel = new BigDecimal(0);
    private List<SchuldvorderingRegelDto> schuldvorderingRegelDtoList = new ArrayList<SchuldvorderingRegelDto>();
    private BigDecimal voorstelDeelopdrachtTotaal;

    public String getPostnr() {
        return postnr;
    }

    public void setPostnr(String postnr) {
        this.postnr = postnr;
    }

    public String getTaak() {
        return taak;
    }

    public void setTaak(String taak) {
        this.taak = taak;
    }

    public BigDecimal getRegelTotaalOfferte() {
        return regelTotaalOfferte;
    }

    public void setRegelTotaalOfferte(BigDecimal regelTotaal) {
        this.regelTotaalOfferte = regelTotaal;
    }

    public BigDecimal getTotaalViewRegel() {
        return totaalViewRegel;
    }

    public void setTotaalViewRegel(BigDecimal totaalViewRegel) {
        this.totaalViewRegel = totaalViewRegel;
    }

    public List<SchuldvorderingRegelDto> getSchuldvorderingRegelDtoList() {
        return schuldvorderingRegelDtoList;
    }

    public void setSchuldvorderingRegelDtoList(List<SchuldvorderingRegelDto> schuldvorderingRegelDtoList) {
        this.schuldvorderingRegelDtoList = schuldvorderingRegelDtoList;
    }

    public void setVoorstelDeelopdrachtTotaal(BigDecimal voorstelDeelopdrachtTotaal) {
        this.voorstelDeelopdrachtTotaal = voorstelDeelopdrachtTotaal;
    }

    public BigDecimal getVoorstelDeelopdrachtTotaal() {
        return voorstelDeelopdrachtTotaal;
    }
}
