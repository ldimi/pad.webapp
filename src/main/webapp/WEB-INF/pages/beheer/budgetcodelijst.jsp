<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" uri="/tags/custom" %>

<div style="margin: 50px;" align="left">
    <h3>Beheer Budgetcodes</h3>

    <div id="myGrid" style="width:480px; height:300px" ></div>
</div>

<div id="detailDiv">
</div>

<tiles:insertDefinition name="laadJS" />

<script type="text/javascript">
    _G_ = _G_ || {};
    <logic:present role="adminArt46">
    _G_.isAdminArt46 = true;
    </logic:present>

    _G_.artikels = <custom:outJson object="artikels" />;

    laadBacking('beheer/budgetcodelijstBacking');
</script>


