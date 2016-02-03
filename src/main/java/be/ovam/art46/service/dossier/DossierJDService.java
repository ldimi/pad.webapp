package be.ovam.art46.service.dossier;

import be.ovam.art46.dao.DossierJDDAO;
import be.ovam.art46.model.DossierJD;
import be.ovam.art46.model.JuridischDossier;
import be.ovam.art46.service.BaseService;

import java.util.List;

public class DossierJDService extends BaseService {	
	
	public List<DossierJD> getDossiersJDById(Integer dossier_id) {
		return getDossierJDDAO().getDossiersJDById(dossier_id);
	}
	
	
	public List<JuridischDossier> getJuridischDossiersById(Integer dossier_id) {
		return getDossierJDDAO().getJuridischDossiersById(dossier_id);
	}


	private DossierJDDAO getDossierJDDAO() {
		return (DossierJDDAO) getDao();
	}
	
}
