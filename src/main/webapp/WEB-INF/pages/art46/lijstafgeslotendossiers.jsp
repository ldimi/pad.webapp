<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<display:table id="dossier"  name="sessionScope.lijst"  requestURI="/lijstafgeslotendossiers.do" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' class="lijst" >
    <display:column property="gemeente_b" title="Gemeente" class="left" sortable="true"/>
    <display:column property="dossier_b_ivs" title="DossiernaamIVS"
        href="dossierdetailsArt46.do?selectedtab=Basis&crudAction=read" paramId="id" paramProperty="id"  sortable="true" />
    <display:column property="dossier_nr" title="Dossiernr IVS" class="center" sortable="true"/>
    <display:column title="Dossiernr BB" sortProperty="dossier_b" class="center" sortable="true">
        <a href='dossierdetails.do?dossier_id_boa=<bean:write name="dossier" property="dossier_id"/>'>
            <bean:write name="dossier" property="dossier_id"/>
        </a>
    </display:column>
    <display:column property="eerste_pub_d" title="Eerste publicatie" class="center" sortable="true"/>
    <display:column property="aantal_dagen" title="Aantal dagen" class="center" sortable="true"/>
</display:table>



