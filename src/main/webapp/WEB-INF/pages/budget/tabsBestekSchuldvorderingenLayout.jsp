<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tilesx:useAttribute name="sub_tab_body" classname="java.lang.String" />
<tilesx:useAttribute name="sub_tab_selected" classname="java.lang.String" />

<style>
    .tabHoogte {
        height: 21px !important;
    }
    div.subtab {
        width: 120px;
    }
</style>

<div style="margin: 5px;">
    <div class='tab subtab tabHoogte <%=("schuldvorderingen".equals(sub_tab_selected) ? "selected" : "")%> ' >
        <b><a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/schuldvorderingen/" >Schuldvorderingen</a></b>
    </div>
    <div class='tab subtab tabHoogte <%=("overzichtSchuldvorderingen".equals(sub_tab_selected) ? "selected" : "")%>' >
        <b><a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/overzichtschuldvorderingen/" >Overzicht</a></b>
    </div>
</div>



<div style="border-left: 1px solid;
                border-top: 1px solid;
                position: absolute;
                top: 30px;
                left: 5px;
                right: 5px;
                bottom: 0px;">
    <tiles:insertAttribute name="sub_tab_body" />
</div>

