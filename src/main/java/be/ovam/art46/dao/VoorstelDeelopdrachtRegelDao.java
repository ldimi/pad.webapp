package be.ovam.art46.dao;


import be.ovam.pad.model.VoorstelDeelopdrachtRegel;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Koen on 2/05/2014.
 */
@Repository
@Transactional
public class VoorstelDeelopdrachtRegelDao extends GenericDAO<VoorstelDeelopdrachtRegel>  {
    public VoorstelDeelopdrachtRegelDao() {
        super(VoorstelDeelopdrachtRegel.class);
    }
}
