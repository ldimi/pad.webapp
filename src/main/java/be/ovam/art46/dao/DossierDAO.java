package be.ovam.art46.dao;

import be.ovam.pad.model.Dossier;
import org.apache.commons.beanutils.DynaBean;

import java.io.Serializable;
import java.util.List;

public class DossierDAO extends BaseDAO {	
		
	public Object getObject(Class clazz, Serializable id) {		
		if (clazz.equals(Dossier.class)) {
			try {
				return getDossierByNr(id.toString());
			} catch (Exception e) {			
				e.printStackTrace();
				return null;
			}
		}
		return super.getObject(clazz,id);
	}
	
	public Object getObject(Class clazz, Integer id) {		
		return super.getObject(clazz,id);
	}

	public void saveObject(Object object) throws Exception {
		if (object instanceof Dossier) {
			Dossier dossier = (Dossier) object;
			if (dossier.getDossier_id() == null || dossier.getDossier_id().length() == 0 || dossier.getDossier_id().startsWith("_")) {
				if ("X".equals(dossier.getDossier_type())) {			
				    throw new RuntimeException("Aanmaken van nieuw dossier moet via DossierService gebeuren.");
				} else {
					if (dossier.getDossier_b() != null && dossier.getDossier_b().length() > 0) {
						throw new RuntimeException("Aanmaken van nieuw dossier moet via DossierService gebeuren.");
					}
				}
			}
		} else {
            throw new IllegalArgumentException("Argument is geen Dossier.");
        }
		super.saveObject(object);
	}
	
	public DynaBean getActieInfo(Integer actietype, Integer dossierId) throws Exception {
		return findFirstByDynaBeans("select oa.*, ac.* from ART46.OVAM_AMBTENAAR_VIEW oa, ART46.V_DOSSIER ad, ART46.DOSSIER_ACTIE ac where ad.doss_hdr_id = oa.ambtenaar_id and ac.actie_type_id = ? and ad.id = ?", new Integer[] {actietype, dossierId});		
	}
	
	public List<Dossier> getIvsDossiers(String doss_hdr_id) {
		return getHibernateTemplate().findByNamedQuery("dossierIVS.by.doss_hdr_id", doss_hdr_id);
	}
	
	public boolean alleBestekkenAfgesloten(Integer dossier_id) {
		return getHibernateTemplate().findByNamedQuery("dossier.has.openbestekken", dossier_id).isEmpty();
	}
	
	private Dossier getDossierByNr(String dossier_id) throws Exception {		
		return (Dossier) findFirstByNamedQuery("dossierIVS.by.dossieridIVS", dossier_id);
	}
	
	public Dossier getDossierById(Integer id) throws Exception {
		List<Dossier> dossiers = getHibernateTemplate().findByNamedQuery("dossierIVS.by.dossierid", id);
		if (dossiers.size() == 1) {
			return ((Dossier) dossiers.get(0));
		}
		return null;
	}
	
    public List<Dossier> getIvsDossiersTypeNietAnders(String doss_hdr_id) {
        return getHibernateTemplate().findByNamedQuery("dossierIVS.by.doss_hdr_id_niet_ander", doss_hdr_id);
    }
}
