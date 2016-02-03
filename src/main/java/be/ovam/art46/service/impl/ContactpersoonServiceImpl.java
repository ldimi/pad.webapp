package be.ovam.art46.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ovam.art46.dao.ContactpersoonDAO;
import be.ovam.art46.model.Contactpersoon;
import be.ovam.art46.service.ContactpersoonService;

@Service
public class ContactpersoonServiceImpl implements ContactpersoonService{

	
	@Autowired
	ContactpersoonDAO contactpersoonDAO;
	
	
	public void save(Contactpersoon contactpersoon) {
		contactpersoonDAO.save(contactpersoon);
		
	}

	public List<Contactpersoon> compare(Contactpersoon contactpersoon) {
		return contactpersoonDAO.doesContactpersoonExist(contactpersoon);
	}

	public List<Contactpersoon> find(Contactpersoon contactpersoon) {
		return contactpersoonDAO.searchSimilar(contactpersoon);
	}

	public void delete(Integer contactpersoonid) {
		 contactpersoonDAO.delete(contactpersoonid);
		
	}

}
