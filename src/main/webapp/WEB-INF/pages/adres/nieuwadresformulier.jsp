<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<link rel="stylesheet" href="/resources/demos/style.css">
<style>
    .ui-autocomplete-loading {
        background: white url('resources/images/ui-anim_basic_16x16.gif') right center
            no-repeat;
    }
</style>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href='style/slickGrid/slick.grid.css' />
<link rel="stylesheet" type="text/css" href='style/slickGrid/slick-default-theme.css' />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script data-main="resources/js/pad/main" src="resources/js/require-2.1.6.min.js"></script>






<h1>Maak een nieuw adres</h1>
<br>
<script>
    $(function() {
        $("#gemeente").autocomplete({
            source: "/pad/s/adres/stelgemeentenvoor",
            minLength: 2,
            focus: function(event, ui) {
                // prevent autocomplete from updating the textbox
                event.preventDefault();
                // manually update the textbox
                // $(this).val(ui.item.label+", "+ ui.item.value);
                $(this).val(ui.item.combo);
            },
            select: function(event, ui) {
                // prevent autocomplete from updating the textbox
                event.preventDefault();
                // manually update the textbox and hidden field
                $(this).val(ui.item.gemeente);
                $("#postcode").val(ui.item.value);
            }
        });
    });

    //TODO: koppel postcode aan de gemeente
    //TODO: koppel straten aan de gemeente
    $(function() {
        $("#straatnaam").autocomplete({
            source: "/pad/s/adres/stelstratenvoor",
            minLength: 2,
            select: function(event, ui){}
        });
    });


    $(function() {
        $("#land").autocomplete({
            source: "/pad/s/adres/stellandvoor",
            minLength: 1,
            focus: function(event, ui) {
                // prevent autocomplete from updating the textbox
                event.preventDefault();
                // manually update the textbox
                $(this).val(ui.item.label+", "+ ui.item.value);
            },
            select: function(event, ui) {
                // prevent autocomplete from updating the textbox
                event.preventDefault();
                // manually update the textbox and hidden field

                $("#land").val(ui.item.value);
            }
        });
    });

</script>
<script>
$(function() {
    $("#postcode").autocomplete({
        source: "/pad/s/adres/stelgemeentenvoorviapostcode",
        minLength: 2,
        focus: function(event, ui) {
            // prevent autocomplete from updating the textbox
            event.preventDefault();
            // manually update the textbox
            $(this).val(ui.item.postcode);
        },
        select: function(event, ui) {
            // prevent autocomplete from updating the textbox
            event.preventDefault();
            // manually update the textbox and hidden field
            $(this).val(ui.item.postcode);
            $("#gemeente").val(ui.item.value);
        }
    });
});
</script>

<form:form id="adres" modelAttribute="adres" name="adres">

<c:if test="${not empty bestaandeAdressen}">
    <div class="error" style="margin: 10px;font-size: 15px">
        Opgelet: dit adres bestaat al! --> eventueel zeggen of en aan wie dat het gelinkt is?<br>
        <c:forEach items="${bestaandeAdressen}" var="item">
            ${item}<br>
        </c:forEach>
    </div>
</c:if>

    <table>
        <tr>
            <td>Land:</td>
            <td>
                <form:input path="land" cssClass="input"/>
                <form:errors path="land"  style="color: red;"/>
            </td>
        </tr>
        <tr>
            <td>postcode:</td>
            <td>
                <form:input path="postcode" cssClass="input"/>
                <form:errors path="postcode"  style="color: red;"/>
            </td>
        </tr>
        <tr>
            <td>gemeente:</td>
            <td>
                <form:input path="gemeente" cssClass="input"/>
                <form:errors path="gemeente"  style="color: red;"/>
            </td>
        </tr>

                <tr>
            <td>straatnaam:</td>
            <td>
                <form:input path="straatnaam" cssClass="input"/>
                <form:errors path="straatnaam"  style="color: red;"/>
            </td>
        </tr>
        <tr>
            <td>huisnummer:</td>
            <td>
                <form:input path="huisnummer" cssClass="input"/>
                <form:errors path="huisnummer"  style="color: red;"/>
            </td>
        </tr>
        <tr>
            <td>busnummer:</td>
            <td>
                <form:input path="busnummer" cssClass="input"/>
                <form:errors path="busnummer"  style="color: red;"/>
            </td>
        </tr>
        <tr>
            <td><button type="submit">Nieuw adres aanmaken</button></td>
        </tr>
    </table>
</form:form>
