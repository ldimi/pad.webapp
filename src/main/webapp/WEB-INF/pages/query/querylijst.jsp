<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<table class="planning">
    <tr>
        <th>
            Queries
        </th>
        <th align="right">
            <logic:present role="adminArt46">
                <html:link action="query?crudAction=view&query_id=0">
                    <html:img  page="/resources/images/add.gif" alt="Query toevoegen" title="Query toevoegen" border="0"/>
                </html:link>
            </logic:present>
        </th>
    </tr>
    <tr>
        <td colspan="2">
            <logic:notEmpty name="querylijst" scope="request">
                <display:table id="query" name="requestScope.querylijst" class="planning" >
                    <display:column property="query_b" title="Naam"/>
                    <display:column property="query_l" title="Query" maxLength="100"/>
                    <display:column style="text-align: center;">

                            <html:link action="query?crudAction=read" paramId="query_id" paramName="query" paramProperty="query_id">
                                <html:img  page="/resources/images/edit.gif" alt="Query wijzigen" title="Query wijzigen" border="0"/>
                            </html:link>

                    </display:column>
                    <display:column style="text-align: center;">
                        <logic:present role="adminArt46">
                            <html:link action="query?crudAction=delete" paramId="query_id" paramName="query" paramProperty="query_id">
                                <html:img  page="/resources/images/delete.gif" alt="Query verwijderen" title="Query verwijderen" border="0"/>
                            </html:link>
                        </logic:present>
                    </display:column>
                    <display:setProperty name="basic.msg.empty_list_row" value=""/>
                </display:table>
            </logic:notEmpty>
        </td>
    </tr>
</table>
