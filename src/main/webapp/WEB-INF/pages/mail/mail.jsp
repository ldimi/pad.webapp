<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<form:form method="post" accept-charset="UTF-8"
           action="/pad/s/bestek/${bestekId}/voorstel/${voorstelId}/sendmail"
           modelAttribute="mailForm">
    <table border="0" width="80%">
        <tr>
            <td>Aan:</td>
            <td>
                <form:select path="aan">
                    <form:options items="${DDH.wrap(emails)}" itemLabel="m['label']" itemValue="m['value']"/>
                </form:select>
                <form:errors path="aan" style="color: red;"/>
        </tr>
        <tr>
            <td>Onderwerp:</td>
            <td>
                <form:input path="onderwerp"/>
                <form:errors path="onderwerp" style="color: red;"/>
            </td>
        </tr>
        <tr>
            <td>Bericht:</td>
            <td>
                <form:textarea path="bericht" cols="100" rows="10" htmlEscape="true"/>
                <form:errors path="bericht" style="color: red;"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Verzend E-mail" name="action" />
                <input type="submit" value="Annuleer" name="action" />
            </td>
        </tr>
    </table>
</form:form>