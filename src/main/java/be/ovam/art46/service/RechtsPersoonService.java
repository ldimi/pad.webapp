package be.ovam.art46.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ovam.art46.model.RechtsPersoon;

@Service
public interface RechtsPersoonService {
	
	public void save(RechtsPersoon rechtsPersoon);
	
	List<RechtsPersoon> getExistingRechtsPersonen(RechtsPersoon rechtspersoon);
	
	List<RechtsPersoon> find(RechtsPersoon rechtspersoon);
	
	RechtsPersoon getByID(Integer rechtspersoonid);

}
