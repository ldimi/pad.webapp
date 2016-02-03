<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%String zoeklijst = "sessionScope.zoeklijst"; %>
<logic:present name="sublijst" scope="session">
    <% zoeklijst = "sessionScope.sublijst"; %>
</logic:present>
<table>
    <tr>
        <td align="center">
            <table cellpadding='0' cellspacing='0'>
                <html:form action="actiejdzoekresult" method="get">
                <tr valign='middle'>
                    <td valign='middle'>
                        <html:select property="actie_type_id" styleClass="input">
                            <html:option value="">Alle acties</html:option>
                            <html:optionsCollection name="DDH" property="JDActies" label="actie_type_b" value="actie_type_id" />
                        </html:select>
                    </td>
                    <td valign='middle'>
                        <html:select property="jaar_actie_d" styleClass="input">
                            <html:option value="">Actie jaar</html:option>
                            <html:optionsCollection name="vastlegging_jaar" />
                        </html:select>
                    </td>
                    <td valign='middle'>
                        <html:select property="jaar_dossier_d" styleClass="input">
                            <html:option value="">Dossier jaar</html:option>
                            <html:optionsCollection name="bestek_jaar" />
                        </html:select>
                    </td>
                    <td valign='middle'>
                        <html:select property="doss_hdr_id" styleClass="input">
                            <html:option value="">Alle dossierhouders</html:option>
                            <html:optionsCollection name="DDH" property="ambtenarenJD" label="ambtenaar_b" value="ambtenaar_id" />
                        </html:select>
                    </td>

                    <td valign='middle' align="right">
                        <html:submit styleClass="inputbtn">
                            <bean:message key="lijst.opname.button.ophalen" />
                        </html:submit>
                    </td >
                </tr>
                </html:form>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <logic:equal name="zoekaction" scope="session" value="/lijstjdactiestview.do">
                <display:table class="lijst width1000" id="actie" name="<%=zoeklijst%>" requestURI="/lijstjdactiestview.do" export="true" defaultsort="1" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                    <display:column titleKey="lijst.jdactie.actie_type" class="center" sortable="true" media="html">
                        <div style="150px;">
                            <html:select name="actie" property="actie_type_id" styleClass="input" disabled="true">
                                <html:optionsCollection name="DDH" property="JDActies" label="actie_type_b" value="actie_type_id" />
                            </html:select>
                        </div>
                    </display:column>
                    <display:column titleKey="lijst.jdactie.actie_type" property="actie_type_b" media="xml pdf excel csv"/>
                    <display:column property="commentaar" title="Commentaar"/>
                    <display:column property="actie_d" titleKey="lijst.jdactie.datum" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator"/>
                    <display:column property="dossier_id" titleKey="lijst.jdactie.dossier_id" sortable="true" href="dossierdetailsArt46.do?selectedtab=JD" paramId="id" paramProperty="id"/>
                    <display:column sortProperty="doss_hdr_id" titleKey="lijst.jdactie.doss_hdr" class="center" sortable="true" media="html">
                        <html:select name="actie" property="doss_hdr_id" styleClass="input" disabled="true">
                            <html:option value="" />
                            <html:optionsCollection name="DDH" property="ambtenarenJD" label="ambtenaar_b" value="ambtenaar_id" />
                        </html:select>
                    </display:column>
                    <display:column titleKey="lijst.jdactie.doss_hdr" property="ambtenaar_b" media="xml pdf excel csv"/>
                    <display:column title="Gemeente" sortable="true" media="html">
                        <html:select name="actie" property="nis_id" styleClass="input" disabled="true">
                            <html:option value=""/>
                            <html:optionsCollection name="DDH" property="fusiegemeenten" label="gemeente_b" value="nis_id" />
                        </html:select>
                    </display:column>
                    <display:column title="Gemeente" property="nis_id" media="xml pdf excel csv"/>
                    <display:column property="dossier_b" title="Dossier JD"/>
                </display:table>
            </logic:equal>
        </td>
    </tr>
</table>

