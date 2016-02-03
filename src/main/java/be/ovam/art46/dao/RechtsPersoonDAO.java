package be.ovam.art46.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import be.ovam.art46.model.Adres_1;
import be.ovam.art46.model.RechtsPersoon;

@Repository
public class RechtsPersoonDAO extends GenericDAO<RechtsPersoon>{
	
	public RechtsPersoonDAO() {
		super(RechtsPersoon.class);
	}
	
	public void save(RechtsPersoon rechtspersoon){
		super.save(rechtspersoon);
	}

	public List<RechtsPersoon> doesRechtsPersoonExist(RechtsPersoon rechtspersoon){
		
		//Criteria RPCrit= sessionFactory.getCurrentSession().createCriteria(RechtsPersoon.class);
		List<RechtsPersoon> cats = sessionFactory.getCurrentSession().createCriteria(RechtsPersoon.class).add(Example.create(rechtspersoon)).list();
		
		return cats;
		
	}
	
public List<RechtsPersoon> searchSimilar(RechtsPersoon rechtspersoon){
	Example criteria= Example.create(rechtspersoon);
	criteria.enableLike();
	criteria.ignoreCase();
	criteria.enableLike(MatchMode.ANYWHERE);
	
	List<RechtsPersoon> cats = sessionFactory.getCurrentSession().createCriteria(RechtsPersoon.class).add(criteria).list();
	
	return cats;
		
	}

}
