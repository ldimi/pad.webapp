<%@ page language="java" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<logic:notEmpty name="deelopdrachtlijstschuldvordering"  scope="request">
    <table>
        <tr>
            <th>Schuldvorderingen</th>
        <tr>
        <tr>
            <td class="nopadding">
                <table>
                    <tr>
                        <td>
                            <display:table id="schuldvordering" name="requestScope.deelopdrachtlijstschuldvordering" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                                <display:column property="brief_nr" titleKey="schuldvordering.lijst.briefnr" />
                                <display:column property="bestek_nr" title="Bestek" sortable="true"/>
                                <display:column property="vordering_d" decorator="be.ovam.art46.decorator.DateDecorator" titleKey="schuldvordering.lijst.datum" sortable="true"/>
                                <display:column property="goedkeuring_d" decorator="be.ovam.art46.decorator.DateDecorator" title="Datum goedkeuring" sortable="true"/>
                                <display:column property="vordering_bedrag" titleKey="schuldvordering.lijst.bedrag" decorator="be.ovam.art46.decorator.CurrencyDecorator" sortable="true"/>
                                <display:column property="goedkeuring_bedrag" title="Goedgekeurd" decorator="be.ovam.art46.decorator.CurrencyDecorator" sortable="true"/>
                            </display:table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</logic:notEmpty>
<logic:empty name="deelopdrachtlijstschuldvordering" scope="request">
    Aan deze deelopdracht zijn geen schuldvorderingen gekoppeld.
</logic:empty>
