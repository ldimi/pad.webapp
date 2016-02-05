<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <th>
            Dossier zoeklijst .
        </th>
    </tr>
    <tr>
        <td>
            <display:table class="lijst" id="dossier" name="sessionScope.zoeklijst" export="true" requestURI="/s/dossier/lijst" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                <display:column title="Fusiegemeente" sortable="true" property="gemeente_b" />
                <display:column title="Adres" property="adres"/>

                <display:column property="dossier_b" title="Dossiernaam IVS" sortable="true" media="xml csv excel" />
                <display:column title="Dossiernaam IVS" sortable="true" sortProperty="dossier_b" class="center" media="html">
                    <a href="/pad/s/dossier/<bean:write name="dossier" property="id"/>/basis"/><bean:write name="dossier" property="dossier_b"/></a>
                </display:column>

                <display:column property="dossier_nr" title="Dossiernr IVS" sortable="true" media="xml csv excel" />
                <display:column title="Dossiernr IVS" sortable="true" sortProperty="dossier_nr" class="center" media="html">
                    <a href="/pad/s/dossier/<bean:write name="dossier" property="id"/>/basis"/><bean:write name="dossier" property="dossier_nr"/></a>
                </display:column>
                <display:column property="dossier_type" sortable="true" class="center"/>

                <display:column property="sap_project_nr" title="SAP nr" class="center" sortable="false"/>
                <display:column property="programma_code" title="Programma" class="center" sortable="true"/>
                <display:column property="rechtsgrond_code" title="Rechtsgrond" class="center" sortable="true"/>
                <display:column property="doelgroep_type_b" title="Doelgroep" class="center" sortable="true"/>

                <display:column property="doss_hdr_naam" title="Dossierhouder IVS" class="center" sortable="true"/>

                <display:column property="dossier_b_boa" title="Dossiernaam IVS" sortable="true" media="xml csv excel" />
                <display:column title="Dossiernaam BB" sortable="true" sortProperty="dossier_b_boa" class="center" media="html">
                    <a href="/pad/s/dossier/<bean:write name="dossier" property="id"/>/basis"/><bean:write name="dossier" property="dossier_b_boa"/></a>
                </display:column>

                <display:column property="dossier_id_boa" title="Dossiernr BB" class="center" sortable="true"/>
                <display:column title="Art46" sortProperty="lijst_art46" class="center" sortable="true" media="html">
                    <html:checkbox name="dossier" property="lijst_art46" value="1" styleClass="checkbox"/>
                </display:column>
                <display:column property="lijst_art46" title="Art46" media="xml csv excel"/>
                <logic:notEmpty name="searchform" scope="session">
                    <logic:equal name="searchform" property="searchFlag" scope="session" value="dossiernieuw">
                        <display:column>
                            <logic:match name="dossier" property="dossier_nr" value="_">
                                <a href="javascript:loadurl('<html:rewrite action="stopsearch"/>?forwardURL=', '/dossierdetailsArt46.do?id=<bean:write name="dossier" property="id"/>', top.opener)">
                                    <html:img border="0" page="/resources/images/add.gif" title="Toevoegen dossier"/>
                                </a>
                            </logic:match>
                        </display:column>
                    </logic:equal>
                </logic:notEmpty>
            </display:table>
        </td>
    </tr>
</table>