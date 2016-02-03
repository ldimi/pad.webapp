<%@ page language="java" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="custom" uri="/tags/custom"%>

<style>
.header_vastgelegd {
    background-color: #99FFFF;
}

.header_gepland {
    background-color: #FFFF33;
}
</style>

<div style="border: 1px solid;
            position: absolute;
            top: 40px;
            left: 0px;
            right: 0px;
            background-color: #fdfdfd;
            padding: 5px;" >

    <div style="padding-top: 5px;">
        <form:form modelAttribute="params" action="s/planning/jaar/detailoverzicht/vastgelegd/zoek" method="GET">
            <div>
                <form:select path="dossier_type"  class="input">
                    <form:option value="">Alle Dossiers</form:option>
                    <form:option value="A">Afval Dossiers</form:option>
                    <form:option value="X">Andere Dossiers</form:option>
                    <form:option value="B">Bodem Dossiers</form:option>
                </form:select>
                <form:select path="doss_hdr_id" class="input">
                    <option value="">Alle dossierhouders</option>
                    <form:options items="${DDH.wrap(DDH.dossierhouders)}" itemValue="m['doss_hdr_id']" itemLabel="m['doss_hdr_b']" />
                </form:select>
                <form:select path="programma_code" class="input">
                    <option value="">Alle programma's</option>
                    <form:options items="${DDH.wrap(DDH.programmaTypes)}" itemValue="m['code']" itemLabel="m['programma_type_b']" />
                </form:select>
                <form:select path="budget_code" class="input">
                    <option value="">Alle budgetten</option>
                    <form:options items="${DDH.wrap(DDH.budgetCodeDD)}" itemValue="m['value']" itemLabel="m['label']" />
                </form:select>
                <form:select path="fase_code" class="input">
                    <option value="">Alle fasen</option>
                    <form:options items="${DDH.wrap(DDH.faseDD)}" itemValue="m['value']" itemLabel="m['label']" />
                </form:select>
            </div>
            <div style="padding-top: 5px;" >
                vastgelegd van :
                <form:input path="benut_van_d" id=""/>
                tot :
                <form:input path="benut_tot_d" id=""/>
                <input type="submit" value="ophalen" class="inputbtn" />
            </div>
        </form:form>
    </div>


    <div>
        <display:table id="detailOverzichtTable" name="requestScope.detailoverzichtLijst" export="true" requestURI="s/planning/jaar/detailoverzicht/vastgelegd/zoek" defaultsort="1"
                pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >

            <%-- <display:column title="Lijn_id" sortable="true" property="lijn_id" /> --%>
            <display:column title="Dossier hdr" sortable="true" property="doss_hdr_id" />
            <display:column title="Doss. Type" sortable="true" property="dossier_type" />
            <display:column title="Doss. Nr" sortable="true" property="dossier_nr" />
            <display:column title="Doss. Titel" sortable="true" property="dossier_b" />
            <display:column title="Gemeente" sortable="true" property="dossier_gemeente_b" />
            <display:column title="Programma" sortable="true" property="programma_code" />
            <display:column title="Budget code" sortable="true" property="budget_code" />
            <display:column title="Fase" headerClass="header_gepland" sortable="true" property="fase_code" />
            <%-- <display:column title="contract_id" sortable="true" property="contract_id" /> --%>
            <display:column title="Dd gepland" headerClass="header_gepland" sortable="true" property="igb_d" decorator="be.ovam.art46.decorator.DateDecorator" />
            <display:column title="Dd benut/vastgelegd" headerClass="header_vastgelegd" sortable="true" property="ibb_d" decorator="be.ovam.art46.decorator.DateDecorator" />
            <display:column title="Benut/vastgelegd bedrag" headerClass="header_vastgelegd" sortable="true" property="ib_bedrag" class="number" decorator="be.ovam.art46.decorator.CurrencyDecorator" />
            <display:column title="Nr" headerClass="header_vastgelegd" sortable="true" property="volgnummer" />
        </display:table>
    </div>

    <div style="width: 600px; margin-left: 200px; margin-top: 20px;">
        <div style="float: left;">totaal gepland:</div>
        <div style="margin-left: 10px;float: left;">
            <input type="text" value="${totaal_gepland}" readonly="true" style="text-align:right" />
        </div>
        <div style="margin-left: 30px; float: left;">totaal benut/vastgelegd:</div>
        <div style="margin-left: 10px;float: left;">
            <input type="text" value="${totaal_vastgelegd}" readonly="true" style="text-align:right" />
        </div>
    </div>


</div>

<tiles:insertDefinition name="laadJS" />

<script type="text/javascript">
    laadBacking('planning/jaar/detailoverzichtBacking');
</script>

