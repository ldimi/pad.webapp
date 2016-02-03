package be.ovam.art46.service.impl;

import be.ovam.art46.service.EsbService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Koen on 30/06/2014.
 */
public class EsbCacheService {
    private String path;
    @Autowired
    private EsbService esbService;
    @Value("${ovam.dms.user}")
    private String dmsAdminID;

    public EsbCacheService(String path){
        this.path = path;
    }

    protected LoadingCache<String, String> nodeRefCacheForFileNaam = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES)
            .maximumSize(250)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String fileNaamTemplate) {
                    String noderef = esbService.getNodeRefFor(path, fileNaamTemplate, dmsAdminID);
                    if(StringUtils.isEmpty(noderef)){return StringUtils.EMPTY;};
                    return noderef;
                }
            });
    protected LoadingCache<String, byte[]> documentAsStreamCacheForFileName = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES)
            .maximumSize(250)
            .build(new CacheLoader<String, byte[]>() {
                @Override
                public byte[] load(String fileNaamTemplate) throws ExecutionException {
                    String noderef = nodeRefCacheForFileNaam.get(fileNaamTemplate);
                    if(StringUtils.isEmpty(noderef)){
                        return new byte[0];
                    }
                    return esbService.getDocumentAsStreamByte(dmsAdminID, noderef);
                }
            });
}
