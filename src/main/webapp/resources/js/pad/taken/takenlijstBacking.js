/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, m: false, console: false, _G_ */

define([
    "taken/TaakModel",
    "taken/nieuw_pad_dossier_taakDialog",
    "taken/brief_in_check_afd_hfd_taakDialog",
    "taken/brief_in_check_auteur_taakDialog",
    "ov/GridComp",
    "ov/Model",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (TaakModel, nieuw_pad_dossier_taakDialog, brief_in_check_afd_hfd_taakDialog, brief_in_check_auteur_taakDialog, GridComp, Model, ajax, fhf) {
    'use strict';

    var comp, ParamsModel, _initGrid, _grid, nieuw_pad_dossier_taakDialogCtrl, brief_in_check_afd_hfd_taakDialogCtrl, brief_in_check_auteur_taakDialogCtrl;

    window.taakAfwerken = function (cid) {
        var taak;
        cid = parseInt(cid, 10);
        taak = _.find(_grid.getData(), function(item) {
            return item.cid === cid;
        });
        if (taak.get("taak_type") === 'brief_in_check_afd_hfd') {
            brief_in_check_afd_hfd_taakDialogCtrl.open(taak);
            return;
        }
        if (taak.get("taak_type") === 'brief_in_check_auteur') {
            brief_in_check_auteur_taakDialogCtrl.open(taak);
            return;
        }
        if (taak.get("taak_type") === 'nieuw PAD dossier') {
            nieuw_pad_dossier_taakDialogCtrl.open(taak);
            return;
        }
        alert("onbekend taak type :" + taak.get("taak_type"));
    };

    window.taakAfgesloten = function (taak) {
        var data = _grid.getData();
        data = _.filter(data, function (item) {
            return item.cid !== taak.cid;
        });
        _grid.setData(data);
    };

    nieuw_pad_dossier_taakDialogCtrl = new nieuw_pad_dossier_taakDialog.controller({
        taakAfgeslotenCb: window.taakAfgesloten
    });
    brief_in_check_afd_hfd_taakDialogCtrl = new brief_in_check_afd_hfd_taakDialog.controller({
        taakAfgeslotenCb: window.taakAfgesloten
    });
    brief_in_check_auteur_taakDialogCtrl = new brief_in_check_auteur_taakDialog.controller({
        taakAfgeslotenCb: window.taakAfgesloten
    });

    ParamsModel = Model.extend({
        meta: Model.buildMeta([{
            name: 'doss_hdr_id'
        }])
    });

    _initGrid = function () {
        _grid = new GridComp({
            el: '#takenlijstDiv',
            model: TaakModel
        });
    };



    comp = {};
    comp.controller = function() {

        console.log("init controller");

        this.params = new ParamsModel({doss_hdr_id: _G_.doss_hdr_id});
        this.dossierhoudersOptions = _(_G_.dossierhouders).map(function (dossHdr) {
            return {
                value: dossHdr.doss_hdr_id,
                label: dossHdr.doss_hdr_b
            };
        });
    };
    _.extend(comp.controller.prototype, {
        getTaken: function () {
            var self, urlArr, promiseArr;

            self = this;
            urlArr = [
                "/pad/s/takenlijst/sv/beoordelen",
                "/pad/s/takenlijst/sv/ondertekenen",
                "/pad/s/takenlijst/sv/printen",
                "/pad/s/takenlijst/sv/scannen",
                "/pad/s/takenlijst/voorstelDeelopdracht/beoordelen",
                "/pad/s/takenlijst/brief_in_check_afd_hfd",
                "/pad/s/takenlijst/brief_in_check_auteur",
                "/pad/s/takenlijst/brieven_printen",
                "/pad/s//takenlijst/deelopdracht/goedkeuren",
                "/pad/s/takenlijst/nieuw_pad_dossier"
            ];

            $('#takenlijstDiv').addClass('invisible');


            promiseArr = _(urlArr).map( function (url) {
                return ajax.postJson({
                    url: url,
                    content: self.params
                });
            });

            m.sync(promiseArr).then(function (results) {
                var arr = _.chain(results)
                    .flatten()
                    .sortBy(function(taak) {
                        return (10000 * taak.taak_type_nr + taak.termijn);
                    })
                    .value();

                _grid.setData(arr);
                $('#takenlijstDiv').removeClass('invisible');
            });
        }
    });





    comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.params);

        return [
            m("div", [
                m("#paramsDiv", [
                    m("form[autocomplete=off]", [
                        m("label", [
                            m("span", "Dossierhouder:"),
                            ff.select("doss_hdr_id", {
                                    class: "input"
                                },
                                ctrl.dossierhoudersOptions
                            )
                        ])
                    ]),
                    m("button", {onclick: _.bind(ctrl.getTaken, ctrl)}, "Ophalen")
                ])
            ]),
            m("#takenlijstDiv", {
                style: {
                    position: "absolute",
                    top: "50px",
                    left: "5px",
                    right: "5px",
                    bottom: "5px"
                },
                class: "invisible"
            }),
            nieuw_pad_dossier_taakDialog.view(nieuw_pad_dossier_taakDialogCtrl),
            brief_in_check_afd_hfd_taakDialog.view(brief_in_check_afd_hfd_taakDialogCtrl),
            brief_in_check_auteur_taakDialog.view(brief_in_check_auteur_taakDialogCtrl)
        ];
    };

    m.mount($("#detailDiv").get(0), comp);

    return {
        onReady: function () {
            _initGrid();
        }
    };
});
