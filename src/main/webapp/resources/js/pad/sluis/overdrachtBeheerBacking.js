/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "sluis/paramsComp",
    "common/dossier/DossierOverdrachtModel",
    "sluis/dossierOverdrachtDialog",
    "ov/GridComp",
    "ov/mithril/ajax",
    "ov/events"
], function (paramsComp, DossierOverdrachtModel, dossierOverdrachtDialog, GridComp, ajax, event) {
    'use strict';
    var _comp, _postData, _processReceivedData, _initGrid;

    // bewerking van globale gegevens
    /////////////////////////////////////////////


    // events en service calls naar backend.
    ///////////////////////////////////////////////////////////

    event.on("dossierOverdracht:fetchOverdrachten", function (params) {
        _postData('/pad/s/sluis/overdrachten', params);
    });

    event.on("dossierOverdracht:save", function (item) {
        _postData('/pad/s/sluis/overdracht/save', item);
    });

    _postData = function (url, data) {
        ajax.postJson({
            url: url,
            content: data
        }).then(function (response) {
            if (response && response.success) {
                _processReceivedData(response.result);
            } else {
                alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            }
        });
    };

    _processReceivedData = function (data) {
        var overdrachtenArray;

        overdrachtenArray = _.map(data.dossierOverdrachtLijst, function (overdrachtAttributes) {
            var overdracht, filterFn, filterFn2;
            overdracht = new DossierOverdrachtModel(overdrachtAttributes);

            filterFn = function (it) {
                return (it.overdracht_id === overdrachtAttributes.overdracht_id);
            };
            filterFn2 = function (it) {
                return (it.dossier_id === overdrachtAttributes.dossier_id);
            };

            overdracht.initParamsCollection(_.filter(data.dossierOverdrachtLijst_parameters, filterFn));
            overdracht.initStofgroepCollection(_.filter(data.dossierOverdrachtLijst_stofgroepen, filterFn));
            overdracht.initActiviteitenCollection(_.filter(data.dossierOverdrachtLijst_activiteiten, filterFn2));
            overdracht.initInstrumentenCollection(_.filter(data.dossierOverdrachtLijst_instrumenten, filterFn2));
            return overdracht;
        });

        event.trigger("dossierOverdracht:dataReceived", {
                overdrachten: overdrachtenArray,
                params: data.params
        });
    };

    // initialisatie van grid
    //////////////////////////////////////////////////

    _initGrid = function (el, isInitialized) {
        var grid;

        if (!isInitialized) {
            grid = new GridComp({
                el: el,
                model: DossierOverdrachtModel,
                editBtn: window._G_.model.isAdminSluis,
                newBtn: window._G_.model.isAdminSluis,
                deleteBtn: window._G_.model.isAdminSluis,
                exportCsv: true,
                exportCsvFileName: "overdrachten.csv",
                onEditClicked: function (item) {
                    var cloned = item.clone();
                    event.trigger("dossierOverdrachtDialog:open", cloned);
                },
                onNewClicked: function () {
                    event.trigger("dossierOverdrachtDialog:open", new DossierOverdrachtModel());
                },
                onDeleteClicked: function (item) {
                    var cloned = item.clone();
                    cloned.set('deleted_jn', 'J');
                    cloned.set('status_crud', 'U');
                    event.trigger("dossierOverdracht:save", cloned);
                }
            });
            event.on("dossierOverdracht:dataReceived", function (data) {
                grid.setData(data.overdrachten);
            });
        }
    };

    // basisComponent voor deze pagina
    ///////////////////////////////////////////////////////

    _comp = {};

    _comp.controller = function () {
        this.paramsCtrl = new paramsComp.controller();
        this.dialogCtrl = new dossierOverdrachtDialog.controller();
    };

    _comp.view = function (ctrl) {

        return [
            m("div", [
                paramsComp.view(ctrl.paramsCtrl)
            ]),
            m("#overdrachtenGrid", {
                config: _initGrid,
                style: {
                    position: "absolute",
                    top: (_G_.model.page_status_list.length > 3) ? "140px" : "60px",
                    left: "5px",
                    right: "5px",
                    bottom: "5px"
                }
            }),
            dossierOverdrachtDialog.view(ctrl.dialogCtrl)
        ];
    };

    m.mount($("#jsviewContentDiv").get(0), _comp);

    return null;
});