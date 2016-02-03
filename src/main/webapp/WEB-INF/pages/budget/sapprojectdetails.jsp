<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<table>
    <tr>
        <th>
            Details SAP project fiche
        </th>
    </tr>
    <tr>
        <td class="nopadding">
            <display:table uid="projecten" htmlId="projecten" id="project" name="requestScope.sapproject" class="planning" requestURI="./sapprojectdetails.do" export="true">
                <display:column property="project_id" title="Fiche Nr." class="center" />
                <display:column property="project_b" title="Omschrijving" class="center" />
                <display:column property="budgetair_artikel" title="Artikel" class="center" />
                <display:column property="budgetair_artikel_b" title="" class="center" />
                <display:column property="schuldeiser" title="Schuldeiser" class="center" />
                <display:column property="contactpersoon" title="Dossierhouder" class="center" />
                <display:setProperty name="basic.msg.empty_list_row" value=""/>
            </display:table>
        </td>
    </tr>
    <tr>
        <td height="20"></td>
    </tr>
    <tr>
        <th>
            Lijst Vastleggingen
        </th>
    </tr>
    <tr>
        <td class="nopadding">
            <display:table uid="vastlegging" htmlId="vastlegging" id="vastlegging" name="requestScope.sapprojectvastlegginglijst" class="planning" requestURI="./sapprojectdetails.do" export="true">
                <display:column property="vastlegging_id"  title="Vastlegging nr." class="center" />
                <display:column property="bedrag" title="Bedrag" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator" />
                <display:column property="datum" title="Datum" class="center" decorator="be.ovam.art46.decorator.DateDecorator" />
                <display:setProperty name="basic.msg.empty_list_row" value=""/>
            </display:table>
        </td>
    </tr>
    <tr>
        <td height="20"></td>
    </tr>
    <tr>
        <th>
            Lijst Facturen
        </th>
    </tr>
    <tr>
        <td class="nopadding">
            <display:table uid="facturen" htmlId="facturen" id="factuur" name="requestScope.sapprojectfactuurlijst" class="planning" requestURI="./sapprojectdetails.do" export="true">
                <display:column property="factuur_id"  title="Factuur nr." class="center" />
                <display:column property="bedrag" title="Bedrag" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator" />
                <display:column property="factuur_d" title="Factuurdatum" class="center" decorator="be.ovam.art46.decorator.DateDecorator" />
                <display:column property="betaal_d" title="Betaaldatum" class="center" decorator="be.ovam.art46.decorator.DateDecorator" />
                <display:column property="dossier_id" title="Dossier" class="center"/>

                <display:setProperty name="basic.msg.empty_list_row" value=""/>
            </display:table>
        </td>
    </tr>
    <tr>
        <td height="20"></td>
    </tr>
    <tr>
        <th>
            Lijst Ordonnanceringen
        </th>
    </tr>
    <tr>
        <td class="nopadding">
            <display:table id="ordonnancering" name="requestScope.sapprojectordonnanceringlijst" class="planning"  requestURI="./sapprojectdetails.do" export="true">
                <display:column property="volgnummer"  title="Volgnummer"  />
                <display:column property="jaar" title="Jaar" />
                <display:column property="waarde" title="Waarde" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator" />
                <display:setProperty name="basic.msg.empty_list_row" value=""/>
            </display:table>
        </td>
    </tr>
</table>