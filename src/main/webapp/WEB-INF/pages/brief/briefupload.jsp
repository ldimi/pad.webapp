<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<table class="windowfree">
    <tr>
        <th>
            Opladen brief <bean:write name="briefform" property="brief_nr"/>
        </th>
    </tr>
    <tr>
        <td>
            <html:form action="briefupload" method="post" enctype="multipart/form-data">
                <html:hidden property="brief_id"/>
                <html:hidden property="popup" value="yes"/>
                <html:hidden property="fromTree" />
                <html:hidden property="categorie_id" />
                <table>
                    <tr>
                        <td align="right">Kies een brief voor op te laden.</td>
                        <td align="left"><html:file property="file"/></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="2">

                            <logic:equal name="briefform" property="fromTree" value="true">
                                <input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('<html:rewrite action="dossierdetails.do?selectedtab=Brieven"/>',top.opener)" class="inputbtn" />
                            </logic:equal>
                            <logic:equal name="briefform" property="fromTree" value="false">
                                <input type="button" value="Venster sluiten" name="dummy" onclick="pad_load('<html:rewrite action="briefdetails"/>',top.opener)" class="inputbtn" />
                            </logic:equal>
                            <html:submit styleClass="inputbtn">Bestand opladen</html:submit>
                        </td>
                    </tr>
                </table>
            </html:form>
        </td>
    </tr>
</table>

