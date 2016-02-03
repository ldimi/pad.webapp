package be.ovam.art46.service.impl;

import be.ovam.art46.service.EsbService;
import com.fasterxml.jackson.databind.JsonNode;
import fr.opensagres.xdocreport.core.utils.Base64Utility;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Koen on 27/06/2014.
 */
@Service
public class EsbServiceImpl implements EsbService {
    
    private final static Logger LOG = Logger.getLogger(EsbServiceImpl.class);
    
    @Autowired
    private RestTemplate restTemplate;

    @Value("${ovam.esb.url}/haalDocumentInhoudOp")
    private String haalDocumentInhoudOpUrl;
    
    @Value("${ovam.esb.url}/convertToPdf")
    private String documentUrl;
    
    @Value("${ovam.esb.url}/printFileVoorblad")
    private String printUrl;
    
    @Value("${ovam.esb.url}/haalInhoudOp")
    private String haalBijlageUrl;
    
    @Value("${ovam.esb.url}/secure/delete")
    private String verwijderUri;
    
    @Value("${ovam.esb.url}/generateBarcodePdf")
    private String barcodePdfUrl;
    
    @Value("${ovam.esb.url}/upload?fileName={fileName}")
    private String uploadUrl;
    
    @Value("${ovam.esb.url}/maakFolder")
    private String maakFolder;

    public byte[] getDocumentAsStreamByte(String alfrescoUserId, String noderef){
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("uid", alfrescoUserId);
        formData.add("nodeRef", noderef);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
        ResponseEntity<byte[]> response  = restTemplate.exchange(haalDocumentInhoudOpUrl, HttpMethod.POST, requestEntity, byte[].class);
        return response.getBody();
    }

    public void convertToPDF(File odtFile, File pdfFile) throws Exception {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("file", new FileSystemResource(odtFile));
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
        ResponseEntity<byte[]> response  = restTemplate.exchange(documentUrl, HttpMethod.POST, requestEntity, byte[].class);
        byte[] body = response.getBody();
        IOUtils.copy(new ByteArrayInputStream(body), new FileOutputStream(pdfFile));
    }

    public boolean print(String nodeRef, String uid, String jobname) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("nodeRef", nodeRef);
        formData.add("uid", uid);
        formData.add("jobname", jobname);

// The URL for making the POST request
        HttpHeaders requestHeaders = new HttpHeaders();

// Sending multipart/form-data
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

// Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
        ResponseEntity<JsonNode> response = restTemplate.exchange(printUrl, HttpMethod.POST, requestEntity, JsonNode.class);
        JsonNode body = response.getBody();
        JsonNode succesNode = body.path("succes");
        return succesNode.asBoolean();
    }


    public String getNodeRefFor(String path, String bestandsnaam, String uid) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("uid", uid);
        formData.add("path", path);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
        ResponseEntity<JsonNode> response = restTemplate.exchange(haalBijlageUrl, HttpMethod.POST, requestEntity, JsonNode.class);
        JsonNode body = response.getBody();
        JsonNode succesNode = body.path("succes");
        if(succesNode.asBoolean()&& body.path("result")!=null){
            Iterator<JsonNode> result = body.path("result").elements();
            while(result.hasNext()){
                JsonNode document = result.next();
                String naam = document.get("name").asText();
                if(StringUtils.equals(naam, bestandsnaam)){
                    return document.get("nodeRef").asText();
                }
            }
        }
        return null;
    }
    
    @Override
    public void deleteByPath(String path, String filename, String alfrescoUser) throws Exception {
        String noderef = getNodeRefFor(path, filename, alfrescoUser);
        delete(alfrescoUser, noderef, "");
    }

    
    private void delete(String alfrescoUser, String noderef, String cmisId) throws Exception {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("uid", alfrescoUser);
        formData.add("nodeRef", noderef);
        formData.add("cmisId", cmisId);
        
        ResponseEntity<String> response = restTemplate.exchange(verwijderUri, HttpMethod.POST, getMultiValueMapHttpRequestEntity(formData), String.class);
    }

    

    @Override
    public byte[] getBarcodesPdf(String onzichtbarePrefix, String topPrefix, String nummerPrefix, Long eersteNummer, Long laatsteNummer, Integer aantalcijfers){
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("onzichtbarePrefix", onzichtbarePrefix);
        formData.add("topPrefix", topPrefix);
        formData.add("nummerPrefix", nummerPrefix);
        formData.add("eersteNummer", eersteNummer);
        formData.add("laatsteNummer", laatsteNummer);
        formData.add("aantalcijfers", aantalcijfers);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
        ResponseEntity<byte[]> response  = restTemplate.exchange(barcodePdfUrl, HttpMethod.POST, requestEntity, byte[].class);
        return response.getBody();
    }

    public String uploadFile(String uid, File file, String path, String fileName) throws IOException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        formData.add("path", path);
        formData.add("uid", uid);
        formData.add("file", new FileSystemResource(file));
        formData.add("fileName", fileName);
        HttpHeaders requestHeaders = new HttpHeaders();

// Sending multipart/form-data
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

// Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);

// Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        converter.addPartConverter(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(converter);
        ResponseEntity<JsonNode> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, com.fasterxml.jackson.databind.JsonNode.class, fileName);
        if(! response.getStatusCode().equals(HttpStatus.OK)){
            LOG.error("StatusCode EsbService is niet gelijk aan 200 maar is " + response.getStatusCode().value());
            return null;
        }
        JsonNode body = response.getBody();
        if (!body.get("succes").asBoolean()) {
            LOG.error(body.get("errorMsg"));
            return null;
        } else {
            JsonNode jsonNode1 = body.path("result").path("nodeRef");
            return jsonNode1.asText();
        }
    }

    public String copy(String fromPath, String toPath, String uid){
        Map<String,String> uriVariables = new HashMap<String, String>();
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
        String url = maakFolder + "?templatePath={templatePath}";
        uriVariables.put("templatePath", fromPath );
        formData.add("path", toPath);
        formData.add("uid", uid);
        HttpHeaders requestHeaders = new HttpHeaders();
        // Sending multipart/form-data
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class,uriVariables);
        JsonNode body = response.getBody();
        if (!body.get("succes").asBoolean()) {
            LOG.error(body.get("errorMsg"));
            return null;
        }else {
            JsonNode jsonNode1 = body.path("result").path("nodeRef");
            return jsonNode1.asText();
        }
    }


    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public void setBarcodePdfUrl(String barcodePdfUrl) {
        this.barcodePdfUrl = barcodePdfUrl;
    }
    
    private HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpRequestEntity(MultiValueMap<String, Object> formData) {
        return new HttpEntity<MultiValueMap<String, Object>>(formData, getRequestHeaders());
    }

    private MultiValueMap<String,String> getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        //String authorizationHeader = Base64Utility.encode((esbserviceUserName+":"+esbservicePassword).getBytes());
        String authorizationHeader = Base64Utility.encode(("webloket-pad" + ":" + "p8aDuhAfufre").getBytes());
        requestHeaders.add("Authorization", "Basic " + authorizationHeader);
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return requestHeaders;
    }

}
