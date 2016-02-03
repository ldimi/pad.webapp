<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<tilesx:useAttribute name="tab_selected" classname="java.lang.String" />
<tilesx:useAttribute name="tab_body" classname="java.lang.String" />


<div style="height: 40px;">
    <div class='tab <%=("Planning dossiers".equals(tab_selected) ? "selected" : "")%>' style="width: 90px;" >
        <a href="/pad/s/planning/individueel/bodemEnAfval" >Planning dossiers</a>
    </div>
    <div class='tab <%=("Planning groepcontracten".equals(tab_selected) ? "selected" : "")%>'>
        <a href="/pad/s/planning/individueel/gegroepeerdeOpdrachten" >Planning groepcontracten</a>
    </div>
    <div class='tab <%=("Planning raamcontracten".equals(tab_selected) ? "selected" : "")%>' >
        <a href="/pad/s/planning/individueel/raamcontracten" >Planning raamcontracten</a>
    </div>
    <div class='tab <%=("Takenlijst".equals(tab_selected) ? "selected" : "")%>' >
        <a href="/pad/s/planning/individueel/takenlijst" >Takenlijst</a>
    </div>
    <div class='tab <%=("Grafieken".equals(tab_selected) ? "selected" : "")%>' >
        <a href="/pad/s/planning/individueel/grafieken" >Grafieken</a>
    </div>
</div>

<div style="border: 1px solid;
            position: absolute;
            top: 40px;
            left: 0px;
            right: 0px;
            bottom: 0px;
            background-color: #fdfdfd;
            ">
    <tiles:insertAttribute name="tab_body" />
</div>

