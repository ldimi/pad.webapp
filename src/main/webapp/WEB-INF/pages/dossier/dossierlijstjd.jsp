<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@ taglib uri="/tags/display-tags" prefix="display"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<table>
    <tr>
        <td>
            <logic:notEmpty name="dossiers" scope="request">
                <display:table id="dossier" name="requestScope.dossiers" class="lijst width980">
                    <display:column property="dossier_id_jd" title="Dossier nummer" />
                    <display:column property="dossier_b" title="Titel" class="center" />
                    <display:column title="Dossierhouder" class="center">
                        <html:select name="dossier" property="doss_hdr_id"
                            styleClass="input" disabled="true">
                            <html:optionsCollection name="DDH" property="ambtenarenJD"
                                label="ambtenaar_b" value="ambtenaar_id" />
                        </html:select>
                    </display:column>
                    <display:column property="commentaar" title="Commentaar"  class="center" />
                    <display:column property="stand_terugvordering" title="Stand Terugvordering"  class="center" />

                    <%--
                    TODO : Eventueel een detailscherm om gekoppelde acties te bekijken bouwen.
                       (editeren is waarschijnlijk niet meer nodig, laatste actie dateert van november 2013)
                       voortaan kan je naar jurisch dossier springen indien gekoppeld.
                    --%>


                    <display:setProperty name="basic.msg.empty_list_row" value="" />
                </display:table>
            </logic:notEmpty>
        </td>
        <td valign="top">
            <logic:present role="adminJD">
                <img src='<html:rewrite page="/"/>/resources/images/add.gif'
                    alt="Dossier toevoegen" title="Dossier toevoegen"
                    onclick="popupWindow('<html:rewrite action="dossierjd"/>?crudAction=view&popup=yes&dossier_id=<bean:write name="dossierart46form" property="id" scope="session"/>','DossierJD')" />
            </logic:present>
        </td>
    </tr>
</table>

<br/>
<br/>
<c:if test="${not empty juridischedossiers}">

    Gelinkte dossiers uit toepassing dossieropvolging juridische dienst (DOJUR):

    <table>

        <c:forEach var="juridischDossier" items="${juridischedossiers}">
            <tr>
                <td><a
                    href="http://toepassing.ovam.be/jd/toon_dossier.xhtml?dossierId=${juridischDossier.id}">
                        ${juridischDossier.nummer} </a></td>
                <td>${juridischDossier.naam}</td>
                <td>${juridischDossier.dossierhouder}</td>

            </tr>

        </c:forEach>

    </table>

</c:if>