package be.ovam.art46.dao;

import be.ovam.art46.model.ActieSubType;
import be.ovam.art46.model.ActieType;
import be.ovam.art46.model.DossierAfspraak;
import be.ovam.util.mybatis.SqlSession;

import java.util.List;
import java.util.Map;

public class ActieDAO extends BaseDAO {
	
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@SuppressWarnings("unchecked")
	public List<DossierAfspraak> getDossierAfspraken(Integer dossier_id) {
		return getHibernateTemplate().findByNamedQuery("afspraken.by.dossier_id", dossier_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getBestekActies(Long bestek_id) {
		return sqlSession.selectList("be.ovam.art46.mappers.DossierMapper.getBestekActies", bestek_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActieType> getActieTypes(String dossier_type) {		
		return getHibernateTemplate().findByNamedQuery("types.by.dossier_type", dossier_type);
	}			
	
	@SuppressWarnings("unchecked")
	public List<ActieSubType> getActieSubTypes(Integer actie_type_id) {
		return getHibernateTemplate().findByNamedQuery("subtypes.by.actietype_id", actie_type_id);
	}
	
	
	public boolean hoofdActieExists(Integer dossier_id, int actie_type_id) throws Exception {	
		return !getDynaBeans("select * from ART46.DOSSIER_ACTIE where dossier_id = ? and actie_type_id = ? and (actie_sub_type_id is null or actie_sub_type_id =0)", new Integer[] {dossier_id, Integer.valueOf(actie_type_id)}).isEmpty();
	}
	
}
