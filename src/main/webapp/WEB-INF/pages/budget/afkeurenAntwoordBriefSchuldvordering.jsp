<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form:form method="post" accept-charset="UTF-8" action="/pad/s/afkeurenAntwoordBriefSchuldvordering/opslaan" modelAttribute="afkeurenAntwoordBriefSchuldvorderingForm">
<logic:present role="ondertekenaar">
<table style="vertical-align: top;">
    <tr>
        <th>
            Afkeuren schuldvordering: ${schuldvordering.nummer}
            <form:hidden path="id"/>
        </th>
    </tr>
    <tr><td>Motivatie: </td></tr>
    <tr><td style="vertical-align: top;">
            <form:textarea path="motivatie" rows="8" cssStyle="width: 600px;" maxlength="1024"/>
    </td></tr>
    <tr><td>
        <br/><input type="submit" value="Annuleren" name="action"/> <input type="submit" value="Terug naar dosierhouder" name="action"/>
    </td></tr>
</table>
</logic:present>
</form:form>