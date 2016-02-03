package be.ovam.art46.controller;

import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.Schuldvordering;
import be.ovam.pad.model.VoorstelDeelopdracht;
import be.ovam.util.IdUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Koen on 11/06/2014.
 */
@Controller
public class ZoekVoorIdController {
    @Autowired
    private SchuldvorderingService schuldvorderingService;
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;

    @RequestMapping(value = "/zoekVoorId", method = RequestMethod.GET)
    public String getById(@RequestParam("id") String stringId) {

                if(StringUtils.startsWith(stringId, Schuldvordering.PREFIX_NUMMER)) {
                    Integer id = IdUtils.getIdAsInteger(stringId);
                    if (id != null) {
                        Schuldvordering schuldvordering = schuldvorderingService.get(id);
                        if (schuldvordering != null) {
                            if (schuldvordering.getAanvraagSchuldvordering() == null) {
                                return "redirect:/s/bestek/" + schuldvordering.getBestek_id() + "/schuldvorderingen/" + schuldvordering.getVordering_id();
                            } else {
                                return "redirect:/s/bestek/" + schuldvordering.getBestek_id() + "/aanvraagSchuldvordering/" + schuldvordering.getAanvraagSchuldvordering().getId();
                            }
                        }
                    }
                }else if(StringUtils.startsWith(stringId, VoorstelDeelopdracht.PREFIX_NUMMER)) {
                    Long id = IdUtils.getIdAsLong(stringId);
                    if(id!=null) {
                        VoorstelDeelopdracht voorstelDeelopdracht = voorstelDeelopdrachtService.get(id);
                        if(voorstelDeelopdracht!=null) {
                            return "redirect:/s/bestek/" + voorstelDeelopdracht.getOfferte().getBestekId() + "/voorstel/" + voorstelDeelopdracht.getId();
                        }
                    }
                }
        return "redirect:/briefzoek.do";
    }

}
