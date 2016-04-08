<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<h1>Te scannen inkomende brieven </h1>
<tiles:insert page="../util/uploader.jsp"/>
<table>
    <thead>
    <tr>
        <th>Inschrijving</th>
        <th>Uit Datum</th>
        <th>uit Aard</th>
        <th>Type VOS</th>
        <th>Ovam Briefnummer</th>
        <th>Dossier</th>
        <th>Doshdr</th>
        <th>Gemeente</th>
        <th>Correspondent</th>
        <th>Qr code</th>
        <th>Wijzigingsdatum</th>
        <th>Gebruiker</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${brieven}" var="brief" varStatus="status">
        <c:choose>
            <c:when test="${status.index mod 2 == 0 }">
                <c:set var="styleclass" value="tableRowEven"/>
            </c:when>
            <c:otherwise>
                <c:set var="styleclass" value="tableRowOdd"/>
            </c:otherwise>
        </c:choose>
        <tr class="${styleclass}">
            <td><fmt:formatDate value="${brief.inschrijfDatum}" pattern="dd/MM/yyyy"/></td>
            <td><fmt:formatDate value="${brief.uitDatum}" pattern="dd/MM/yyyy"/></td>
            <td>${brief.uitAartBeschrijving}</td>
            <td>${brief.uitTypeBeschrijvingVos}</td>
            <td>
                <a href="/pad/briefdetails.do?brief_id=${brief.id}" target="_blank">
                        ${brief.briefNummer}
                </a>
            </td>
            <td>${brief.dossierNummer}</td>
            <td>${brief.dossierhouderId}</td>
            <td>${brief.gemeente}</td>
            <td>${brief.adresNaam}</td>
            <td>${brief.qrcode}</td>
            <td><fmt:formatDate value="${brief.laatsteWijzigingDatum}" pattern="dd/MM/yyyy HH:mm"/></td>
            <td>${brief.laatsteWijzigingUser}</td>
            <td><input type="button" onclick="scanOpladen('/pad/s/beheer/qrbrievenzonderscan/opladen/I${brief.qrcode}')" value="Scan opladen"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h1>Gescande inkomende brieven </h1>
<table>
    <thead>
    <tr>
        <th>Inschrijving</th>
        <th>Uit Datum</th>
        <th>uit Aard</th>
        <th>Type VOS</th>
        <th>Ovam Briefnummer</th>
        <th>Dossier</th>
        <th>Doshdr</th>
        <th>Gemeente</th>
        <th>Correspondent</th>
        <th>Qr code</th>
        <th>Wijzigingsdatum</th>
        <th>Gebruiker</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${brievenMetQr}" var="brief" varStatus="status">
        <c:choose>
            <c:when test="${status.index mod 2 == 0 }">
                <c:set var="styleclass" value="tableRowEven"/>
            </c:when>
            <c:otherwise>
                <c:set var="styleclass" value="tableRowOdd"/>
            </c:otherwise>
        </c:choose>
        <tr class="${styleclass}">
            <td><fmt:formatDate value="${brief.inschrijfDatum}" pattern="dd/MM/yyyy"/></td>
            <td><fmt:formatDate value="${brief.uitDatum}" pattern="dd/MM/yyyy"/></td>
            <td>${brief.uitAartBeschrijving}</td>
            <td>${brief.uitTypeBeschrijvingVos}</td>
            <td>
                <a href="/pad/briefdetails.do?brief_id=${brief.id}" target="_blank">
                        ${brief.briefNummer}
                </a>
            </td>
            <td>${brief.dossierNummer}</td>
            <td>${brief.dossierhouderId}</td>
            <td>${brief.gemeente}</td>
            <td>${brief.adresNaam}</td>
            <td>${brief.qrcode}</td>
            <td><fmt:formatDate value="${brief.laatsteWijzigingDatum}" pattern="dd/MM/yyyy HH:mm"/></td>
            <td>${brief.laatsteWijzigingUser}</td>
            <td>
                <a href='<%=System.getProperty("ovam.dms.webdrive.base") %>${brief.dmsFolder}/${brief.dmsFileName}'
                            target="_blank" style="text-decoration: none;">
                    <img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('lijsten/scanopladenBacking');
</script>