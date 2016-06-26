<%@ page language="java" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<display:table id="adres"
    class="lijst1"
    name="sessionScope.zoeklijst"
    requestURI="/s/adres/lijst"
    pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >

    <display:column property="naam" title="Naam" href="adres.do?crudAction=read&adres_id=${adres.adres_id}" sortable="true" />
    <display:column property="voornaam" title="Voornaam" sortable="true"/>
    <display:column property="straat" title="Straat" sortable="true"/>
    <display:column property="postcode" title="Postcode" class="center" sortable="true"/>
    <display:column property="gemeente" title="Gemeente" class="center" sortable="true"/>
    <display:column property="contact_naam" title="Contact" sortable="true"/>
    <display:column property="contact_functie" title="Functie" sortable="true"/>
    <display:column property="actief_jn" title="Actief ?" sortable="true"/>
    <display:column property="adres_type_b" title="type" sortable="true"/>
    <display:column>
        <a href="/pad/adres.do?crudAction=delete&adres_id=${adres.adres_id}">
            <img border="0" src="/pad/resources/images/delete.gif" title="Verwijderen adres"/>
        </a>
    </display:column>
</display:table>
