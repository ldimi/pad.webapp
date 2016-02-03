<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<display:table htmlId="lijstvoorstel"  id="dossierkadaster" name="requestScope.lijstvoorgoedkpersoon" requestURI="/lijstvoorgoedkpersoon.do" export="true" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' defaultsort="1">
	<display:column title="Dossiernr. BB" property="dossier_id" />
	<display:column title="Kadasterafdeling" property="kadaster_afd_b" />
	<display:column title="Kadasterafdeling" property="kadaster_afd_id" />
	<display:column title="Sectie" property="sectie" />
	<display:column title="Grondnummer" property="grondnummer" />
	<display:column title="Bisnummer" property="bisnummer" />
	<display:column title="Exponent1" property="exponent1" />
	<display:column title="Exponent2" property="exponent2" />
	<display:column title="Artikel" property="artikel_b" />
	<display:column title="Eigenaar" property="eigenaar" />
	<display:column title="Gebruiker" property="gebruiker" />
	<display:column title="OVAM kadaster" property="ovam_kadaster" />
	<display:column title="Naam" property="persoon_b" />
	<display:column title="Firma" property="firma" />
	<display:column title="Adres" property="adres" />
	<display:column title="Postcode" property="postcode" />
	<display:column title="Stad" property="stad" />
	<display:column title="Land" property="land" />
</display:table>
