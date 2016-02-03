<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%String zoeklijst = "sessionScope.zoeklijst"; %>
<logic:present name="sublijst" scope="session">
    <% zoeklijst = "sessionScope.sublijst"; %>
</logic:present>
<html:form action="lijstnietafgeslotendeelopdrachtensave">
    <table>
        <tr>
            <td>
                <logic:equal name="zoekaction" scope="session" value="lijstnietafgeslotendeelopdrachten">
                    <display:table class="lijst width1000" id="deelopdracht" name="<%=zoeklijst%>" requestURI="/lijstnietafgeslotendeelopdrachtenview.do" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                        <display:column group="1" property="dossier_b_ander" title="Dossier ander" href="dossierdetailsArt46.do?selectedtab=Basis" paramProperty="dossier_id_ander" paramId="id"/>
                        <display:column group="1" sortProperty="bestek_nr" title="Besteknr." class="center">
                            <a href='s/bestek/<bean:write name="deelopdracht" property="bestek_id"/>'>
                                <bean:write name="deelopdracht" property="bestek_nr" />
                            </a>
                        </display:column>
                        <display:column title="Saldo geraamd" group="1" >
                            <bean:write name="deelopdracht" property="saldo" formatKey="number.format" filter="false"/>
                        </display:column>
                        <display:column title="Deelopdracht" class="center">
                            <html:link action="dossierdetailsArt46" paramId="id" paramName="deelopdracht" paramProperty="dossier_id">
                                <bean:write name="deelopdracht" property="dossier_l" />
                            </html:link>
                        </display:column>
                        <display:column title="Dossierhouder" class="center" >
                            <bean:write name="deelopdracht" property="doss_hdr_id"/>
                        </display:column>
                        <display:column title="Ingepland bedrag" class="number">
                            <bean:write name="deelopdracht" property="igbedrag" formatKey="number.format" filter="false"/>
                        </display:column>
                        <display:column title="Laatste goedgekeurd bedrag" class="number">
                            <bean:write name="deelopdracht" property="goedkeuring_bedrag" formatKey="number.format" filter="false"/>
                        </display:column>
                        <display:column title="Geraamd bedrag" class="number">
                            <bean:write name="deelopdracht" property="bedrag" formatKey="number.format" filter="false"/>
                        </display:column>
                        <display:column title="Datum goedkeuring" class="center">
                            <bean:define id="deelopdracht_id" name="deelopdracht" property="deelopdracht_id"/>
                            <bean:define id="goedkeuring_d" name="deelopdracht" property="goedkeuring_d" type="java.lang.String"/>
                            <logic:empty name="deelopdrachtlijstform" property="goedkeuring_ds" scope="request">
                                <html:text property='<%= "goedkeuring_d(" + deelopdracht_id + ")"%>' value='<%= goedkeuring_d %>' styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                            </logic:empty>
                            <logic:notEmpty name="deelopdrachtlijstform" property="goedkeuring_ds" scope="request">
                                <html:text property='<%= "goedkeuring_d(" + deelopdracht_id + ")"%>' styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                            </logic:notEmpty>
                        </display:column>
                        <display:column title="Voorstel" class="center">
                            <bean:define id="bestek_id" name="deelopdracht" property="bestek_id"/>
                            <c:if test="${not empty deelopdracht.voorstel_deelopdracht_id}">
                                <bean:define id="voorstel_deelopdracht_id" name="deelopdracht" property="voorstel_deelopdracht_id"/>
                                <a href = "/pad/s/bestek/${bestek_id}/voorstel/${voorstel_deelopdracht_id}" target="_blank">Bekijk voorstel</a>
                            </c:if>
                        </display:column>
                        <display:column class="center">
                            <img src='<html:rewrite page="/"/>/resources/images/edit.gif'
                                 alt="Aanpassen deelopdracht"
                                 onclick="window.openDeelopdracht('<bean:write name="deelopdracht" property="deelopdracht_id" />',
                                                                  '<bean:write name="deelopdracht" property="bestek_id" />',
                                                                  '<bean:write name="deelopdracht" property="bestek_nr" />',
                                                                  'J');" />
                        </display:column>
                    </display:table>
                </logic:equal>
            </td>
        </tr>
        <tr>
            <td align="right">
                <logic:present role="adminArt46">
                    <html:submit styleClass="inputbtn">Wijzigingen opslaan</html:submit>
                </logic:present>
            </td>
        </tr>
    </table>
</html:form>

<div id="deelopdrachtDetailDiv" > </div>

<tiles:insert definition="laadJS" />

<script type="text/javascript">
    laadBacking('budget/deelopdracht/deelopdrachtBacking');
</script>
