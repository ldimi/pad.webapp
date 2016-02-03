package be.ovam.art46.struts.action;

import be.ovam.art46.model.Query;
import be.ovam.art46.service.BaseService;
import be.ovam.art46.struts.action.base.ZoekAction;
import be.ovam.art46.struts.actionform.QueryForm;
import be.ovam.art46.struts.plugin.LoadPlugin;
import be.ovam.util.mybatis.SqlSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryExecuteAction extends ZoekAction {
	
	private BaseService service = (BaseService) LoadPlugin.applicationContext.getBean("baseService");
    private SqlSession sqlSession = (SqlSession) LoadPlugin.applicationContext.getBean("sqlSession");
	
	public Object fecthResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Query query = (Query) service.getObject(Query.class,Integer.valueOf(((QueryForm) form).getQuery_id()));
        String query_b = query.getQuery_b();
	    String sql = query.getQuery_l();

	    Map<String, String> params = new HashMap<String, String>();
	    params.put("sql", sql);
        List results = sqlSession.selectList("be.ovam.art46.mappers.QueryMapper.getResultForQuery", params);

        request.getSession().setAttribute("query_b", query_b);	
        request.getSession().setAttribute("query_aantal_resultaten", results.size());
        
	    return results;
	}
}
