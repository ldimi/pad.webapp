<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>

.accentueerClass {
    background-color: lightyellow;
    border-color: darkgray;
}

</style>

<html:form action="briefdetailssave">
    <input type="hidden" name="popup" value="yes" />
    <input type="hidden" name="bestek_id_org" value='<bean:write name="briefform" property="bestek_id" />' />
    <html:hidden property="brief_id" />
    <html:hidden property="dossier_id" />
    <html:hidden property="adres_id" />
    <html:hidden property="brief_nr" />
    <html:hidden property="volgnummer" />
    <html:hidden property="dms_id" />
    <html:hidden property="dms_filename" />
    <html:hidden property="dms_folder" />
    <html:hidden property="vordering_id" />
    <html:hidden property="parent_brief_id" />
    <table class="nopadding">
        <tr>
            <td class="nopadding">
                <table  bgcolor="#eeeeee" width="800">
                    <tr>
                        <th colspan="3" align="left">
                            Bestemmeling
                        </th>
                        <th align="right" width="180" >
                            <logic:notEmpty name="briefform" property="adres_id">
                                <html:link action="adres?crudAction=read" paramId="adres_id" paramName="briefform" paramProperty="adres_id">
                                    <html:img border="0" page="/resources/images/edit.gif" title="Adres Details"/>
                                </html:link>
                            </logic:notEmpty>
                        </th>
                    </tr>
                    <tr>
                        <td class="nopadding">
                            Naam
                        </td>
                        <td class="nopadding">
                            <html:text property="adres_naam" disabled="true" styleClass="input" size="80"/>
                            <html:text property="adres_voornaam" disabled="true" styleClass="input" size="80"/>
                        </td>
                        <td class="nopadding">
                            Contact
                        </td>
                        <td class="nopadding">
                            <html:select property="contact_id" styleClass="input">
                                <html:option value="0">&nbsp;</html:option>
                                <html:optionsCollection name="briefform" property="contactList" label="naam" value="contact_id" />
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td class="nopadding">
                            Adres
                        </td>
                        <td class="nopadding" colspan="2">
                            <html:text property="adres_straat" disabled="true" styleClass="input" size="50"/> <html:text property="adres_gemeente" disabled="true" styleClass="input"/>
                        </td>
                        <td class="nopadding" align="right">
                            <html:button styleClass="inputbtn" property="jos" onclick="popupWindow('startsearch.do?popup=yes&forwardURL=/briefdetailsview.do&searchFlag=briefadres&searchForward=success_briefadres', 'Adressen')">Zoeken</html:button>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="nopadding">
                <table  bgcolor="#eeeeee" width="800">
                    <tr>
                        <th align="left" colspan="2">
                            Dossier IVS
                        </th>
                        <th align="right">
                            <logic:notEmpty name="briefform" property="dossier_id_boa">
                                <html:link action="dossierdetails.do" paramId="dossier_id" paramName="briefform" paramProperty="dossier_id_boa">
                                    <html:img border="0" page="/resources/images/edit.gif" title="Dossier Details"/>
                                </html:link>
                            </logic:notEmpty>
                        </th>
                    </tr>
                    <tr>
                        <td class="nopadding" valign="top">
                            <table >
                                <tr>
                                    <td class="nopadding" valign="top">
                                        Dossier type IVS:
                                    </td>
                                    <td class="nopadding">
                                        <html:hidden property="dossier_type_ivs"/>
                                        <html:checkbox property="dossier_type_ivs" value="A" styleClass="checkbox" disabled="true">Afval</html:checkbox><br/>
                                        <html:checkbox property="dossier_type_ivs" value="X" styleClass="checkbox" disabled="true">Ander</html:checkbox><br/>
                                        <html:checkbox property="dossier_type_ivs" value="B" styleClass="checkbox" disabled="true">Bodem</html:checkbox>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td class="nopadding" valign="top">
                            <table >
                                <logic:notEmpty name="briefform" property="dossier_id">
                                    <tr>
                                        <td class="nopadding">
                                            Dossiernummer &nbsp;
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="dossier_nr" disabled="true" styleClass="input" size="8"/>
                                        </td>
                                        <td class="nopadding">
                                            &nbsp;Dossierhouder &nbsp;
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="dossier_hdr_ivs" disabled="true" styleClass="input">
                                                <html:option  value=""/>
                                                <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Dossiernaam
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="dossier_b_ivs" disabled="true" styleClass="input" size="39"/>
                                        </td>
                                        <td class="nopadding">
                                            &nbsp;Gemeente
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="nis_id" disabled="true" styleClass="input">
                                                <html:option  value=""/>
                                                <html:optionsCollection name="DDH" property="fusiegemeenten" label="gemeente_b" value="nis_id" />
                                            </html:select>
                                        </td>
                                    </tr>
                                </logic:notEmpty>
                            </table>
                        </td>
                        <logic:empty name="briefform" property="brief_nr">
                            <td class="nopadding" valign="bottom" align="right">
                                <html:button styleClass="inputbtn" property="" onclick="popupWindow('startsearch.do?popup=yes&forwardURL=/briefdetailsview.do&searchFlag=briefdossier&searchForward=success_briefdossieralle', 'Dossiers');">Zoeken</html:button>
                            </td>
                        </logic:empty>
                    </tr>

                </table>
            </td>
        </tr>
        <logic:notEmpty name="briefform" property="dossier_id">
            <logic:notEqual name="briefform" property="dossier_id" value="-1">
            <tr>
                <td class="nopadding">
                    <table bgcolor="#eeeeee" width="800" >
                        <tr>
                            <th align="left" colspan="2" >
                                Brief details: &nbsp;&nbsp;
                                <span style="font-size:20">
                                    <a href='/pad/dossierdetailsArt46.do?id=<bean:write name="briefform" property="dossier_id"/>&selectedtab=Brieven&expand=bcategorie<bean:write name="briefform" property="categorie_id"/>' >
                                        <bean:write name="briefform" property="brief_nr" />
                                    </a>
                                </span>
                            </th>
                        </tr>
                        <tr>
                            <td class="nopadding">

                                <table >
                                    <tr>
                                        <td class="nopadding" valign="top">
                                            Datum van inschrijving
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="inschrijf_d" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding" valign="top">
                                            Reactie voor
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="reactie_voor_d" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding" valign="top">
                                            Reactie op
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="reactie_d" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Auteur
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="auteur_id" disabled="false" styleClass="input">
                                                <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Betreft
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="betreft" styleClass="input" size="39"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Categorie
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="categorie_id" styleClass="input">
                                                <html:optionsCollection name="briefform" property="categorieen" label="brief_categorie_b" value="brief_categorie_id" />
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            QR-code: IVS-I
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="qr_code" styleClass="input" disabled="${briefform.parent_brief_id != null}"  errorStyleClass="errorclass" size="40"/>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td class="nopadding" align="left">
                                <table>
                                    <tr>
                                        <td>Bestek:</td>
                                        <td width="350" >
                                            <html:select name="briefform" property="bestek_id" disabled="${briefform.vordering_id != null}" styleClass="input accentueerClass" errorStyleClass="errorclass">
                                                <html:option value="" />
                                                <html:options collection="briefdetailsopenbesteklijst" property="bestek_id" labelProperty="bestek_nr" />
                                            </html:select>
                                        </td>
                                    </tr>
                                </table>

                                <html:textarea property="commentaar" rows="8" cols="60" styleClass="input"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="nopadding" valign="top">
                                <table width="100%">
                                    <tr>
                                        <th class="nopadding" colspan="2">Binnenkomend</th>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Aard
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="in_aard_id" styleClass="input" disabled="false" errorStyleClass="errorclass">
                                                <html:option value="">&nbsp;</html:option>
                                                <html:optionsCollection name="DDH" property="briefAard" label="brief_aard_b" value="brief_aard_id" />
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Type
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="in_type_id" styleClass="input">
                                                <html:optionsCollection name="DDH" property="briefType" label="brief_type_b" value="brief_type_id" />
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Datum OVAM &nbsp;
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="in_d" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Datum Stuk
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="in_stuk_d" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Referte
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="in_referte" styleClass="input" size="40"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Bijlagen
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="in_bijlage" styleClass="input" size="40"/>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td class="nopadding" align="right" valign="top" width="50%">
                                <table width="100%">
                                    <tr>
                                        <th class="nopadding" colspan="2">Uitgaand</th>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Aard
                                        </td>
                                        <td class="nopadding">
                                            <logic:equal name="briefform" property="isUitAardEnabled" value="true">
                                                <html:select property="uit_aard_id" styleClass="input" errorStyleClass="errorclass">
                                                    <html:option value="">&nbsp;</html:option>
                                                    <html:optionsCollection name="DDH" property="briefAard" label="brief_aard_b" value="brief_aard_id" />
                                                </html:select>
                                            </logic:equal>
                                            <logic:equal name="briefform" property="isUitAardEnabled" value="false">
                                                <html:select property="uit_aard_id" styleClass="input" disabled="true">
                                                    <html:option value="">&nbsp;</html:option>
                                                    <html:optionsCollection name="DDH" property="briefAard" label="brief_aard_b" value="brief_aard_id" />
                                                </html:select>
                                            </logic:equal>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Type
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="uit_type_id" styleClass="input">
                                                <html:optionsCollection name="DDH" property="briefType" label="brief_type_b" value="brief_type_id" />
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Datum OVAM &nbsp;
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="uit_d" styleClass="input" size="10" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Bijlagen
                                        </td>
                                        <td class="nopadding">
                                            <html:text property="uit_bijlage" styleClass="input" size="40"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="nopadding">
                                            Type Vos
                                        </td>
                                        <td class="nopadding">
                                            <html:select property="uit_type_id_vos" styleClass="input">
                                                <html:option value="">&nbsp;</html:option>
                                                <html:optionsCollection name="DDH" property="briefTypeVos" label="type_b" value="type_id" />
                                            </html:select>
                                        </td>
                                    </tr>



                                    <logic:empty name="briefform" property="dms_id">
                                        <html:hidden property="teprinten_jn" />
                                    </logic:empty>
                                    <logic:notEmpty name="briefform" property="dms_id">
                                        <logic:empty name="briefform" property="uit_aard_id">
                                            <html:hidden property="teprinten_jn" />
                                        </logic:empty>
                                        <logic:notEmpty name="briefform" property="uit_aard_id">
                                            <tr>
                                                <td class="nopadding">
                                                    Te printen ?
                                                </td>
                                                <td class="nopadding">
                                                    <html:select property="teprinten_jn" styleClass="input">
                                                        <html:option value="J">Ja</html:option>
                                                        <html:option value="N">Nee</html:option>
                                                    </html:select>
                                                </td>
                                            </tr>
                                        </logic:notEmpty>
                                    </logic:notEmpty>




                                    <logic:empty name="briefform" property="print_d">
                                        <html:hidden property="print_d" />
                                    </logic:empty>
                                    <logic:notEmpty name="briefform" property="print_d">
                                        <tr>
                                            <td class="nopadding">
                                                Datum geprint
                                            </td>
                                            <td class="nopadding">
                                                <html:text property="print_d" styleClass="input"  disabled="true"  size="10"/>
                                            </td>
                                        </tr>
                                    </logic:notEmpty>


                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="nopadding">
                                <logic:empty name="briefform" property="vordering_id">
                                    <input type="checkbox"  disabled="disabled">Is schuldvordering&nbsp;&nbsp;&nbsp;
                                </logic:empty>
                                <logic:notEmpty name="briefform" property="vordering_id">
                                    <input type="checkbox" checked="checked"  disabled="disabled">Is schuldvordering&nbsp;&nbsp;&nbsp;
                                    <logic:present role="secretariaat">
                                        <c:if test="${briefform.vordering_definitief_jn == 'N'}">
                                                <a href="/pad/s/brief/${briefform.brief_id}/schuldvordering/verwijder/${briefform.vordering_id}" >
                                                    Verwijder_schuldvordering
                                                </a>
                                        </c:if>
                                    </logic:present>
                                </logic:notEmpty>
                                <input type="button" value="Schuldvordering" id="schuldvorderingBtn" class="inputbtn" />

                            </td>
                            <td class="nopadding" align="right">
                                <logic:notEqual name="briefform" property="brief_id" value="0">
                                    <input type="button" value="Verwijderen" id="verwijderBtn" class="inputbtn" />
                                </logic:notEqual>
                                <logic:empty name="briefform" property="parent_brief_id" >
                                    <input type="button" value="Scan Opladen" class="inputbtn" onclick="popupWindow('<html:rewrite action="briefuploadview"/>?&fromTree=false&popup=yes&parent_brief_id=<bean:write name="briefform" property="brief_id" />','Brief upload')"/>
                                </logic:empty>
                                <logic:empty name="briefform" property="dms_id">
                                    <input type="button" value="Brief Opladen" class="inputbtn" onclick="popupWindow('<html:rewrite action="briefuploadview"/>?&popup=yes&fromTree=false&brief_id=<bean:write name="briefform" property="brief_id" />','Brief upload')"/>
                                </logic:empty>
                                <html:submit styleClass="inputbtn">Aanpassen</html:submit>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            </logic:notEqual>
        </logic:notEmpty>
    </table>
</html:form>

<logic:notEmpty name="briefform" property="parent_brief_id">
    <div>
         Document :
         <a href='<%=System.getProperty("ovam.dms.webdrive.base") %>/<bean:write name="briefform" property="dms_folder"/>/<bean:write name="briefform" property="dms_filename"/>'
            target="_blank" style="text-decoration: none;">
            <img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
         </a>
    </div>
    <div>
        Dit document in een scan gekoppeld aan deze
        <a href='briefdetails.do?brief_id= <bean:write name="briefform" property="parent_brief_id"/>'
            target="_blank">
            Brief
        </a>
    </div>
</logic:notEmpty>

<logic:empty name="briefform" property="parent_brief_id">
    <div id="documenten_div">
    </div>
</logic:empty>

<tiles:insert page="../schuldvordering/editSchuldVorderingDialog.jsp" />
<tiles:insert page="../schuldvordering/maakInSapDialog.jsp" />


<tiles:insert definition="laadJS" />

<script type="text/javascript">
    _G_.brief_id = 83061;
    _G_.dms_webdrive_base = '<%=System.getProperty("ovam.dms.webdrive.base")%>';

    laadBacking('brief/briefdetailsBacking');
</script>
