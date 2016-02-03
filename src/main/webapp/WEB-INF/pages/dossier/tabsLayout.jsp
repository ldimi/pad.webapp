<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tilesx:useAttribute name="tab_selected" classname="java.lang.String" />
<tilesx:useAttribute name="tab_body" classname="java.lang.String" />

<div style="height: 40px; width: 1000px;">

    <div class='tab <%=("Basis".equals(tab_selected) ? "selected" : "")%>'>
        <a class="sm" href="/pad/s/dossier/${dossier_id}/basis" >Basis</a>
    </div>

    <c:if test="${dossierart46form.id != null}" >

        <c:if test="${dossierart46form.dossier_type != 'X'}" >
            <div class='tab <%=("Contact".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/contact" >Contact</a>
            </div>
        </c:if>

        <c:if test="${dossierart46form.dossier_type == 'B'}" >
            <div class='tab <%=("JD".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/jd" >JD</a>
            </div>
            <div class='tab <%=("Opname".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/opname" >Opname</a>
            </div>
            <div class='tab <%=("Publicatie".equals(tab_selected) ? "selected" : "")%>'>
                <a class="sm" href="/pad/s/dossier/${dossier_id}/publicatie" >Publicatie</a>
            </div>
        </c:if>



        <logic:present role="adminIVS,adminJD,adminBoek">
            <div class='tab <%=("Brieven".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/brieven" >Brieven</a>
            </div>
            <div class='tab <%=("Planning".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/planning" >Planning</a>
            </div>

            <div class='tab <%=("Bestek".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/bestek" >Bestek</a>
            </div>
            <div class='tab <%=("Facturen".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/facturen" >Facturen</a>
            </div>
        </logic:present>

        <div class='tab <%=("Archief".equals(tab_selected) ? "selected" : "")%>' >
            <a class="sm" href="/pad/s/dossier/${dossier_id}/archief" >Archief</a>
        </div>


        <c:if test="${dossierart46form.dossier_type != 'X'}" >
            <div class='tab <%=("Projectfiche".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/projectfiche" >Projectfiche</a>
            </div>
        </c:if>

        <logic:present role="adminIVS">
            <div class='tab <%=("Toegang webloket".equals(tab_selected) ? "selected" : "")%>' >
                <a class="sm" href="/pad/s/dossier/${dossier_id}/toegangwebloket" >Toegang webloket</a>
            </div>
        </logic:present>

    </c:if>

</div>
<br/>
<div style="border-left: 1px solid;
            border-top: 1px solid;
            position: absolute;
            top: 40px;
            left: 0px;
            right: 1px;
            bottom: 0px;
            padding: 2px;
            background-color: white;">
            <tiles:insertAttribute name="tab_body" />
</div>
<%--

--%>