package be.ovam.art46.service;


import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.VoorstelDeelopdracht;

import java.util.List;

/**
 * Created by Koen on 2/05/2014.
 */
public interface VoorstelDeelopdrachtService {
    VoorstelDeelopdracht getEmptyVoorstelDeelopdrachtId(Long id);

    VoorstelDeelopdracht get(Long id);

    Long save(VoorstelDeelopdracht voorstelDeelopdracht, String gebruiker);

    void save(VoorstelDeelopdracht voorstelDeelopdracht);

    void verwijderRegel(Long regelId);

    List<VoorstelDeelopdracht> getAll(Long offerteId);

    List<DeelOpdracht> getAllForBestek(Long bestekId);

    String getWebloketLink(VoorstelDeelopdracht voorstelDeelopdracht);

}
