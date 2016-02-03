<%@ page language="java" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<logic:notEmpty name="dossierart46form" property="dossier_id" scope="session">
    <table>
        <tr>
            <td>
                <display:table id="archief" name="requestScope.dossierdetailsarchieflijst" requestURI="/dossierdetails.do" class="lijst width980">
                    <display:column title="IVS nr." property="archief_id" class="center"/>
                    <display:column title="Log. nr." property="archief_nr"/>
                    <display:column title="Dienst" sortProperty="dienst_id" class="center">
                        <html:select name="archief" property="dienst_id" styleClass="input" disabled="true">
                            <html:optionsCollection name="DDH" property="diensten" label="dienst_b" value="dienst_id" />
                        </html:select>
                    </display:column>
                    <display:column title="Datum" property="archief_d"  decorator="be.ovam.art46.decorator.DateDecorator"/>
                    <display:column title="Plaats" property="plaats" />
                    <display:column title="Inhoud" property="archief_b" />
                    <display:column>
                        <img src='<html:rewrite page="/"/>/resources/images/edit.gif' onclick="popupWindow('archief.do?crudAction=read&popup=yes&archief_id=${archief.archief_id}&dossier_id=${dossierart46form.id}', 'Archief');"/>
                    </display:column>
                    <display:setProperty name="basic.msg.empty_list_row" value=""/>
                </display:table>
            </td>
            <td align="left" valign="top">
                <logic:present role="adminArt46,adminIVS,adminBOA">
                    <img src='<html:rewrite page="/"/>/resources/images/add.gif' onclick="popupWindow('archief.do?crudAction=view&popup=yes&archief_id=0&dossier_id=${dossierart46form.id}', 'Archief');"/>
                </logic:present>
            </td>
        </tr>
    </table>
</logic:notEmpty>
<logic:empty name="dossierart46form" property="dossier_id" scope="session">
    Enkel aan een IVS dossier kan er een archief gekoppeld worden.
</logic:empty>