package be.ovam.art46.dao;

import be.ovam.art46.model.ActieSubType;
import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 7/02/14.
 */
@Repository
public class ActieSubTypeDAO extends GenericDAO<ActieSubType> {

    public ActieSubTypeDAO() {
        super(ActieSubType.class);
    }

}
