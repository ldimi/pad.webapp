<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="custom" uri="/tags/custom" %>
<form:form action="/pad/s/bestek/${bestekId}/overzichtschuldvorderingen" method="post" modelAttribute="form"><br/>
    <c:if test="${not empty offertes}">
        Offerte: <form:select path="offerteId" items="${offertes}" itemLabel="inzender" itemValue="id" onchange="this.form.submit();" cssStyle="width: 150"/>
        <br/>
    </c:if>
    <c:if test="${not empty deelOpdrachten}">
        Deelopdracht:
        <form:select path="deelOpdrachtId" onchange="this.form.submit();" cssStyle="width: 150">
            <form:options items="${deelOpdrachten}" itemLabel="name" itemValue="id"/>
            <form:option value="0" label="Alle"/>
        </form:select>
    </c:if>
    <br/>
</form:form>

<c:choose>
    <c:when test="${form.deelOpdrachtId eq 0}">
        <a href="/pad/s/bestek/exportoverzichtschuldvorderingen${form.offerteId}.xls" target="_blank"> Excel </a>
    </c:when>
    <c:otherwise>
        <a href="/pad/s/bestek/exportoverzichtschuldvorderingendeelopdracht${form.deelOpdrachtId}.xls" target="_blank"> Excel </a>
    </c:otherwise>
</c:choose>

<div id="overzichtsWrapper" class="hidden">
    <div id="outeroverzichtGrid">
        <div id="overzichtGrid"></div>
    </div>
</div>
<tiles:insert definition="laadJSzonderSlickgrid"/>

<script type="text/javascript">
    require(
            ['appMinimum'],
            function (app) {

                require(
                        ['jquery', 'lijsten/overzichtSchuldvorderingenBacking'],
                        function ($, overzicht) {

                            $(document)
                                    .ready(
                                    function () {
                                        window.regels = <custom:outJson object="view" />;
                                                window.deelopdrachtId = form.deelOpdrachtId;
                                                overzicht.render();
                                    });

                        });

            });
</script>