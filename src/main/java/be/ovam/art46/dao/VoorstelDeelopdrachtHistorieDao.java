package be.ovam.art46.dao;

import be.ovam.pad.model.VoorstelDeelopdrachtHistorie;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Koen on 6/05/2014.
 */

@Repository
@Transactional
public class VoorstelDeelopdrachtHistorieDao extends GenericDAO<VoorstelDeelopdrachtHistorie> {
    public VoorstelDeelopdrachtHistorieDao() {
        super(VoorstelDeelopdrachtHistorie.class);
    }
}
