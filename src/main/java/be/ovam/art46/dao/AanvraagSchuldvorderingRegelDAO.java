package be.ovam.art46.dao;

import be.ovam.pad.model.AanvraagSchuldvorderingRegel;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Koen on 30/01/14.
 */
@Repository
public class AanvraagSchuldvorderingRegelDAO extends GenericDAO<AanvraagSchuldvorderingRegel> {

    public AanvraagSchuldvorderingRegelDAO(){
        super(AanvraagSchuldvorderingRegel.class);
    }

}
