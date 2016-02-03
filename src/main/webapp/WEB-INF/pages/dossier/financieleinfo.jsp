<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>


<%--
<html:hidden property="crudAction" value="save"/>
<html:hidden property="selected" value="16"/>
--%>
<table>

    <form action="s/dossier/financieel/save" method="post">
        <input type="text" name="id" value="${dossier.id}" class="hidden" />
        <tr>
            <td>
                <strong>
                    <bean:write name="dossier" property="dossier_b_l" />
                </strong>
            </td>
            <td style="text-align: right;">
                <a href='http://dgs.ovam.be:8080/ovam/applications/brieven_ivs/project_fiche.odt?id=<bean:write name="dossier" property="id"/>&returnDocument=true&format=odt'>
                    <img src='<html:rewrite page="/"/>/resources/images/copy.gif' border="0" title="Exporteren"/>
                </a>
            </td>
        </tr>
        <tr>
            <th colspan="2">
                Technische/Juridisch/Administratieve omschrijving
            </th>
        </tr>
        <tr>
            <td valign='top'  colspan="2">
                <textarea name="financiele_info" style="width: 100%" rows="10" >${dossier.financiele_info}</textarea>
            </td>
        </tr>

        <tr>
            <td align='right' colspan="2" >
                <logic:present role="adminIVS,adminBOA">
                    <html:submit value="Wijzigingen opslaan" styleClass="inputbtn"/>
                </logic:present>
            </td>
        </tr>

    </form>

    <tr>
        <td  colspan="2">
            <div class="planning">
                <strong>Afspraken</strong>
                <table>
                    <tr>
                        <td>
                            <logic:notEmpty name="dossierafspraken" scope="request">
                                <display:table id="afspraak" name="requestScope.dossierafspraken" class="lijst width980">
                                    <display:column property="datum" title="Datum" decorator="be.ovam.art46.decorator.DateDecorator" style="text-align: center;"/>
                                    <display:column property="omschrijving" title="Omschrijving" />
                                    <display:column style="text-align: center;">
                                        <img src='<html:rewrite page="/"/>/resources/images/edit.gif' alt="Afspraak wijzigen" title="Afspraak wijzigen" onclick="popupWindow('<html:rewrite action="dossierAfspraak"/>?crudAction=read&popup=yes&id=<bean:write name="afspraak" property="id"/>','Afspraak')"/>
                                    </display:column>
                                    <display:column style="text-align: center;">
                                        <a href='dossierAfspraak.do?crudAction=delete&id=<bean:write name="afspraak" property="id"/>'>
                                                <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen"/>
                                        </a>
                                    </display:column>
                                    <display:setProperty name="basic.msg.empty_list_row" value=""/>
                                </display:table>
                            </logic:notEmpty>
                        </td>
                        <td valign="top">
                            <img src='<html:rewrite page="/"/>/resources/images/add.gif' alt="Afspraak toevoegen" title="Afspraak toevoegen" onclick="popupWindow('<html:rewrite action="dossierAfspraak"/>?crudAction=view&popup=yes&id=0&dossier_id=<bean:write name="dossierart46form" property="id"/>','Afspraak')"/>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>

    <logic:notEmpty name="dossierart46form" property="dossier_id_boa">
        <tr>
            <td  colspan="2">
                <div class="planning">
                    <strong>OverdrachtsFiches</strong>
                    <table>
                        <tr>
                            <td>
                                <display:table id="fiche" name="requestScope.overdrachtsFiches" class="lijst width980">
                                    <display:column property="omschrijving" />
                                    <display:column>
                                        <a href='<bean:write name="fiche" property="dms_edit_url"/>' >
                                            <html:img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
                                        </a>
                                    </display:column>

                                    <display:setProperty name="basic.msg.empty_list_row" value=""/>
                                </display:table>
                            </td>
                            <td valign="top">
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </logic:notEmpty>

</table>
