<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <th>
            Dossier zoeklijst ...
        </th>
    </tr>
    <tr>
        <td>
            <display:table id="dossier" name="sessionScope.zoeklijst" requestURI="/dossierart46zoekresultview.do" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >

                <display:column property="gemeente_b" title="Fusiegemeente" sortable="true" />
                <display:column property="adres" title="Adres"/>
                <display:column property="dossier_b" title="Dossiernaam IVS" sortable="true"/>
                <display:column property="dossier_id" title="Dossiernr IVS" sortable="true" media="xml csv excel" />
                <display:column title="Dossiernr IVS" sortable="true" sortProperty="dossier_id" class="center" media="html" >
                    <logic:equal parameter="popup" value="yes">
                        <bean:write name="dossier" property="dossier_id"/>
                    </logic:equal>
                    <logic:notEqual parameter="popup" value="yes">
                        <html:link action="dossierdetailsArt46.do?selectedtab=Basis" paramId="id" paramName="dossier" paramProperty="id"><bean:write name="dossier" property="dossier_id"/></html:link>
                    </logic:notEqual>
                </display:column>
                <display:column property="dossier_type" sortable="true" class="center"/>
                <display:column property="sap_project_nr" title="SAP nr" class="center" sortable="false"/>
                <display:column property="programma_code" title="Programma" class="center" sortable="true"/>
                <display:column property="rechtsgrond_code" title="Rechtsgrond" class="center" sortable="true"/>
                <display:column property="doss_hdr_naam" title="Dossierhouder IVS" class="center" sortable="true"/>
                <display:column property="dossier_b_boa" title="Dossiernaam BB" sortable="true" />
                <display:column property="dossier_id_boa" title="Dossiernr BB" class="center" sortable="true"/>
                <display:column title="Art46" sortProperty="lijst_art46" class="center" sortable="true" media="html">
                    <html:checkbox name="dossier" property="lijst_art46" value="1" styleClass="checkbox"/>
                </display:column>
                <display:column property="lijst_art46" title="Art46" media="xml csv excel"/>
                <logic:notEmpty name="searchform" scope="session">
                    <logic:equal name="searchform" property="searchFlag" scope="session" value="briefdossier">
                        <display:column>
                            <a href='javascript:pad_load("<html:rewrite action="briefloaddossier"/>?dossier_id=<bean:write name="dossier" property="id"/>",top.opener)'>
                                <html:img border="0" page="/resources/images/add.gif" title="Toevoegen dossier aan brief"/>
                            </a>
                        </display:column>
                    </logic:equal>
                </logic:notEmpty>
            </display:table>
        </td>
    </tr>
</table>
