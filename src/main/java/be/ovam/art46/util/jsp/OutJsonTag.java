package be.ovam.art46.util.jsp;

import be.ovam.web.util.json.JsonObjectMapperFactory;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class OutJsonTag extends SimpleTagSupport {

	private String object;

	public void setObject(Object object) {
		this.object = (String) object;
	}

	public void doTag() throws JspException, IOException {
		PageContext context =  (PageContext) getJspContext();
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		
		JspWriter out = context.getOut();
		Object value = request.getAttribute(object);
		if (value != null) {
			String jsonStr = JsonObjectMapperFactory.getMapper().writeValueAsString(value);
			out.println(jsonStr);
		} else {
			out.println("null");
		}
		
	}

}
