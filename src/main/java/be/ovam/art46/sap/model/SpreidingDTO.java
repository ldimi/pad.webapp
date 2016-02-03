package be.ovam.art46.sap.model;

import java.math.BigDecimal;

public class SpreidingDTO {

	
	public SpreidingDTO() {
		super();
	}
	
	public SpreidingDTO(Spreiding spreiding) {
		super();
		this.jaar = spreiding.getJaar(); 
		this.bedrag = spreiding.getBedrag();
		this.id = spreiding.getId();
		
	}
	
	
	 
	public SpreidingDTO(Integer jaar, BigDecimal bedrag) {
		super();
		this.jaar = jaar;
		this.bedrag = bedrag;
	}



	private Long id; 
	
    private Integer jaar; 
	
	private BigDecimal bedrag = new BigDecimal(0);
	
	
	private BigDecimal vorigBedrag= new BigDecimal(0);
	
	

	private BigDecimal gefactureerd= new BigDecimal(0);



	public Integer getJaar() {
		return jaar;
	}



	public void setJaar(Integer jaar) {
		this.jaar = jaar;
	}



	public BigDecimal getBedrag() {
		return bedrag;
	}



	public void setBedrag(BigDecimal bedrag) {
		this.bedrag = bedrag;
	}



	public BigDecimal getVorigBedrag() {
		return vorigBedrag;
	}



	public void setVorigBedrag(BigDecimal vorigBedrag) {
		this.vorigBedrag = vorigBedrag;
	}



	public BigDecimal getGefactureerd() {
		return gefactureerd;
	}



	public void setGefactureerd(BigDecimal gefactureerd) {
		this.gefactureerd = gefactureerd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
