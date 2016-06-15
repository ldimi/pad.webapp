
<div id="svDetailDialog" title="Details Schuldvordering" class="hidden" >
    <form id="svDetailForm" autocomplete="off" novalidate >

        <table id="svDetailFormTable">
            <tr>
                <td width="145px" >Schuldvordering nr.:</td>
                <td width="100px" ><input type="text" name="schuldvordering_nr" readonly /></td>
                <td width="85px" style="padding-left:5px;" >Brief :</td>
                <td colspan="3" ><input type="text" name="brief_nr" readonly /></td>
            </tr>
            <tr>
                <td></td>
                <td><div id="antw_pdf_brief_div"></div></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td >Bestek :</td>
                <td ><input type="text" name="bestek_nr" readonly /></td>
                <td style="padding-left:5px;" class="_deelopdracht" >Deelopdracht :</td>
                <td colspan="3" >
                    <select name="deelopdracht_id" class="_deelopdracht editeerbaar" ></select>
                    <label id="deelopdrachtSaldoMsg" class="hidden" style="color: red;">saldo deelopdracht is ontoereikend.</label>
                </td>
            </tr>
            <tr>
                <td >Fase :</td>
                <td ><select name="schuldvordering_fase_id" class="editeerbaar" /></td>
                <td colspan="2" style="padding-left:5px;" >Nummer schuldvordering:</td>
                <td colspan="2" ><input type="text" name="vordering_nr" maxlength="10" class="editeerbaar" ></td>
            </tr>
            <tr>
                <td width="145px" >Datum schuldvordering :</td>
                <td width="100px" ><input type="text" name="vordering_d" readonly ></td>
                <td width="85px" ></td>
                <td width="65px" ></td>
                <td width="100px" ></td>
                <td width="150px" ></td>
            </tr>

            <tr>
                <td>Datum uiterste verificatie:</td>
                <td><input type="text" name="uiterste_verific_d" readonly ></td>
                <td colspan="2" style="padding-left:5px;" >Uiterste datum betaling:</td>
                <td><input type="text" name="uiterste_d" readonly ></td>
                <td ></td>
            </tr>

            <tr>
                <td>Datum effectieve verificatie:</td>
                <td><input type="text" name="goedkeuring_d" readonly ></td>
                <td ></td>
                <td ></td>
                <td ></td>
                <td ></td>
            </tr>

            <tr>
                <td>Van datum:</td>
                <td><input type="text" name="van_d" class="editeerbaar" ></td>
                <td colspan="2" style="padding-left:5px;" >Tot datum:</td>
                <td><input type="text" name="tot_d" class="editeerbaar" ></td>
                <td ></td>
            </tr>
            <tr>
                <td >Inkomend bedrag schuldvordering (incl. BTW):</td>
                <td ><input type="text" name="vordering_bedrag" class="editeerbaar bedrag" ></td>
                <td colspan="2" style="padding-left:5px;" >Gecorrigeerd bedrag schuldvordering:</td>
                <td ><input type="text" name="vordering_correct_bedrag" class="editeerbaar bedrag"  ></td>
                <td ></td>
            </tr>
            <tr>
                <td style="padding-left:5px;" >Inkomend bedrag herziening (incl. BTW):</td>
                <td ><input type="text" name="herziening_bedrag" class="editeerbaar bedrag" ></td>
                <td colspan="2" style="padding-left:5px;" >Gecorrigeerd bedrag prijsherziening:</td>
                <td ><input type="text" name="herziening_correct_bedrag" class="editeerbaar bedrag"  ></td>
                <td ></td>
            </tr>
            <tr>
                <td style="padding-left:5px;" >Bedrag boete:</td>
                <td ><input type="text" name="boete_bedrag" class="editeerbaar bedrag" ></td>
                <td ></td>
                <td ></td>
                <td ></td>
                <td ></td>
            </tr>
            <tr>
                <td >Totaal bedrag goedkeuring (incl. BTW):</td>
                <td ><input type="text" name="goedkeuring_bedrag" disabled ></td>
                <td colspan="4" >
                    <span id="nietAfgekeurdMsg" style="color: blue;"> !! alleen het bedrag dat aan ovam gefactureerd wordt !!</span>
                    <span id="afgekeurdMsg" style="font-weight: bold; color: red;">Schuldvordering is afgekeurd</span>
                </td>
            </tr>
        </table>


        <label>opmerkingen</label>
        <br />
        <textarea  name="commentaar"  style="width:720px;heigth: 200px;"  class="editeerbaar" maxlength="3000" ></textarea>
    </form>

    <div style="margin-bottom: 0px;" >
        <button id="svbewaarBtn" >Bewaar</button>
        <button id="svAanmakenSapBtn">Aanmaken in SAP</button>
        <button id="svGoedkeurenBtn">Goedkeuren</button>
        <button id="svAfkeurenBtn">Afkeuren</button>
    </div>
    <div id="motivatieMsg" style="color: red; margin-top: 5px;" ></div>


    <form id="svProjectForm_0"  style="margin: 0px;" >
        <table>
            <tr>
                    <td >Vastlegging:</td>
                    <td width="100px" ><input type="text" name="initieel_acht_nr" readonly /></td>
                    <td >SAP wbs nummer:</td>
                    <td width="180px" ><input type="text" name="wbs_nr" readonly /></td>
                    <td >Bedrag:</td>
                    <td ><input type="text" name="bedrag" readonly /></td>
            </tr>
        </table>
    </form>
    <form id="svProjectForm_1"  style="margin: 0px;" >
        <table>
            <tr>
                    <td >Vastlegging:</td>
                    <td width="100px" ><input type="text" name="initieel_acht_nr" readonly /></td>
                    <td >SAP wbs nummer:</td>
                    <td width="180px" ><input type="text" name="wbs_nr" readonly /></td>
                    <td >Bedrag:</td>
                    <td ><input type="text" name="bedrag" readonly /></td>
            </tr>
        </table>
    </form>

    <div class="facturen" style="margin-top: 5px;" >Facturen :</div>
    <div id="facturenDiv" class="facturen" style="width: 720px; height: 100px;" ></div>
</div>

