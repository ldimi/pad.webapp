<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>

<tilesx:useAttribute id="title" name="title" classname="java.lang.String" ignore="true" />

<html style="height: 100%;">
    <head>
        <title>Jasmine Spec Runner</title>

        <meta http-equiv=Content-Type content="text/html; charset=UTF-8">

        <%
            String baseRef = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/pad/";
        %>
        <base href="<%= baseRef %>" target="_self"/>

        <script>
            window._G_ = {};
            _G_.omgeving = '<%=System.getProperty("ovam.omgeving")%>';
        </script>

        <link rel="stylesheet" type="text/css" href="resources/css/pad.css" />
        <link rel="stylesheet" type="text/css" href="resources/js/jasmine/jasmine-1.3.1/jasmine.css">

    </head>
    <body  style="height: 100%; ">

        <div id="testDiv_1" >
            <form id="testForm_1"  autocomplete="off" novalidate >
                <table>
                    <tr>
                        <td width="60px" >Code:</td>
                        <td width="100px" ><input type="text" name="code" maxlength="5" /></td>
                        <td>Kleur:</td>
                        <td><input type="text" name="kleur" /></td>
                    </tr>
                    <tr>
                        <td>Stuk:</td>
                        <td><select name="stuk_code" /></td>
                        <td>Prijs:</td>
                        <td><input type="text" name="prijs" /></td>
                    </tr>
                    <tr>
                        <td>Aankoop datum:</td>
                        <td><input type="text" name="aankoop_d" /></td>
                    </tr>
                </table>
                <input type="hidden" name="status_crud" value="" />
                <button id="bewaarBtn" >Bewaar</button>
                <button id="resetBtn" >Reset</button>
            </form>
        </div>

        <tiles:insertTemplate template="/WEB-INF/pages/layouts/notifyContainer.jsp" />
        <tiles:insertDefinition name="laadJS" />

        <script type="text/javascript">
            laadBacking('testrunnerBacking');
        </script>
    </body>
</html>