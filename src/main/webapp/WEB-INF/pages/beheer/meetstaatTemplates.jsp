<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<table style="width: 80%">
    <tr>
        <th>Naam</th>
    </tr>
    <c:forEach items="${templates}" var="template" varStatus="status">
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
                <a href="/pad/s/beheer/meetstaat/template/${template.id}/">
                    -${template.naam}
                </a>
            </td>
            <td>
                <a href="/pad/s/beheer/meetstaat/template/${template.id}/verwijder/">
                    verwijder
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="/pad/s/beheer/meetstaat/template/nieuw/">
    Meetstaat template toevoegen
</a>

<hr/>