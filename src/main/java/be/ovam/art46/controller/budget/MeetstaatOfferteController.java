package be.ovam.art46.controller.budget;

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
import static be.ovam.web.util.JsView.jsview;
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

    public static final String MODEL_ATTRIBUTE_NAME_OFFERTE_FORM = "offerteForm";
    public static final String MODEL_ATTRIBUTE_NAME_BTW_TARIEVEN = "btwTarieven";
    private static final String MODEL_ATTRIBUTE_NAME_OFFERTE_ID = "offerte_id";
    
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

   
    
    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offertes", method = RequestMethod.GET)
    public String start(@PathVariable Long bestek_id, Model model) throws Exception {
        setBasicModel(bestek_id, model, null);
        
        model.addAttribute("offertes", meetstaatOfferteService.getOrCreateOffertesForBestek(bestek_id));
        
        model.addAttribute("organisaties_dd", ovamcore_sqlSession.selectList("organisatie_financieelBeheer_lijst"));
        
        return jsview("bestek.meetstaat.offertes", "budget/meetstaat/offertes", model);
    }

    @ModelAttribute(value = MODEL_ATTRIBUTE_NAME_OFFERTE_FORM)
    public OfferteForm setofferteFormModel(@RequestParam(required = false) Long offerte_id) {
        if (offerte_id != null) {
            return meetstaatOfferteService.getOfferte(offerte_id);
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

    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offertes", method = RequestMethod.POST)
    public String create(@PathVariable Long bestek_id, @Valid @ModelAttribute(MODEL_ATTRIBUTE_NAME_OFFERTE_FORM) OfferteForm offerteForm, BindingResult result, Model model, @RequestParam String action) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute(MODEL_ATTRIBUTE_NAME_BINDING_RESULT, result);
            return setBasicModelOfferte(bestek_id, model, offerteForm);
        }
        if (action.equals("Toevoegen")) {
            offerteForm.getOfferteRegels().add(offerteForm.getNieuweOfferteRegel());
        }
        Long resultOfferteId = meetstaatOfferteService.save(offerteForm, bestek_id);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_OFFERTE_ID, offerteForm.getOfferte().getId());
        return "redirect:/s/bestek/" + bestek_id + "/meetstaat/offertes/" + resultOfferteId + "/";
    }

    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offertes/{offerte_id}/afsluiten/", method = RequestMethod.GET)
    public String afsluiten(@PathVariable String bestek_id, @PathVariable Long offerte_id) throws Exception {
        meetstaatOfferteService.afsluiten(offerte_id);
        return "redirect:/s/bestek/" + bestek_id + "/meetstaat/offertes/" + offerte_id + "/";
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
    

    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offertes/{offerte_id}/toekennen/", method = RequestMethod.GET)
    public String toekenen(@PathVariable String bestek_id, @PathVariable Long offerte_id) throws Exception {
        meetstaatOfferteService.toekenen(offerte_id);
        return "redirect:/s/bestek/" + bestek_id + "/meetstaat/offertes/" + offerte_id + "/";
    }

    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offertes/{offerte_id}/toekenningverwijderen/", method = RequestMethod.GET)
    public String toekenningverwijderen(@PathVariable String bestek_id, @PathVariable Long offerte_id) throws Exception {
        meetstaatOfferteService.toekenningverwijderen(offerte_id);
        return "redirect:/s/bestek/" + bestek_id + "/meetstaat/offertes/" + offerte_id + "/";
    }

    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offertes/{offerte_id}/", method = RequestMethod.GET)
    public String start(@PathVariable Long bestek_id, @PathVariable Long offerte_id, Model model) throws Exception {
        return setBasicModelOfferte(bestek_id, model, meetstaatOfferteService.getOfferte(offerte_id));
    }

    @RequestMapping(value = "/bestek/{bestek_id}/meetstaat/offertes/{offerte_id}/upload/", method = RequestMethod.POST)
    public
    @ResponseBody
    Response uploadMedia(@RequestParam("file") MultipartFile file, @PathVariable("bestek_id") Long bestek_id, @PathVariable Long offerte_id) throws Exception {
        List<String> errors = meetstaatOfferteService.uploudMeetstaatCSV(new InputStreamReader(file.getInputStream()), bestek_id, offerte_id);
        return new Response(errors, true, null);
    }


    @RequestMapping(value = "/bestek/meetstaat/offertes/{offerte_id}/export/draftOfferte-{inzender}.xls", method = RequestMethod.GET)
    public void exportExcel(@PathVariable Integer offerte_id, @PathVariable String inzender, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftOfferte-" + inzender + ".xls");
            op = response.getOutputStream();
            meetstaatExportExcelService.createOfferteExport(offerte_id, op);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (op != null) {
                op.flush();
                op.close();
            }
        }
    }

    @RequestMapping(value = "/bestek/meetstaat/offertes/{offerte_id}/export/draftOfferte-{inzender}.pdf", method = RequestMethod.GET)
    public void exportPdf(@PathVariable Integer offerte_id, @PathVariable String inzender, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try {
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftOfferte-" + inzender + ".pdf");
            op = response.getOutputStream();
            meetstaatExportPdfService.createOfferteExport(offerte_id, op);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (op != null) {
                op.flush();
                op.close();
            }
        }
    }

    @RequestMapping(value = "/bestek/meetstaat/offertes/export/draftOffertes-{bestek_id}.xls", method = RequestMethod.GET)
    public void exportOffertesPdf(@PathVariable Long bestek_id, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try {
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftOffertes-" + bestek_id + ".xls");
            op = response.getOutputStream();
            meetstaatExportExcelService.createOffertesExport(bestek_id, op);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if (op != null) {
                op.flush();
                op.close();
            }
        }
    }


    private String setBasicModelOfferte(Long bestek_id, Model model, OfferteForm offerteForm) throws Exception {
        setBasicModel(bestek_id, model, offerteForm);
        return "bestek.meetstaat.offerte";
    }

    private void setBasicModel(Long bestek_id, Model model, OfferteForm offerteForm) throws Exception {
        super.startBasic(bestek_id, model);
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
    public String addDocument(@RequestParam("bestek_id") Long bestek_id, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        bestekService.addControle(bestek_id, multipartFile);
        return "redirect:/s/bestek/" + bestek_id + "/meetstaat/offertes/";
    }

    @RequestMapping(value = "/financieel/toegangwebloket/organisatie/{organisatie_id}/logins", method = RequestMethod.GET)
    public @ResponseBody List getLoginsVoorOrganisatie (@PathVariable Integer organisatie_id) {
        return ovamcore_sqlSession.selectList("medewerkers_financieelBeheer_voor_organisatie", organisatie_id);
    }

}
