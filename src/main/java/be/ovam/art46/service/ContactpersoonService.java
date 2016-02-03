package be.ovam.art46.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ovam.art46.model.Contactpersoon;


public interface ContactpersoonService {
	
	
	
	public void save(Contactpersoon contactpersoon);
	public void delete(Integer contactpersoonid);
	List<Contactpersoon> compare(Contactpersoon contactpersoon);
	List<Contactpersoon> find(Contactpersoon contactpersoon);
	

}
