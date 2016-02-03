<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>


<html:form action="addremovedossierkadasters">
    <html:hidden property="selected" value="4"/>
    <html:hidden property="dossier_id" value="${dossierart46form.id}"/>
    <html:hidden property="nextpage" value="success_dossierdetail"/>
    <html:hidden property="nexterrorpage" value="error_dossierdetail"/>

    <logic:present name="dossierdetailsopname" scope="request">
        <logic:notEmpty name="dossierdetailsopname" scope="request">
            <display:table id="dossierkadaster" name="requestScope.dossierdetailsopname" defaultsort="1" export="true" requestURI="dossierdetails.do?selectedtab=Opname" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                <bean:define name="dossierkadaster" property="kadaster_id" id="skadaster_id" type="java.lang.String"/>
                <display:column titleKey="empty" class="center" media="html">
                    <logic:notEqual name="dossierkadaster" property="goedkeuring_s" value="1">
                        <input type='checkbox' name='dossierkadasters' value='<bean:write name="dossierkadaster" property="id"/>-<bean:write name="dossierkadaster" property="kadaster_id"/>'/>
                    </logic:notEqual>
                </display:column>
                <display:column property="artikel_b" titleKey="dossier.opname.artikel" class="center" sortable="true"/>
                <display:column titleKey="dossier.opname.kadaster" class="center" media="html">
                    <a href='<html:rewrite href="kadasterdetails.do"/>?kadaster_id=<bean:write name="dossierkadaster" property="kadaster_id"/>'>
                            <bean:write name="dossierkadaster" property="kadaster_afd_b"/>
                            <bean:write name="dossierkadaster" property="kadaster_id_sh"/>
                    </a>
                </display:column>
                <display:column property="kadaster_id" titleKey="dossier.opname.kadaster" media="csv excel xml pdf"/>
                <display:column titleKey="dossier.opname.ob" class="center" media="html">
                    <html:checkbox styleClass="checkbox" name="dossierkadaster" property="onschuldige_eig_s" value="<%= skadaster_id%>" />
                </display:column>
                <display:column titleKey="dossier.opname.ob" class="center" media="csv excel xml pdf">
                    <logic:equal name="dossierkadaster" property="onschuldige_eig_s" value="<%= skadaster_id%>">
                        1
                    </logic:equal>
                </display:column>
                <display:column titleKey="dossier.opname.ig" class="center" media="html">
                    <html:checkbox styleClass="checkbox" name="dossierkadaster" property="ingebreke_stel_s" value="<%= skadaster_id%>" />
                </display:column>
                <display:column titleKey="dossier.opname.ig" class="center" media="csv excel xml pdf">
                    <logic:equal name="dossierkadaster" property="ingebreke_stel_s" value="<%= skadaster_id%>">
                        1
                    </logic:equal>
                </display:column>
                <logic:present role="adminArt46,adminIVS,adminBOA,adminJD">
                    <display:footer>
                        <tr>
                            <td colspan='3'>
                                <html:select property="artikelid" styleClass="input">
                                    <html:option value="0">&nbsp;</html:option>
                                    <html:optionsCollection name="DDH" property="artikels" label="artikel_b" value="artikel_id"/>
                                </html:select>
                                <logic:present role="adminArt46,adminIVS,adminBOA,adminJD">
                                    <html:submit styleClass="inputbtn" value=">>" title="Percelen voorstellen voor opname op lijst Art46"/>
                                </logic:present>
                                <input type="button" value="Alle percelen selecteren" onClick="checkAll(this.form.dossierkadasters)" class="inputbtn"/>
                            </td>
                            <td colspan='4' algin='center'>
                                <html:submit styleClass="inputbtn" value="Wijzigingen opslaan" title="Opslaan wijzigingen ivm OB en IG" onclick="setAction(this.form,'dossierkadastertypeset.do')"/>
                            <td>
                    </display:footer>
                </logic:present>
        <%--
        --%>
            </display:table>
        </logic:notEmpty>
    </logic:present>
</html:form>