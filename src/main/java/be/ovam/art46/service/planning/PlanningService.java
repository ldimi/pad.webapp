package be.ovam.art46.service.planning;

import be.ovam.art46.model.planning.ParamsDO;
import be.ovam.art46.model.planning.PlanningDataDO;
import be.ovam.art46.model.planning.PlanningLijnDO;

import java.util.HashMap;
import java.util.List;

public interface PlanningService {
	
	public PlanningDataDO getPlanning(ParamsDO params);
	
	@SuppressWarnings("rawtypes")
	public List getOverzichtRaamcontract(Integer dossier_id);
	
	public PlanningLijnDO bewaar(PlanningLijnDO planningLijn);
	
	@SuppressWarnings("rawtypes")
	public Integer markeer(HashMap markering);

}
