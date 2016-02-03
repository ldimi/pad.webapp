
<jsp:useBean id="pad_versie" scope="application" type="java.lang.String" />
<jsp:useBean id="build_profile" scope="application" type="java.lang.String" />
<jsp:useBean id="build_timestamp" scope="application" type="java.lang.String" />



<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css" type="text/css" media="all" />

<script type="text/javascript">
    if (typeof console === "undefined" || typeof console.log === "undefined") {
          var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml",
              "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];

          window.console = {};
          for (var i = 0; i < names.length; ++i) {
            window.console[names[i]] = function() {}
          }
    }

    function laadBacking (backing) {
        require(["appMinimum"], function(app) {
            app.laadBacking(backing);
        });
    }

    console.log("voor require");
</script>

<script src="http://cdnjs.cloudflare.com/ajax/libs/knockout/2.3.0/knockout-min.js"></script>

<% 
String script_path;
if ("release".equals(build_profile))  {
    script_path = "resources/js/" + build_timestamp;
} else {
    script_path = "resources/js";
}
%>


<link rel="stylesheet" type="text/css" href='<%= script_path %>/jqwidgets/styles/jqx.base.css' />
<link rel="stylesheet" type="text/css" href='<%= script_path %>/jqwidgets/styles/jqx.ui-sunny.css' />
<script>
    require = { baseUrl: "<%= script_path %>/pad" };
</script>
<script src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.10/require.min.js"></script>
<script src="<%= script_path %>/pad/main.js"></script>

