<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <th>
            Dossierlijst
        </th>
    </tr>
    <tr>
        <td>
            <display:table class="lijst width1000" id="dossier" name="sessionScope.zoeklijst" requestURI="/indexview.do" export="true" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                <display:column property="gemeente_b" title="Fusiegemeente" sortable="true" media="html csv excel pdf xml"/>
                <display:column property="dossier_b" title="Titel" sortable="true" media="html csv excel pdf xml"/>

                <display:column title="Dossiernummer" sortable="true" sortProperty="dossier_nr" class="center" media="html">
                    <a href="/pad/s/dossier/<bean:write name="dossier" property="id"/>/basis"/><bean:write name="dossier" property="dossier_nr"/></a>
                </display:column>
                <display:column title="Dossiernummer" property="dossier_id" media="csv excel pdf xml" />

                <display:column property="dossier_type" title="Type" sortable="true" class="center" media="html csv excel pdf xml"/>
                <display:column property="opgestart_jn" title="Opgestart ?" sortable="true" class="center" media="html csv excel pdf xml" />
                <display:column property="fase_s" title="Fase" sortable="true" media="html csv excel pdf xml"/>

                <display:setProperty name="paging.banner.all_items_found" value="Dit is alles"/>
            </display:table>
        </td>
    </tr>
</table>