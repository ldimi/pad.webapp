/*jslint debug: true, browser: true */
/*global define: false, require: false, $: false, _: false, m: false, console: false, alert: false, window*/

define([
    "ov/mithril/ajax",
    "budget/deelopdracht/deelopdrachtDialog",
    "ov/events"
], function (ajax, deelopdrachtDialog, event) {
    "use strict";

    function deelopdrachtPromise(deelopdracht_id, bestekId, bestekNr, raamcontract_jn, dossier_id, planning_lijn_id, bedrag, voorstel_d) {
        var deferred;
        if (deelopdracht_id) {
            return ajax.getJSON({
                url: '/pad/s/deelopdracht?id=' + deelopdracht_id
            });
        }
        // nieuwe deelopdracht
        deferred = m.deferred();
        deferred.resolve({
            result: {
                bestek_id: bestekId,
                bestek_nr: bestekNr,
                dossier_id: dossier_id,
                planning_lijn_id: planning_lijn_id,
                raamcontract_jn: raamcontract_jn,
                bedrag: bedrag,
                voorstel_d: voorstel_d,
                goedkeuring_d: (raamcontract_jn === 'J') ? null : new Date(),
                status_crud: 'C'
            }
        });
        return deferred.promise;
    }

    function dossiersPromise(deelopdracht_id) {
        return ajax.getJSON({
            url: '/pad/s/deelopdracht/dossiers?deelopdracht_id=' + deelopdracht_id
        });
    }

    function planningLijnenPromise(bestek_id, deelopdracht_id) {
        return ajax.getJSON({
            url: '/pad/s/deelopdracht/planning_lijnen?bestek_id=' + bestek_id + '&deelopdracht_id=' + deelopdracht_id
        });
    }

    function offertenPromise(bestek_id) {
        return ajax.getJSON({
            url: '/pad/s/deelopdracht/offertes?bestek_id=' + bestek_id
        });
    }

    function _openDeelopdracht(deelopdracht_id, bestek_id, bestek_nr, raamcontract_jn, dossier_id, planning_lijn_id, bedrag, voorstel_d) {

        m.sync([
            deelopdrachtPromise(deelopdracht_id, bestek_id, bestek_nr, raamcontract_jn, dossier_id, planning_lijn_id, bedrag, voorstel_d),
            dossiersPromise(deelopdracht_id),
            planningLijnenPromise(bestek_id, deelopdracht_id),
            offertenPromise(bestek_id)
        ])
        .then(function (responseArr) {
            var deelopdracht, dossiers, planningLijnen, offerten;

            deelopdracht = responseArr[0].result;

            dossiers = responseArr[1].result;
            _.each(dossiers, function (item) {
                item.label = item.dossier_b_l;
                item.value = item.dossier_id;
            });
            dossiers.unshift({
                value: "",
                label: ""
            });

            planningLijnen = responseArr[2].result;
            planningLijnen.unshift({
                value: "",
                label: ""
            });

            offerten = responseArr[3].result;
            offerten.unshift({
                value: "",
                label: ""
            });

            event.trigger("deelopdrachtDialog:open", deelopdracht, dossiers, offerten, planningLijnen);
        });
    }

    window.newDeelopdracht = function (bestek_id, bestek_nr, raamcontract_jn, dossier_id, planning_lijn_id, bedrag, voorstel_d) {
        _openDeelopdracht(0, bestek_id, bestek_nr, raamcontract_jn, dossier_id, planning_lijn_id, bedrag, voorstel_d);
    };


    window.openDeelopdracht = function (deelopdracht_id, bestek_id, bestek_nr, raamcontract_jn) {
        _openDeelopdracht(deelopdracht_id, bestek_id, bestek_nr, raamcontract_jn);
    };

    m.mount($("#deelopdrachtDetailDiv").get(0), deelopdrachtDialog);

    if (_G_.bestek_id && _G_.deelopdracht_id) {
        _openDeelopdracht(_G_.deelopdracht_id, _G_.bestek_id);
    }

    return {};
});