<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="planning" style="width: 1080px; height: 200px;">
    <form id="paramForm">
        <input name="dossier_id" type="text" value="${dossierart46form.id}" class="hidden" />
        <input name="doss_hdr_id" type="text" value="${dossierart46form.doss_hdr_id}" class="hidden" />

        <span style="margin-left: 50px; margin-right: 5px;">jaar:</span>
        <select name="jaar" class="input invisible">
            <option value=""></option>
            <c:forEach var="jaar" items="${DDH.jaren}" >
                <option value="${jaar}">${jaar}</option>
            </c:forEach>
       </select>
    </form>
    <div id="planningGridDiv"  style="height: 160px;">
    </div>
</div>

<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('planning/dossier/dossierPlanningBacking');
</script>
