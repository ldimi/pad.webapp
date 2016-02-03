<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<b>Eenheden:</b>
<table style="width: 80%">
    <tr>
        <th>Code</th><th>Naam</th><th></th>
    </tr>
    <c:forEach items="${eenheden}" var="eenheid" varStatus="status">
        <c:choose>
            <c:when test="${status.index mod 2 == 0 }">
                <c:set var="styleclass" value="tableRowEven"/>
            </c:when>
            <c:otherwise>
                <c:set var="styleclass" value="tableRowOdd"/>
            </c:otherwise>
        </c:choose>
        <tr class="${styleclass}">
            <td>
                -${eenheid.naam}
            </td>
            <td>
                <a href="/pad/s/beheer/meetstaat/eenheden/${eenheid.naam}/verwijder/">
                    verwijder
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
    <form:form  method="post" accept-charset="UTF-8" action="/pad/s/beheer/meetstaat/eenheden/toevoegen/" modelAttribute="meetstaatEenheid">
        <form:input path="naam"/>
        <input type="submit" value="Toevoegen" class="inputbtn"/>
    </form:form>
<b>Eenheden Mapping</b>
<table style="width: 80%">
    <tr>
        <th>Waarde</th>
        <th>Gekopelde eenheid</th><th></th>
    </tr>
    <c:forEach items="${eenhedenmappers}" var="mapper" varStatus="status">
        <c:choose>
            <c:when test="${status.index mod 2 == 0 }">
                <c:set var="styleclass" value="tableRowEven"/>
            </c:when>
            <c:otherwise>
                <c:set var="styleclass" value="tableRowOdd"/>
            </c:otherwise>
        </c:choose>
        <tr class="${styleclass}">
            <td>
                -${mapper.naam}
            </td>
            <td>
                -${mapper.eenheidCode}
            </td>
            <td>
                <a href="/pad/s/beheer/meetstaat/eenheid/mapping/${mapper.naam}/verwijder/">
                    verwijder
                </a>
            </td>
        </tr>
    </c:forEach>

</table>
<form:form  method="post" accept-charset="UTF-8" action="/pad/s/beheer/meetstaat/eenheden/mapping/toevoegen/" modelAttribute="meetstaatEenheidMapping">
    <form:input path="naam"/>
    <form:select items="${eenheden}" itemValue="naam" itemLabel="naam" path="eenheidCode"/>
    <input type="submit" value="Toevoegen" class="inputbtn"/>
</form:form>