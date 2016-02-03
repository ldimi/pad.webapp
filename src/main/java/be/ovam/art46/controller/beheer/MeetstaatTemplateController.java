package be.ovam.art46.controller.beheer;

import be.ovam.art46.model.NewMeetstaatRequest;
import be.ovam.pad.model.MeetstaatRegel;
import be.ovam.pad.model.MeetstaatTemplate;
import be.ovam.web.Response;
import be.ovam.art46.service.meetstaat.MeetstaatEenheidService;
import be.ovam.art46.service.meetstaat.MeetstaatService;
import be.ovam.art46.service.meetstaat.MeetstaatTemplateService;
import be.ovam.art46.service.meetstaat.response.ResponseReadMeetstaatCSV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by koencorstjens on 12-9-13.
 */
@Controller
public class MeetstaatTemplateController {
    public static final String MODEL_ATTRIBUTE_NAME_EENHEDEN = "eenheden";
    public static final String MODEL_ATTRIBUTE_NAME_TYPES = "types";
    private static final String MODEL_ATTRIBUTE_NAAM_TEMPLATE = "naam";

    @Autowired
    MeetstaatTemplateService meetstaatTemplateService;
    @Autowired
    MeetstaatService meetstaatService;
    @Autowired
    MeetstaatEenheidService meetstaatEenheidService;

    private static BigDecimal DEFAULT_BTW = new BigDecimal(21);

    @RequestMapping(value = "/beheer/meetstaat/templates/", method = RequestMethod.GET)
    public String start(Model model) throws Exception {
        model.addAttribute("templates", meetstaatTemplateService.getAll());
        return "beheer.meetstaat.templates";
    }


    @RequestMapping(value = "/beheer/meetstaat/template/nieuw/", method = RequestMethod.GET)
    public String toonNieuw(Model model) throws Exception {
        model.addAttribute(MODEL_ATTRIBUTE_NAME_EENHEDEN, meetstaatEenheidService.getAllUniqueEenheden());
        model.addAttribute(MODEL_ATTRIBUTE_NAME_TYPES, MeetstaatRegel.TYPES);
        return "beheer.meetstaat.template";
    }

    @RequestMapping(value = "/beheer/meetstaat/template/save/{naam}/", method = RequestMethod.POST)
    public
    @ResponseBody
    Response save(@PathVariable String naam, @RequestBody ArrayList<MeetstaatRegel> meetstaatRegels) {
        return new Response(meetstaatTemplateService.save(meetstaatRegels, naam), true, null);
    }

    @RequestMapping(value = "/beheer/meetstaat/template/{templateId}/", method = RequestMethod.GET)
    public String toon(@PathVariable Long templateId, Model model) throws Exception {
        model.addAttribute("templateId", templateId);
        MeetstaatTemplate meetstaatTemplate = meetstaatTemplateService.get(templateId);
        model.addAttribute(MODEL_ATTRIBUTE_NAAM_TEMPLATE, meetstaatTemplate.getNaam());
        model.addAttribute(MODEL_ATTRIBUTE_NAME_EENHEDEN, meetstaatEenheidService.getAllUniqueEenheden());
        model.addAttribute(MODEL_ATTRIBUTE_NAME_TYPES, MeetstaatRegel.TYPES);
        return "beheer.meetstaat.template";
    }

    @RequestMapping(value = "/beheer/meetstaat/template/{templateId}/verwijder/", method = RequestMethod.GET)
    public String verwijder(@PathVariable String templateId) throws Exception {
        meetstaatTemplateService.delete(new Long(templateId));
        return "redirect:/s/beheer/meetstaat/templates/";
    }

    @RequestMapping(value = "/beheer/meetstaat/template/{templateId}/regels/", method = RequestMethod.GET)
    public
    @ResponseBody
    Response getRegels(@PathVariable Long templateId) throws Exception {
        return new Response(meetstaatTemplateService.getAllregels(templateId), true, null);
    }


    @RequestMapping(value = "/beheer/meetstaat/template/upload/{templateId}", method = RequestMethod.POST)
    @ResponseBody
    Response uploadMedia(@PathVariable Long templateId, @RequestParam("file") MultipartFile file) throws Exception {
        ResponseReadMeetstaatCSV responseReadMeetstaatCSV = meetstaatService.readMeetstaatCSV(new InputStreamReader(file.getInputStream()), null);
        List<MeetstaatRegel> meetstaatRegels = responseReadMeetstaatCSV.getMeetstaatRegels();
        if(templateId!=0){
            for (MeetstaatRegel meetstaatRegel : meetstaatRegels){
                meetstaatRegel.setMeetstaatTemplate(meetstaatTemplateService.get(templateId));
            }
        }
        return new Response(responseReadMeetstaatCSV, true, null);
    }

    @RequestMapping(value = "/beheer/meetstaat/template/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Response add(@RequestBody NewMeetstaatRequest newMeetstaatRequest) {
        return new Response(meetstaatService.addNewMeetstaat(newMeetstaatRequest.getNewMeetstaatRegel(), newMeetstaatRequest.getMeetstaatRegels(), DEFAULT_BTW), true, null);
    }

    @RequestMapping(value = "/beheer/meetstaat/template/herbereken", method = RequestMethod.POST)
    public
    @ResponseBody
    Response herbereken(@RequestBody ArrayList<MeetstaatRegel> meetstaatRegels) {
        return new Response(meetstaatService.herbereken(meetstaatRegels, DEFAULT_BTW), true, null);
    }
    
    @RequestMapping(value = "/beheer/meetstaat/template/verplaats/{huidigPostnr}/{nieuwPostnr}/", method = RequestMethod.POST)
    public
    @ResponseBody
    Response verplaats(@PathVariable String huidigPostnr, @PathVariable String nieuwPostnr, @RequestBody ArrayList<MeetstaatRegel> meetstaatRegels) {
        return new Response(meetstaatService.replace(meetstaatRegels, huidigPostnr, nieuwPostnr, null), true, null);
    }


}
