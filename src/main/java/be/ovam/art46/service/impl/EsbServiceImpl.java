package be.ovam.art46.service.impl;

import be.ovam.art46.service.EsbService;
import be.ovam.pad.service.BasisEsbServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EsbServiceImpl extends BasisEsbServiceImpl implements EsbService {
    
    private final static Logger LOG = Logger.getLogger(EsbServiceImpl.class);
    
    @Value("${ovam.esb.url}/printFileVoorblad")
    private String printUrl;
    
    @Value("${ovam.esb.url}/secure/delete")
    private String verwijderUri;
    
    @Value("${ovam.esb.url}/generateBarcodePdf")
    private String barcodePdfUrl;
    
    @Value("${ovam.esb.url}/upload?fileName={fileName}")
    private String uploadUrl;
    
    @Value("${ovam.esb.url}/maakFolder")
    private String maakFolder;

    @Override
    public boolean print(String nodeRef, String uid, String jobname) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("nodeRef", nodeRef);
        formData.add("uid", uid);
        formData.add("jobname", jobname);

        ResponseEntity<JsonNode> response = restTemplate.exchange(printUrl, HttpMethod.POST, buildRequestEntity(formData, MediaType.APPLICATION_FORM_URLENCODED), JsonNode.class);
        JsonNode body = response.getBody();
        JsonNode succesNode = body.path("succes");
        return succesNode.asBoolean();
    }

    @Override
    public byte[] getBarcodesPdf(String onzichtbarePrefix, String topPrefix, String nummerPrefix, Long eersteNummer, Long laatsteNummer, Integer aantalcijfers){
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("onzichtbarePrefix", onzichtbarePrefix);
        formData.add("topPrefix", topPrefix);
        formData.add("nummerPrefix", nummerPrefix);
        formData.add("eersteNummer", eersteNummer);
        formData.add("laatsteNummer", laatsteNummer);
        formData.add("aantalcijfers", aantalcijfers);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = buildRequestEntity(formData, MediaType.MULTIPART_FORM_DATA);
        ResponseEntity<byte[]> response  = restTemplate.exchange(barcodePdfUrl, HttpMethod.POST, requestEntity, byte[].class);
        return response.getBody();
    }

    @Override
    public String uploadFile(String uid, File file, String path, String fileName) throws IOException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("path", path);
        formData.add("uid", uid);
        formData.add("file", new FileSystemResource(file));
        formData.add("fileName", fileName);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = buildRequestEntity(formData, MediaType.MULTIPART_FORM_DATA);

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

    @Override
    public String copy(String fromPath, String toPath, String uid){
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("path", toPath);
        formData.add("uid", uid);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = buildRequestEntity(formData, MediaType.APPLICATION_FORM_URLENCODED);
        
        String url = maakFolder + "?templatePath={templatePath}";
        Map<String,String> uriVariables = new HashMap<>();
        uriVariables.put("templatePath", fromPath );
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
    
}
