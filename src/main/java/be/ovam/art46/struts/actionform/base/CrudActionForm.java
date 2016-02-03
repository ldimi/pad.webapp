package be.ovam.art46.struts.actionform.base;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public abstract class CrudActionForm extends ValidatorForm {
		
	public String crudAction;	
	public String forwarddelete;
	public String forward;
	private boolean useForward;
	private boolean clear;
	
	public boolean isClear() {
		return clear;
	}
	public void setClear(boolean clear) {
		this.clear = clear;
	}
	public String getCrudAction() {
		return crudAction;
	}
	public void setCrudAction(String operation) {
		this.crudAction = operation;
	}
	
	abstract public Class getObjectClass(); 
	abstract public  Serializable getCrudId();
	abstract public void clear();
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		if ("save".equals(getCrudAction())) {
			return super.validate(mapping, request);
		}
		return new ActionErrors();
	}
	public String getForwarddelete() {
		return forwarddelete;
	}
	public void setForwarddelete(String forwarddelete) {
		this.forwarddelete = forwarddelete;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public boolean isUseForward() {
		return useForward;
	}
	public void setUseForward(boolean useForward) {
		this.useForward = useForward;
	}
}
