package be.ovam.art46.service.schuldvordering;

import be.ovam.pad.model.AsvDO;

/**
 * Created by Koen on 27/01/14.
 */
public interface AsvService {

    AsvDO getAsv(Long aanvr_schuldvordering_id);

    Long save(String actie, AsvDO asv, String gebruiker) throws Exception;

}
