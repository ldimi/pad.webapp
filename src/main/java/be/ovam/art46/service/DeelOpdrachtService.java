package be.ovam.art46.service;

import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.DeelOpdrachtDO;

import java.util.List;

/**
 * Created by Koen on 6/05/2014.
 */
public interface DeelOpdrachtService {
    
    DeelOpdrachtDO getDeelopdrachtBy(int id);
    
    List<DeelOpdracht> getMogelijkeDeelopdrachtenVoorVoorstel(Long voorstelId);

    DeelOpdracht get(Integer ld);

    Integer uploadBrief(Integer deelopdracht_id, String name, byte[] content)  throws Exception ;

    void save(DeelOpdracht deelOpdracht);
}
