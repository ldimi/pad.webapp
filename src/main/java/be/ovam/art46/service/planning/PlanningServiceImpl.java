package be.ovam.art46.service.planning;

import be.ovam.art46.model.planning.ParamsDO;
import be.ovam.art46.model.planning.PlanningDataDO;
import be.ovam.art46.model.planning.PlanningDossierVersieDO;
import be.ovam.art46.model.planning.PlanningLijnDO;
import be.ovam.art46.util.Application;
import be.ovam.util.mybatis.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Component(value = "planningService")
@Scope("prototype")
public class PlanningServiceImpl implements PlanningService {

	@Autowired
	private SqlSession sqlSession;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PlanningDataDO getPlanning(ParamsDO planningParams) {
		PlanningDataDO planning;
		List result = null;
		
		result = sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getPlanningDetails", planningParams);
		
		planning = new PlanningDataDO();
		planning.setLijnen(result);

		if ("X".equals(planningParams.getDossier_type())) {
			// dropdown met maar 1 dossier 
			planning.setDossierDD(sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDossiersDDbyDossierId", planningParams.getDossier_id()));
		} else {
			planning.setDossierDD(sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getDossiersDDbyUid", planningParams.getDoss_hdr_id()));
		}
        
        // TODO : beter eenmalig als model attribuut doorgeven.
		planning.setFaseDD(sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getFaseDD", null));
		planning.setFaseDetailDD(sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getFaseDetailDD", null));
        
		planning.setContractenDD(sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getContractenDD", planningParams.getDoss_hdr_id()));

		return planning;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PlanningLijnDO bewaar(PlanningLijnDO planningLijnDO) {
		String user_id = Application.INSTANCE.getUser_id();
		
        if ("U".equals(planningLijnDO.getStatus_crud()) || "C".equals(planningLijnDO.getStatus_crud())) {

            PlanningDossierVersieDO planningDossierVersie = new PlanningDossierVersieDO();
            planningDossierVersie.setDossier_id(planningLijnDO.getDossier_id());
            planningDossierVersie.setWijzig_user(user_id);
            sqlSession.insert("be.ovam.art46.mappers.PlanningMapper.insertPlanningDossierVersie", planningDossierVersie);

            planningLijnDO.setWijzig_user(user_id);
            planningLijnDO.setPlanning_dossier_versie(planningDossierVersie.getPlanning_versie());

            if ("C".equals(planningLijnDO.getStatus_crud())) {
                sqlSession.insert("be.ovam.art46.mappers.PlanningMapper.insertPlanningLijn", planningLijnDO);
            }

            sqlSession.insert("be.ovam.art46.mappers.PlanningMapper.insertPlanningLijnVersie", planningLijnDO);
            planningLijnDO.setStatus_crud("R");
        }
		
		return planningLijnDO;
	}


	@SuppressWarnings("rawtypes")
	public List getOverzichtRaamcontract(Integer dossier_id) {
		return sqlSession.selectList("be.ovam.art46.mappers.PlanningMapper.getOverzichtRaamcontract", dossier_id);
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public Integer markeer(HashMap markering) {
		sqlSession.insert("be.ovam.art46.mappers.PlanningMapper.insertPlanningMarkering", markering);
		sqlSession.insert("be.ovam.art46.mappers.PlanningMapper.insertPlanningDossierVersieMarkering", markering);
		return (Integer) markering.get("markering_id");		
	}


}
