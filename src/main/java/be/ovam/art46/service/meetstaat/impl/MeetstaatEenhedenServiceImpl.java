package be.ovam.art46.service.meetstaat.impl;

import be.ovam.art46.dao.MeetstaatEenheidDao;
import be.ovam.art46.dao.MeetstaatEenheidMappingDao;
import be.ovam.art46.model.MeetstaatEenheid;
import be.ovam.art46.model.MeetstaatEenheidMapping;
import be.ovam.art46.model.SelectElement;
import be.ovam.art46.service.meetstaat.MeetstaatEenheidService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by koencorstjens on 2-8-13.
 */
@Service
public class MeetstaatEenhedenServiceImpl implements MeetstaatEenheidService {
    @Autowired
    MeetstaatEenheidDao meetstaatEenheidDao;

    @Autowired
    MeetstaatEenheidMappingDao meetstaatEenheidMappingDao;

    public Map<String, String> getAllEenheden() {
        List<MeetstaatEenheid> meetstaatEenheden = getAll();
        HashMap<String, String> types = new HashMap<String, String>();
        for (MeetstaatEenheid meetstaatEenheid : meetstaatEenheden) {
            types.put(meetstaatEenheid.getNaam().toUpperCase(), meetstaatEenheid.getNaam());
        }
        List<MeetstaatEenheidMapping> eenheidMappings = getAllMappings();
        for (MeetstaatEenheidMapping meetstaatEenheidMapping : eenheidMappings) {
            types.put(meetstaatEenheidMapping.getNaam().toUpperCase(), types.get(meetstaatEenheidMapping.getEenheidCode().toUpperCase()));
        }

        return types;
    }

    public ArrayList<SelectElement> getAllUniqueEenheden() {
        List<MeetstaatEenheid> eenheidList = getAll();
        ArrayList<SelectElement> selectList = new ArrayList<SelectElement>();
        selectList.add(new SelectElement(StringUtils.EMPTY, StringUtils.EMPTY));
        for (MeetstaatEenheid meetstaatEenheid : eenheidList) {
            selectList.add(new SelectElement(meetstaatEenheid.getNaam(), meetstaatEenheid.getNaam()));
        }
        return selectList;
    }

    public List<MeetstaatEenheidMapping> getAllMappings() {
        return meetstaatEenheidMappingDao.getAll();
    }

    public List<MeetstaatEenheid> getAll() {
        return meetstaatEenheidDao.getAll();
    }

    public void add(MeetstaatEenheid meetstaatEenheid) {
        meetstaatEenheidDao.save(meetstaatEenheid);
    }

    public void add(MeetstaatEenheidMapping meetstaatEenheidMapping) {
        meetstaatEenheidMappingDao.save(meetstaatEenheidMapping);
    }

    public void delete(String code) {
        meetstaatEenheidDao.delete(code);
    }

    public void deleteMapping(String code) {
        meetstaatEenheidMappingDao.delete(code);
    }


    public void setMeetstaatEenheidDao(MeetstaatEenheidDao meetstaatEenheidDao) {
        this.meetstaatEenheidDao = meetstaatEenheidDao;
    }

    public void setMeetstaatEenheidMappingDao(MeetstaatEenheidMappingDao meetstaatEenheidMappingDao) {
        this.meetstaatEenheidMappingDao = meetstaatEenheidMappingDao;
    }
}

