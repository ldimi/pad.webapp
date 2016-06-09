package be.ovam.art46.service.meetstaat;

import be.ovam.art46.model.OfferteForm;
import be.ovam.art46.model.OffertesExport;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.Offerte;
import be.ovam.pad.model.OfferteRegel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Koen Corstjens on 29-8-13.
 */
public interface MeetstaatOfferteService extends MeetstaatBaseService {

    List<OfferteRegel> getEmptyOfferte(Long bestekId);

    Long save(OfferteForm offerteForm, Long BestekId) throws Exception;

    //List<Offerte> getOrCreateForBestek(Long bestekId) throws Exception;
    
    List getOrCreateOffertesForBestek(Long bestekId);

    OfferteForm getOfferte(Long offerteId);

    List<Brief> getOfferteBrieven(Long bestekId);

    List<String> uploudMeetstaatCSV(InputStreamReader inputStreamReader, Long bestekId, Long offerteId) throws IOException;

    void toekenen(Long offerteId) throws Exception;

    List<Offerte> getToegekendeOffertes(Long bestekId);


    void toekenningverwijderen(Long offerteId) throws Exception;

    OffertesExport getOffertesRegelsForBestek(Long bestekId) throws Exception;

    void afsluiten(Long offerteId);

    Offerte get(Long offerteId);
}
