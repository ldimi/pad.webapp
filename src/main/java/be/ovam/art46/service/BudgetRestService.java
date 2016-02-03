package be.ovam.art46.service;

import be.ovam.art46.model.AanvraagVastlegging;
import be.ovam.art46.model.rest.SchuldvorderingBudget;
import be.ovam.pad.model.Schuldvordering;

/**
 * Created by Koen on 1/07/2014.
 */
public interface BudgetRestService {
    long verzend(AanvraagVastlegging aanvraagVastlegging);

    void verzendSchuldvordering(Integer vordering_id);
    
    void verzend(SchuldvorderingBudget schuldvorderingBudget);

    public long annuleer(Long schuldvorderingId);
}
