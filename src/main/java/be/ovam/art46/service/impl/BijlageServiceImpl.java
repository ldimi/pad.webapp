package be.ovam.art46.service.impl;

import be.ovam.art46.dao.BijlageDao;
import be.ovam.art46.service.BijlageService;
import be.ovam.art46.service.EsbService;
import be.ovam.art46.service.VoorstelDeelopdrachtService;
import be.ovam.dms.alfresco.DmsUserAlfresco;
import be.ovam.dms.model.DmsDocument;
import be.ovam.dms.model.DmsDocumentName;
import be.ovam.pad.model.Bijlage;
import be.ovam.pad.model.VoorstelDeelopdracht;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Koen on 15/05/2014.   
 */
@Service
public class BijlageServiceImpl implements BijlageService {
    @Autowired
    private BijlageDao bijlageDao;
    @Autowired
    private DmsUserAlfresco dmsAlfresco;
    @Autowired
    private VoorstelDeelopdrachtService voorstelDeelopdrachtService;
    @Autowired
    private EsbService esbService;

    public String saveBijlageForVoorstelDeelopdracht(Long id , MultipartFile multipartFile) throws IOException {
        if (bijlageDao.voorstelDeelopdrachtBestaat(multipartFile.getOriginalFilename(), id)) {
            return multipartFile.getOriginalFilename() + " bestaat al!";
        } else {
            VoorstelDeelopdracht voorstelDeelopdracht = voorstelDeelopdrachtService.get(id);
            String path = "/Toepassingen/ivs/13/" + voorstelDeelopdracht.getOfferte().getBestek().getDossier_id();
            path = path + "/OFFERTES/" + voorstelDeelopdracht.getOfferte().getId() + "/Voorstellen/" + voorstelDeelopdracht.getId() + "/";

            File tmpFile = File.createTempFile("upload" + UUID.randomUUID().toString(), "tmp");
            multipartFile.transferTo(tmpFile);
            String nodeRef = esbService.uploadFile("admin", tmpFile , path, multipartFile.getOriginalFilename() );
            tmpFile.delete();
            
            Bijlage bijlage = createBijlage(nodeRef, multipartFile.getOriginalFilename(), voorstelDeelopdracht, path,nodeRef);
            bijlage.setVoorstelDeelopdracht(voorstelDeelopdracht);
            bijlageDao.save(bijlage);
            
        }
        return StringUtils.EMPTY;
    }

    private Bijlage createBijlage(String dmsId, String  name, VoorstelDeelopdracht voorstelDeelopdracht, String path, String nodeRef) {
        Bijlage bijlage = new Bijlage();
        bijlage.setDocId(dmsId);
        bijlage.setName(name);
        bijlage.setVoorstelDeelopdracht(voorstelDeelopdracht);
        bijlage.setAlfrescoNodeId(nodeRef);
        return bijlage;
    }

    private String voegBijlageToe(String user, String path, String originalFilename, MultipartFile file) throws IOException {
        DmsDocument document = new DmsDocument(user, new DmsDocumentName(file.getOriginalFilename(), file.getOriginalFilename(), file.getOriginalFilename()), file.getBytes(), path.replaceAll("Toepassingen/", ""), null);
        Map metaData = new HashMap();
        document.setWorkflow("mistralzonderindexering");
        metaData.put("kenmerk2", "" + file.getName());
        metaData.put("documentcategorie", "doccat1");
        document.setMetaData(metaData);
        return dmsAlfresco.createLargeDocument(document, null);
    }

    public void deleteForVoorstelDeelopdracht(Long voorstelDeelopdrachtId, Long bijlageId) {

    }
}
