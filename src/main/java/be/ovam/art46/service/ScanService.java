package be.ovam.art46.service;

import be.ovam.art46.model.ScanDTO;

import java.io.File;

/**
 * Created by Koen on 17/03/2015.
 */
public interface ScanService {
    ScanDTO uploadScan(String barcodeNr, File file) throws Exception;
}
