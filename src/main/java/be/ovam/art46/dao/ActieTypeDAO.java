package be.ovam.art46.dao;

import be.ovam.art46.model.ActieType;
import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 7/02/14.
 */

@Repository
public class ActieTypeDAO extends GenericDAO<ActieType> {

    public ActieTypeDAO() {
        super(ActieType.class);
    }

}
