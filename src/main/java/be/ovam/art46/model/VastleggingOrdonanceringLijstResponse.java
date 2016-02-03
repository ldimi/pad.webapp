package be.ovam.art46.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class VastleggingOrdonanceringLijstResponse {


    private List<VastleggingOrdonanceringLijstDO> VastleggingOrdonanceringLijst = new ArrayList<VastleggingOrdonanceringLijstDO>();


    private BigDecimal vekBegrotingTotaal;

    private BigDecimal vekVoorzienTotaal;
    private BigDecimal gefactureerdTotaal;


    public List<VastleggingOrdonanceringLijstDO> getVastleggingOrdonanceringLijst() {
        return VastleggingOrdonanceringLijst;
    }

    public void setVastleggingOrdonanceringLijst(
            List<VastleggingOrdonanceringLijstDO> vastleggingOrdonanceringLijst) {
        VastleggingOrdonanceringLijst = vastleggingOrdonanceringLijst;
    }

    public BigDecimal getVekBegrotingTotaal() {
        return vekBegrotingTotaal;
    }

    public void setVekBegrotingTotaal(BigDecimal vekBegrotingTotaal) {
        this.vekBegrotingTotaal = vekBegrotingTotaal;
    }

    public BigDecimal getVekVoorzienTotaal() {
        return vekVoorzienTotaal;
    }

    public void setVekVoorzienTotaal(BigDecimal vekVoorzienTotaal) {
        this.vekVoorzienTotaal = vekVoorzienTotaal;
    }

    public BigDecimal getGefactureerdTotaal() {
        return gefactureerdTotaal;
    }

    public void setGefactureerdTotaal(BigDecimal gefactureerdTotaal) {
        this.gefactureerdTotaal = gefactureerdTotaal;
    }

}
