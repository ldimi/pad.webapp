<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>
<br>
<logic:iterate id="kadasterdetail" name="kadasterdetailsopname" scope="request" length="1">
    Kadaster <bean:write name="kadasterdetail" property="kadaster_afd_b"/> <bean:write name="kadasterdetail" property="kadaster_id_sh"/>
</logic:iterate>
<br><br>
<html:form action="addremovedossierkadasters">
    <html:hidden property="kadaster_id"/>
    <html:hidden property="nextpage" value="success_kadasterdetail"/>
    <html:hidden property="nexterrorpage" value="error_kadasterdetail"/>
    <display:table id="dossierkadaster" name="requestScope.kadasterdetailsopname" export="true" requestURI="/kadasterdetails.do">
        <bean:define name="dossierkadaster" property="id" id="sdossier_id" type="java.lang.Integer"/>
        <display:column title="" class="center">
            <logic:notEqual name="dossierkadaster" property="goedkeuring_s" value="1">
                <input type='checkbox' name='dossierkadasters' value='<bean:write name="dossierkadaster" property="id"/>-<bean:write name="dossierkadaster" property="kadaster_id"/>'/>
            </logic:notEqual>
        </display:column>
        <display:column title="Artikel" property="artikel_b" />
        <display:column title="DossierId" property="dossier_id_boa" />
        <display:column title="DossierNaam" property="dossier_b" href="dossierdetailsArt46.do" paramId="id" paramProperty="id"/>
        <display:column title="OB" class="center">
            <html:checkbox styleClass="checkbox" name="dossierkadaster" property="onschuldige_eig_s" value="<%= sdossier_id.toString() %>" />
        </display:column>
        <display:column title="IG" class="center">
            <html:checkbox styleClass="checkbox" name="dossierkadaster" property="ingebreke_stel_s" value="<%= sdossier_id.toString() %>" />
        </display:column>





        <logic:present role="adminArt46,adminIVS,adminBOA,adminJD">
            <display:footer>
                <tr>
                    <td colspan='4'>
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
                        <html:submit styleClass="inputbtn" value="Wijzigingen opslaan" onclick="setAction(this.form,'dossierkadastertypeset.do')"/>
                    <td>
                </tr>
            </display:footer>
        </logic:present>
    </display:table>
</html:form>

