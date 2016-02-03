package be.ovam.art46.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ovam.art46.dao.Adres_1DAO;
import be.ovam.art46.model.Adres_1;
import be.ovam.art46.service.Adres_1Service;

@Service
public class Adres_1ServiceImpl implements Adres_1Service {
	
	@Autowired
	Adres_1DAO adres_1DAO;
	
	public void save(Adres_1 adres){
		adres_1DAO.save(adres);
	}

	public List<Adres_1> compare(Adres_1 adres) {
	
		return adres_1DAO.doesAdresExist(adres);
	}

	public List<Adres_1> find(Adres_1 adres) {
		return adres_1DAO.searchSimilar(adres);
	}

	public Adres_1 getAdresByID(Integer id) {
		
		return adres_1DAO.get(id);
	}

//	public void update(Adres_1 adres) {
//		System.out.println(this.toString() +"#########################################"+ "hier moet ik zoeken hoe ik een adres ga updaten...");
//		
//	}
	
	
	
}
