<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<style>
    #sidebarDiv {
        background-color: #B8C400;
    }

    #sidebarDiv>h3.menuItem.selected {
        background-color: #DD0;
    }

    #sidebarDiv>div.submenu.selected {
        background-color: #DD0;
    }
    #sidebarDiv>div.submenu.selected li>a {
        background-color: #B8C400;
    }
    #sidebarDiv>div.submenu.selected li.selected>a {
        background-color: #DD0;
    }

    .ui-dialog-titlebar.ui-widget-header {
        background-color: #B8C400;
    }

</style>


<div style="height: 50px; color: white; font-size: 20px; font-weight: bold; margin-left: 40px; margin-top: 30px;" >
    Sluis
</div>

<jsp:useBean id="menuSluisHelper" scope="application" type="be.ovam.art46.util.menu.MenuSluisHelper" />
<tilesx:useAttribute id="menuId" name="menuId" classname="java.lang.String"/>
<%=menuSluisHelper.render(menuId)%>



<div style="border-top: 1px solid gray; margin: 5px 5px 5px 5px; padding: 5px 0px 5px 5px;" >
    <div class="ajax-loading invisible" >
        <html:img page="/resources/images/loading4.gif" alt="ajax-loading" style="padding-left: 0px;" />
    </div>

</div>
