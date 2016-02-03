package be.ovam.art46.model;

public class Adres_1_Rechtspersoon extends Adres_1 {
	public RechtsPersoon rechtspersoon;

	public RechtsPersoon getRechtspersoon() {
		return rechtspersoon;
	}

	public void setRechtspersoon(RechtsPersoon rechtspersoon) {
		this.rechtspersoon = rechtspersoon;
	}
	public String getNaam(){
		return this.rechtspersoon.getNaam();
	}
	public String getNaam2(){
		return this.rechtspersoon.getNaam2();
	}
	

}
