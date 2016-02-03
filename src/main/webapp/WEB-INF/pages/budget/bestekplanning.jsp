<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%--
<form:form method="post" action="/pad/s/bestek/${bestekId}/planning/opslaan/" commandName="bestekramingform">
--%>
    <table>
        <tr>
            <td>
                <c:if test="${not empty ramingen}">
                    <display:table id="raming" name="ramingen" class="lijst width980">
                        <display:column title="Type">
                            <html:select name="raming" property="fase_id" styleClass="input" disabled="true">
                                <html:optionsCollection name="DDH" property="faseRaming" label="fase_b" value="fase_id" />
                            </html:select>
                        </display:column>
                        <display:column title="Jaartal van gunning" class="center">
                            <bean:write name="raming" property="maand"/>/<bean:write name="raming" property="jaartal"/>
                        </display:column>
                        <display:column property="raming" title="Raming VK te voorzien" decorator="be.ovam.art46.decorator.CurrencyDecorator" class="number"/>
                        <display:column title="Prioriteit">
                            <html:select name="raming" property="prioriteit_id" styleClass="input" disabled="true" >
                                <html:optionsCollection name="DDH" property="prioriteitRaming" label="prioriteit_b" value="prioriteit_id" />
                            </html:select>
                        </display:column>
                        <display:column property="budget_vastgelegd" title="Budget vastgelegdd" decorator="be.ovam.art46.decorator.CurrencyDecorator" class="number"/>
                        <display:column title="Afgesloten" class="center">
                            <html:checkbox name="raming" property="afgesloten_s" value="1" styleClass="checkbox" disabled="true"/>
                        </display:column>
                        <display:column property="commentaar" title="Commentaar" />
                        <%--
                        <display:column>
                            <form:radiobutton path="raming_id" value="${raming.raming_id}" styleClass="checkbox" />
                        </display:column>
                        --%>
                        <display:setProperty name="basic.msg.empty_list_row" value=""/>
                    </display:table>
                </c:if>
            </td>
        </tr>
        <%--
        <tr>
            <td align="right">
                <logic:present role="adminArt46,adminIVS,adminBOA">
                    <input type="submit" value="Opslaan" class="inputbtn"/>
                </logic:present>
            </td>
        </tr>
        --%>
    </table>
    
<%--
</form:form>
--%>