    <%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:form action="nieuwdossier">
    <table>
        <tr>
            <td>
                <table bgcolor="#eeeeee" width="300">
                    <tr>
                        <th colspan="2" align="left">
                            Dossier Type IVS
                        </th>
                    </tr>
                    <tr>
                        <td valign="top">
                            Dossier type IVS:
                        </td>
                        <td>
                            <html:radio property="dossier_type_ivs" value="A" styleClass="checkbox">Afval</html:radio><br>
                            <html:radio property="dossier_type_ivs" value="X" styleClass="checkbox">Ander</html:radio><br>
                            <html:radio property="dossier_type_ivs" value="B" styleClass="checkbox">Bodem</html:radio>

                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="right">
                                <html:submit styleClass="inputbtn" property="jos" >Doorgaan</html:submit>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</html:form>