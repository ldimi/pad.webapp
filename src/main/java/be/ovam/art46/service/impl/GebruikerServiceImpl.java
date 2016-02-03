package be.ovam.art46.service.impl;

import be.ovam.art46.dao.AmbtenaarDao;
import be.ovam.art46.service.GebruikerService;
import be.ovam.art46.util.Application;
import be.ovam.pad.model.Ambtenaar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Koen on 23/06/2014.
 */
@Service
public class GebruikerServiceImpl implements GebruikerService {
    @Autowired
    private AmbtenaarDao ambtenaarDao;

    public Ambtenaar getHuidigAmbtenaar(){
        return getAmbtenaar(Application.INSTANCE.getUser_id());
    }
    public Ambtenaar getAmbtenaar(String user_id){
        return ambtenaarDao.get(user_id);

    }
}
