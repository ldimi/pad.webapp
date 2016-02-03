package be.ovam.art46.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Koen on 9/09/2014.
 */
public class EsbServiceImplTest {
    private EsbServiceImpl esbService;

    @Before
    public void setUp(){
    RestTemplate restTemplate = new RestTemplate();
        esbService = new EsbServiceImpl();
        esbService.setRestTemplate(restTemplate);
        esbService.setDocumentUrl("http://10.1.11.155:8001/rest/cmis/convertToPdf");
        esbService.setBarcodePdfUrl("http://localhost:8380/rest/cmis/generateBarcodePdf");
    }

    @Test
    public void generateBarcodePdf(){
        byte[] bytes =  esbService.getBarcodesPdf("OVAM-","IVS-I","", new Long(1), new Long(35),0);
        Assert.assertNotNull(bytes);
    }
}
