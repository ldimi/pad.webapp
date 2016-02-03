<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="custom" uri="/tags/custom" %>

<logic:empty name="bestekschuldvorderinglijst" scope="request">
    <div style="margin: 20px;">
        geen schuldvorderingen
    </div>
</logic:empty>

<logic:notEmpty name="bestekschuldvorderinglijst" scope="request">

    <display:table class="planning" id="schuldvordering" name="requestScope.bestekschuldvorderinglijst" requestURI="/pad/s/bestek/${bestekId}/schuldvorderingen/">
        <display:column>
            <img src='<html:rewrite page="/"/>/resources/images/edit.gif' alt="Aanpassen schuldvordering" onclick="editSchuldVorderingDialog.show(${schuldvordering.vordering_id});"/>
        </display:column>
        <display:column title="Vordering_nr"  sortable="true" >
            <c:choose>
                <c:when test="${empty schuldvordering.brief_id}">
                    <a href="/pad/s/bestek/${bestekId}/aanvraagSchuldvordering/${schuldvordering.aanvr_schuldvordering_id}">${schuldvordering.schuldvordering_nr}</a>
                </c:when>
                <c:otherwise>
                    ${schuldvordering.schuldvordering_nr}
                </c:otherwise>
            </c:choose>
        </display:column>

        <display:column title="Brief nr" sortable="true" >
            <a href="briefdetails.do?brief_id=${schuldvordering.brief_id}">${schuldvordering.brief_nr}</a>
            <c:if test="${! empty schuldvordering.brief_dms_filename}">
                <a href='<%=System.getProperty("ovam.dms.webdrive.base") %>${schuldvordering.brief_dms_folder}/${schuldvordering.brief_dms_filename}' target="_blank">
                    <html:img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Antwoord bekijken" title="Antwoord bekijken"/>
                </a>
            </c:if>
        </display:column>

        <display:column property="deelopdracht_b" title="Deelopdracht"  sortable="true" />
        <display:column property="beheerder" title="Beheerder"  sortable="true" />

        <display:column property="vordering_d" decorator="be.ovam.art46.decorator.DateDecorator" title="Vordering" sortable="true"/>
        <display:column property="betaal_d" decorator="be.ovam.art46.decorator.DateDecorator" title="Betaling" sortable="true"/>
        <display:column property="vordering_bedrag" title="Bedrag" sortable="true" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator"/>

        <display:column title="Goedgekeurd bedrag" class="number">
            <c:if test="${schuldvordering.afgekeurd_jn == 'J' }">
                <span>AFGEKEURD</span>
            </c:if>
            <c:if test="${schuldvordering.afgekeurd_jn == 'N'}">
                <span>  <fmt:formatNumber value="${schuldvordering.goedkeuring_bedrag}" currencySymbol="&euro;" type="currency" maxFractionDigits="2" minFractionDigits="2"/> </span>
            </c:if>
        </display:column>
        <display:column property="status" title="Status" style="text-align: center;" />


        <c:if test="${empty schuldvordering.antw_dms_folder}">
            <display:column style="text-align: center;">
                <a href="/pad/s/bestek/${bestekId}/schuldvordering/draftSchuldvordering-${schuldvordering.vordering_id}.pdf" target="_blank" >
                    draft
                </a>
            </display:column>
        </c:if>
        <c:if test="${!empty schuldvordering.antw_dms_folder}">
            <display:column style="text-align: center;">
                <a href='<%=System.getProperty("ovam.dms.webdrive.base") %>${schuldvordering.antw_dms_folder}/${schuldvordering.antw_dms_filename}' target="_blank">
                    <html:img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Antwoord bekijken" title="Antwoord bekijken"/>
                </a>
             </display:column>
        </c:if>

        <logic:present role="boekhouding">
            <c:if test="${!empty schuldvordering.wbs_nr}">
                <display:column style="text-align: center;">
                    <a href="/pad/s/bestek/${bestekId}/schuldvorderingen/verwijderWbs?vordering_id=${schuldvordering.vordering_id}" >
                        Verwijder Wbs
                    </a>
                </display:column>
            </c:if>
        </logic:present>
        <display:column style="text-align: center;">
            <c:if test="${!empty schuldvordering.brief_id &&
                          empty schuldvordering.initieel_acht_nr &&
                          schuldvordering.afgekeurd_jn == 'N'}">
                <logic:present role="adminArt46,adminIVS">
                    <a href="/pad/s/bestek/${bestekId}/schuldvorderingen/verwijder?vordering_id=${schuldvordering.vordering_id}" >
                        <img src='<html:rewrite page="/"/>/resources/images/delete.gif' title="Verwijderen"/>
                    </a>
                </logic:present>
            </c:if>
        </display:column>

        <display:setProperty name="basic.msg.empty_list_row" value=""/>
    </display:table>
</logic:notEmpty>

<tiles:insert page="../schuldvordering/editSchuldVorderingDialog.jsp" />
<tiles:insert page="../schuldvordering/maakInSapDialog.jsp" />


<tiles:insert definition="laadJS" />

<script type="text/javascript">
    _G_.vordering_id = <custom:outJson object="vordering_id" />;
    _G_.dms_webdrive_base = '<%=System.getProperty("ovam.dms.webdrive.base") %>'
    laadBacking('schuldvordering/schuldvorderinglijstBacking');
</script>


