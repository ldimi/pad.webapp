package be.ovam.art46.service.impl;

import be.ovam.art46.dao.BriefCategorieDao;
import be.ovam.art46.dao.BriefDAO;
import be.ovam.art46.service.BriefCategorieService;
import be.ovam.pad.model.BriefCategorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Koen on 11/04/2014.
 */
@Service
public class BriefCategorieServiceImpl implements BriefCategorieService {
    @Autowired
    BriefCategorieDao briefCategorieDao;

    public List<BriefCategorie> getFasenForSchuldvordering() {
        return briefCategorieDao.getFasenForSchuldvordering();
    }

    public BriefCategorie get(Integer id) {
        return briefCategorieDao.get(id);
    }

    public BriefCategorie getBasicFase() {
        return briefCategorieDao.get(BriefCategorieDao.BASIC_FASE);
    }
}
