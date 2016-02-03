<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<h3>Oeps, vervelend, we hebben een probleem.</h3>

<div>
    Failed URL: ${url}
</div>
<br />
<div>
    Exception:  ${exception.message}
    <br />
    <br />
    Stacktrace:
    <c:forEach items="${exception.stackTrace}" var="ste">
         <br/> ${ste}
    </c:forEach>
</div>