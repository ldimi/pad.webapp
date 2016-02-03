package be.ovam.art46.controller.brief;

import be.ovam.art46.service.dossier.DossierService;
import be.ovam.art46.dao.BriefDAO;
import be.ovam.art46.service.*;
import be.ovam.pad.model.Brief;
import be.ovam.pad.model.Dossier;
import be.ovam.art46.model.ScanDTO;
import be.ovam.art46.service.schuldvordering.SchuldvorderingService;
import be.ovam.art46.struts.actionform.BriefForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BriefServiceController {

	@Autowired
	private BriefDAO briefDao;
	@Autowired
	private BriefService briefService;
	@Autowired
	private DossierService dossierService;
    @Autowired
    private ScanService scanService;
    
    private final static Logger logger = LoggerFactory.getLogger(BriefServiceController.class);


    @RequestMapping(value = "/briefService/getBriefId", method = RequestMethod.GET)
	public @ResponseBody
	String getBriefId(@RequestParam(value = "adresId", required = false) Integer adresId,
			@RequestParam(value = "contactId", required = false) Integer contactId,
			@RequestParam(value = "dossierId", required = false) Integer dossierId,
			@RequestParam(value = "dienstId", required = false) Integer dienstId,
            @RequestParam(value = "aardId", required = false) Integer aardId,
			@RequestParam(value = "auteurId", required = false) String auteurId,
            @RequestParam(value = "betreft", required = false) String betreft,
			@RequestParam(value = "categorieId", required = false) Integer categorieId,
			@RequestParam(value = "briefNummer", required = false) String briefNummer,
			@RequestParam(value = "bestekId", required = false) Integer bestekId) throws Exception {

		System.out.println("adresId : " + adresId);
		System.out.println("contactId : " + contactId);
		System.out.println("dossierId : " + dossierId);
		System.out.println("dienstId : " + dienstId);
		System.out.println("aardId : " + aardId);
		System.out.println("auteurId : " + auteurId);
		System.out.println("betreft : " + betreft);
		System.out.println("categorieId : " + categorieId);
		System.out.println("briefNummer: " + briefNummer);
		System.out.println("bestekId : " + bestekId);

		BriefForm brief = new BriefForm();
		brief.setInschrijf_d(new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

		Dossier dossier = dossierService.getDossier(dossierId);

		if (adresId == null) {
			brief.setAdres_id("2124"); // OVAM
		} else {
			brief.setAdres_id(adresId.toString());
		}
		if (contactId != null) {
			brief.setContact_id(contactId.toString());
		}
        
		brief.setDossier_id(dossierId);
		brief.setDossier_nr(dossier.getDossier_id());
        
		if (categorieId != null && categorieId.intValue() != 0) {
			brief.setCategorie_id(categorieId.toString());
		} else {
			brief.setCategorie_id("1");
		}
		if (StringUtils.isNotBlank(briefNummer)) {
			brief.setBrief_nr(briefNummer);
		}
		if (aardId != null && aardId.intValue() != 0) {
			brief.setUit_aard_id(aardId.toString());
		} else {
			brief.setUit_aard_id("2");
		}
		if (bestekId != null && bestekId.intValue() != 0) {
			brief.setBestek_id(bestekId);
		}
        
		brief.setAuteur_id(auteurId);
		brief.setBetreft(betreft);
		brief.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
		briefDao.saveOrUpdate(brief);
		return brief.getBrief_id();
	}

	@RequestMapping(value = "/briefService/saveDmsId", method = RequestMethod.GET)
	public @ResponseBody
	void saveDmsId(@RequestParam("briefId") String briefId, @RequestParam("dmsId") String dmsId, @RequestParam("briefTypeId") Integer briefTypeId,
			@RequestParam("fileName") String fileName, @RequestParam("folderName") String folderName) throws Exception {

	    logger.info("saveDmsId voor briefId , briefTypeId, fileName, folderName : " + briefId + ", " + briefTypeId + ", " + fileName + ", " + folderName );
	    
	    if (folderName.startsWith("/")) {
	        folderName = folderName.substring(1);
	    }

        briefDao.updateBriefInfo(Integer.valueOf(briefId), briefTypeId, dmsId, fileName, folderName);
	}

    @RequestMapping(value = "/briefService/scanlocationfor", method = RequestMethod.GET)
    public @ResponseBody ScanDTO scanlocationfor(@RequestParam String code) throws Exception {
        logger.info("scanlocationfor code :" + code);
		return briefService.getscanlocationfor(code);
    }


//    @Deprecated
////	@RequestMapping(value = "/briefService/scanlocation", method = RequestMethod.GET)
//	public @ResponseBody
//	ScanDTO scanlocation(@RequestParam int briefId) throws Exception {
//
//        logger.info("scanlocation briefId :" + briefId);
//        
//		Brief scan = new Brief();
//		Brief brief = briefDao.get(briefId);
//
//		if (brief == null) {
//			throw new RuntimeException("Geen Brief gevonden voor briefId " + briefId);
//		}
//
//		BeanUtils.copyProperties(scan, brief);
//		scan.setDms_id(null);
//		scan.setDms_folder(null);
//		scan.setDms_filename(null);
//		scan.setBrief_id(null);
//		scan.setCategorie_id(5);
//		scan.setParent_brief_id(brief.getBrief_id());
//		scan.setUit_aard_id(2);
//		scan.setLtst_wzg_d(new Timestamp(System.currentTimeMillis()));
//
//		ScanDTO scanDto = new ScanDTO();
//		scanDto.setBriefId(brief.getBrief_id());
//
//		Dossier dossier = dossierService.getDossier(scan.getDossier_id());
//		String folderName = BriefService.getAlfrescoUrl() + dossier.getDossier_id().substring(0, 2) + "/" + dossier.getDossier_id() + "/"
//				+ briefDao.getBriefCategorie(scan.getCategorie_id()).get("brief_categorie_b");
//
//		scanDto.setDmsFolder(folderName);
//
//		return scanDto;
//
//	}

	@RequestMapping(value = "/briefService/uploadscanfull", method = RequestMethod.POST)
	public @ResponseBody
	ScanDTO uploadScanFull(@RequestBody ScanDTO dto) throws Exception {
        logger.info("uploadScanFull scanDTO :" + dto);
        dto.setStatus(false);
        dto = briefService.uploadscanfull(dto);
        return dto;
	}

	@RequestMapping(value = "/briefService/makescan", method = RequestMethod.GET)
	public @ResponseBody
	ScanDTO makescan(@RequestParam int briefId) throws Exception {
	    
        logger.info("makescan briefId :" + briefId);
        
		Brief brief = briefService.makeScan(briefId);

		ScanDTO scan = new ScanDTO();
		scan.setBriefId(brief.getBrief_id());

		Dossier dossier = dossierService.getDossier(brief.getDossier_id());
		
        String folderName = BriefService.getAlfrescoUrl() + dossier.getDossier_id().substring(0, 2) + "/" + dossier.getDossier_id() + "/Scans";
                
		scan.setDmsFolder(folderName);
		return scan;
	}

	@RequestMapping(value = "/briefService/uploadscan", method = RequestMethod.POST)
	public @ResponseBody
	ScanDTO uploadscan(@RequestBody ScanDTO dto) throws Exception {

        logger.info("uploadscan scanDTO :" + dto);
        if(StringUtils.isEmpty(dto.getDmsId())){
            dto.setStatus(false);
            dto.setErrorMsg("Dms id is empty voor brief "+dto.getBriefId());
            logger.error("Dms id is empty voor brief " +dto.getBriefId());
        }else {
            briefService.updateBriefAfterUpload(dto.getBriefId(), dto.getDmsId(), dto.getFileName());
            dto.setStatus(true);
        }
		return dto;

	}

}
