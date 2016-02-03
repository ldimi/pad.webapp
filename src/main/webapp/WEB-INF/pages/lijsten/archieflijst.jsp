<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <td align="center">
            <table cellpadding='0' cellspacing='0'>
                <html:form action="archiefzoekresult" method="get">
                <tr valign='middle'>
                    <td valign='middle'>
                        <html:select property="doss_hdr_id" styleClass="input">
                            <html:option value="">Alle dossierhouders</html:option>
                            <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                        </html:select>
                    </td>
                    <td valign='middle'>
                        <html:submit styleClass="inputbtn">
                            <bean:message key="lijst.opname.button.ophalen" />
                        </html:submit>
                    </td >
                </tr>
                </html:form>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <logic:equal name="zoekaction" scope="session" value="/archieflijstview.do">
                <display:table class="lijst width1000" id="archief" name="sessionScope.zoeklijst" requestURI="/archieflijstview.do"  pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                    <display:column title="IVS. nr." property="archief_id"/>
                    <display:column title="Log. nr." property="archief_nr"/>
                    <display:column title="Dienst" sortProperty="dienst_id" class="center">
                        <html:select name="archief" property="dienst_id" styleClass="input" disabled="true">
                            <html:optionsCollection name="DDH" property="diensten" label="dienst_b" value="dienst_id" />
                        </html:select>
                    </display:column>
                    <display:column title="Dossierhouder">
                        <html:select name="archief" property="doss_hdr_id" styleClass="input" disabled="true">
                            <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                        </html:select>
                    </display:column>
                    <display:column title="Dossier" sortable="true" sortProperty="dossier_id" class="center" media="html">
                        <%---bean:write name="archief" property="dossier_type"/--%>
                        <logic:equal name="archief" property="dossier_type" value="A">
                            <html:link action="dossierdetailsArt46.do?selectedtab=Archief" paramId="id" paramName="archief" paramProperty="dossier_id"><bean:write name="archief" property="dossier_nr"/></html:link>
                        </logic:equal>
                        <logic:equal name="archief" property="dossier_type" value="B">
                            <html:link action="dossierdetailsArt46.do?selectedtab=Archief" paramId="id" paramName="archief" paramProperty="dossier_id"><bean:write name="archief" property="dossier_nr"/></html:link>
                        </logic:equal>
                        <logic:equal name="archief" property="dossier_type" value="X">
                            <bean:write name="archief" property="dossier_nr"/>
                        </logic:equal>
                    </display:column>
                    <display:column title="Datum" property="archief_d"  decorator="be.ovam.art46.decorator.DateDecorator"/>
                    <display:column title="Plaats" property="plaats" />
                    <display:column title="Inhoud" property="archief_b" />
                    <display:column>
                        <img src='<html:rewrite page="/"/>/resources/images/edit.gif' onclick="popupWindow('archief.do?crudAction=read&lijst=yes&popup=yes&archief_id=<bean:write name="archief" property="archief_id" />&dossier_id=<bean:write name="archief" property="dossier_id"/>', 'Archief');"/>
                    </display:column>
                    <display:setProperty name="basic.msg.empty_list_row" value=""/>
                </display:table>
            </logic:equal>
        </td>
    </tr>
</table>