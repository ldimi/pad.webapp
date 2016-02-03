package be.ovam.art46.controller.taken;

import be.ovam.art46.service.schuldvordering.SchuldvorderingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by Koen on 25/04/2014.
 */
@Controller
public class ScanAanvraagSchuldvorderingenController {
    @Autowired
    private SchuldvorderingService schuldvorderingService;

    @RequestMapping(value = "/tescannenschuldvorderingen", method = RequestMethod.GET)
    public String get(@RequestParam(required = false) String melding, Model model){
        model.addAttribute("schuldvorderingen",schuldvorderingService.getTeScannenSchuldvorderingen());
        model.addAttribute("melding", melding);
        return "tescannenschuldvorderingen";
    }

    @RequestMapping(value = "/tescannenschuldvorderingen/{schuldvorderingId}/uploadscan/", method = RequestMethod.POST)
    public String uploadscan(@PathVariable Integer schuldvorderingId,@RequestParam("file") MultipartFile multipartFile, Model model) throws Exception {
        String melding = schuldvorderingService.addScan(schuldvorderingId, multipartFile);
        return "redirect:/s/tescannenschuldvorderingen?melding="+melding;
    }




}
