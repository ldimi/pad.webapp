<%@ page language="java" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="custom" uri="/tags/custom" %>

<style>

    #paramsDiv label {
        float: left;
        width: 270px;
        margin-right: 5px;
    }

    #paramsDiv label select {
        float: right;
        max-width: 150px;
    }

</style>

<div id="detailDiv"></div>

<tiles:insert definition="laadJS" />

<script type="text/javascript">
    _G_ = {};
    _G_.dossierhouders = <custom:outJson object="dossierhouders" />;
    _G_.doss_hdr_id = <custom:outJson object="doss_hdr_id" />;
    _G_.dms_webdrive_base = <custom:outJson object="dms_webdrive_base" />;

    laadBacking('taken/takenlijstBacking');
</script>
