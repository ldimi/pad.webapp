
<div id="sapMaakDialog" title="Aanmaken Schuldvordering in SAP" class="hidden" >
    <form id="sapMaakForm"  autocomplete="off" novalidate >
        <table>
            <tr class="hidden">
                <td width="120px" >schuldvordering id:</td>
                <td width="120px" ><input type="text" name="vordering_id" readonly ></td>
                <td width="120px" >&nbsp;</td>
                <td width="120px" >&nbsp;</td>
            </tr>
            <tr>
                <td width="120px" >brief:</td>
                <td width="120px" colspan="3" ><input type="text" name="brief_nr" readonly ></td>
            </tr>
            <tr>
                <td width="120px" >bestek:</td>
                <td width="120px" ><input type="text" name="bestek_nr" readonly ></td>
            </tr>
            <tr>
                <td width="120px" class="invisible _deelopdracht">deelopdracht:</td>
                <td width="120px" colspan="3" class="invisible _deelopdracht"><input type="text" name="deelopdracht" readonly ></td>
            </tr>
            <tr>
                <td width="120px" >Totaal bedrag goedkeuring (incl. BTW):</td>
                <td><input type="text" name="goedkeuring_bedrag" required="true" readonly /></td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>vastlegging :</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td colspan="3">
                    <select name="project_id" required="true" class="styled-select" ></select>
                </td>
                <td width="120px" class="_vastlegging_bedrag">
                    <input type="text" name="vastlegging_bedrag" >
                </td>
            </tr>
            <tr class="hidden _vastlegging_2">
                <td>vastlegging (2):</td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr class="hidden _vastlegging_2">
                <td colspan="3">
                    <select name="project_id_2" required="true" ></select>
                </td>
                <td><input type="text" name="vastlegging_bedrag_2" ></td>
            </tr>
            <tr class="invisible _tr_paar_minuten">
                <td colspan="4" style="color:blue;">De Schuldvordering wordt in sap aangemaakt. Dit kan een paar minuten duren.</td>
            </tr>

        </table>
    </form>

    <div>
        <button id="sapBewaarBtn" >Bewaar</button>
        <span style="color: blue;" >Let op. Werd correcte draft gebruikt?</span>
    </div>
</div>


