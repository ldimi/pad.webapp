package be.ovam.art46.model;

import java.math.BigDecimal;
import java.util.Date;

public class SchuldvorderingFactuurDO {

    public Integer vordering_id;
    public String project_id;
    public String initieel_acht_nr;
    public String factuur_id;
    public Integer volgnummer;
    public BigDecimal bedrag;
    public BigDecimal saldo;
    public Date factuur_d;
    public Date betaal_d;

}