<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:useAttribute id="requestURI" name="requestURI" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="isMelding" name="isMelding" classname="java.lang.String" ignore="true" />

<%String zoeklijst = "sessionScope.zoeklijst"; %>
<logic:present name="sublijst" scope="session">
    <% zoeklijst = "sessionScope.sublijst"; %>
</logic:present>
<table>
    <tr>
        <th>
            Brief Zoeklijst
        </th>
    </tr>
    <tr>
        <td>
            <display:table id="brief" name="<%=zoeklijst%>" requestURI="<%=requestURI %>" defaultsort="1" defaultorder="descending" export="true" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' class="lijst" >

                <display:column property="inschrijf_d" title="Inschrijving" decorator="be.ovam.art46.decorator.DateDecorator" sortable="true" class="center"/>
                <display:column property="reactie_voor_d" title="Reactie voor" decorator="be.ovam.art46.decorator.DateDecorator" sortable="true" class="center"/>
                <display:column property="reactie_d" title="Reactie" decorator="be.ovam.art46.decorator.DateDecorator" sortable="true" class="center"/>
                <display:column property="brief_categorie_b" title="Categorie" sortable="true" media="html xml csv excel"/>
                <display:column property="aard_b" title="Aard" sortable="true" media="html xml csv excel"/>
                <display:column property="in_uit_aard" title="I/U" sortable="true" media="html xml csv excel" />
                <display:column property="uit_type_vos_b" title="Type VOS" sortable="true" media="html xml csv excel"/>
                <display:column property="brief_nr" title="OVAM briefnummer" href="briefdetails.do" paramId="brief_id" paramProperty="brief_id" sortable="true" class="center"  media="html"/>
                <display:column property="brief_nr" title="OVAM briefnummer" sortable="true" media="xml csv excel"/>
                <display:column property="betreft" title="Betreft" sortable="true" media="html"/>
                <display:column title="Dossier" sortable="true" sortProperty="dossier_nr" class="center" media="html">
                    <html:link action="dossierdetailsArt46" paramId="id" paramName="brief" paramProperty="dossier_id">
                        <bean:write name="brief" property="dossier_nr"/>
                    </html:link>
                </display:column>
                <display:column property="dossier_nr" title="Dossier" media="xml csv excel"/>
                <display:column property="doss_hdr_id" title="Doshdr" class="center"  sortable="true" media="html xml csv excel"/>
                <display:column property="gemeente_b" title="Gemeente" class="center"  sortable="true" media="html xml csv excel"/>
                <display:column property="adres_naam" title="Correspondent" sortable="true" class="center"/>
                <display:footer>
                    <html:form action="brieflijstfilter">
                        <td colspan="4"/>
                        <td>
                            <html:select property="aard_id" styleClass="input" onchange="this.form.submit()">
                                <html:option value="">&nbsp;</html:option>
                                <html:optionsCollection name="DDH" property="briefAard" label="brief_aard_b" value="brief_aard_id" />
                            </html:select>
                        </td>
                        <td colspan="3"/>
                    </html:form>
                </display:footer>
            </display:table>
        </td>
    </tr>
</table>