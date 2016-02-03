package be.ovam.art46.struts.action;

import be.ovam.art46.struts.actionform.DossierArt46Form;
import java.util.Map;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionRedirect;

public class DossierArt46LoadAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
                
        ActionRedirect redirect = new ActionRedirect("/s/dossier");
        
        Map pmap =  request.getParameterMap();
        for (Object key : pmap.keySet()) {
            String skey = (String) key;
            Object val = pmap.get(skey);
            redirect.addParameter(skey, ((String[]) val)[0]);
        }

        return redirect;
 	}	
}
