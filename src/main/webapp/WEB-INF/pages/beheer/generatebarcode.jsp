<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form modelAttribute="barcodeForm" action="/pad/s/beheer/generatebarcode.pdf" method="post">
    eerste nummmer: <form:input path="startnummer"/>
    laatste nummer: <form:input path="eindnummer"/>
    <input type="submit" name="Genereer barcode PDF">

</form:form>
