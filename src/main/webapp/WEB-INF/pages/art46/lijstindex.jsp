<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<table>
    <tr>
        <td align="left">
            <li><html:link action="lijstopnameselect">Opname</html:link></li>
            <li><html:link action="lijstvoorgoedkselect">Voor Goedkeuring</html:link></li>
            <li><html:link action="lijstnagoedkselect">Na Goedkeuring</html:link></li>
            <li><html:link action="lijstnapubselect">Na publicatie</html:link></li>
            <li><html:link action="lijsthistoriek">Overzicht publicatie</html:link></li>
            <li><html:link action="lijstenoverzicht">Lijsten</html:link></li>
            <li><html:link action="lijstafgeslotendossiers">Afgesloten dossiers</html:link></li>
        </td>
    </tr>
</table>