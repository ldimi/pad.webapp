<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin: 10px 50px;" >
    <h3>Beheer JaarBudget plannning</h3>

    <div>
        <form id="paramForm" >
            <table>
                <tr>
                    <td>Jaar:</td>
                    <td>
                        <select name="jaar" class="input" >
                            <c:forEach var="jaar" items="${DDH.jaren}" >
                                <option value="${jaar}">${jaar}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="button" id="ophalenBtn" value="Ophalen" class="inputbtn invisible"/></td>
            </table>
        </form>
    <div>

    <div id="jaarBudgetGridDiv" style="width:500px; height:200px" class="invisible" ></div>

</div>

<logic:present role="adminArt46">
    <div id="detailDialog" class="hidden">
        <form id="detailForm" autocomplete="off" novalidate >
            <input type="hidden" name="jaar" />
            <input type="hidden" name="status_crud" />
            <table>
                <tr>
                    <td width="100px" >Budget code:</td>
                    <td width="100px" >
                        <select name="budget_code" class="input">
                             <c:forEach var="item" items="${DDH.budgetCodeList}" >
                                    <option value="${item.budget_code}">${item.budget_code}</option>
                             </c:forEach>
                        </select>

                    </td>
                </tr>
                <tr>
                    <td>Budget bedrag:</td>
                    <td><input type="text" name="budget" value="" required /></td>
                </tr>
                <tr>
                    <td>Effectief budget:</td>
                    <td><input type="text" name="effectief_budget" value="" /></td>
                </tr>
            </table>
        </form>
        <button id="bewaarBtn" >Bewaar</button>
        <button id="annuleerBtn" >annuleer</button>
    </div>

</logic:present>

<tiles:insertDefinition name="laadJS" />

<logic:present role="adminArt46">
    <script type="text/javascript">
        _G_isAdminArt46 = true;
    </script>
</logic:present>

<script type="text/javascript">
    laadBacking('beheer/jaarbudgetlijstBacking');
</script>


