package be.ovam.art46.service.impl;

import be.ovam.art46.service.HandtekeningenService;
import be.ovam.art46.util.Application;
import fr.opensagres.xdocreport.document.images.ByteArrayImageProvider;
import fr.opensagres.xdocreport.document.images.IImageProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Created by Koen on 27/06/2014.
 */
@Service
public class HandtekeningenServiceImpl extends EsbCacheService implements HandtekeningenService {
    public final static String DRAFT = "draft";
    public HandtekeningenServiceImpl() {
        super("/Toepassingen/ivs/Handtekeningen_IVS");
    }


    @Override
    public String handtekenningBeschikbaar() throws ExecutionException {
        if(StringUtils.isEmpty(getNoderefHandtekening())){
            return "U kan geen schuldvordering brieven beoordelen er is geen handtekening beschikbaar.";
        }
        return StringUtils.EMPTY;
    }

    @Override
    public IImageProvider getHandtekenning() throws ExecutionException {
        byte[] body = documentAsStreamCacheForFileName.get(Application.INSTANCE.getUser_id() + ".jpg");
        return new ByteArrayImageProvider(body);
    }
    @Override
    public IImageProvider getDraft() throws ExecutionException {
        byte[] body = documentAsStreamCacheForFileName.get(DRAFT + ".jpg");
        return new ByteArrayImageProvider(body);
    }


    public String getNoderefHandtekening() throws ExecutionException {
        String bestandsnaam = Application.INSTANCE.getUser_id() + ".jpg";
        return nodeRefCacheForFileNaam.get(bestandsnaam);
    }
}
