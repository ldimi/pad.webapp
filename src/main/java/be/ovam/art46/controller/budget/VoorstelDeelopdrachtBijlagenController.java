package be.ovam.art46.controller.budget;

import be.ovam.art46.service.BijlageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Koen on 15/05/2014.
 */
@Controller
public class VoorstelDeelopdrachtBijlagenController {

    @Autowired
    private BijlageService bijlageService;

    @RequestMapping(value = "/bestek/{bestekId}/voorstel/{voorstelDeelopdrachtId}/upload/", method = RequestMethod.POST)
    public String addFile(@PathVariable Long bestekId, @RequestParam("voorstelDeelopdrachtId") Long voorstelDeelopdrachtId, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        String error = bijlageService.saveBijlageForVoorstelDeelopdracht(voorstelDeelopdrachtId, multipartFile);
        return "redirect:/s/bestek/"+bestekId+"/voorstel/" + voorstelDeelopdrachtId;
    }

    @RequestMapping(value = "/bestek/{bestekId}/voorstel/{voorstelDeelopdrachtId}/bijlage/{bijlageId}/delete/", method = RequestMethod.GET)
    public String deleteFile(@PathVariable Long bestekId, @PathVariable Long voorstelDeelopdrachtId, @PathVariable Long bijlageId) throws Exception {
        bijlageService.deleteForVoorstelDeelopdracht(voorstelDeelopdrachtId, bijlageId);
        return "redirect:/s/bestek/"+bestekId+"/voorstel/" + voorstelDeelopdrachtId;
    }
}
