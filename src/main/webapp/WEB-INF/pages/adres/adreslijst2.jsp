<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<table>
    <tr>
        <td>
            <display:table id="adres"
                class="lijst1"
                name="sessionScope.zoeklijst"
                requestURI="/s/adres/lijst"
                pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >

                <%-- <display:column property="adres_id" title="adres_id" sortable="true"/> --%>
                <display:column property="naam" title="Naam" href="adres.do?crudAction=read" paramId="adres_id" paramProperty="adres_id"  sortable="true" />
                <display:column property="voornaam" title="Voornaam" sortable="true"/>
                <display:column property="straat" title="Straat" sortable="true"/>
                <display:column property="postcode" title="Postcode" class="center" sortable="true"/>
                <display:column property="gemeente" title="Gemeente" class="center" sortable="true"/>
                <display:column property="contact_naam" title="Contact" sortable="true"/>
                <display:column property="contact_functie" title="Functie" sortable="true"/>
                <display:column property="actief_jn" title="Actief ?" sortable="true"/>
                <display:column property="adres_type_b" title="type" sortable="true"/>
                <display:column>
                    <html:link action="adres?crudAction=delete" paramId="adres_id" paramName="adres" paramProperty="adres_id">
                        <html:img border="0" page="/resources/images/delete.gif" title="Verwijderen adres"/>
                    </html:link>
                </display:column>
            </display:table>
        </td>
    </tr>
</table>