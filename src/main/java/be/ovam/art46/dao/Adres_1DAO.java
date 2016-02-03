package be.ovam.art46.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import be.ovam.art46.model.Adres_1;
import be.ovam.art46.model.RechtsPersoon;


@Repository
public class Adres_1DAO extends GenericDAO<Adres_1>{

	public Adres_1DAO() {
		super(Adres_1.class);		
	}
	
	public void save(Adres_1 adres){
		super.save(adres);
	}
	
	public List<Adres_1> doesAdresExist(Adres_1 adres){
		
		
		List<Adres_1> cats = sessionFactory.getCurrentSession().createCriteria(Adres_1.class).add(Example.create(adres)).list();
		return cats;
		
	}

	public List<Adres_1> searchSimilar(Adres_1 adres) {
		Example criteria= Example.create(adres);
		criteria.enableLike();	
		criteria.ignoreCase();
		criteria.enableLike(MatchMode.ANYWHERE);
		
		List<Adres_1> cats = sessionFactory.getCurrentSession().createCriteria(Adres_1.class).add(criteria).list();
		
		return cats;
	}

	
	
}