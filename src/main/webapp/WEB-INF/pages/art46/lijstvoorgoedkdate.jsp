<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<bean:define id="artikelid" name="art46selectform" property="artikelid" scope="session" type="java.lang.String"></bean:define>
<html:form action="setgoedkeuringsdatum">
    <html:hidden property="artikelid" value="<%=artikelid %>"/>
    <table cellpadding='3' cellspacing='3'>
        <tr valign='middle'>
            <td valign='middle'>
                <bean:message key="lijst.voorgoedk.date.date" />
            </td>
            <td >&nbsp;</td>
            <td valign='middle'>
                <html:text maxlength="10" size="10" styleClass="input" property="goedkeuring_d" errorStyleClass="errorclass" onclick="scwShow(this,this);"/>
            </td >
        </tr>
        <tr valign='middle'>
            <td valign='middle'>
                <bean:message key="lijst.voorgoedk.date.lijst" />
            </td>
            <td >&nbsp;</td>
            <td valign='middle'>
                <html:select property="lijst_id" styleClass="input">
                    <html:optionsCollection name="DDH" property="actieveLijsten" label="lijst_b" value="lijst_id" />
                </html:select>
            </td >
        </tr>
    </table>
    <html:submit styleClass="inputbtn" value=">>"/>
</html:form>