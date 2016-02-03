<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet"
	href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css"
	type="text/css" media="all" />
<link rel="stylesheet" type="text/css"
	href='style/slickGrid/slick.grid.css' />
<link rel="stylesheet" type="text/css"
	href='style/slickGrid/slick-default-theme.css' />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script data-main="resources/js/pad/main" src="resources/js/require-2.1.6.min.js"></script>
<script>
	$(function() {
		$("#gemeente").autocomplete({
			source : "/pad/s/adres/stelgemeentenvoor",
			minLength : 2,
			focus : function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox
				$(this).val(ui.item.label);
			},
			select : function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox and hidden field
				$(this).val(ui.item.label);
				$("#postcode").val(ui.item.value);
			}
		});
	});

	//TODO: koppel postcode aan de gemeente
	//TODO: koppel straten aan de gemeente
	$(function() {
		$("#straatnaam").autocomplete({
			source : "/pad/s/adres/stelstratenvoor",
			minLength : 2,
			select : function(event, ui) {
			}
		});
	});

	//TODO: nog landtabel creeëren?
	$(function() {
		$("#land").autocomplete({
			source : "/pad/s/adres/stellandvoor",
			minLength : 2,
			focus : function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox
				$(this).val(ui.item.label);
			},
			select : function(event, ui) {
				// prevent autocomplete from updating the textbox
				event.preventDefault();
				// manually update the textbox and hidden field
				// 				$(this).val(ui.item.label);
				$("#land").val(ui.item.value);
			}
		});
	});
</script>


<h1>Corrigeer hier de onvolledige adressen!</h1>
<c:if test="${aantalOnvolledigeAdressen>0 }">
<br>
je heb er nog ${aantalOnvolledigeAdressen} te gaan
<br>
<br>
Het volgende te corrigeren adres is:
<br>
<br>
<form:form action="/pad/s/adres/corrigeer" 
	id="adres"
	modelAttribute="adres" 
	name="adres">

	<table>
		<tr>
			<td>Land:</td>
			<td><form:input path="land" cssClass="input" /> <form:errors
					path="land" style="color: red;" /></td>
		</tr>
		<tr>
			<td>gemeente:</td>
			<td><form:input path="gemeente" cssClass="input" /> <form:errors
					path="gemeente" style="color: red;" /></td>
		</tr>
		<tr>
			<td>postcode:</td>
			<td><form:input path="postcode" cssClass="input" /> <form:errors
					path="postcode" style="color: red;" /></td>
		</tr>
		<tr>
			<td>straatnaam:</td>
			<td><form:input path="straatnaam" cssClass="input" /> <form:errors
					path="straatnaam" style="color: red;" /></td>
		</tr>
		<tr>
			<td>huisnummer:</td>
			<td><form:input path="huisnummer" cssClass="input" /> <form:errors
					path="huisnummer" style="color: red;" /></td>
		</tr>
		<tr>
			<td>busnummer:</td>
			<td><form:input path="busnummer" cssClass="input" /> <form:errors
					path="busnummer" style="color: red;" /></td>
		</tr>
		<tr>
			<td><form:input path="id" type="hidden" />
				<button type="submit">corrigeer adres</button></td>
		</tr>
	</table>
</form:form>

<form action="/pad/s/adres/verwijderAdres" method="POST">
	<br> of wil je dit adres markeren als "te verwijderen?" <br>
	<input name="adresid" type="hidden" value="${adres.id}" />
	<button type="submit">verwijder adres</button>
</form>
</c:if>

<c:if test="${aantalOnvolledigeAdressen<=0 }">
Er zijn geen onvolledige adressen meer in de databank aanwezig
</c:if>