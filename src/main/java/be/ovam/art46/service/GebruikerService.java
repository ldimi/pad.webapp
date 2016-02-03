package be.ovam.art46.service;

import be.ovam.pad.model.Ambtenaar;

/**
 * Created by Koen on 23/06/2014.
 */
public interface GebruikerService {
    public Ambtenaar getHuidigAmbtenaar();
    public Ambtenaar getAmbtenaar(String user_id);
}
