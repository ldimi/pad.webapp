<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>

<logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
    <body onload='sumdossier(new Array("sumog","sumig"), new Array("lijstvoorstel","lijstvoorstel"), new Array(5,6));sumkadaster(new Array("sumogk","sumigk"), new Array("lijstvoorstel","lijstvoorstel"), new Array(5,6))'>
</logic:equal>
<logic:equal name="art46selectform" property="forward" scope="session" value="dossier">
    <body onload='sumdossier(new Array("sumog","sumig"), new Array("lijstvoorstel","lijstvoorstel"), new Array(4,5))'>
</logic:equal>
<table cellpadding='0' cellspacing='0' bgcolor="#eeeeee">
    <html:form action="lijstvoorgoedkselect">
    <tr valign='middle'>
        <td valign='middle'>
            <html:select property="artikelid" styleClass="input">
                <html:option value="-1">Alle Artikels</html:option>
                <html:optionsCollection name="DDH" property="artikels" label="artikel_b" value="artikel_id" />
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
                <bean:message key="lijst.voorgoedk.button.ophalen" />
            </html:submit>
        </td >
        <td>
            <img src='<html:rewrite page="/"/>/resources/images/add.gif' alt="Lijst personen" onclick="popupWindow('<html:rewrite action="lijstvoorgoedkpersoon"/>?popup=yes', 'Personen')"/>
        </td>
    </tr>
    </html:form>
</table>
<html:form action="updatedossierkadasters">
    <html:hidden property="artikelid" />
    <html:hidden property="goedkeuring_s" value="0" />
    <display:table class="lijst width980" htmlId="lijstvoorstel"  id="dossierkadaster" name="sessionScope.lijst" requestURI="/lijstvoorgoedkview.do" export="true" pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' defaultsort="1">
        <logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
            <logic:equal name="art46selectform" property="artikelid" value='-1'>
                <display:column property="artikel_b" class="center" titleKey="lijst.opname.header.artikel" sortable="true" media="html"/>
            </logic:equal>
            <logic:notEqual name="art46selectform" property="artikelid" value='-1'>
                <display:column class="center" titleKey="lijst.opname.header.leeg" media="html">
                    <html:multibox styleClass="checkbox" property="dossierkadasters" >
                        <bean:write name="dossierkadaster" property="dossierkadaster"/>
                    </html:multibox>
                </display:column>
            </logic:notEqual>
        </logic:equal>
        <logic:equal name="art46selectform" property="forward" scope="session" value="dossier">
            <display:column property="artikel_b" class="center" titleKey="lijst.opname.header.artikel" sortable="true" media="html"/>
        </logic:equal>
        <display:column  title="IVS" sortable="true" group="1" media="html">
            <bean:write name="dossierkadaster" property="gemeente_b"/>
            <logic:notEmpty name="dossierkadaster" property="dossier_b">
                <html:link action="dossierdetailsArt46" paramId="id" paramName="dossierkadaster" paramProperty="dossier_id">
                    <bean:write name="dossierkadaster" property="dossier_b"/>
                </html:link>
            </logic:notEmpty>
        </display:column>
        <logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
            <display:column class="center" titleKey="lijst.opname.header.kadaster" property="kadaster_id_l" href="kadasterdetails.do" paramId="kadaster_id" paramProperty="kadaster_id"  sortable="true" media="html"/>
        </logic:equal>
        <display:column class="center" titleKey="lijst.opname.header.dossier" property="dossier_id_boa" href="dossierdetailsArt46.do?selectedtab=Basis" paramId="id" paramProperty="dossier_id" sortable="true"/>
        <display:column class="center" titleKey="lijst.voorgoedk.header.doss_hdr_id" property="doss_hdr_id" sortable="true" media="html"/>
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
        <display:column title="Dossierhouder" property="ambtenaar_b" media="csv excel pdf xml" />
        <display:column title="OE" property="onschuldige_eig_s" media="csv excel pdf xml" />
        <display:column title="IG" property="ingebreke_stel_s" media="csv excel pdf xml" />
    </display:table>
    <logic:equal name="art46selectform" property="forward" scope="session" value="kadaster">
        <logic:present role="adminArt46">
            <logic:notEqual name="art46selectform" property="artikelid" value='-1'>
                <html:submit styleClass="inputbtn" value="<<" title="Percelen verwijderen van de lijst voorstel"/>
                <input type="button" value="Alle percelen selecteren" onClick="checkAll(this.form.dossierkadasters)" class="inputbtn"/>
                <html:submit styleClass="inputbtn" value=">>" title="Percelen goedkeuren" onclick="setActionAndName(this.form,'lijstvoorgoedkdate.do','setdateform')" />
            </logic:notEqual>
        </logic:present>
    </logic:equal>
</html:form>
