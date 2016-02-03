package be.ovam.art46.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ovam.art46.dao.RechtsPersoonDAO;
import be.ovam.art46.model.RechtsPersoon;
import be.ovam.art46.service.RechtsPersoonService;

@Service
public class RechtsPersoonServiceImpl implements RechtsPersoonService {
	
	@Autowired
	RechtsPersoonDAO rechtsPersoonDAO;

	public void save(RechtsPersoon rechtsPersoon) {
		rechtsPersoonDAO.save(rechtsPersoon);		

	}

	public List<RechtsPersoon> getExistingRechtsPersonen(RechtsPersoon rechtspersoon) {
	
		return rechtsPersoonDAO.doesRechtsPersoonExist(rechtspersoon);
	}

	public List<RechtsPersoon> find(RechtsPersoon rechtspersoon) {
		
		return rechtsPersoonDAO.searchSimilar(rechtspersoon);
	}

	public RechtsPersoon getByID(Integer rechtspersoonid) {
		// TODO Auto-generated method stub
		return rechtsPersoonDAO.get(rechtspersoonid);
	}

}
