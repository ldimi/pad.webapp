package be.ovam.art46.dao;

import be.ovam.art46.model.MeetstaatEenheid;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by koencorstjens on 13-9-13.
 */
@Repository
public class MeetstaatEenheidDao extends GenericDAO<MeetstaatEenheid> {

    public MeetstaatEenheidDao() {
        super(MeetstaatEenheid.class);
    }

    public List<MeetstaatEenheid> getAll() {
        return getAllAsc("naam");
    }

    public void save(MeetstaatEenheid meetstaatEenheid) {
        if(get(meetstaatEenheid.getNaam())== null){
            super.save(meetstaatEenheid);
        }
    }

}
