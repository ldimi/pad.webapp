package be.ovam.art46.controller.beheer;

import be.ovam.art46.dto.BriefViewDto;
import be.ovam.art46.model.ScanDTO;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.ScanService;
import be.ovam.pad.model.BriefView;
import be.ovam.web.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Koen on 21/01/2015.
 */
@Controller
public class BrievenZonderScanController {
    @Autowired
    private BriefService briefService;
    @Autowired
    private ScanService scanService;

    @RequestMapping(value = "/beheer/qrbrievenzonderscan")
    public String brievenzonderscan(Model model) {
        try {
            List<BriefView> brievenZonderQr = briefService.getQrBrievenZonderScan();
            List<BriefView> brievenMetQr = briefService.getQrBrievenMetScan();
            model.addAttribute("brieven", brievenZonderQr);
            model.addAttribute("brievenMetQr", brievenMetQr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "beheer.qrbrievenzonderscan";
    }

    @RequestMapping(value = "/beheer/gegenereerdeBrieven")
    public String gegenereerdeBrievenZonderScan(Model model) throws InvocationTargetException, IllegalAccessException, ParseException {

        List<BriefViewDto> brievenMet = briefService.gegenereerdeBrievenMetScan();
        List<BriefViewDto> brievenZonder = briefService.gegenereerdeBrievenZonderScan();
        model.addAttribute("brievenZonder", brievenZonder);
        model.addAttribute("brievenMet", brievenMet);

        return "beheer.gegenereerdeBrieven";
    }
    @RequestMapping(value = "/beheer/qrbrievenzonderscan/opladen/{qrCode}", method = RequestMethod.POST)
    public @ResponseBody
    Response opladenQrbrievenzonderscan(@PathVariable String qrCode, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        File file = multipartToFile(multipartFile);
        ScanDTO scanDTO = scanService.uploadScan(qrCode, file);
        file.delete();
        return new Response(scanDTO, true, null);
    }

    @RequestMapping(value = "/beheer/gegenereerdeBrieven/opladen/{qrCode}", method = RequestMethod.POST)
    public @ResponseBody
    Response opladengegenereerdeBrieven(@PathVariable String qrCode, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        File file = multipartToFile(multipartFile);
        ScanDTO scanDTO = scanService.uploadScan(qrCode, file);
        file.delete();

        return new Response(scanDTO, true, null);
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        String[] split = StringUtils.split(multipart.getOriginalFilename(), ".");
        File convFile = File.createTempFile(split[0],"." + split[1]);
        multipart.transferTo(convFile);
        return convFile;
    }
}
