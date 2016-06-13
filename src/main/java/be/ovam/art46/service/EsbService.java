package be.ovam.art46.service;

import be.ovam.pad.service.BasisEsbService;
import java.io.File;
import java.io.IOException;

/**
 * Created by Koen on 27/06/2014.
 */
public interface EsbService extends BasisEsbService {
    
    boolean print(String dms_id, String user_id, String nummer);

    void deleteByPath(String path, String filename, String alfrescoUser) throws Exception;

    byte[] getBarcodesPdf(String onzichtbarePrefix, String topPrefix, String nummerPrefix, Long eersteNummer, Long laatsteNummer, Integer aantalcijfers);

    String uploadFile(String uid, File file, String path, String fileName) throws IOException;

    String copy(String fromPath, String toPath, String uid);
}
