package be.ovam.art46.service.meetstaat;

import be.ovam.art46.model.MeetstaatEenheid;
import be.ovam.art46.model.MeetstaatEenheidMapping;
import be.ovam.art46.model.SelectElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by koencorstjens on 2-8-13.
 */
public interface MeetstaatEenheidService {

    Map<String, String> getAllEenheden();

    ArrayList<SelectElement> getAllUniqueEenheden();

    List<MeetstaatEenheidMapping> getAllMappings();

    List<MeetstaatEenheid> getAll();

    void add(MeetstaatEenheid meetstaatEenheid);

    void add(MeetstaatEenheidMapping meetstaatEenheidMapping);

    void delete(String code);

    void deleteMapping(String code);
}
