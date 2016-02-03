<%@ page language="java" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>


<h3>${query_b} [${query_aantal_resultaten} resultaten]</h3>

<logic:notEmpty name="zoeklijst" scope="session">
    <logic:equal name="zoekaction" value="queryresult" scope="session">
        <display:table name="sessionScope.zoeklijst" id="resultMap" requestURI="/queryexecuteview.do?popup=yes" export="true" >
            <c:forEach var="key" items="${resultMap.keySet()}">
                <display:column property="${key}"  sortable="true" class="center" />
            </c:forEach>
        </display:table>
    </logic:equal>
</logic:notEmpty>
