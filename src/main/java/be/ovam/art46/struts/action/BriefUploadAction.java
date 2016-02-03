package be.ovam.art46.struts.action;

import be.ovam.art46.model.User;
import be.ovam.art46.service.BriefService;
import be.ovam.art46.struts.action.base.Action;
import be.ovam.art46.struts.actionform.BriefForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.art46.util.ZipEntry;
import be.ovam.art46.util.ZipUtil;
import be.ovam.pad.model.Brief;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;


public class BriefUploadAction extends Action {
		
	private final BriefService service =  (BriefService) LoadPlugin.applicationContext.getBean("briefService");	
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response,
		ActionErrors errors) throws Exception {
		ActionMessages messages = new ActionMessages();
		BriefForm briefForm = (BriefForm) form;
		briefForm.setAuteur_id(((User) request.getSession().getAttribute("user") ).getUser_id());
		if (Arrays.asList( new String[] {"6", "18", "21","22","23"}).contains(briefForm.getCategorie_id())) { 
			if (briefForm.getFile().getFileName().endsWith(".zip")) {
				ZipUtil zipUtil = new ZipUtil();
				List<ZipEntry> entries = zipUtil.getFilesFromZip(new ByteArrayInputStream(briefForm.getFile().getFileData()));
				for (ZipEntry entry : entries) {
					Brief zipEntryBrief = service.makeDocument(briefForm.getDossier_id(), Integer.valueOf(briefForm.getCategorie_id()), briefForm.getAuteur_id());
					service.uploadBrief(zipEntryBrief.getBrief_id(), entry.getContent(), entry.getFileName());				
				}
			} else {
				Brief brief = service.makeDocument(briefForm.getDossier_id(), Integer.valueOf(briefForm.getCategorie_id()),briefForm.getAuteur_id());
				service.uploadBrief(brief.getBrief_id(), briefForm.getFile().getFileData(), briefForm.getFile().getFileName());	
			}				
		} else if (StringUtils.isNotBlank(briefForm.getParent_brief_id())) {
			service.uploadScan(Integer.valueOf(briefForm.getParent_brief_id()), briefForm.getFile().getFileData(), briefForm.getFile().getFileName());				
		} else {
			service.uploadBrief(Integer.valueOf(briefForm.getBrief_id()), briefForm.getFile().getFileData(), briefForm.getFile().getFileName());
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("upload.ok"));
		saveMessages(request, messages);
		return mapping.findForward("success");
	}

}
