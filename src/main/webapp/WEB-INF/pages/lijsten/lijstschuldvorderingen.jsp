<%@ page language="java"%>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="de_DE"/>

<table>
    <tr>
        <td align="center">
            <table cellpadding='0' cellspacing='0'>
                <html:form action="lijstschuldvorderingselect">
                <tr valign='middle'>
                    <td>
                        <html:select property="forward" styleClass="input">
                            <html:option value="lijstschuldvorderingenietbetaald">Niet betaalde schuldvorderingen</html:option>
                            <html:option value="lijstschuldvorderingenbetaald">Betaalde schuldvorderingen</html:option>
                            <html:option value="lijstschuldvorderingenalle">Alle schuldvorderingen</html:option>
                        </html:select>
                    </td>
                    <td>
                        <html:select property="programma_code" styleClass="input">
                            <html:option value="">Programma</html:option>
                            <html:optionsCollection name="DDH" property="programmaTypes" label="code" value="code" />
                        </html:select>
                    </td>
                    <td>
                        <td valign='middle'>
                        <html:select property="doss_hdr_id" styleClass="input">
                            <html:option value="">Alle dossierhouders</html:option>
                            <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                        </html:select>
                    </td>
                    </td>
                    <td valign='middle'>
                        <html:submit styleClass="inputbtn">
                            <bean:message key="lijst.opname.button.ophalen" />
                        </html:submit>
                    </td >
                </tr>
                </html:form>
            </table>
        </td>
    </tr>
    <html:form action="lijstschuldvorderingsave">
        <tr>
            <td>
                <display:table class="lijst width1000" id="schuldvordering" name="sessionScope.zoeklijst" requestURI="/lijstschuldvorderingenview.do" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' export="true">
                    <display:column titleKey="lijst.schuldvordering.vordering_d" property="vordering_d" class="center" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator"/>
                    <display:column titleKey="lijst.schuldvordering.dossier_b"  sortable="true" sortProperty="dossier_b" media="html">
                        <html:link action="dossierdetailsArt46?selectedtab=Bestek" paramId="id" paramName="schuldvordering" paramProperty="dossier_id">
                            <bean:write name="schuldvordering" property="dossier_b" />
                        </html:link>
                    </display:column>
                    <display:column property="dossier_b" media="csv xml pdf excel"/>
                    <display:column title="Programma" property="programma_code" sortable="true" media="html csv xml pdf excel"/>
                    <display:column title="Bestek" property="bestek_nr" sortable="true" media="csv xml pdf excel"/>
                    <display:column title="Bestek" sortProperty="bestek_nr" sortable="true" media="html">
                        <html:link action="dossierdetailsArt46?selectedtab=Bestek" paramId="id" paramName="schuldvordering" paramProperty="dossier_id">
                        <bean:write name="schuldvordering" property="bestek_nr" />
                        </html:link>
                    </display:column>
                    <display:column property="vordering_bedrag" titleKey="lijst.schuldvordering.bedragvordering" class="number" sortable="true" decorator="be.ovam.art46.decorator.CurrencyDecorator" media="html"/>
                    <display:column property="vordering_bedrag" media="csv xml pdf excel"/>

                    <display:column titleKey="lijst.schuldvordering.bedraggoedkeuring" media="html">
                        <c:if test="${schuldvordering.afgekeurd_jn == 'J' }">
                            <span>AFGEKEURD</span>
                        </c:if>
                        <c:if test="${schuldvordering.afgekeurd_jn == 'N'}">
                            <span> <fmt:formatNumber type="currency" value="${schuldvordering.goedkeuring_bedrag}" /> </span>
                        </c:if>
                    </display:column>
                    <display:column property="goedkeuring_bedrag" media="csv xml pdf excel"/>
                    <display:column property="goedkeuring_d" title="Effectieve verificatie datum" class="center" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" media="html csv xml pdf excel" />
                    <display:column property="laattijdige_verificatie_j" title="laattijdige verificatie?" class="center" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" media="html csv xml pdf excel" />

                    <display:column property="uiterste_d" titleKey="lijst.schuldvordering.uiterste_d" class="center" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" media="html csv xml pdf excel"/>
                    <display:column property="betaal_d" title="Datum betaling" class="center" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" media="html csv xml pdf excel"/>
                    <display:setProperty name="export.decorated" value="true"/>
                </display:table>
            </td>
        </tr>
    </html:form>
</table>
