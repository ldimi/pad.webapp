<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<c:if test="${not empty melding}">
    <b>${melding}</b>
</c:if>
<logic:present role="secretariaat">
<table style="width: 50%">
    <tr>
        <th></th>
        <th>Vordering nr</th>
        <th>Brief nummer</th>
        <th>Dossierhouder</th>
        <th></th>
    </tr>
    <c:forEach items="${schuldvorderingen}" var="schuldvordering" varStatus="status">
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
                <c:choose>
                    <c:when test="${schuldvordering.goedgekeurd}">
                        Goedkeuring
                    </c:when>
                    <c:otherwise>
                        Afkeuring
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${not empty schuldvordering.aanvraagSchuldvordering}">
                        <a href="/pad/s/bestek/${schuldvordering.bestek_id}/aanvraagSchuldvordering/${schuldvordering.aanvraagSchuldvordering.id}">
                                ${schuldvordering.nummer}
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="/pad/s/bestek/${schuldvordering.bestek_id}/schuldvorderingen/${schuldvordering.vordering_id}">
                                ${schuldvordering.nummer}
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td style="text-align: left">
                    ${schuldvordering.brief.brief_nr}
            </td>
            <td>
                    ${schuldvordering.bestek.dossier.doss_hdr_id}
            </td>
            <td>
                <form action="/pad/s/tescannenschuldvorderingen/${schuldvordering.vordering_id}/uploadscan/"
                      enctype="multipart/form-data" accept="application/pdf" method="post">
                    <input type="file" name="file" onchange="this.form.submit()"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</logic:present>
<hr/>


