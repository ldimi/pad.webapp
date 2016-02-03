<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>Success</h1>



<c:if test="${not empty adres}">

    Het volgende adres werd opgeslagen:${adres}<br>
    <table>
        <thead>
            <td>ID</td>
            <td>Land</td>
            <td>Gemeente</td>
            <td>Postcode</td>
            <td>Straatnaam</td>
            <td>Huisnummer</td>
            <td>Busnummer</td>
        </thead>
        <tbody>
            <tr>
                <td>${adres.id}</td>
                <td>${adres.land}</td>
                <td>${adres.gemeente}</td>
                <td>${adres.postcode}</td>
                <td>${adres.straatnaam}</td>
                <td>${adres.huisnummer}</td>
                <td>${adres.busnummer}</td>
            </tr>
<%-- 			<c:forEach items="${adres}" var="adres" > --%>
<!-- 				<tr> -->

<%-- 					<td>${adres.id}</td> --%>
<%-- 					<td>${adres.land}</td> --%>
<%-- 					<td>${adres.gemeente}</td> --%>
<%-- 					<td>${adres.postcode}</td> --%>
<%-- 					<td>${adres.straatnaam}</td> --%>
<%-- 					<td>${adres.busnummer}</td> --%>
<!-- 				</tr>			 -->
<%-- 			</c:forEach>		 --%>
        </tbody>
    </table>
    <br>
    <br>

<%-- 	<form action="" method="POST"> --%>
<%-- 	<input type="hidden" value="${adres.id}"/> --%>
<!-- 	<input type="submit" value="wilt u dit adres wijzige?" > -->
<!-- 	</input> -->


<br>
<br>
<br>

<form action="/pad/s/adresrechtspersoonLink" method="POST">
    <input type="hidden" name="adresid" value="${adresid}"/>
<input type="submit" value="wilt u een rechtspersoon linken aan dit adres?" >
</input>
<br>
</form>

</c:if>




<c:if test="${not empty rechtspersoon}">
    Het volgende adres werd opgeslagen:${rechtspersoon}<br>
    <table>
        <thead>
        <td>id</td>
            <td>Bedrijfs/achternaam</td>
            <td>Afdeling/voornaam</td>
            <td>website</td>
            <td>tel</td>
            <td>Emailadres</td>
        </thead>
        <tbody>
            <tr>
                <td>${rechtspersoon.id}</td>
                <td>${rechtspersoon.naam}</td>
                <td>${rechtspersoon.naam2}</td>
                <td>${rechtspersoon.website}</td>
                <td>${rechtspersoon.tel}</td>
                <td>${rechtspersoon.emailadres}</td>
            </tr>
        </tbody>
    </table>
<form action="/pad/s/rechtspersoonadresLink" method="POST">

    <input type="hidden" name="rechtspersoonid" value="${rechtspersoonid}"/>
<input type="submit" value="wilt u een adres linken aan deze rechtspersoon?" >
</input>
<br>
</form>
    wilt u wijzigen?<br>
wilt u een adres toekennen aan deze rechtspersoon?<br>
wilt u contactpersonen koppelen aan deze bedrijf/natuurlijke persoon?<br>

</c:if>



