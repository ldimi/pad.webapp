package be.ovam.art46.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity
@Table(name="RECHTSPERSOON", schema="ART46")
public class RechtsPersoon {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@NotNull(message="minstens de achternaam / bedrijfsnaam moet ingevuld worden")
	private String naam;
	
	
	private String naam2 ;
	
	private String website;	
	private String tel ;	
	
	private String emailadres ;
	
	//@Digits(fraction = 0, integer = 10,message="Een belgisch vkboNummer bestaat uit 10 cijfers, zonder de landsindicatie")
	
	//@NumberFormat(style=Style.NUMBER ,pattern ="##########")zet de output in bepaald format
	@Size(min=10,max=10,message="Een belgisch vkboNummer bestaat uit 10 cijfers, zonder de landsindicatie")
	private String vkbonr;	
	
	private String type="1";
	private String MZ_adres_id;
	private String old_id;
	//private String old_MZ;
//	private String old_MZ_adres_id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getNaam2() {
		return naam2;
	}
	public void setNaam2(String naam2) {
		this.naam2 = naam2;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getVkbonr() {
		return vkbonr;
	}
	public void setVkbonr(String vkbonr) {
		this.vkbonr = vkbonr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMZ_adres_id() {
		return MZ_adres_id;
	}
	public void setMZ_adres_id(String mZ_adres_id) {
		MZ_adres_id = mZ_adres_id;
	}
	public String getOld_id() {
		return old_id;
	}
	public void setOld_id(String old_id) {
		this.old_id = old_id;
	}
//	public String getOld_MZ() {
//		return old_MZ;
//	}
//	public void setOld_MZ(String old_MZ) {
//		this.old_MZ = old_MZ;
//	}
//	public String getOld_MZ_adres_id() {
//		return old_MZ_adres_id;
//	}
//	public void setOld_MZ_adres_id(String old_MZ_adres_id) {
//		this.old_MZ_adres_id = old_MZ_adres_id;
//	}
	public String getEmailadres() {
		return emailadres;
	}
	public void setEmailadres(String emailadres) {
		this.emailadres = emailadres;
	}
	
	@Override
	public String toString() {
		
		return "id= "+this.id+", naam= "+this.getNaam()+", naam2= "+ this.getNaam2();
	}
	
	
	
	

}
