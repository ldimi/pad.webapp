
<jsp:useBean id="pad_versie" scope="application" type="java.lang.String" />
<jsp:useBean id="build_profile" scope="application" type="java.lang.String" />
<jsp:useBean id="build_timestamp" scope="application" type="java.lang.String" />


<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.min.css" type="text/css" media="all"/>

<link rel="stylesheet" type="text/css" href='//services.ovam.be/jsrepo/slickgrid/2.2.1/slick.grid.css'/>
<link rel="stylesheet" type="text/css" href='//services.ovam.be/jsrepo/slickgrid/2.2.1/slick-default-theme.css'/>

<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.1/css/selectize.min.css" />

<style>
    .selectize-dropdown {
        z-index: 10000;
    }

    .selectize-input {
        padding: 2px;
    }
    .selectize-control.multi .selectize-input.has-items {
        padding: 3px 8px 3px;
    }

    .selectize-control .selectize-input.disabled {
        opacity: 1;
        background-color: #EEE;
    }
    
    .selectize-dropdown,
    .selectize-input,
    .selectize-input input {
        font-size: 11px;
    }
</style>


<script type="text/javascript">
    if (typeof console === "undefined" || typeof console.log === "undefined") {
        var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml",
            "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];

        window.console = {};
        for (var i = 0; i < names.length; ++i) {
            window.console[names[i]] = function () {
            };
        }
    }

    function laadBacking(backing) {
        require(["app"], function (app) {
            app.laadBacking(backing);
        });
    }

    console.log("voor require");
</script>

<%
String script_path;
if ("release".equals(build_profile))  {
    script_path = "resources/js/" + build_timestamp;
} else {
    script_path = "resources/js";
}
%>


<script>
    require = { baseUrl: "<%= script_path %>/pad" };
</script>
<script src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.20/require.min.js"></script>
<script src="<%= script_path %>/pad/main.js"></script>

