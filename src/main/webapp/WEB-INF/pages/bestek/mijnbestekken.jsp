<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<table>
    <tr>
        <td>
            <display:table class="lijst1" id="bestek" name="mijnbestekken" requestURI="/s/mijnbestekken" export="true" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                <display:column  title="Besteknr" sortProperty="bestek_nr">
                    <a href='/pad/s/bestek/${bestek.bestek_id}/basisgegevens/' >
                        ${bestek.bestek_nr}
                    </a>
                </display:column>
                <display:column property="bestek_nr" title="Besteknr" sortable="true" media="csv excel pdf xml" />
                <display:column  title="Omschrijving" sortProperty="bestek_nr"  style="width: 300px;">
                    <a href='/pad/s/bestek/${bestek.bestek_id}/basisgegevens/' >
                        ${bestek.omschrijving}
                    </a>
                </display:column>
                <display:column property="omschrijving" title="Omschrijving" sortable="true" media="csv excel pdf xml" />
                <display:column property="fase_b" title="Fase" sortable="true" media="html csv excel pdf xml"/>
                <display:column property="type_b" title="Type" sortable="true" media="html csv excel pdf xml"/>
                <display:column property="wbs_nr" title="SAP WBS" sortable="true" media="html csv excel pdf xml"/>
                <display:column property="raamcontract_jn" title="Raamcontract ?" sortable="true" media="html csv excel pdf xml"/>
                <display:column property="dossier_nr" title="Dossier nr." sortable="true" media="html csv excel pdf xml"/>
                <display:column property="dossier_b_l" title="Dossier titel" sortable="true" media="html csv excel pdf xml"/>
                <display:column property="dossier_type" title="Dos. type" sortable="true" media="html csv excel pdf xml"/>

            </display:table>
        </td>
    </tr>
</table>

