package be.ovam.art46.dao;

import be.ovam.pad.model.BriefCategorie;

import com.google.common.collect.ImmutableList;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Koen on 11/04/2014.
 */
@Repository
public class BriefCategorieDao extends GenericDAO<BriefCategorie>{
    private static final List FASE_CATEGORIEN = ImmutableList.of(12, 13, 14, 15);
    public static final Integer BASIC_FASE = 12;

    public BriefCategorieDao() {
        super(BriefCategorie.class);
    }

    public List<BriefCategorie> getFasenForSchuldvordering(){
        return sessionFactory.getCurrentSession().createCriteria(type, "t").add(Restrictions.in("t.id", FASE_CATEGORIEN)).list();
    }
}
