package be.ovam.art46.controller;

import be.ovam.art46.model.NewMeetstaatRequest;
import be.ovam.pad.model.Bestek;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.web.Response;
import be.ovam.art46.service.meetstaat.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Koen Corstjens on 29-7-13.
 */
@Controller
public class MeetstaatController extends BasicMeetstaatController{

    public static final String MODEL_ATTRIBUTE_NAME_TEMPLATES = "templates";
    private final Logger log = Logger.getLogger(MeetstaatController.class);

    @Autowired
    private MeetstaatEenheidService meetstaatEenheidService;

    @Autowired
    private MeetstaatTemplateService meetstaatTemplateService;

    @Autowired
    private MeetstaatExportPdfService meetstaatExportPdfService;

    @Autowired
    MeetstaatExportExcelService meetstaatExportExcelService;
    @Autowired
    MeetstaatService meetstaatService;



    @RequestMapping(value = "/meetstaat/getMeetstaat", method = RequestMethod.GET)
    public
    @ResponseBody
    Response getAllMeetstaten(@RequestParam("bestekId") Long bestekId) throws IOException, InvocationTargetException, IllegalAccessException {
        return new Response(meetstaatService.getAll(bestekId), true, null);
    }

    @RequestMapping(value = "/meetstaat/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    Response uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("bestekId") Long bestekId) throws Exception {
        Bestek bestek = bestekService.getBestek(bestekId);
        return new Response(meetstaatService.readMeetstaatCSV(new InputStreamReader(file.getInputStream()), bestek), true, null);
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Response add(@PathVariable Long bestekId, @RequestBody NewMeetstaatRequest newMeetstaatRequest) {
        Bestek bestek = bestekService.getBestek(bestekId);
        return new Response(meetstaatService.addNewMeetstaat(newMeetstaatRequest.getNewMeetstaatRegel(), newMeetstaatRequest.getMeetstaatRegels(), bestek.getBtw_tarief()), true, null);
    }


    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Response save(@PathVariable Long bestekId, @RequestBody ArrayList<MeetstaatRegel> meetstaatRegels) {
        return new Response(meetstaatService.save(meetstaatRegels, bestekId), true, null);
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/herbereken", method = RequestMethod.POST)
    public
    @ResponseBody
    Response herbereken(@PathVariable Long bestekId, @RequestBody ArrayList<MeetstaatRegel> meetstaatRegels) {
        Bestek bestek = bestekService.getBestek(bestekId);
        return new Response(meetstaatService.herbereken(meetstaatRegels, bestek.getBtw_tarief()), true, null);
    }

    @RequestMapping(value = "/meetstaat/verplaats/{huidigPostnr}/{nieuwPostnr}/", method = RequestMethod.POST)
    public
    @ResponseBody
    Response verplaats(@PathVariable String huidigPostnr, @PathVariable String nieuwPostnr, @RequestBody ArrayList<MeetstaatRegel> meetstaatRegels) {
        return new Response(meetstaatService.replace(meetstaatRegels, huidigPostnr, nieuwPostnr, null), true, null);
    }

    @RequestMapping(value = "/meetstaat/getTemplate", method = RequestMethod.POST)
    public
    @ResponseBody
    Response getTemplate(@RequestParam("templateId") Long templateId, @RequestParam("bestekId") Long bestekId) throws Exception {
        return new Response(meetstaatTemplateService.laadTemplate(templateId, bestekId), true, null);
    }

    @RequestMapping(value = "/meetstaat/getFromOtherBestek", method = RequestMethod.POST)
    public
    @ResponseBody
    Response getFromOtherBestek(@RequestParam("bestekNr") String bestekNr, @RequestParam("bestekId") Long bestekId) throws Exception {
        return new Response(meetstaatTemplateService.getFromOtherBestek(bestekNr, bestekId), true, null);
    }

    @RequestMapping(value = "/bestek/{bestekId}/meetstaat", method = RequestMethod.GET)
    public String start(@PathVariable Long bestekId, Model model) throws Exception {
        super.startBasic(bestekId,model);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_EENHEDEN, meetstaatEenheidService.getAllUniqueEenheden());
        model.addAttribute(MODEL_ATTRIBUTE_NAME_TYPES, MeetstaatRegel.TYPES);
        model.addAttribute(MODEL_ATTRIBUTE_NAME_TEMPLATES, meetstaatTemplateService.getAll());
        return "bestek.meetstaat.meetstaat";
    }

    @RequestMapping(value = "/bestek/meetstaat/export/draftMeetstaat-{bestekId}.pdf", method = RequestMethod.GET)
    public void exportPdf(@PathVariable Long bestekId, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try{
            op = response.getOutputStream();
            meetstaatExportPdfService.createDraftExport(bestekId, op);
            response.setContentType("application/download");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftMeetstaat-" + bestekId + ".pdf");
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if(op!=null){
                op.flush();
                op.close();
            }
        }
    }

    @RequestMapping(value = "/bestek/meetstaat/export/draftMeetstaat-{bestekId}.xls", method = RequestMethod.GET)
    public void exportExcel(@PathVariable Long bestekId, HttpServletResponse response) throws IOException {
        ServletOutputStream op = null;
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment;filename=draftMeetstaat-" + bestekId + ".xls");
            op = response.getOutputStream();
            meetstaatExportExcelService.createDraftExport(bestekId, op);
        } catch (Exception e) {
            log.error(e, e);
        } finally {
            if(op!=null){
                op.flush();
                op.close();
            }
        }
    }


    @RequestMapping(value = "/bestek/{bestekId}/meetstaat/ontgrendel", method = RequestMethod.GET)
    public String ontgrendel(@PathVariable Long bestekId) throws Exception {
        meetstaatService.ontgrendelDefinitieveMeetstaat(bestekId);
        return "redirect:/s/bestek/" + bestekId + "/meetstaat/";
    }

}
