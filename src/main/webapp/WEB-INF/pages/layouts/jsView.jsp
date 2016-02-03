<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<jsp:useBean id="JsonMapper" scope="application" type="com.fasterxml.jackson.databind.ObjectMapper" />
<jsp:useBean id="__MODEL__" scope="request" type="java.util.Map" />
<jsp:useBean id="__VIEW_NAME__" scope="request" type="java.lang.String" />

<div id="jsviewContentDiv">
</div>

<tiles:insertDefinition name="laadJS" />

<script>
    _G_ = _G_ || {};

    require(["app"], function(app) {
        require(["ov/jsonPostParseProcessor"], function(jsonPostParseProcessor) {
            _G_.model = <%=JsonMapper.writeValueAsString(__MODEL__)%>
            jsonPostParseProcessor(_G_.model);
    
            laadBacking(<%=JsonMapper.writeValueAsString(__VIEW_NAME__)%> + 'Backing');
        });
    });

</script>

