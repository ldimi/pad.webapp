<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<jsp:useBean id="pad_versie" scope="application" type="java.lang.String" />
<jsp:useBean id="build_profile" scope="application" type="java.lang.String" />
<jsp:useBean id="build_timestamp" scope="application" type="java.lang.String" />

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <%
            String baseRef = be.ovam.web.util.BaseRefHelper.getBaseRef(request);

            String omgeving = System.getProperty("ovam.omgeving");

            String bustToken = null;
            if ("ontwikkeling".equals(omgeving)) {
                bustToken = "";
            } else {
                bustToken = pad_versie + "_" + build_timestamp;
            }
        %>
        <base href="<%= baseRef %>" target="_self"/>

        <link rel="stylesheet" type="text/css" href='resources/css/pad.css?bust=<%= bustToken %>' />

        <%
            String script_path;
            if ("release".equals(build_profile))  {
                script_path = "resources/js/" + build_timestamp;
            } else {
                script_path = "resources/js";
            }
        %>

        <script type="text/javascript" src="/pad/<%= script_path %>/script_2_06.js"></script>

        <script>
            window._G_ = {};
            _G_.omgeving = '<%= omgeving %>';
        </script>

