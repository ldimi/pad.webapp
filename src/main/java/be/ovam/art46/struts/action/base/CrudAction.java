package be.ovam.art46.struts.action.base;

import be.ovam.art46.service.BaseService;
import be.ovam.art46.struts.actionform.base.CrudActionForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class CrudAction extends Action {

	private BaseService service = (BaseService) LoadPlugin.applicationContext.getBean("baseService");
    private static Log log = LogFactory.getLog(CrudAction.class);
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		CrudActionForm crudForm = (CrudActionForm) form;		
		try {	
			if ("save".equals(crudForm.getCrudAction())) {
				Object obj = crudForm.getObjectClass().newInstance();
				LocaleBeanUtils.copyProperties(obj,crudForm);
				getService().saveObject(obj);
				LocaleBeanUtils.copyProperties(crudForm,obj);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.db." + crudForm.getCrudAction(), crudForm.getObjectClass().getName()));
			}
			else if ("read".equals(crudForm.getCrudAction())) {	
				Class clazz = crudForm.getObjectClass();
				Serializable id = crudForm.getCrudId();
				crudForm.clear();
				Object object = getService().getObject(clazz, id);
				LocaleBeanUtils.copyProperties(form, object);
			} 
			else if ("delete".equals(crudForm.getCrudAction())) {
				Object obj = crudForm.getObjectClass().newInstance();
				LocaleBeanUtils.copyProperties(obj, crudForm);
				getService().deleteObject(obj);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.db." + crudForm.getCrudAction(), crudForm.getObjectClass().getName()));
			}
			if (crudForm.isClear()) {
				crudForm.clear();
			}
		} catch (Exception e) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.db", e));	
			log.error(e.getMessage()+e,e);
		} 
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			if ("delete".equals(crudForm.getCrudAction())) {
				return mapping.findForward("errordelete");
			}
			return mapping.findForward("error");
		}
		saveMessages(request, messages);
		if (crudForm.isUseForward()) {
			String forward = crudForm.getForward();
			crudForm.setForward(null);
			crudForm.setUseForward(false);
			return mapping.findForward(forward);
		}
		return mapping.findForward(crudForm.getCrudAction());	 	 		
	}	
	
	public BaseService getService() {
		return service;
	}
}
