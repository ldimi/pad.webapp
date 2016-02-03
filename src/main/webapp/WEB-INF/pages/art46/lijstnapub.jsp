<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
    <body onload='sumdossier(new Array("sumog","sumig"), new Array("lijstnapub","lijstnapub"), new Array(7,8));sumkadaster(new Array("sumogk","sumigk"), new Array("lijstnapub","lijstnapub"), new Array(7,8))'>
</logic:equal>
<logic:equal name="art46selectform" property="forward" scope="session" value="dossier">
    <body onload='sumdossier(new Array("sumog","sumig"), new Array("lijstnapub","lijstnapub"), new Array(6,7))'>
</logic:equal>
<div style="padding-left: 4px;">
    <table cellpadding='0' cellspacing='0' bgcolor="#eeeeee">
        <html:form action="lijstnapubselect">
        <tr valign='middle'>
            <td valign='middle'>
                <html:select property="artikelid" styleClass="input">
                    <html:option value="-1">Alle Artikels</html:option>
                    <html:optionsCollection name="DDH" property="artikels" label="artikel_b" value="artikel_id" />
                </html:select>
                <html:select property="lijst_id" styleClass="input">
                    <html:optionsCollection name="DDH" property="lijsten" label="lijst_b" value="lijst_id" />
                </html:select>
            </td>
            <td>
                <html:radio property="forward" value="kadaster" styleClass="radio">Kadaster</html:radio>
            </td>
            <td>
                <html:radio property="forward" value="dossier" styleClass="radio">Dossier</html:radio>
            </td>
            <td valign='middle'>
                <html:submit styleClass="inputbtn">
                    <bean:message key="lijst.napub.button.ophalen" />
                </html:submit>
            </td >
        </tr>
        </html:form>
    </table>
    <br/>
    <display:table class="lijst width980" htmlId="lijstnapub"  id="dossierkadaster" name="sessionScope.lijst" requestURI="/lijstnapubview.do" export="true" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' defaultsort="1">
        <display:column class="center" titleKey="lijst.napub.header.artikel" sortProperty="artikel_b" media="html">
            <logic:equal name="art46selectform" property="artikelid" value='-1'>
                <bean:write name="dossierkadaster" property="artikel_b"/>
            </logic:equal>
        </display:column>
        <display:column  title="IVS" sortable="true" group="1" media="html">
            <bean:write name="dossierkadaster" property="gemeente_b"/>
            <logic:notEmpty name="dossierkadaster" property="dossier_b">
                <html:link action="dossierdetailsArt46" paramId="id" paramName="dossierkadaster" paramProperty="id">
                    <bean:write name="dossierkadaster" property="dossier_b"/>
                </html:link>
            </logic:notEmpty>
        </display:column>
        <logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
            <display:column class="center" titleKey="lijst.opname.header.kadaster" property="kadaster_id_l" href="kadasterdetails.do" paramId="kadaster_id" paramProperty="kadaster_id"  sortable="true" media="html"/>
        </logic:equal>
        <display:column class="center" titleKey="lijst.opname.header.dossier" property="dossier_id_boa" href="dossierdetailsArt46.do?selectedtab=Basis" paramId="id" paramProperty="id" sortable="true"/>
        <display:column class="center" titleKey="lijst.voorgoedk.header.doss_hdr_id" property="doss_hdr_id" sortable="true" media="html"/>
        <display:column class="center" property="goedkeuring_d" titleKey="lijst.nagoedk.header.goedkeuring_d" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" media="html"/>
        <display:column class="center" property="publicatie_d" titleKey="lijst.napub.header.publicatie_d" sortable="true" decorator="be.ovam.art46.decorator.DateDecorator" media="html"/>
        <display:column class="center" titleKey="lijst.voorgoedk.header.ob" sortProperty="onschuldige_eig_s" sortable="true" media="html">
            <html:checkbox name="dossierkadaster" property="onschuldige_eig_s" value="1" styleClass="checkbox" disabled="true"/>
            <logic:equal name="dossierkadaster" property="onschuldige_eig_s" value="1" >
                <div class="small" name='sumog' id='sumog'><bean:write name="dossierkadaster" property="dossier_id"/>d1</div>
                <logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
                    <div class="small" name='sumogk' id='sumogk'><bean:write name="dossierkadaster" property="kadaster_id"/>k1</div>
                </logic:equal>
            </logic:equal>
        </display:column>
        <display:column class="center" titleKey="lijst.voorgoedk.header.ig" sortProperty="ingebreke_stel_s" sortable="true" media="html">
            <html:checkbox name="dossierkadaster" property="ingebreke_stel_s" value="1" styleClass="checkbox" disabled="true"/>
            <logic:equal name="dossierkadaster" property="ingebreke_stel_s" value="1" >
                <div class="small" name='sumig' id='sumig'><bean:write name="dossierkadaster" property="dossier_id"/>d1</div>
                <logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
                    <div class="small" name='sumigk' id='sumigk'><bean:write name="dossierkadaster" property="kadaster_id"/>k1</div>
                </logic:equal>
            </logic:equal>
        </display:column>
        <display:column title="Dossiernaam IVS" property="dossier_b" media="csv excel pdf xml" />
        <logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
            <display:column title="Kadasterafdeling" property="kadaster_afd_b" media="csv excel pdf xml" />
            <display:column title="Sectie" property="sectie" media="csv excel pdf xml" />
            <display:column title="Grondnummer" property="grondnummer" media="csv excel pdf xml" />
            <display:column title="Bisnummer" property="bisnummer" media="csv excel pdf xml" />
            <display:column title="Exponent1" property="exponent1" media="csv excel pdf xml" />
            <display:column title="Exponent2" property="exponent2" media="csv excel pdf xml" />
        </logic:equal>
        <display:column title="Artikel" property="artikel_b" media="csv excel pdf xml" />
        <display:column title="Datum goedkeuring" media="csv excel pdf xml">
            <bean:write name="dossierkadaster" property="goedkeuring_d" formatKey="date.format" filter="false"/>
        </display:column>
        <display:column title="Datum publicatie" media="csv excel pdf xml">
            <bean:write name="dossierkadaster" property="publicatie_d" formatKey="date.format" filter="false"/>
        </display:column>
        <display:column title="Dossierhouder" property="ambtenaar_b" media="csv excel pdf xml" />
        <display:column title="OE" property="onschuldige_eig_s" media="csv excel pdf xml" />
        <display:column title="IG" property="ingebreke_stel_s" media="csv excel pdf xml" />
    </display:table>
</div>
