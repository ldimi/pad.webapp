package be.ovam.art46.struts.action;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;

public class PadDispatchAction extends DispatchAction {
	
	protected void saveErrors_(Exception e, HttpServletRequest request) {
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionError("error.db", e.getMessage()));	
		saveMessages(request, messages);			
	}

}
