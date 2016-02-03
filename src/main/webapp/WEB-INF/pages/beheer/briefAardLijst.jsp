<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div style="margin-left: 50px;" align="left">
    <h3>Beheer BriefAard</h3>

    <div id="briefAardGrid" style="position:relative; width:600px; height:90%;" ></div>
</div>

<div id="detailDiv">
</div>


<tiles:insertDefinition name="laadJS" />

<logic:present role="adminArt46">
    <script type="text/javascript">
        _G_.isAdminArt46 = true;
    </script>
</logic:present>


<script type="text/javascript">
    laadBacking('beheer/briefAardLijstBacking');
</script>


