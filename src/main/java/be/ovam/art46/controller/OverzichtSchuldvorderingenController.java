package be.ovam.art46.controller;

import be.ovam.art46.controller.form.OverzichtSchuldvorderingenForm;
import be.ovam.art46.dao.OfferteDao;
import be.ovam.art46.dto.ReportViewRegelDto;
import be.ovam.art46.dto.SelectOptionIntegerDto;
import be.ovam.art46.service.DeelOpdrachtService;
import be.ovam.art46.service.schuldvordering.OverzichtSchuldvorderingenService;
import be.ovam.pad.model.DeelOpdracht;
import be.ovam.pad.model.Offerte;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Koen on 12/01/2015.
 */
@Controller
public class OverzichtSchuldvorderingenController extends BasicMeetstaatController {
    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private OverzichtSchuldvorderingenService overzichtSchuldvorderingenService;
    @Autowired
    private OfferteDao offerteDao;
    @Autowired
    private DeelOpdrachtService deelOpdrachtService;

    @RequestMapping(value = "/bestek/exportoverzichtschuldvorderingen{offerteId}.xls", method= RequestMethod.GET)
    public void export(@PathVariable Long offerteId, HttpServletResponse response) throws Exception {
        ServletOutputStream op = response.getOutputStream();
        try{
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "exportoverzichtschuldvorderingen"+offerteId+".xls");
            overzichtSchuldvorderingenService.createExportOFferte(op, offerteId, 0);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if(op!=null){
                op.flush();
                op.close();
            }
        }
    }

    @RequestMapping(value = "/bestek/exportoverzichtschuldvorderingendeelopdracht{deelopdrachtId}.xls", method= RequestMethod.GET)
    public void export(@PathVariable Integer deelopdrachtId ,  HttpServletResponse response) throws Exception {
        ServletOutputStream op = null;
        try{
            DeelOpdracht deelOpdracht = deelOpdrachtService.get(deelopdrachtId);
            String title = "Schuldvorderingen deelopdracht " + deelOpdracht.getNaam();
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename= exportoverzichtschuldvorderingendeelopdracht"+deelopdrachtId+".xls");
            op = response.getOutputStream();
            overzichtSchuldvorderingenService.createExportDeelOpdracht(op, title, deelopdrachtId);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if(op!=null){
                op.flush();
                op.close();
            }
        }

    }



    @RequestMapping(value = "/bestek/{bestekId}/overzichtschuldvorderingen", method= RequestMethod.POST)
    public String filter(@PathVariable Long bestekId, @ModelAttribute OverzichtSchuldvorderingenForm overzichtSchuldvorderingenForm, Model model) throws Exception {
        return toon(bestekId, overzichtSchuldvorderingenForm.getOfferteId(), overzichtSchuldvorderingenForm.getDeelOpdrachtId(), model);
    }

    @RequestMapping(value = "/bestek/{bestekId}/overzichtschuldvorderingen")
    public String toon(@PathVariable Long bestekId, @RequestParam(required = false) Long offerteId, @RequestParam(required = false) Integer deelOpdrachtId, Model model) throws Exception {
        List<Offerte> offertes = offerteDao.getToegekendForBestekId(bestekId);
        Offerte geselecteerdeOfferte = null;
        DeelOpdracht geselecteerdeDeelOpdracht = null;
        if (CollectionUtils.isNotEmpty(offertes)) {
            if(deelOpdrachtId==null){
                deelOpdrachtId = 0;
            }
            if (offerteId == null) {
                geselecteerdeOfferte = offertes.get(0);
                offerteId = geselecteerdeOfferte.getId();
            } else {
                for (Offerte offerte : offertes) {
                    if (offerte.getId().equals(offerteId)) {
                        geselecteerdeOfferte = offerte;
                        break;
                    }
                }
            }
            LinkedHashMap<String, ReportViewRegelDto> reportViewDto;
            if (deelOpdrachtId != 0) {
                reportViewDto = overzichtSchuldvorderingenService.getReportViewDtoForDeelopdracht(deelOpdrachtId);
            } else {
                reportViewDto = overzichtSchuldvorderingenService.getReportViewDtoForOfferte(offerteId);
            }
            model.addAttribute("offertes", offertes);
            OverzichtSchuldvorderingenForm overzichtSchuldvorderingenForm = new OverzichtSchuldvorderingenForm();
            overzichtSchuldvorderingenForm.setDeelOpdrachtId(deelOpdrachtId);
            overzichtSchuldvorderingenForm.setOfferteId(offerteId);
            model.addAttribute("form", overzichtSchuldvorderingenForm);
            List<SelectOptionIntegerDto> deelOpdrachten = new ArrayList<SelectOptionIntegerDto>();
            vul(deelOpdrachten, geselecteerdeOfferte.getDeelOpdrachten());
            model.addAttribute("deelOpdrachten", deelOpdrachten);

            List<ReportViewRegelDto> reportViewRegelDtos = new ArrayList<ReportViewRegelDto>(reportViewDto.values());
            model.addAttribute("view", reportViewRegelDtos);
        }
        return "bestek.meetstaat.offerte.overzicht.schuldvorderingen";
    }

    private void vul(List<SelectOptionIntegerDto> deelOpdrachtenDtos, List<DeelOpdracht> deelOpdrachten) {
        if(CollectionUtils.isNotEmpty(deelOpdrachten)) {
            for (DeelOpdracht deelOpdracht : deelOpdrachten) {
                SelectOptionIntegerDto selectOptiopnDto = new SelectOptionIntegerDto();
                selectOptiopnDto.setId(deelOpdracht.getDeelopdracht_id());
                selectOptiopnDto.setName(deelOpdracht.getNaam());
                deelOpdrachtenDtos.add(selectOptiopnDto);
            }
        }

    }

}
