<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<html:form action="dossierart46zoekresult">
    <logic:equal parameter="popup" value="yes">
        <input type="hidden" name="popup" value="yes" />
    </logic:equal>
    <table>
        <tr>
            <td>
                <table class="windowfree">
                    <tr>
                        <th colspan="4">
                            Zoek dossier ...
                        </th>
                    </tr>
                    <tr>
                        <td>Dossier type</td>
                        <td>
                            <html:select property="dossier_type" styleClass="input">
                                <html:option value="" />
                                <html:option value="B">Bodem</html:option>
                                <html:option value="A">Afval</html:option>
                                <html:option value="X">Ander</html:option>
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Besteknummer
                        </td>
                        <td>
                            <html:text property="bestek_nr" styleClass="input"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Dossier nummer IVS
                        </td>
                        <td>
                            <html:text property="dossier_nr" styleClass="input" />
                        </td>
                        <td>
                            Dossierhouder IVS
                        </td>
                        <td>
                            <html:select property="doss_hdr_id" styleClass="input">
                                <html:option value="" />
                                <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Dossier nummer BB
                        </td>
                        <td>
                            <html:text property="dossier_id_boa" styleClass="input" />
                        </td>
                        <td>
                            Dossierhouder BB
                        </td>
                        <td>
                            <html:select property="doss_hdr_id_boa" styleClass="input">
                                <html:option value="" />
                                <html:optionsCollection name="DDH" property="ambtenarenBOA" label="ambtenaar_b" value="ambtenaar_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Dossier nummer JD
                        </td>
                        <td>
                            <html:text property="dossier_id_jd" styleClass="input" />
                        </td>
                        <td>
                            Dossierhouder JD
                        </td>
                        <td>
                            <html:select property="doss_hdr_id_jd" styleClass="input">
                                <html:option value=""></html:option>
                                <html:optionsCollection name="DDH" property="ambtenarenJD" label="ambtenaar_b" value="ambtenaar_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Dossiernaam IVS
                        </td>
                        <td>
                            <html:text property="dossier_b" styleClass="input" size="40"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            SAP Projectnr
                        </td>
                        <td>
                            <html:text property="sap_project_nr" styleClass="input"/>
                        </td>
                        <td>
                            SAP twaalfnr
                        </td>
                        <td>
                            <html:text property="project_id" styleClass="input"/>
                        </td>
                    </tr>
                    <tr>
                         <%-- rechtsgrond en fase dd zijn afhankelijk van type --%>
                    </tr>
                    <tr>
                        <td>Programma</td>
                        <td>
                            <html:select property="programma_code" styleClass="input">
                                <html:option value="">&nbsp;</html:option>
                                <html:optionsCollection name="DDH" property="programmaTypes" label="programma_type_b" value="code" />
                            </html:select>
                        </td>
                        <td>Doelgroep</td>
                        <td>
                            <html:select property="doelgroep_type_id" styleClass="input">
                                <html:option value="" />
                                <html:optionsCollection name="DDH" property="doelgroepen_dd" label="label" value="value" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Adres
                        </td>
                        <td>
                            <html:text property="adres" styleClass="input"/>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            Provincie
                        </td>
                        <td>
                            <html:select property="provincie" styleClass="input">
                                <html:optionsCollection name="provincies" />
                            </html:select>
                        </td>
                        <td>
                            Fusiegemeente
                        </td>
                        <td>
                            <html:select property="nis_id" styleClass="input">
                                <html:option value="" />
                                <html:optionsCollection name="DDH" property="fusiegemeenten" label="gemeente_b" value="nis_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="checkbox" name="incl_afgesloten_s" value="1"  />Inclusief afgesloten dossiers
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="checkbox" name="ig_s" value="1"  />Enkel Ingebreke stelling
                        </td>
                        <td>
                            Artikel 46
                        </td>
                        <td>
                            <div>
                                <div style="width: 135px; float: left;">
                                    <html:select property="art46_lijst_id" styleClass="input">
                                        <html:option value="">&nbsp;</html:option>
                                        <html:option value="-1">Alle Artikels</html:option>
                                        <html:optionsCollection name="DDH" property="artikels" label="artikel_b" value="artikel_id"/>
                                    </html:select>
                                </div>
                                <div style="width: 135px; float: left;">
                                    <html:select property="historiek_lijst_id" styleClass="input">
                                        <html:option value="">&nbsp;</html:option>
                                        <html:optionsCollection name="DDH" property="lijsten" label="lijst_b" value="lijst_id"/>
                                    </html:select>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="checkbox" name="ob_s" value="1"  />Enkel Onschuldige eigenaar
                        </td>
                        <td colspan="2">
                            <input type="checkbox" name="aanpak_onderzocht_s" value="1"  />Geintegreerde oplossing
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="checkbox" name="ivs_s" value="1"  />IVS dossiers
                        </td>
                        <td colspan="2">
                            <input type="checkbox" name="aanpak_s" value="1"  />Dossier opgestart
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td align='right'>
                <html:submit styleClass="inputbtn">
                    <bean:message key="dossier.zoek.button" />
                </html:submit>
            </td>
        </tr>
    </table>

</html:form>