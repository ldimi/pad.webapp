package be.ovam.art46.util.jsp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class OptionsTag extends SimpleTagSupport {

    private List items;
	private String itemValue = "value";
	private String itemLabel = "label";
	private Object selectedValue;


    public void doTag() throws JspException, IOException {
		PageContext context =  (PageContext) getJspContext();
		//HttpServletRequest request = (HttpServletRequest) context.getRequest();
		
		JspWriter out = context.getOut();
		
		String selectedValue_string = null;
		if (selectedValue != null) {
		    selectedValue_string = selectedValue.toString();
		}
		
		
		for (Object object : items) {
			Map item = (Map) object;
			StringWriter sw = new StringWriter();
			sw.append("<option value=\"");
			sw.append(item.get(itemValue).toString());
			sw.append("\" " );
			
			if (selectedValue_string != null) {
	            Object value = item.get(itemValue);
	            if (value != null && selectedValue_string.equals(value.toString())) {
	                sw.append("selected");
	            }
			}
			sw.append(" >");
			sw.append(item.get(itemLabel).toString());
			sw.append("</option>");
			
			out.println(sw.toString());
		}
		
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public Object getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(Object selectedValue) {
		this.selectedValue = selectedValue;
	}

	
	
	
}
