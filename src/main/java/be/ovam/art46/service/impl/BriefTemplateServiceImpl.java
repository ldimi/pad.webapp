package be.ovam.art46.service.impl;

import be.ovam.art46.service.BriefTemplateService;
import be.ovam.art46.service.EsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**
 * Created by Koen on 30/06/2014.
 */
@Service
class BriefTemplateServiceImpl extends EsbCacheService implements BriefTemplateService {
    public static final String PATH = "/Toepassingen/ivs/Brief_Templates_IVS";
    @Autowired
    EsbService esbService;

    public BriefTemplateServiceImpl() {
        super(PATH);
    }

    public InputStream getTemplate(String fileNaamTemplate) throws ExecutionException {
        byte[] body = documentAsStreamCacheForFileName.get(fileNaamTemplate);
        return new ByteArrayInputStream(body);
    }


}
