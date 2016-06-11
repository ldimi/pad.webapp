<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tilesx:useAttribute name="tab_selected" classname="java.lang.String" />
<tilesx:useAttribute name="tab_body" classname="java.lang.String" />

<tilesx:useAttribute name="sub_tab_body" classname="java.lang.String" />
<tilesx:useAttribute name="sub_tab_selected" classname="java.lang.String" />

<div style="height: 40px; width: 1000px;">
    <div class='tab <%=("Basisgegevens".equals(tab_selected) ? "selected" : "")%>' >
        <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/basisgegevens/" >Basisgegevens <br/>${bestekNr}</a>
    </div>
    <c:if test="${!empty bestekDO.bestek_id}">
        <div class='tab <%=("Schuldvorderingen".equals(tab_selected) ? "selected" : "")%>'>
            <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/schuldvorderingen/" >Schuldvorderingen</a>
        </div>
        <div class='tab <%=("Acties".equals(tab_selected) ? "selected" : "")%>' >
            <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/acties/" >Acties</a>
        </div>
        <div class='tab <%=("Deelopdrachten".equals(tab_selected) ? "selected" : "")%>' >
            <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/deelopdrachten/" >Deelopdrachten</a>
        </div>
        <div class='tab <%=("Vastleggingen".equals(tab_selected) ? "selected" : "")%>' >
            <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/vastleggingen/" >Vastleggingen</a>
        </div>
        <div class='tab <%=("Opdrachthouders".equals(tab_selected) ? "selected" : "")%>'>
            <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/opdrachthouders/" >Opdrachthouders</a>
        </div>
        <div class='tab <%=("Meetstaat".equals(tab_selected) ? "selected" : "")%>' >
            <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/meetstaat/" >Meetstaat</a>
        </div>
        <div class='tab <%=("Planning".equals(tab_selected) ? "selected" : "")%>' >
            <a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/planning/" >Planning</a>
        </div>
        <div class="tab ">
            <a style="text-align: right" href="/pad/${dossierUrl}" >Dossier: ${dossierNr}</a>
        </div>
    </c:if>
</div>
<br/>
<div style="border-left: 1px solid;
            border-top: 1px solid;
            position: absolute;
            top: 40px;
            left: 0px;
            right: 0px;
            bottom: 0px;
            background-color: white;">
            <tiles:insertAttribute name="tab_body" >
                <tiles:putAttribute name="sub_tab_body" value="${sub_tab_body}" />
                <tiles:putAttribute name="sub_tab_selected" value="${sub_tab_selected}" />
            </tiles:insertAttribute>
</div>