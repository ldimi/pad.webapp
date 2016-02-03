<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<tilesx:useAttribute name="tab_selected" classname="java.lang.String" />
<tilesx:useAttribute name="tab_body" classname="java.lang.String" />


<div style="height: 40px;">
    <div class='tab <%=("Overzicht budgetten".equals(tab_selected) ? "selected" : "")%>' >
        <a href="/pad/s/planning/jaar/overzichtbudgetten" >Overzicht budgetten</a>
    </div>
    <div class='tab <%=("Detailoverzicht_gepland".equals(tab_selected) ? "selected" : "")%>' >
        <a href="/pad/s/planning/jaar/detailoverzicht/gepland" >Detailoverzicht gepland</a>
    </div>
    <div class='tab <%=("Detailoverzicht_vastgelegd".equals(tab_selected) ? "selected" : "")%>' >
        <a href="/pad/s/planning/jaar/detailoverzicht/vastgelegd" >Detailoverzicht vastgelegd</a>
    </div>
</div>

<tiles:insertAttribute name="tab_body" />

