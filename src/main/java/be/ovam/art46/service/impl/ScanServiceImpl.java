package be.ovam.art46.service.impl;

import be.ovam.art46.model.ScanDTO;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.service.EsbService;
import be.ovam.art46.service.ScanService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Koen on 17/03/2015.
 */
@Service
public class ScanServiceImpl implements ScanService {
    private final static Logger LOG = Logger.getLogger(ScanServiceImpl.class);

    @Autowired
    private BriefService briefService;
    @Autowired
    private EsbService esbService;

    public ScanDTO uploadScan(String barcodeNr, File file) throws Exception {
        ScanDTO scanDTO = briefService.getscanlocationfor(barcodeNr);
        scanDTO.setFileName(barcodeNr+".pdf");
        String dmsId = esbService.uploadFile("admin", file, scanDTO.getDmsFolder(),scanDTO.getFileName());
        if(dmsId!=null) {
            scanDTO.setDmsId(dmsId);
            briefService.uploadscanfull(scanDTO);
        }
        return scanDTO;

    }
}
