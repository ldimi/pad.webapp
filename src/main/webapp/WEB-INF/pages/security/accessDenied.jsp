<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/display-tags" prefix="display"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h3>Geen toegang</h3>   


<c:set var="pageTitle" value="Please Login" scope="request" />



U bent ingelogd maar heeft geen toegang tot deze applicatie: de rol mistral in LDAP ontbreekt. <br/>
 Gelieve hiervoor contact op te nemen met de helpdesk via het kace systeem. <br/>
 </br>
 log uit en probeer het opnieuw: <a href="<c:url value="/s/logout"  />">log uit</a>	
