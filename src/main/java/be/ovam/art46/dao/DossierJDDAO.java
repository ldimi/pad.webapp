package be.ovam.art46.dao;

import be.ovam.art46.model.DossierJD;
import be.ovam.art46.model.JuridischDossier;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class DossierJDDAO extends BaseDAO {

	public void saveObject(Object object) throws Exception {
		DossierJD dossierDb = null;
		DossierJD dossier = (DossierJD) object;
		try {
			dossierDb = (DossierJD) getHibernateTemplate().load(DossierJD.class, dossier.getDossier_id_jd());
		}
		catch (Exception e) {
			// dossier komt niet voor in db
		}
		if (dossierDb != null) {
			if (!dossierDb.getDossier_id().equals(dossier.getDossier_id())) {
				throw new Exception("Een dossier met dezelfde id komt reeds voor in de databank bij een ander bodem dossier");
			} 
			else {
				// copy props
				try {
					PropertyUtils.copyProperties(dossierDb, dossier);
				} catch (Exception e) {
					throw new RuntimeException("Probleem bij copieren van properties juridisch dossier", e);
				}
				getHibernateTemplate().saveOrUpdate(dossierDb);
			}
		} 
		else {
			getHibernateTemplate().save(dossier);
		}
	}
	
	public List<DossierJD> getDossiersJDById(Integer dossier_id) {
		return getHibernateTemplate().findByNamedQuery("dossierJD.by.dossierId", dossier_id);
	}
	
	public List<JuridischDossier> getJuridischDossiersById(Integer dossier_id) {
	
		
		Session session = getCurrentSession();
		
		Criteria criteria = session.createCriteria(JuridischDossier.class);
		criteria.add(Restrictions.eq("art46DossierId", dossier_id));
		
		List<JuridischDossier> list = criteria.list(); 
		
		return list; 
		
		
		
	}
	
	
	
			
}
