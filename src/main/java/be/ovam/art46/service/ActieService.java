package be.ovam.art46.service;

import be.ovam.art46.dao.*;
import be.ovam.art46.model.*;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.ActieJDZoekForm;
import be.ovam.art46.struts.actionform.ActieSubTypeForm;
import be.ovam.art46.struts.actionform.ActieTypeForm;
import be.ovam.zimbra.Appointment;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ActieService extends BaseService {

    @Autowired
	private DossierDAO dossierDAO;

    @Autowired
    private ActieTypeDAO actieTypeDAO;
    @Autowired
    private ActieSubTypeDAO actieSubTypeDAO;
    @Autowired
    private BestekActieDAO bestekActieDAO;

    private ActieDAO actieDAO;

    @Autowired
    public ActieService(ActieDAO actieDAO){
        this.actieDAO = actieDAO;
        super.setDao(actieDAO);
    }

	private String zimbraUrl="http://mail.ovam.be";
	
	public void saveObject(Object object) throws Exception {
		if (object instanceof DossierActie) {
			DossierActie actie = (DossierActie) object;
			if ("1".equals(actie.getZimbraSave())) {
				DynaBean actieInfo = dossierDAO.getActieInfo(actie.getActie_type_id(), actie.getDossier_id());
				Appointment zimbraActie = new Appointment();
				zimbraActie.createConnection(zimbraUrl, (String) actieInfo.get("email"));
				Date startDate = new Date(actie.getActie_d().getTime() + 32400000);
				Date endDate = new Date(startDate.getTime() + 3600000);	
				Date startDateTot = new Date(actie.getStop_d().getTime() + 32400000);
				Date endDateTot = new Date(startDateTot.getTime() + 3600000);	
				if (actie.getActie_id() == 0) {
					actie.setZimbraIdVan(zimbraActie.createAppointment(startDate, endDate, (String) actieInfo.get("actie_type_b"), (String) actieInfo.get("dossier_b"), (String) actieInfo.get("email")));
					actie.setZimbraIdTot(zimbraActie.createAppointment(startDateTot, endDateTot, (String) actieInfo.get("actie_type_b"), (String) actieInfo.get("dossier_b"), (String) actieInfo.get("email")));
					
				} else {		
					if (actie.getZimbraIdVan() != null && actie.getZimbraIdVan().length() != 0) {
						actie.setZimbraIdVan(zimbraActie.modifiyAppointment(actie.getZimbraIdVan(), startDate, endDate, (String) actieInfo.get("actie_type_b"), (String) actieInfo.get("dossier_b"), (String) actieInfo.get("email")));
					}
					else {
						actie.setZimbraIdVan(zimbraActie.createAppointment(startDate, endDate, (String) actieInfo.get("actie_type_b"), (String) actieInfo.get("dossier_b"), (String) actieInfo.get("email")));
					}
					if (actie.getZimbraIdTot() != null && actie.getZimbraIdTot().length() != 0) {
						actie.setZimbraIdTot(zimbraActie.modifiyAppointment(actie.getZimbraIdTot(), startDateTot, endDateTot, (String) actieInfo.get("actie_type_b"), (String) actieInfo.get("dossier_b"), (String) actieInfo.get("email")));
					} else {
						actie.setZimbraIdTot(zimbraActie.createAppointment(startDateTot, endDateTot, (String) actieInfo.get("actie_type_b"), (String) actieInfo.get("dossier_b"), (String) actieInfo.get("email")));
					}
				}
			} else {
				actie.setZimbraIdVan(null);
				actie.setZimbraIdTot(null);
			}
		}
        actieDAO.saveObject(object);
	}
	
    public List<Map<String,Object>> getBestekActies(Long bestekId){
        return actieDAO.getBestekActies(bestekId);
    }
	
	public List<ActieType> getActieTypes(String dossier_type) {
		return actieDAO.getActieTypes(dossier_type);
	}

	public List<ActieSubType> getActieSubTypes(Integer actie_type_id) {
		return actieDAO.getActieSubTypes(actie_type_id);
	}
	
	public ActieSubType getActieSubType(Integer actie_sub_type_id) {
		return (ActieSubType) actieSubTypeDAO.get(actie_sub_type_id);
	}
	
	public void deleteActieSubType(ActieSubTypeForm form) throws IllegalAccessException, InvocationTargetException {
		ActieSubType subtype = new ActieSubType();
		BeanUtils.copyProperties(subtype, form);
        actieDAO.deleteObject(subtype);
	}
	
	public void saveActieSubType(ActieSubTypeForm form) throws Exception {
		ActieSubType subtype = new ActieSubType();
		BeanUtils.copyProperties(subtype, form);
        actieDAO.saveObject(subtype);
		BeanUtils.copyProperties(form, subtype);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActieType> getAllActieTypes() {
		return actieDAO.getAll(ActieType.class);
	}
	
	public void deleteActieType(ActieTypeForm form) throws IllegalAccessException, InvocationTargetException {
		ActieType type = new ActieType();
		BeanUtils.copyProperties(type, form);
        actieDAO.deleteObject(type);
	}
	
	public void saveActieType(ActieTypeForm form) throws Exception {
		ActieType type = new ActieType();
		BeanUtils.copyProperties(type, form);
        actieTypeDAO.save(type);
		BeanUtils.copyProperties(form,type);
	}
		
	public List<DossierAfspraak> getDossierAfspraken(Integer dossier_id) {
		return actieDAO.getDossierAfspraken(dossier_id);
	}
	public ActieType getActieType(Integer id) {
		return actieTypeDAO.get(id);
	}
	

    public List<ActieSubType> getActieAllSubTypes() {
        return actieSubTypeDAO.getAll();
    }

    public BestekActie get(Integer actieId) {
        return bestekActieDAO.get(actieId);
    }

    public void delete(BestekActie bestekActie) {
        bestekActieDAO.delete(bestekActie);
    }

}
