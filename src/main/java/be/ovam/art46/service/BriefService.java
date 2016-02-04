package be.ovam.art46.service;

import be.ovam.art46.service.dossier.DossierService;
import be.ovam.art46.dao.BriefDAO;
import be.ovam.art46.dao.BriefViewDao;
import be.ovam.art46.dao.DossierDAO;
import be.ovam.art46.dto.BriefViewDto;
import be.ovam.art46.model.ScanDTO;
import be.ovam.dms.alfresco.DmsUserAlfresco;
import be.ovam.dms.model.DmsDocument;
import be.ovam.dms.model.DmsDocumentName;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.BriefView;
import be.ovam.pad.model.Dossier;
import be.ovam.pad.model.Schuldvordering;
import be.ovam.util.mybatis.SqlSession;
import com.jenkov.prizetags.tree.impl.Tree;
import com.jenkov.prizetags.tree.impl.TreeNode;
import com.jenkov.prizetags.tree.itf.ITree;
import com.jenkov.prizetags.tree.itf.ITreeNode;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
@Transactional
public class BriefService {
    public final static String ALFRESCO_URL = "Toepassingen/ivs/";
    public final static int CATEGORIE_ID_MEETSTAAT = 22;
    public final static int CATEGORIE_ID_VOORSTEL_DEELOPDRACHTEN = 18;
    public final static int CATEGORIE_ID_SCAN = 5;
    private final static Logger log = Logger.getLogger(BriefService.class);
    private static List<Integer> uplaadCategorieen = new ArrayList<Integer>(Arrays.asList(new Integer[]{6, 16, 17, 18, 21, 22, 23}));
    @Autowired
    private DossierService dossierService;
    @Autowired
    @Qualifier("sqlSession")
    private SqlSession sqlSession;
    @Autowired
    private BriefViewDao briefViewDao;
    @Autowired
    private BriefDAO briefDAO;
    @Autowired
    private DossierDAO dossierDAO;
    @Autowired
    private DmsUserAlfresco dmsAlfresco;
    @Value("${ovam.dms.webdrive.base}")
    private String pathToAlfresco;

    public static boolean isUploadCategorie(Integer categorie_id) {
        return uplaadCategorieen.contains(categorie_id);
    }

    public static String getAlfrescoUrl() {
        return ALFRESCO_URL;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public Brief getBrief(Integer briefId) throws Exception {
        Brief brief = briefDAO.get(briefId);
        if (brief == null) {
            log.error("brief met id " + briefId + " bestaat niet");
            return null;
        }
        return brief;
    }

    public ITree buildTree(Integer dossierId, Integer uit_type_id_vos, Integer bestek_id, Date datum_van, Date datum_tot) throws Exception {
        List<Brief> brieven = briefDAO.getBrieven(dossierId, uit_type_id_vos, bestek_id, datum_van, datum_tot);
        ITree tree = new Tree();
        Map<Integer, ITreeNode> treeComponents = new HashMap<Integer, ITreeNode>();
        ITreeNode root = createComponent("d" + dossierId, "dossier", "dossier", null);
        root.setObject(dossierId);
        tree.setRoot(root);
        buildBriefCategorieTree(treeComponents, root, dossierId);
        if (brieven != null && brieven.size() > 0) {
            for (Brief brief : brieven) {
                if (brief.getCategorie_id() != null) {
                    ITreeNode briefTypeNode = getBriefCategorieComponent(brief.getCategorie_id(), treeComponents, root);
                    briefTypeNode.addChild(createComponent("b" + brief.getBrief_id(), brief.getBrief_nr(), "brief", brief));
                }
            }
        }
        if (bestek_id != null && bestek_id != 0) {
            tree.expandAll();
        }
        return tree;
    }

    private void buildBriefCategorieTree(Map treeComponents, ITreeNode root, Integer dossierId) throws Exception {
        List<Map> briefCategorien = briefDAO.getBriefCategorien(dossierId);
        for (Map briefCategorie : briefCategorien) {
            getBriefCategorieComponent((Integer) briefCategorie.get("brief_categorie_id"), treeComponents, root);
        }
    }

    private ITreeNode getBriefCategorieComponent(Integer briefCategorieId, Map treeComponents, ITreeNode root) throws Exception {
        if (!treeComponents.containsKey(briefCategorieId)) {
            if (briefCategorieId != 0) {
                @SuppressWarnings("rawtypes")
                Map briefCategorie = briefDAO.getBriefCategorie(briefCategorieId);
                ITreeNode briefTypeNode = createComponent("bcategorie" + briefCategorieId, briefCategorie.get("brief_categorie_b").toString(),
                        "briefCategorie", briefCategorieId);
                if (briefCategorie.get("parent_brief_categorie_id") != null) {
                    ITreeNode parent = getBriefCategorieComponent((Integer) briefCategorie.get("parent_brief_categorie_id"), treeComponents, root);
                    parent.addChild(briefTypeNode);
                } else {
                    root.addChild(briefTypeNode);
                }
                treeComponents.put(briefCategorieId, briefTypeNode);
            } else {
                ITreeNode briefTypeNode = createComponent("bcategorie" + briefCategorieId, "Onbekend", "briefCategorie", briefCategorieId);
                root.addChild(briefTypeNode);
                treeComponents.put(briefCategorieId, briefTypeNode);
            }

        }
        return (ITreeNode) treeComponents.get(briefCategorieId);
    }

    private ITreeNode createComponent(String id, String label, String type, Serializable object) {
        ITreeNode node = new TreeNode(id, StringUtils.abbreviate(label, 60), type);
        node.setObject(object);
        if ("brief".equals(type)) {
            node.setCollapsedImageUrl("./resources/images/copy.gif");
            node.setExpandedImageUrl("./resources/images/copy.gif");
        } else {
            node.setCollapsedImageUrl("./resources/images/closedFolder.gif");
            node.setExpandedImageUrl("./resources/images/openFolder.gif");
        }
        return node;
    }

    public void uploadScan(Integer parent_brief_id, byte[] content, String fileName) throws Exception {
        Brief scan = this.makeScan(parent_brief_id);
        this.uploadBrief(scan.getBrief_id(), content, fileName);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void uploadBrief(Integer briefId, byte[] content, String fileName) throws Exception {

        Map brief = sqlSession.selectOne("be.ovam.art46.mappers.BriefMapper.getBriefDetails", briefId);
        String brief_nr = (String) brief.get("brief_nr");
        String dossier_nr = (String) brief.get("dossier_nr");
        Integer categorie_id = (Integer) brief.get("categorie_id");
        String dms_id = (String) brief.get("dms_id");
        String auteur_id = (String) brief.get("auteur_id");


        String folderName = ALFRESCO_URL + dossier_nr.substring(0, 2) + "/" + dossier_nr;
        if (categorie_id == 5) {
            folderName = folderName + "/Scans";
        }

        if (StringUtils.isBlank(dms_id)) {
            DmsDocument document = new DmsDocument(auteur_id, new DmsDocumentName(fileName, brief_nr, brief_nr),
                    content, folderName.replaceAll("Toepassingen/", ""), null);
            Map metaData = new HashMap();
            document.setWorkflow("mistralzonderindexering");
            metaData.put("kenmerk2", "" + brief_nr);
            metaData.put("documentcategorie", "doccat1");
            document.setMetaData(metaData);
            log.debug("createLargeDocument fileName: " + fileName + " ,folderName: " + folderName);
            String dmsId = dmsAlfresco.createLargeDocument(document, null);
            if (dmsId != null) {
                log.debug("updateBriefInfo fileName: " + fileName + " ,folderName: " + folderName);
                briefDAO.updateBriefInfo(briefId, null, dmsId, fileName, folderName);
            } else {
                throw new Exception("De brief is niet opgeladen in het DMS. folderName" + folderName + " ,fileName " + fileName);
            }
        } else {
            throw new Exception("Brief is reeds opgeladen. folderName+ " + folderName + " ,fileName" + fileName);
        }
    }

    public boolean updateBriefAfterUpload(Integer briefId, String dmsId, String fileName) throws Exception {
        Brief brief = briefDAO.get(briefId);
        return updateBriefAfterUpload(brief, dmsId, fileName);
    }

    public boolean updateBriefAfterUpload(Brief brief, String dmsId, String fileName) throws Exception {
        Dossier dossier = (Dossier) dossierDAO.getObject(Dossier.class, brief.getDossier_id());
        String folderName = ALFRESCO_URL + dossier.getDossier_nr().substring(0, 2) + "/" + dossier.getDossier_nr();
        if (brief.getCategorie_id() == 5) {
            folderName = folderName + "/Scans";
        }

        if (StringUtils.isBlank(brief.getDms_id())) {

            if (dmsId != null) {
                briefDAO.updateBriefInfo(brief.getBrief_id(), null, dmsId, fileName, folderName);
            } else {
                throw new Exception("De brief is niet opgeladen in het DMS.");
            }
        } else {
            throw new Exception("Brief is reeds opgeladen.");
        }
        return true;
    }

    public Brief makeScan(Integer briefId) throws Exception {
        Brief scan = new Brief();
        Brief brief = briefDAO.get(briefId);
        BeanUtils.copyProperties(scan, brief);
        scan.setQr_code(null);
        scan.setDms_id(null);
        scan.setDms_folder(null);
        scan.setDms_filename(null);
        scan.setBrief_id(null);
        scan.setCategorie_id(5);
        scan.setParent_brief_id(brief.getBrief_id());
        scan.setUit_aard_id(2);
        scan.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
        briefDAO.save(scan);
        return scan;
    }

    public Brief makeDocument(Schuldvordering schuldvordering, String auteur_id, String commentaar) throws Exception {


        Brief doc = new Brief();
        doc.setBestek_id(schuldvordering.getBestek_id());
        doc.setDossier_id(schuldvordering.getBestek().getDossier_id());
        if (schuldvordering.getSchuldvordering_fase_id() == null) {
            doc.setCategorie_id(12);
        } else {
            doc.setCategorie_id(schuldvordering.getSchuldvordering_fase_id());
        }
        doc.setBetreft("Schuldvordering " + schuldvordering.getNummer());
        if (schuldvordering.isGoedgekeurd()) {
            doc.setUit_type_id_vos(Brief.BriefVosType.IU1_SCHULDVORDRING_BRIEF_PV.getValue());
        } else {
            doc.setUit_type_id_vos(Brief.BriefVosType.IU1A_AFKEURING_SCHULDVORDRING_BRIEF_PV.getValue());
        }
        doc.setUit_d(new Date());
        doc.setUit_aard_id(Brief.BRIEF_AARD.BRIEF.getValue());
        doc.setUit_type_id(Brief.BRIEF_TYPE.GEWOON.getValue());
        doc.setAuteur_id(auteur_id);
        doc.setCommentaar(commentaar);
        Integer volgNr = briefDAO.getVolgnummer(schuldvordering.getBestek().getDossier_id());
        doc.setVolgnummer(volgNr);
        doc.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
        doc.setBrief_nr(briefDAO.generateBriefNr(doc, schuldvordering.getBestek().getDossier().getDossier_nr()));
        if (schuldvordering.getAanvraagSchuldvordering() != null && schuldvordering.getAanvraagSchuldvordering().getOfferte() != null && schuldvordering.getAanvraagSchuldvordering().getOfferte().getBrief() != null) {
            doc.setAdres_id(schuldvordering.getAanvraagSchuldvordering().getOfferte().getBrief().getAdres_id());
            doc.setContact_id(schuldvordering.getAanvraagSchuldvordering().getOfferte().getBrief().getContact_id());
        } else if (schuldvordering.getBrief() != null) {
            doc.setAdres_id(schuldvordering.getBrief().getAdres_id());
            doc.setContact_id(schuldvordering.getBrief().getContact_id());
        }
        briefDAO.save(doc);
        return doc;
    }

    public Brief makeScanDocument(Integer dossierId, Integer categorie_id, String auteur_id, String commentaar, Long bestekId) throws Exception {
        Brief doc = new Brief();
        doc.setAdres_id(0); // Adres met ID onbekend
        doc.setCategorie_id(categorie_id);
        doc.setDossier_id(dossierId);
        doc.setAuteur_id(auteur_id);
        doc.setBetreft(commentaar);
        doc.setCommentaar(commentaar);
        doc.setIn_d(new Date());
        doc.setIn_aard_id(Brief.BRIEF_AARD.BRIEF.getValue());
        doc.setBestek_id(bestekId);
        Integer volgNr = briefDAO.getVolgnummer(dossierId);
        doc.setVolgnummer(volgNr);
        doc.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
        if (21 == categorie_id) {
            // terugvordering
            doc.setBrief_nr("FAC" + StringUtils.leftPad("" + volgNr, 3, "0"));
            doc.setIn_aard_id(11); // factuur
        } else if (18 == categorie_id) {
            // voorstel deelopdrachten
            doc.setBrief_nr("O" + StringUtils.leftPad("" + volgNr, 5, "0"));
            doc.setIn_aard_id(12); // rapport
        } else if (22 == categorie_id) {
            // meetstaat
            doc.setBrief_nr("Meetstaat-" + StringUtils.leftPad("" + volgNr, 5, "0"));
        } else {
            doc.setBrief_nr(StringUtils.leftPad("" + volgNr, 5, "0"));
        }
        briefDAO.save(doc);
        return doc;
    }

    public Brief makeDocument(Integer dossierId, Integer categorie_id, String auteur_id, String commentaar, Long bestekId) throws Exception {

        log.info("makeDocument voor dossierId, categorid_id, auteur_id, bestekId :" + dossierId + ", " + categorie_id + ", " + auteur_id + ", " + bestekId);

        Brief doc = new Brief();
        doc.setAdres_id(0); // Adres met ID onbekend
        doc.setCategorie_id(categorie_id);
        doc.setDossier_id(dossierId);
        doc.setAuteur_id(auteur_id);
        doc.setBetreft(commentaar);
        doc.setCommentaar(commentaar);
        doc.setBestek_id(bestekId);
        Integer volgNr = briefDAO.getVolgnummer(dossierId);
        doc.setVolgnummer(volgNr);
        doc.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
        if (21 == categorie_id) {
            // terugvordering
            doc.setBrief_nr("FAC" + StringUtils.leftPad("" + volgNr, 3, "0"));
            doc.setIn_aard_id(11); // factuur
        } else if (18 == categorie_id) {
            // voorstel deelopdrachten
            doc.setBrief_nr("O" + StringUtils.leftPad("" + volgNr, 5, "0"));
            doc.setIn_aard_id(12); // rapport
        } else if (22 == categorie_id) {
            // meetstaat
            doc.setBrief_nr("Meetstaat-" + StringUtils.leftPad("" + volgNr, 5, "0"));
        } else {
            doc.setBrief_nr(StringUtils.leftPad("" + volgNr, 5, "0"));
        }
        briefDAO.save(doc);
        return doc;
    }

    public Brief makeDocument(Integer dossierId, Integer categorie_id, String auteur_id, String commentaar) throws Exception {
        return makeDocument(dossierId, categorie_id, auteur_id, commentaar, null);
    }

    public Brief makeDocument(Integer dossierId, Integer categorie_id, String auteur_id) throws Exception {
        return makeDocument(dossierId, categorie_id, auteur_id, "");
    }

    public void deleteBrief(Integer briefId) throws Exception {
        Brief brief = briefDAO.get(briefId);
        if (brief != null) {
            deleteBrief(brief);
        }
    }

    public void deleteBrief(Brief brief) {
        String dmsId = brief.getDms_id();
        briefDAO.delete(brief);
        if (StringUtils.isNotEmpty(dmsId)) {
            try {
                dmsAlfresco.deleteDocument(dmsId, null);
            } catch (Exception e) {
                log.error("Error bij het verwijderen van Brief: " + e, e);
                throw new RuntimeException("Error bij het verwijderen van Brief uit Alfresco", e);
            }
        }
    }

    public List<Brief> getOfferteBrieven(Long bestekId) {
        return briefDAO.getBrievenVoorInAard(bestekId, Brief.BRIEF_AARD.OFFERTE.getValue());
    }

    public InputStream getFileBrief(Brief brief) {
        DmsDocument dmsDocument = dmsAlfresco.getDocument(brief.getDms_id(), "admin");
        if (dmsDocument == null) {
            throw new RuntimeException("Geen brief gevonden in dms voor brief_id: " + brief.getBrief_id() + ", brief_nr : " + brief.getBrief_nr());
        }
        return new ByteArrayInputStream(dmsDocument.getContent());
    }

    public Brief getBriefForQr(Long code) {
        return briefDAO.getBriefForQr(code);
    }

    public Brief getBriefForDmsId(String dms_id) {
        return briefDAO.getBriefForDmsId(dms_id);
    }

    public List<BriefView> getQrBrievenZonderScan() {
        return briefViewDao.getQrBrievenZonderScan();
    }

    public List<BriefView> getQrBrievenMetScan() {
        return briefViewDao.getQrBrievenMetScanLaatste15Dagen();
    }

    public ScanDTO uploadscanfull(ScanDTO dto) throws Exception {
        Brief scan = getBriefForDmsId(dto.getDmsId());
        if (scan == null) {
            Brief brief = getBrief(dto.getBriefId());
            if (StringUtils.isNotEmpty(brief.getDms_id())) {
                brief = makeScan(dto.getBriefId());
                brief.setQr_code(null);
            }
            updateBriefAfterUpload(brief.getBrief_id(), dto.getDmsId(), dto.getFileName());
        } else {
            updateBriefAfterUpload(scan.getBrief_id(), dto.getDmsId(), dto.getFileName());
        }
        dto.setStatus(true);
        return dto;
    }

    public ScanDTO getscanlocationfor(String code) throws Exception {
        ScanDTO scanDto = new ScanDTO();
        Brief brief = null;
        if (isInteger(code)) {
            Integer briefId = Integer.parseInt(code);
            brief = briefDAO.get(briefId);
        } else if (code.startsWith("I")) {
            return this.scanlocationforQR(code);
        }
        if (brief == null) {
            throw new RuntimeException("Geen Brief gevonden voor briefId " + code);
        }

        scanDto.setBriefId(brief.getBrief_id());
        Dossier dossier = dossierService.getDossier(brief.getDossier_id());
        String folderName = BriefService.getAlfrescoUrl() + dossier.getDossier_nr().substring(0, 2) + "/" + dossier.getDossier_nr() + "/"
                + briefDAO.getBriefCategorie(5).get("brief_categorie_b");
        scanDto.setDmsFolder(folderName);
        return scanDto;
    }

    public ScanDTO scanlocationforQR(String code) {
        long l_code = Long.parseLong(StringUtils.replace(code, "I", ""));
        Brief brief = getBriefForQr(l_code);

        if (brief == null) {
            throw new RuntimeException("Geen Brief gevonden voor qr_code " + code);
        }

        Dossier dossier = dossierService.getDossier(brief.getDossier_id());
        String folderName = BriefService.getAlfrescoUrl() + dossier.getDossier_nr().substring(0, 2) + "/" + dossier.getDossier_nr();
        if (brief.getDms_id() != null) {
            if ((code + ".pdf").equals(brief.getDms_filename())) {
                folderName = brief.getDms_folder();
            } else {
                folderName = folderName + "/Scans";
            }
        }
        log.info("scanlocationforQR folderName :" + folderName);

        ScanDTO scanDto = new ScanDTO();

        scanDto.setBriefId(brief.getBrief_id());
        scanDto.setDmsFolder(folderName);
        return scanDto;
    }

    public List<BriefViewDto> gegenereerdeBrievenZonderScan() throws InvocationTargetException, IllegalAccessException, ParseException {
        List<BriefViewDto> briefViewDtos = new ArrayList<BriefViewDto>();
        for (BriefView briefView : briefViewDao.gegenereerdeBrievenZonderScan()) {
            BriefViewDto briefViewDto = new BriefViewDto();
            BeanUtils.copyProperties(briefViewDto, briefView);
            briefViewDtos.add(briefViewDto);
        }
        return briefViewDtos;
    }

    public List<BriefViewDto> gegenereerdeBrievenMetScan() throws InvocationTargetException, IllegalAccessException {
        List<BriefViewDto> briefViewDtos = new ArrayList<BriefViewDto>();
        for (BriefView briefView : briefViewDao.gegenereerdeBrievenMetScan()) {
            BriefViewDto briefViewDto = new BriefViewDto();
            BeanUtils.copyProperties(briefViewDto, briefView);
            for (BriefView scan : briefView.getScans()) {
                if (scan.getStandaardParentScanBestandsnaam().equals(scan.getDmsFileName())) {
                    briefViewDto.setScanDmsFileName(scan.getDmsFileName());
                    briefViewDto.setScanDmsFolder(scan.getDmsFolder());
                }
            }
            briefViewDtos.add(briefViewDto);
        }
        return briefViewDtos;
    }
}
