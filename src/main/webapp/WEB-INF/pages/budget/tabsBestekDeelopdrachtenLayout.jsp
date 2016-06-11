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
    <div class='tab subtab tabHoogte <%=("deelopdrachten".equals(sub_tab_selected) ? "selected" : "")%>' >
        <b><a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/deelopdrachten/" >Deelopdrachten</a></b>
    </div>

    <c:if test="${dossier_type eq 'X'}">
        <div class='tab tabHoogte <%=("voorstellen".equals(sub_tab_selected) ? "selected" : "")%>' >
            <b><a class="sm" href="/pad/s/bestek/${bestekDO.bestek_id}/deelopdrachtvoorstellen/" >Voorstellen</a></b>
        </div>
    </c:if>

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

