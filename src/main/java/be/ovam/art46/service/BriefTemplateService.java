package be.ovam.art46.service;

import fr.opensagres.xdocreport.document.images.ByteArrayImageProvider;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;


/**
 * Created by Koen on 30/06/2014.
 */
public interface BriefTemplateService {

    InputStream getTemplate(String templateFileNaam) throws ExecutionException;

}
