
<div id="planningLijnDialog" class="hidden" title="Editeer Planningslijn" >
    <form id="planningLijnForm" autocomplete="off" novalidate >
        <table>

            <input type="hidden" name="dossier_id" />
            <input type="hidden" name="dossier_type" />

            <input type="hidden" name="fase_code_heeft_details_jn" />

            <input type="hidden" name="contract_nr" />
            <input type="hidden" name="contract_b" />
            <input type="hidden" name="contract_type" />
            <input type="hidden" name="contract_is_raamcontract_jn" />

            <input type="hidden" name="bestek_nr" />
            <input type="hidden" name="bestek_omschrijving" />
            <input type="hidden" name="benut_bestek_id" />

            <tr>
                <td width="80px" >Dossier:</td>
                <td width="420px"  colspan="3">
                    <div style="width: 100%; background-color: #EEE" >
                        <input type="text" name="dossier_nr" readonly style="border: none; width: 48%;" />
                        <input type="text" name="dossier_gemeente_b" readonly style="border: none; width: 48%;" />
                        <input type="text" name="dossier_b" readonly style="border: none; width: 100%;" />
                    </div>
                </td>
            </tr>
            <tr class="fase_code" >
                <td width="80px" >Fase:</td>
                <td width="420px" colspan="3" ><select  name="fase_code" class="input" /></td>
            </tr>
            <tr class="fase_code_detail"  title="verplicht in te vullen binnen boekjaar" >
                <td><span >Fase detail:</span></td>
                <td colspan="3" ><select name="fase_detail_code" class="input"/></td>
            </tr>
            <tr>
                <td width="80px" >Gepland Datum:</td>
                <td width="170px" ><input type="text" name="igb_d" value="" maxlength="40" /></td>
                <td width="80px" >Benut Datum:</td>
                <td width="170px" ><input type="text" name="ibb_d" value="" readonly style="color: blue;" /></td>
            </tr>
            <tr>
                <td>Actie:</td>
                <td colspan="3" >
                    <select name="actie_code" class="input" />
                </td>
            </tr>
            <tr class="contract_id invisible">
                <td>Contract:</td>
                <td colspan="3" ><select name="contract_id" class="input" /></td>
            </tr>
            <tr class="bestek_id"  title="verplicht in te vullen voor raamcontract en binnen boekjaar" >
                <td >Bestek:</td>
                <td colspan="3" ><select name="bestek_id" class="input" /></td>
            </tr>

            <tr class="benut_bestek">
                <td >Benut bestek:</td>
                <td colspan="3" ><input type="text" name="c_bestek_omschrijving" value="" readonly style="color: blue;" /></td>
            </tr>


            <tr>
                <td width="80px" >Gepland Bedrag:</td>
                <td width="170px" ><input type="text" name="ig_bedrag" value="" maxlength="40" /></td>
                <td width="80px" >Benut Bedrag:</td>
                <td width="170px" ><input type="text" name="ib_bedrag" value="" readonly style="color: blue;" /></td>
            </tr>
            <tr>
                <td >Commentaar:</td>
            </tr>
            <tr>
                <td colspan="4" >
                    <textarea name="commentaar" rows="5" maxlength="1000" style="width: 500px" ></textarea>
                </td>
            </tr>
        </table>
    </form>
    <button id="voegDeelopdrToeBtn" >selecteer planningsitem</button>
    <br />
    <button id="bewaarBtn" >Bewaar</button>
    <button id="annuleerBtn" >annuleer</button>
</div>
