<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<display:table id="kadaster" name="sessionScope.zoeklijst" requestURI="/kadasterzoekresultview.do" export="true" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
    <display:column titleKey="kadaster.zoekresult.kadaster" property="kadaster_id_l" href="kadasterdetails.do" paramId="kadaster_id" paramProperty="kadaster_id"  sortable="true" />
    <display:column titleKey="kadaster.zoekresult.art46.voor" sortProperty="lijst_voor_goedk" class="center" sortable="true">
        <html:checkbox name="kadaster" property="art46" value="1" styleClass="checkbox"/>
    </display:column>
</display:table>





