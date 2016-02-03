package be.ovam.art46.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ovam.art46.model.Adres_1;

@Service
public interface Adres_1Service {
	
	public void save(Adres_1 adres);
	
	List<Adres_1> compare(Adres_1 adres);
	
	List<Adres_1> find(Adres_1 adres);
	
	Adres_1 getAdresByID(Integer id);
	
//	public void update(Adres_1 adres);

}
