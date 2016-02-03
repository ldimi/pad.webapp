<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<div style="margin-left: 50px;" align="left">
    <h3>Beheer Planning fasen</h3>

    <div id="fasenGrid" style="position:relative; width:500px; height:90%;" ></div>
</div>

<div id="planningFaseDialog" class="hidden" title="Editeer Fase">
    <div>
        <form id="planningFaseForm" autocomplete="off" novalidate >
            <table>
                <tr>
                    <td width="100px" >Dossier type:</td>
                    <td width="100px" >
                        <select name="dossier_type" class="input">
                            <option value=""></option>
                            <option value="A">Afval</option>
                            <option value="B">Bodem</option>
                        <select>
                    </td>
                </tr>
                <tr>
                    <td>Fase code:</td>
                    <td><input type="text" name="fase_code" value="" maxlength="5" /></td>
                </tr>
                <tr>
                    <td>Omschrijving:</td>
                    <td><input type="text" name="fase_code_b" value="" maxlength="40" /></td>
                </tr>
                <tr>
                    <td>Budget:</td>
                    <td>
                        <select name="budget_code" class="input">
                            <option value=""></option>
                            <c:forEach var="item" items="${DDH.budgetCodeList}" >
                                <option value="${item.budget_code}">${item.budget_code}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="status_crud" value="" />
        </form>
        <logic:present role="adminArt46">
            <button id="bewaarFaseBtn" >Bewaar</button>
            <button id="annuleerFaseBtn" >annuleer</button>
        </logic:present>
    </div>
    <br />
    <div id="faseDetailDiv" style="position:relative; width:300px; height:150px" ></div>
</div>

<div id="planningFaseDetailDialog" class="hidden" title="Editeer Fasedetail">
    <form id="planningFaseDetailForm" autocomplete="off" novalidate >
        <table>
            <tr>
                <td>Fase code:</td>
                <td><input type="text" name="fase_code" value="" maxlength="5" readonly /></td>
            </tr>
            <tr>
                <td>Fasedetail code:</td>
                <td><input type="text" name="fase_detail_code" value="" maxlength="20" /></td>
            </tr>
            <tr>
                <td>Omschrijving:</td>
                <td><input type="text" name="fase_detail_code_b" value="" maxlength="40" /></td>
            </tr>
        </table>
        <input type="hidden" name="status_crud" value="" />
    </form>
    <logic:present role="adminArt46">
        <button id="bewaarFaseDetailBtn" >Bewaar</button>
        <button id="annuleerFaseDetailBtn" >annuleer</button>
    </logic:present>
</div>

<tiles:insertDefinition name="laadJS" />

<logic:present role="adminArt46">
    <script type="text/javascript">
        _G_isAdminArt46 = true;
    </script>
</logic:present>


<script type="text/javascript">
    laadBacking('beheer/planningFaseLijstBacking');
</script>


