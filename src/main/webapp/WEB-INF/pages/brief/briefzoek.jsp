<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>


    <form action="/pad/s/zoekVoorId" method="GET">
        <table>
        <tr>
            <td>
                <table class="windowfree" width="400">
                    <tr>
                        <th colspan="4" align="left">
                            Zoek voor schuldvordering of voorstel deelopdracht bij Id
                        </th>
                    </tr>
                    <tr>
                        <td>
                            ID
                        </td>
                        <td>
                            <input type="text" name="id" value="" styleClass="input"/>
                        </td>
                    </tr>
                    <tr><td><input type="submit" value="zoek"/></td></tr>
                </table>
            </td>
        </tr>
        </table>
    </form>

<html:form action="briefzoekresult">
    <table>
        <tr>
            <td>
                <table class="windowfree" width="400">
                    <tr>
                        <th colspan="4" align="left">
                            Bestek
                        </th>
                    </tr>
                    <tr>
                        <td>
                            Besteknummer
                        </td>
                        <td>
                            <html:text property="bestek_nr" styleClass="input"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table class="windowfree" width="400">
                    <tr>
                        <th colspan="4" align="left">
                            Bestemmeling
                        </th>
                    </tr>
                    <tr>
                        <td>
                            Naam
                        </td>
                        <td>
                            <html:text property="adres_naam" styleClass="input"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>


        <tr>
            <td>
                <table class="windowfree" width="400">
                    <tr>
                        <th colspan="2" align="left">
                            Dossier IVS
                        </th>
                    </tr>
                    <tr>
                        <td>
                            Dossiernummer IVS
                        </td>
                        <td>
                            <html:text property="dossier_nr" styleClass="input" size="8"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Dossiernummer BB
                        </td>
                        <td>
                            <html:text property="dossier_id_boa" styleClass="input" size="8"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Dossiernaam
                        </td>
                        <td>
                            <html:text property="dossier_b_ivs" styleClass="input" size="39"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Gemeente
                        </td>
                        <td>
                            <html:text property="dossier_gemeente" styleClass="input" size="39"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Dossierhouder
                        </td>
                        <td>
                            <html:select property="dossier_hdr_ivs" styleClass="input">
                                <html:option value="" />
                                <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Fase
                        </td>
                        <td>
                            <html:select property="dossier_fase_id" styleClass="input">
                            <html:option value="">&nbsp;</html:option>
                                <html:optionsCollection name="DDH" property="dossierFasen_dd" label="fase_b_l" value="value" />
                            </html:select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table class="windowfree" width="400px">
                    <tr>
                        <th colspan="3" align="left">
                            Brief details
                        </th>
                    </tr>
                    <tr>
                        <td>
                            Datum van inschrijving
                        </td>
                        <td>
                            van <html:text property="inschrijf_d_van" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                        </td>
                        <td>
                            tot <html:text property="inschrijf_d_tot" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Datum stuk
                        </td>
                        <td>
                            van <html:text property="in_d_van" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                        </td>
                        <td>
                            tot <html:text property="in_d_tot" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Datum uit
                        </td>
                        <td>
                            van <html:text property="uit_d_van" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                        </td>
                        <td>
                            tot <html:text property="uit_d_tot" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Betreft
                        </td>
                        <td colspan="2">
                            <html:text property="betreft" styleClass="input" size="39"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Referte
                        </td>
                        <td colspan="2">
                            <html:text property="in_referte" styleClass="input" size="39"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            OVAM nr.
                        </td>
                        <td colspan="2">
                            <html:text property="brief_nr" styleClass="input" size="39"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Auteur
                        </td>
                        <td colspan="2">
                            <html:select property="auteur_id" disabled="false" styleClass="input">
                                <html:option value=""></html:option>
                                <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Aard In
                        </td>
                        <td colspan="2">
                            <html:select property="in_aard_id" disabled="false" styleClass="input">
                                <html:option value=""></html:option>
                                <html:optionsCollection name="DDH" property="briefAard" label="brief_aard_b" value="brief_aard_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Aard Uit
                        </td>
                        <td colspan="2">
                            <html:select property="uit_aard_id" disabled="false" styleClass="input">
                                <html:option value=""></html:option>
                                <html:optionsCollection name="DDH" property="briefAard" label="brief_aard_b" value="brief_aard_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            QR code
                        </td>
                        <td colspan="2">
                            <html:text property="qr_code" styleClass="input" size="39"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td align="right">
                <html:submit styleClass="inputbtn">Zoeken</html:submit>
            </td>
        </tr>
    </table>
</html:form>