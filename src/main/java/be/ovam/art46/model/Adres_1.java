package be.ovam.art46.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "ADRES_1",schema="ART46" )
public class Adres_1 {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@NotNull(message="straatnaam moet ingevuld worden")
	private String straatnaam;
	
	@NotNull(message="huisnummer moet ingevuld worden")
	@Digits(fraction = 0, integer = 5)
	private String huisnummer;
	
	
	private String busnummer="";
	
	@NotNull(message="postcode moet ingevuld worden")
	private String postcode;
	
	@NotNull(message="gemeente moet ingevuld worden")
	private String gemeente;
	
	@NotNull(message="land moet ingevuld worden")
	private String land;
	
	private String teverwijderen="";
	
	

	public String getTeverwijderen() {
		return teverwijderen;
	}
	public void setTeverwijderen(String teverwijderen) {
		this.teverwijderen = teverwijderen;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStraatnaam() {
		return straatnaam;
	}
	public void setStraatnaam(String straatnaam) {
		straatnaam=StringUtils.trim(straatnaam);
		straatnaam=StringUtils.lowerCase(straatnaam);
		this.straatnaam = straatnaam;
	}
	public String getHuisnummer() {
		return huisnummer;
	}
	public void setHuisnummer(String huisnummer) {
		huisnummer=StringUtils.trim(huisnummer);
		huisnummer=StringUtils.lowerCase(huisnummer);
		this.huisnummer = huisnummer;
	}
	public String getBusnummer() {
		return busnummer;
	}
	
	public void setBusnummer(String busnummer) {
		if(busnummer!=""){
			busnummer=StringUtils.trim(busnummer);
			busnummer=StringUtils.lowerCase(busnummer);
//			if(busnummer==""){
//				this.busnummer="";
//			}
			this.busnummer=busnummer;
		}else{
			this.busnummer = busnummer;
		}
	}

	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		postcode=StringUtils.trim(postcode);
		this.postcode = postcode;
	}
	public String getGemeente() {
//		return StringUtils.lowerCase(gemeente);
		return gemeente;
	}
	public void setGemeente(String gemeente) {
		gemeente=StringUtils.trim(gemeente);
		gemeente=StringUtils.upperCase(gemeente);
		this.gemeente = gemeente;
	}
	public String getLand() {
		return land;
	}
	public void setLand(String land) {
		this.land = land;
	}
	
	@Override
	public String toString() {
		String objectString=this.getId()+", "+this.getLand()+", "+ this.getGemeente()+", "+ this.getPostcode()+", "+ this.getStraatnaam()+", "+ this.getHuisnummer()+", "+ this.getBusnummer();
		return objectString;
	}

}
