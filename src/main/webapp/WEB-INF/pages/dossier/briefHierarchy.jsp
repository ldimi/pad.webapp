<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/treetag" prefix="tree" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="action" value="./dossierdetails.do?selectedtab=Brieven"/>
<c:set var="forward" value="/dossierdetails.do?selectedtab=Brieven"/>


<c:set var="showAanmakenBrief" value="${( dossierart46form.dossier_type == 'X' and not empty briefFilterParams.bestek_id) or  dossierart46form.dossier_type != 'X'}" />

<form method="post" action="/pad/s/dossier/${dossierart46form.id}/brieven">
    <table cellspacing="0" cellpadding="0" border="0" class="nopadding">
        <tr>
            <td>
                Type:
                <html:select name="briefFilterParams" property="uit_type_id_vos" styleClass="input">
                    <html:option value="">&nbsp;</html:option>
                    <html:optionsCollection name="DDH" property="briefTypeVos" label="type_b" value="type_id" />
                </html:select>
            </td>
            <td>
                Bestek:
                <html:select name="briefFilterParams" property="bestek_id" styleClass="input">
                    <html:option value="" />
                    <html:optionsCollection name="dossierdetailsopenbesteklijst" label="bestek_nr" value="bestek_id" />
                </html:select>
            </td>
            <td>
                Van:
                <html:text maxlength="10" size="10" styleClass="input" name="briefFilterParams" property="datum_van" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
            </td>
            <td>
                Tot:
                <html:text maxlength="10" size="10" styleClass="input" name="briefFilterParams" property="datum_tot" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
            </td>
            <td>
                <html:submit value="Filter brieven" styleClass="inputbtn"/>
            </td>
        </tr>
    </table>
</form>
<table cellspacing="0" cellpadding="0" border="0" class="nopadding">
    <tree:tree tree="briefTree" node="example.node" includeRootNode="false">
        <tr>
        <td class="nopadding">
        <table cellspacing="0" cellpadding="0" border="0" class="nopadding">
        <tr>
            <td class="nopadding">
                <tree:nodeIndent    node="example.node" indentationType="type">
                    <tree:nodeIndentVerticalLine indentationType="type" >
                        <img src="./resources/images/verticalLine.gif">
                    </tree:nodeIndentVerticalLine>
                    <tree:nodeIndentBlankSpace   indentationType="type" >
                        <img src="./resources/images/blankSpace.gif">
                    </tree:nodeIndentBlankSpace>
                </tree:nodeIndent>
            </td>
            <tree:nodeMatch    node="example.node" expanded="false" hasChildren="true"  isLastChild="false">
                <td class="nopadding">
                    <a href="${action}&view=true&expand=<tree:nodeId node="example.node"/>"><img src="./resources/images/collapsedMidNode.gif" border="0"></a><img src="<tree:nodeImageUrl node="example.node"/>">
                </td>
            </tree:nodeMatch>
            <tree:nodeMatch    node="example.node" expanded="true"  hasChildren="true"  isLastChild="false"><td class="nopadding"><a href="${action}&view=true&collapse=<tree:nodeId node="example.node"/>"><img src="./resources/images/expandedMidNode.gif"  border="0"></a><img src="<tree:nodeImageUrl node="example.node"/>"></td></tree:nodeMatch>
            <tree:nodeMatch    node="example.node" expanded="false" hasChildren="true"  isLastChild="true" ><td class="nopadding"><a href="${action}&view=true&expand=<tree:nodeId node="example.node"/>"><img src="./resources/images/collapsedLastNode.gif"  border="0"></a><img src="<tree:nodeImageUrl node="example.node"/>"></td></tree:nodeMatch>
            <tree:nodeMatch    node="example.node" expanded="true"  hasChildren="true"  isLastChild="true" ><td class="nopadding"><a href="${action}&view=true&collapse=<tree:nodeId node="example.node"/>"><img src="./resources/images/expandedLastNode.gif" border="0"></a><img src="<tree:nodeImageUrl node="example.node"/>"></td></tree:nodeMatch>
            <tree:nodeMatch    node="example.node" expanded="false" hasChildren="false" isLastChild="false"><td class="nopadding"><img src="./resources/images/noChildrenMidNode.gif"><img src="<tree:nodeImageUrl node="example.node"/>"></td></tree:nodeMatch>
            <tree:nodeMatch    node="example.node" expanded="false" hasChildren="false" isLastChild="true" ><td class="nopadding"><img src="./resources/images/noChildrenLastNode.gif"><img src="<tree:nodeImageUrl node="example.node"/>"></td></tree:nodeMatch>

            <td class="nopadding" valign="bottom" width="100%">
            <tree:detachNodeObject node="example.node" detachedObject="object" />
            <logic:equal name="object" property="class.name"  value="be.ovam.pad.model.Brief">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <logic:notEqual name="object" property="oplaad" value="true">
                            <td class="nopadding" width="18px">
                                <bean:define name="object" property="categorie_id" id="categorieId" type="java.lang.Integer"/>
                                <bean:define id="subgroep" value=""/>
                                <bean:define id="groep" value="IVS"/>
                                <%
                                    if (categorieId == 1) {
                                        subgroep = "Algemeen";
                                    } else if (categorieId == 7) {
                                        subgroep = "Communicatie";
                                    } else if (categorieId == 2) {
                                        subgroep = "Juridisch";
                                    } else if (categorieId == 3 || categorieId == 8 || categorieId == 9 || categorieId == 10 || categorieId == 11) {
                                        subgroep = "Voorbereiding";
                                    } else if (categorieId == 4 || categorieId == 12 || categorieId == 13 || categorieId == 14 || categorieId == 15) {
                                        subgroep = "Uitvoering";
                                    } else if (categorieId == 19) {
                                        groep = "SCHADE";
                                    }
                                %>
                                <img src='<html:rewrite page="/"/>/resources/images/refresh.jpg' alt="Brief beantwoorden" title="Brief beantwoorden" onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?groep=<%=groep %>&dossierId=<bean:write name="dossierart46form" property="id"/>&auteurId=<bean:write name="user" property="user_id"/>&replyBriefId=<bean:write name="object" property="brief_id"/>&categorieId=<bean:write name="object" property="categorie_id"/>&subgroep=<%= subgroep%>','Brief')"/>
                            </td>
                        </logic:notEqual>
                        <td class="nopadding" width="18px">
                            <logic:notEmpty name="object" property="dms_id">
                                <a href='<%=System.getProperty("ovam.dms.webdrive.base") %><bean:write name="object" property="dms_folder"/>/<bean:write name="object" property="dms_filename"/>' target="_blank">
                                    <html:img src="resources/images/AlfrescoLogo32.png" width="16" height="16" border="0" alt="Brief bekijken" title="Brief bekijken"/>
                                </a>
                            </logic:notEmpty>
                            <logic:empty name="object" property="dms_id">
                                <html:img src="resources/images/blankSpace.gif" width="16" height="16" border="0" />
                            </logic:empty>
                        </td>
                        <logic:notEqual name="object" property="oplaad" value="true">
                            <c:if test="${!empty object.in_aard_id && object.in_aard_id != 0}">
                                <td class="nopadding" nowrap="nowrap" width="60" align="left">
                                    <bean:write name="object" property="in_d" formatKey="date.format"/>
                                </td>
                                <td class="nopadding" nowrap="nowrap" width="20" align="left">
                                    In:
                                </td>
                                <td class="nopadding" nowrap="nowrap" width="220" align="left">
                                    <html:select name="object" property="in_aard_id" styleClass="input" disabled="true" errorStyleClass="errorclass">
                                        <html:option value="">&nbsp;</html:option>
                                        <html:optionsCollection name="DDH" property="briefAard" label="brief_aard_b" value="brief_aard_id" />
                                    </html:select>
                                </td>
                            </c:if>
                            <c:if test="${!empty object.uit_aard_id && object.uit_aard_id != 0}">
                                <td class="nopadding" nowrap="nowrap" width="60" align="left">
                                    <bean:write name="object" property="uit_d" formatKey="date.format"/>
                                </td>
                                <td class="nopadding" nowrap="nowrap" width="20" align="left">
                                    Uit:
                                </td>
                                <td class="nopadding" nowrap="nowrap" width="220"  align="left">
                                    <html:select name="object" property="uit_type_id_vos" styleClass="input" disabled="true" >
                                        <html:optionsCollection name="DDH" property="briefTypeVos" label="type_b" value="type_id" />
                                    </html:select>
                                </td>
                            </c:if>
                            <td class="nopadding"  width="230" align="left">
                                <bean:write name="object" property="betreft"/>
                            </td>
                        </logic:notEqual>
                        <td class="nopadding">
                            &nbsp;
                            <html:link action="briefdetails" paramId="brief_id" paramName="object" paramProperty="brief_id" title="Briefdetails bekijken">
                                <tree:nodeName node="example.node"/>
                            </html:link>
                             <logic:notEqual name="object" property="oplaad" value="false">
                                <bean:write name="object" property="commentaar"/>
                            </logic:notEqual>
                        </td>
                        <logic:notEqual name="object" property="oplaad" value="true">
                            <td style="padding: 5px;" width="200" nowrap="nowrap">
                                &nbsp;
                                <bean:write name="object" property="adresnaam" />
                            </td>
                            <td class="nopadding">
                                <img src='./resources/images/copy.gif' height="16" alt='<bean:write name="object" property="straat"/> <bean:write name="object" property="postcode"/> <bean:write name="object" property="gemeente"/>' title='<bean:write name="object" property="straat"/> <bean:write name="object" property="postcode"/> <bean:write name="object" property="gemeente"/>'>
                            </td>
                            <td>
                                <logic:notEqual value="5" name="object" property="categorie_id" >
                                    <img src='<html:rewrite page="/"/>/resources/images/contents.gif' alt="Scan opladen" title="Scan opladen" onclick="popupWindow('<html:rewrite action="briefuploadview"/>?&popup=yes&fromTree=true&forward=${forward}&parent_brief_id=<bean:write name="object" property="brief_id" />','Brief upload')" width="16"/>
                                </logic:notEqual>
                            </td>
                        </logic:notEqual>
                    </tr>
                </table>
            </logic:equal>





            <logic:equal name="object" property="class.name"  value="java.lang.Integer">
                <tree:nodeName node="example.node"/>
                <logic:equal value="18" name="object" >
                    <c:if test="${dossierart46form.dossier_type eq 'X'}">
                        <img src='<html:rewrite page="/"/>/resources/images/upload-16x16.png' alt="Rapport opladen" title="Rapport Opladen" onclick="popupWindow('<html:rewrite action="briefuploadview"/>?&popup=yes&fromTree=true&forward=${forward}&dossier_id=<bean:write name="dossierart46form" property="id" />&categorie_id=<bean:write name="object" />','Rapport upload')" width="16"/>
                    </c:if>
                </logic:equal>
                <logic:notEqual value="18" name="object" >
                    <logic:equal value="22" name="object" >
                        <img src='<html:rewrite page="/"/>/resources/images/upload-16x16.png' alt="Meetstaat opladen" title="Meetstaat Opladen" onclick="popupWindow('<html:rewrite action="briefuploadview"/>?&popup=yes&fromTree=true&forward=${forward}&dossier_id=<bean:write name="dossierart46form" property="id" />&categorie_id=<bean:write name="object" />','Meetstaat upload')" width="16"/>
                    </logic:equal>
                    <logic:notEqual value="22" name="object" >
                        <logic:equal value="21" name="object" >
                            <img src='<html:rewrite page="/"/>/resources/images/upload-16x16.png' alt="Terugvordering opladen" title="Terugvordering Opladen" onclick="popupWindow('<html:rewrite action="briefuploadview"/>?&popup=yes&fromTree=true&forward=${forward}&dossier_id=<bean:write name="dossierart46form" property="id" />&categorie_id=<bean:write name="object" />','Foto upload')" width="16"/>
                        </logic:equal>
                        <logic:notEqual value="21" name="object" >
                            <logic:notEqual value="16" name="object" >
                                <logic:notEqual value="5" name="object" >
                                    <logic:equal value="19" name="object" >
                                        <logic:equal name="showAanmakenBrief" value="true">
                                            <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                            onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?groep=SCHADE&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                        </logic:equal>
                                    </logic:equal>
                                    <logic:notEqual value="19" name="object" >
                                        <logic:equal value="1" name="object" >
                                            <logic:equal name="showAanmakenBrief" value="true">
                                                <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Algemeen&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                            </logic:equal>
                                        </logic:equal>
                                        <logic:notEqual value="1" name="object" >
                                            <logic:equal value="7" name="object" >
                                                <logic:equal name="showAanmakenBrief" value="true">
                                                    <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                    onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Communicatie&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                </logic:equal>
                                            </logic:equal>
                                            <logic:notEqual value="7" name="object" >
                                                <logic:equal value="2" name="object" >
                                                    <logic:equal name="showAanmakenBrief" value="true">
                                                        <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                        onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Juridisch&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                    </logic:equal>
                                                </logic:equal>
                                                <logic:notEqual value="2" name="object" >
                                                    <logic:equal value="3" name="object" >
                                                        <logic:equal name="showAanmakenBrief" value="true">
                                                            <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                            onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Voorbereiding&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                        </logic:equal>
                                                    </logic:equal>
                                                    <logic:notEqual value="3" name="object" >
                                                        <logic:equal value="8" name="object" >
                                                            <logic:equal name="showAanmakenBrief" value="true">
                                                                <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Voorbereiding&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                            </logic:equal>
                                                        </logic:equal>
                                                        <logic:notEqual value="8" name="object" >
                                                            <logic:equal value="9" name="object" >
                                                                <logic:equal name="showAanmakenBrief" value="true">
                                                                    <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                    onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Voorbereiding&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                </logic:equal>
                                                            </logic:equal>
                                                            <logic:notEqual value="9" name="object" >
                                                                <logic:equal value="10" name="object" >
                                                                    <logic:equal name="showAanmakenBrief" value="true">
                                                                        <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                        onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Voorbereiding&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                    </logic:equal>
                                                                </logic:equal>
                                                                <logic:notEqual value="10" name="object" >
                                                                    <logic:equal value="11" name="object" >
                                                                        <logic:equal name="showAanmakenBrief" value="true">
                                                                            <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                            onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Voorbereiding&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                        </logic:equal>
                                                                    </logic:equal>
                                                                    <logic:notEqual value="11" name="object" >
                                                                        <logic:equal value="4" name="object" >
                                                                            <logic:equal name="showAanmakenBrief" value="true">
                                                                                <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                                onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Uitvoering&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                            </logic:equal>
                                                                        </logic:equal>
                                                                        <logic:notEqual value="4" name="object" >
                                                                            <logic:equal value="12" name="object" >
                                                                                <logic:equal name="showAanmakenBrief" value="true">
                                                                                    <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                                    onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Uitvoering&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                                </logic:equal>
                                                                            </logic:equal>
                                                                            <logic:notEqual value="12" name="object" >
                                                                                <logic:equal value="13" name="object" >
                                                                                    <logic:equal name="showAanmakenBrief" value="true">
                                                                                        <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                                        onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Uitvoering&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                                    </logic:equal>
                                                                                </logic:equal>
                                                                                <logic:notEqual value="13" name="object" >
                                                                                    <logic:equal value="14" name="object" >
                                                                                        <logic:equal name="showAanmakenBrief" value="true">
                                                                                            <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                                            onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Uitvoering&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                                        </logic:equal>
                                                                                    </logic:equal>
                                                                                    <logic:notEqual value="14" name="object" >
                                                                                        <logic:equal value="15" name="object" >
                                                                                            <logic:equal name="showAanmakenBrief" value="true">
                                                                                                <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                                                onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?subgroep=Uitvoering&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                                            </logic:equal>
                                                                                        </logic:equal>
                                                                                        <logic:notEqual value="15" name="object" >
                                                                                            <logic:equal name="showAanmakenBrief" value="true">
                                                                                                <img src="resources/images/add.gif" width="16" height="16" border="0" alt="Brief aanmaken" title="Brief aanmaken"
                                                                                                onclick="popupWindow('<%=System.getProperty("pad.vos2Url") %>?groep=IVS&dossierId=<bean:write name="dossierart46form" property="id"/>&bestekId=<bean:write name="dossierart46form" property="bestek_id"/>&auteurId=<bean:write name="user" property="user_id"/>&categorieId=<bean:write name="object"/>','Brief')" />
                                                                                            </logic:equal>
                                                                                        </logic:notEqual>
                                                                                    </logic:notEqual>
                                                                                </logic:notEqual>
                                                                            </logic:notEqual>
                                                                        </logic:notEqual>
                                                                    </logic:notEqual>
                                                                </logic:notEqual>
                                                            </logic:notEqual>
                                                        </logic:notEqual>
                                                    </logic:notEqual>
                                                </logic:notEqual>
                                            </logic:notEqual>
                                        </logic:notEqual>
                                    </logic:notEqual>
                                </logic:notEqual>
                            </logic:notEqual>
                        </logic:notEqual>
                    </logic:notEqual>
                </logic:notEqual>
            </logic:equal>
        </td>
        </tr>
        </table>
        </td>
        </tr>
    </tree:tree>

</table>

<div style="margin-top: 30px;">
    <a href='/pad/s/dossier/${dossierart46form.dossier_nr}/dms/oplaad' target="_blank" >naar Alfresco oplaad folder</a><br/>
    <a href='/pad/s/dossier/${dossierart46form.dossier_nr}/dms/webloket' target="_blank" >naar Alfresco webloket folder</a>
</div>

