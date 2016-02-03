package be.ovam.art46.dao;

import java.util.List;

import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import be.ovam.art46.model.Adres_1;
import be.ovam.art46.model.Contactpersoon;
import be.ovam.art46.model.RechtsPersoon;

@Repository
public class ContactpersoonDAO extends GenericDAO<Contactpersoon> {

	public ContactpersoonDAO() {
		super(Contactpersoon.class);
	}

	public void save(Contactpersoon contactpersoon) {
		super.save(contactpersoon);
	}

	public List<Contactpersoon> doesContactpersoonExist(
			Contactpersoon contactpersoon) {

		List<Contactpersoon> cats = sessionFactory.getCurrentSession()
				.createCriteria(Contactpersoon.class)
				.add(Example.create(contactpersoon))
				.list();
		return cats;

	}

	public List<Contactpersoon> searchSimilar(Contactpersoon contactpersoon) {
		Example criteria = Example.create(contactpersoon);
		criteria.enableLike();
		criteria.ignoreCase();
		criteria.enableLike(MatchMode.ANYWHERE);
		List<Contactpersoon> cats = sessionFactory.getCurrentSession()
				.createCriteria(Contactpersoon.class).add(criteria).list();
		return cats;
	}

}
