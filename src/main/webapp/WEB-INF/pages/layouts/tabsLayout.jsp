<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<%--
  Tabs Layout .
  This layout allows to render several tiles in a tabs fashion.
  @param tabList A list of available tabs. We use MenuItem to carry data (name, body, icon, ...)
  @param selectedIndex Index of default selected tab
  @param parameterName Name of parameter carrying selected info in http request.
--%>

<%--
Use tiles attributes, and declare them as page java variable.
These attribute must be passed to the tile.
--%>

<tiles:useAttribute name="parameterName" classname="java.lang.String" />
<tiles:useAttribute name="action" classname="java.lang.String" />
<tiles:useAttribute name="tabList" classname="java.util.List" />

<%
System.out.println("parameterName: " + parameterName);

int index = 0; // Loop index
int selectedIndex = 0;
String selectedtab = request.getParameter(parameterName + "tab");

if ( selectedtab == null ) {
    System.out.println("selectedtab is leeg");
    try {
        selectedIndex = Integer.parseInt(request.getParameter( parameterName ));
        System.out.println("2. " + selectedIndex);
    } catch( java.lang.NumberFormatException ex ) {
        // bij problemen terugvallen op eerste tab
        selectedIndex = 0;
    }
    if( selectedIndex < 0 || selectedIndex >= tabList.size() ) selectedIndex = 0;
} else {
    // selectie op basis van 'selectedtab' naam.
    selectedIndex = 0;
    int i = 0;
    boolean found = false;
    while (i < tabList.size() && !found) {
        org.apache.struts.tiles.beans.MenuItem item = (org.apache.struts.tiles.beans.MenuItem) tabList.get(i);
        if (selectedtab.equals(item.getValue())) {
            selectedIndex = i;
            found = true;
        }
        i++;
    }
}

String selectedBody = ((org.apache.struts.tiles.beans.MenuItem)tabList.get(selectedIndex)).getLink(); // Selected body
%>

<table border="0"  cellspacing="0" cellpadding="0" width="1050" height="100%">
    <%-- Draw tabs --%>
    <tr style="height: 40px;">
        <td valign="bottom" class="nopadding">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <logic:iterate id="tab" name="tabList" type="org.apache.struts.tiles.beans.MenuItem" >
                        <% // compute href
                            String clazz = "";
                            String _tabName = org.apache.commons.lang3.StringEscapeUtils.escapeHtml3(tab.getValue());
                            String href = ((String )action).indexOf("?") != -1 ? action + "&" + parameterName + "tab=" + _tabName : action + "?" + parameterName + "tab=" + _tabName ;
                            String roles = tab.getIcon();
                            if( index == selectedIndex)
                              {
                              clazz = "selected";
                              } // enf if
                            index++;
                        %>
                        <% if (roles == null) { %>
                            <td align="center" valign="middle" class='tab <%= clazz %>' >
                                <a class="sm" href="<%=href%>" ><%=tab.getValue()%></a>
                            </td>
                        <% } else { %>
                            <logic:present role="<%=roles %>">
                                <td align="center" valign="middle" class='tab <%= clazz %>' >
                                    <a class="sm" href="<%=href%>" ><%=tab.getValue()%></a>
                                </td>
                            </logic:present>

                        <% } %>
                      <td style="border-bottom: 1px solid #000000;" class="nopadding"><html:img src="resources/images/space.gif" /></td>

                    </logic:iterate>
                    <td style="border-bottom: 1px solid #000000;" width="100%">&nbsp</td>
                </tr>
            </table>
        </td>
    </tr>



     <%-- Draw body --%>

    <%
        System.out.println("selectedBody: " + selectedBody);
    %>

    <tr>
      <td valign="top" class="nopadding">
        <table bgcolor="#fff" cellpadding="0" style="border: 1px solid #000000; border-top: 0px" width="100%" height="100%" >
            <tr>
                <td valign="top">
                    <tiles:insert name="<%=selectedBody%>" flush="true" />
                </td>
            </tr>
        </table>
      </td>
    </tr>
</table>

