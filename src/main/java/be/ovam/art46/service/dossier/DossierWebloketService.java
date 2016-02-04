package be.ovam.art46.service.dossier;

import be.ovam.art46.dao.DossierArt46Dao;
import be.ovam.art46.service.EsbService;
import be.ovam.pad.model.Dossier;
import be.ovam.util.mybatis.SqlSession;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DossierWebloketService {
    
    @Autowired
    private DossierArt46Dao dossierArt46Dao;
    
    @Autowired
    private EsbService esbService;
    
    @Value("${ovam.webloket.url}")
    private String webloketurl;

    
    public void createFolderBasedOnTemplate(Integer id) {
        Dossier dossier = dossierArt46Dao.get(id);
        if(StringUtils.isEmpty(dossier.getWebloketNodeRef())) {
            // Alfresco: copieren van template folder naar dossier
            String dossier_nr = dossier.getDossier_nr();
            
            String to = "/Toepassingen/ivs/" + dossier_nr.substring(0, 2) + "/" + dossier_nr + "/WEBLOKET";
            String from = "/Toepassingen/ivs/template_webloket/";
            
            String nodeRef = esbService.copy(from, to, "admin");
            
            // nodeRef wordt in dossier bewaard.
            dossier.setWebloketNodeRef(nodeRef);
            dossierArt46Dao.save(dossier);
        }
    }

    public String getWebloketUrl(Integer id) {
        Dossier dossier = dossierArt46Dao.get(id);
        if(StringUtils.isEmpty(dossier.getWebloketNodeRef())){
            return StringUtils.EMPTY;
        }
        return webloketurl+"/webloket/dossier/"+id+"/";
    }

}
