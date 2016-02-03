package be.ovam.art46.service.meetstaat;

import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.MeetstaatTemplate;

import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by koencorstjens on 13-8-13.
 */
public interface MeetstaatTemplateService {
    List<MeetstaatTemplate> getAll();

    List<MeetstaatRegel> laadTemplate(Long templateId, Long bestekId) throws InvocationTargetException, IllegalAccessException;

    List<MeetstaatRegel> getAllregels(Long templateId);

    List<MeetstaatRegel> save(ArrayList<MeetstaatRegel> meetstaatRegels, String naam);

    MeetstaatTemplate get(Long templateId);

    void delete(Long templateId);

    List<MeetstaatRegel> getFromOtherBestek(String bestekNr, Long bestekId);

}
