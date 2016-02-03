package be.ovam.art46.dao;

import be.ovam.art46.model.BestekActie;
import org.springframework.stereotype.Repository;

/**
 * Created by Koen on 7/02/14.
 */
@Repository
public class BestekActieDAO extends GenericDAO<BestekActie> {
    public BestekActieDAO() {
        super(BestekActie.class);
    }
}
