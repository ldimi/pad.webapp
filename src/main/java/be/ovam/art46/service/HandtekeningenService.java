package be.ovam.art46.service;

import be.ovam.art46.util.Application;
import com.fasterxml.jackson.databind.JsonNode;
import fr.opensagres.xdocreport.document.images.ByteArrayImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.ExecutionException;

/**
 * Created by Koen on 27/06/2014.
 */
public interface HandtekeningenService {

    String handtekenningBeschikbaar() throws ExecutionException;
    IImageProvider getHandtekenning() throws ExecutionException;
    IImageProvider getDraft() throws ExecutionException;
}
