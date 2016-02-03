package be.ovam.art46.struts.action.bestek;

import be.ovam.art46.struts.plugin.LoadPlugin;

import be.ovam.util.mybatis.SqlSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class AdresSaveAction extends Action {

    private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse respons) throws Exception {

        Map<String, String> params = new HashMap<String, String>();
        params.put("bestekId", request.getParameter("bestek_id"));
        params.put("adresId", request.getParameter("adres_id"));
        
        String contact_id = request.getParameter("contact_id");
        if ("0".equals(contact_id) || "".equals(contact_id)) {
            contact_id = null;
        }
        params.put("contactId", contact_id);

        sqlSession.insert("be.ovam.art46.mappers.BestekAdresMapper.insertBestekAdres", params);

        return mapping.findForward("success");
    }
}