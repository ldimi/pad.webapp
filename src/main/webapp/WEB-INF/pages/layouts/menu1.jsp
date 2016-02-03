<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<jsp:useBean id="menuHelper" scope="application" type="be.ovam.art46.util.menu.MenuHelper" />
<tiles:useAttribute id="menuId" name="menuId" classname="java.lang.String" />

<img src="/pad/resources/images/toad2.jpg" width="144" height="71"
     title="Planning administratie en dossieropvolging " alt="OVAM Startpagina">

<%=menuHelper.render(menuId)%>


<div style="border-top: 1px solid gray; margin: 20px 5px 5px 5px; padding: 5px 0px 5px 5px;" >
    <div style="margin-bottom: 5px;">
        <logic:notEmpty name="zoekaction" scope="session">
            <a href='<%=request.getContextPath() + (String) session.getAttribute("zoekaction")%>'>
                Laatste Zoekresultaat
            </a>
        </logic:notEmpty>

        <logic:notEmpty name="dossierart46form" scope="session">
            <a href="/pad/s/dossier/<bean:write name="dossierart46form" property="id"/>/basis"/>
                <logic:notEmpty name="dossierart46form" property="dossier_b">
                    Dossier <bean:write name="dossierart46form" property="dossier_b"/>
                </logic:notEmpty>
                <logic:empty name="dossierart46form" property="dossier_b">
                    Huidig dossier
                </logic:empty>
            </a>
        </logic:notEmpty>
    </div>
    <div>
        <html:form action="setpaging">
            <input type="hidden" name="url" value="<%= request.getSession().getAttribute("helpaction").toString() %>" />
            <html:select property="pagesize" styleClass="input" onchange="this.form.submit();">
                <html:optionsCollection name="pagesizes" />
            </html:select>
        </html:form>
    </div>
    <div class="ajax-loading invisible" >
        <html:img page="/resources/images/loading4.gif" alt="ajax-loading" style="padding-left: 0px;" />
    </div>

</div>
