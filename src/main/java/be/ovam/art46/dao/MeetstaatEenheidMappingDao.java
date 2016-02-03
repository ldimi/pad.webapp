package be.ovam.art46.dao;

import be.ovam.art46.model.MeetstaatEenheidMapping;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by koencorstjens on 13-9-13.
 */
@Repository
public class MeetstaatEenheidMappingDao extends GenericDAO<MeetstaatEenheidMapping> {

    public MeetstaatEenheidMappingDao() {
        super(MeetstaatEenheidMapping.class);
    }

    public List<MeetstaatEenheidMapping> getAll() {
        return getAllAsc("naam");
    }
}
