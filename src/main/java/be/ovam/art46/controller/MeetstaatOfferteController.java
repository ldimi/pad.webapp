package be.ovam.art46.controller;

import be.ovam.art46.model.OfferteForm;
import be.ovam.art46.model.SelectElement;
import be.ovam.art46.service.meetstaat.MeetstaatEenheidService;
import be.ovam.art46.service.meetstaat.MeetstaatExportExcelService;
import be.ovam.art46.service.meetstaat.MeetstaatExportPdfService;
import be.ovam.art46.util.DropDownHelper;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.OfferteRegel;
import be.ovam.util.mybatis.SqlSession;
import be.ovam.web.Response;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Koen Corstjens on 29-8-13.
 */
@Controller
public class MeetstaatOfferteController extends BasicMeetstaatController {

    public static final String BESTEK_MEETSTAAT_OFFERTE = "bestek.meetstaat.offerte";
    public static final String BESTEK_MEETSTAAT_OFFERTES = "bestek.meetstaat.offertes";
    public static final String MODEL_ATTRIBUTE_NAME_OFFERTE_FORM = "offerteForm";
    public static final String MODEL_ATTRIBUTE_NAME_BTW_TARIEVEN = "btwTarieven";
    private static final String MODEL_ATTRIBUTE_NAME_OFFERTE_ID = "offerteId";
    
    @Autowired
    MeetstaatExportExcelService meetstaatExportExcelService;
    private Logger log = Logger.getLogger(MeetstaatOfferteController.class);
    
    @Autowired
    private MeetstaatExportPdfService meetstaatExportPdfService;
    
    @Autowired
    private MeetstaatEenheidService meetstaatEenheidService;

    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    private SqlSession ovamcore_sqlSession;

   
    
    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/offertes", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        setBasicModel(bestekId, model, null);
        
        model.addAttribute("offertes", meetstaatOfferteService.getOrCreateForBestek(bestekId));
        
        model.addAttribute("organisatiesVoorOffertes_dd", ovamcore_sqlSession.selectList("organisatiesVoorOffertes_dd"));
        
        return BESTEK_MEETSTAAT_OFFERTES;
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_OFFERTE_FORM)
    public OfferteForm setofferteFormModel(@RequestParam(required = false) Long offerteId) {
        if (offerteId != null) {
            return meetstaatOfferteService.getOfferte(offerteId);
        }
        return new OfferteForm();
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_EENHEDEN)
    public ArrayList<SelectElement> setEenhedenModel() {
        return meetstaatEenheidService.getAllUniqueEenheden();
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_TYPES)
    public List<String> setTypesModel() {
        return MeetstaatRegel.TYPES;
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_BTW_TARIEVEN)
    public List<Integer> setBtwTarievenModel() {
        return DropDownHelper.getBtwTarieven();

    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/offertes", method = RequestMethod.POST)
    public String create(@PathVariable Long bestekId, @Valid @ModelAttribute(MODEL_ATTRIBUTE_NAME_OFFERTE_FORM) OfferteForm offerteForm, BindingResult result, Model model, @RequestParam String action) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute(MODEL_ATTRIBUTE_NAME_BINDING_RESULT, result);
            return setBasicModelOfferte(bestekId, model, offerteForm);
        }
        if (action.equals("Toevoegen")) {
            offerteForm.getOfferteRegels().add(offerteForm.getNieuweOfferteRegel());
        }
        Long resultOfferteId = meetstaatOfferteService.save(offerteForm, bestekId);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_OFFERTE_ID, offerteForm.getOfferte().getId());
        return "redirect:/s/bestek/" + bestekId + "/meetstaat/offertes/" + resultOfferteId + "/";
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/offertes/{offerteId}/afsluiten/", method = RequestMethod.GET)
    public String afsluiten(@PathVariable String bestekId, @PathVariable Long offerteId) throws Exception {
        meetstaatOfferteService.afsluiten(offerteId);
        return "redirect:/s/bestek/" + bestekId + "/meetstaat/offertes/" + offerteId + "/";
    }
    
    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offerte/{offerte_id}/koppelOrganisatie", method = RequestMethod.GET)
    public String koppelOrganisatie(@PathVariable String bestek_id,
            @PathVariable Long offerte_id,
            @RequestParam(required=false) String organisatie_id) throws Exception {

        Map map = new HashMap();
        map.put("id", offerte_id);
        map.put("organisatie_id", organisatie_id);
        
        sqlSession.updateInTable("art46", "offerte", map);

        return "redirect:/s/bestek/" + bestek_id + "/meetstaat/offertes";
    }
    

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/offertes/{offerteId}/toekennen/", method = RequestMethod.GET)
    public String toekenen(@PathVariable String bestekId, @PathVariable Long offerteId) throws Exception {
        meetstaatOfferteService.toekenen(offerteId);
        return "redirect:/s/bestek/" + bestekId + "/meetstaat/offertes/" + offerteId + "/";
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/offertes/{offerteId}/toekenningverwijderen/", method = RequestMethod.GET)
    public String toekenningverwijderen(@PathVariable String bestekId, @PathVariable Long offerteId) throws Exception {
        meetstaatOfferteService.toekenningverwijderen(offerteId);
        return "redirect:/s/bestek/" + bestekId + "/meetstaat/offertes/" + offerteId + "/";
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/offertes/{offerteId}/", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, @PathVariable Long offerteId, Model model) throws Exception {
        return setBasicModelOfferte(bestekId, model, meetstaatOfferteService.getOfferte(offerteId));
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/offertes/{offerteId}/upload/", method = RequestMethod.POST)
    public
    @ResponseBody
    Response uploadMedia(@RequestParam("file") MultipartFile file, @PathVariable("bestekId") Long bestekId, @PathVariable Long offerteId) throws Exception {
        List<String> errors = meetstaatOfferteService.uploudMeetstaatCSV(new InputStreamReader(file.getInputStream()), bestekId, offerteId);
        return new Response(errors, true, null);
    }


    @RequestMapping(value = "/bestek/meetstaat/offertes/{offerteId}/export/draftOfferte-{inzender}.xls", method = RequestMethod.GET)
    public void exportExcel(@PathVariable Integer offerteId, @PathVariable String inzender, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftOfferte-" + inzender + ".xls");
            op = response.getOutputStream();
            meetstaatExportExcelService.createOfferteExport(offerteId, op);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (op != null) {
                op.flush();
                op.close();
            }
        }
    }

    @RequestMapping(value = "/bestek/meetstaat/offertes/{offerteId}/export/draftOfferte-{inzender}.pdf", method = RequestMethod.GET)
    public void exportPdf(@PathVariable Integer offerteId, @PathVariable String inzender, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try {
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftOfferte-" + inzender + ".pdf");
            op = response.getOutputStream();
            meetstaatExportPdfService.createOfferteExport(offerteId, op);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (op != null) {
                op.flush();
                op.close();
            }
        }
    }

    @RequestMapping(value = "/bestek/meetstaat/offertes/export/draftOffertes-{bestekId}.xls", method = RequestMethod.GET)
    public void exportOffertesPdf(@PathVariable Long bestekId, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try {
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftOffertes-" + bestekId + ".xls");
            op = response.getOutputStream();
            meetstaatExportExcelService.createOffertesExport(bestekId, op);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (op != null) {
                op.flush();
                op.close();
            }
        }
    }


    private String setBasicModelOfferte(Long bestekId, Model model, OfferteForm offerteForm) throws Exception {
        setBasicModel(bestekId, model, offerteForm);
        return BESTEK_MEETSTAAT_OFFERTE;
    }

    private void setBasicModel(Long bestekId, Model model, OfferteForm offerteForm) throws Exception {
        super.startBasic(bestekId, model);
        if (offerteForm != null && offerteForm.getOfferte() != null) {
            offerteForm.setOpmerkingen(new ArrayList<String>());
            if (CollectionUtils.isNotEmpty(offerteForm.getOfferteRegels())) {
                for (OfferteRegel offerteRegel : offerteForm.getOfferteRegels()) {
                    if (StringUtils.isNotEmpty(offerteRegel.getOpmerking()) && StringUtils.isNotEmpty(StringUtils.trim(offerteRegel.getOpmerking()))) {
                        offerteForm.getOpmerkingen().add(offerteRegel.getMeetstaatRegel().getPostnr() + ": " + offerteRegel.getOpmerking());
                    }
                }
            }
            model.addAttribute(MODEL_ATTRIBUTE_NAME_OFFERTE_ID, offerteForm.getOfferte().getId());
        }
        model.addAttribute(MODEL_ATTRIBUTE_NAME_OFFERTE_FORM, offerteForm);
    }

    @RequestMapping(value = "/bestek/meetstaat/offertes/uploaden", method = RequestMethod.POST)
    public String addDocument(@RequestParam("bestekId") Long bestekId, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        bestekService.addControle(bestekId, multipartFile);
        return "redirect:/s/bestek/" + bestekId + "/meetstaat/offertes/";
    }

}
