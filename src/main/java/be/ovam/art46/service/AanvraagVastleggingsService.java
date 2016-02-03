package be.ovam.art46.service;

import be.ovam.art46.model.AanvraagVastlegging;
import be.ovam.art46.model.RequestWeigerVastlegging;

/**
 * Created by koencorstjens on 25-7-13.
 */
public interface AanvraagVastleggingsService {


    void verzend(AanvraagVastlegging aanvraagVastlegging) throws Exception;

    void weigerVastlegging(RequestWeigerVastlegging requestWeigerVastlegging);

    AanvraagVastlegging saveAanvraag(AanvraagVastlegging aanvraagVastlegging) throws Exception;
}
