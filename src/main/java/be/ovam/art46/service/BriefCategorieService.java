package be.ovam.art46.service;

import be.ovam.pad.model.BriefCategorie;

import java.util.List;

/**
 * Created by Koen on 11/04/2014.
 */
public interface BriefCategorieService {
    List<BriefCategorie> getFasenForSchuldvordering();

    BriefCategorie get(Integer l);

    BriefCategorie getBasicFase();
}
