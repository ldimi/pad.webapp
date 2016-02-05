<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/display-tags" prefix="display" %>
<table>
    <tr>
        <td align="center">
            <table cellpadding='0' cellspacing='0'>
                <html:form action="dossiersfzselect">
                <tr valign='middle'>
                    <td>
                        <html:radio property="forward" value="dossiersfzivs" styleClass="radio">IVS Dossiers</html:radio>
                    </td>
                    <td>
                        <html:radio property="forward" value="dossiersfzall" styleClass="radio">Alle Dossier</html:radio>
                    </td>
                    <td valign='middle'>
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
            <display:table class="lijst width1000" id="dossier" name="requestScope.dossiersfz" requestURI="/dossiersfzselect.do"  pagesize='<%= ((be.ovam.art46.struts.actionform.PagingForm) session.getAttribute("pagingform")).getPagesizeInt()%>' >
                <display:column title="Dossiernr. BB" property="dossier_id_boa" class="center" href="./dossierdetailsArt46.do?selectedtab=Basis" paramId="id" paramProperty="id"/>
                <display:column title="Dossiertitel BB" property="dossier_b"/>
                <display:column title="Dossiernr. IVS" property="dossier_nr" class="center"/>
                <display:column title="Fusiegemeente" sortable="true">
                    <html:select name="dossier" property="nis_id" styleClass="input" disabled="true">
                        <html:option value=""/>
                        <html:optionsCollection name="DDH" property="fusiegemeenten" label="gemeente_b" value="nis_id" />
                    </html:select>
                </display:column>
                <display:column title="Dossiertitel IVS" property="dossier_b_ivs" />
                <display:column sortProperty="doss_hdr_id" titleKey="lijst.jdactie.doss_hdr" class="center" sortable="true">
                    <html:select name="dossier" property="doss_hdr_id" styleClass="input" disabled="true">
                        <html:option value="" />
                        <html:optionsCollection name="DDH" property="dossierhouders" label="doss_hdr_b" value="doss_hdr_id" />
                    </html:select>
                </display:column>
            </display:table>
        </td>
    </tr>
</table>