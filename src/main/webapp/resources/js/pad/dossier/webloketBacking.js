/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "dossier/LoginLijstDialog",
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (LoginLijstDialog, Model, events, ajax, fhf) {
    'use strict';

    var _comp, DossierOrganisatieModel;

    DossierOrganisatieModel = Model.extend({
        constructor: function (attrs) {
            events.convert(this);
            Model.call(this, attrs);
        },
        meta: Model.buildMeta([{
                name: "dossier_id",
                type: "int",
                required: true
            }, {
                name: "organisatietype"
            }, {
                name: "organisatie_id",
                type: "int",
                required: true
            }, {
                name: "label"
            }, {
                name: "types"
        }]),
        enforceInvariants: function () {
            if (this.hasChanged("organisatietype")) {
                this.attributes.organisatie_id = null;
                this.trigger("change:organisatietype", this.get("organisatietype"));
            }
        }
    });



    _comp = {};
    _comp.controller = function () {

        // lijst van DossierOrganisatieModel opbouwen.
        //     label zetten op basis van gegevens organisatie_lijst
        //     bedrijfstype per organisatie groeperen en als string toevoegen
        this.modelLijst = _.map(_G_.model.dossierOrganisatie_lijst, function (dosOrg) {
            var org, typesMap;

            // label zetten op basis van gegevens organisatie_lijst
            org = _.find(_G_.model.organisatie_lijst, function (it) {
                return dosOrg.organisatie_id === it.organisatie_id;
            });
            dosOrg.label = (org && org.label) || "ERROR";

            // bedrijfstype per organisatie groeperen en als string toevoegen
            typesMap = {};
            _.each(_G_.model.organisatie_organisatietype_lijst, function (it) {
                if (dosOrg.organisatie_id === it.organisatie_id)  {
                    typesMap[it.organisatietype] = 1;
                }
            });
            dosOrg.types = _.keys(typesMap).join(", ");

            return new DossierOrganisatieModel(dosOrg);
        });

        this.organisatietypes_dd = _.map(_G_.model.organisatietypes, function(type) {
            return {value: type.organisatietype, label: type.organisatietype};
        });
        this.organisatietypes_dd.unshift({value: "", label: ""});

        this.organisaties_dd = null;

        this.currentItem = new DossierOrganisatieModel();
        this.currentItem.set("dossier_id", _G_.model.dossier_id);

        this.currentItem.on("change:organisatietype", this.filter_organisaties_dd.bind(this));

        this.showErrors = m.prop(false);

        this.loginLijstDialogCtrl = new LoginLijstDialog.controller();
    };
    _.extend(_comp.controller.prototype, {
        toevoegen: function() {
            this.showErrors(true);
            if (!this.currentItem.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }
            if (this.checkDuplicate()) {
                $.notifyError("Deze organisatie is reeds gekoppeld.");
                return;
            }
            ajax.postJson({
                url: '/pad/s/dossier/add/organisatie',
                data: this.currentItem
            }).then(function (response) {
                if (response && response.success) {
                    var location = window.location;
                    window.location = location;
                } else {
                    alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
                }
            }.bind(this));
        },
        checkDuplicate: function () {
            return _.some(this.modelLijst, function (dosorg) {
                return dosorg.get("organisatie_id") === this.currentItem.get("organisatie_id");
            }, this);
        },
        verwijder: function(item) {
            ajax.postJson({
                url: '/pad/s/dossier/remove/organisatie',
                data: item
            }).then(function (response) {
                if (response && response.success) {
                    var location = window.location;
                    window.location = location;
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            }.bind(this));
        },
        openLoginlijst: function(item) {
            events.trigger("LoginLijstDialog:open", item.get("organisatie_id"));
        },
        filter_organisaties_dd: function (organisatietype) {
            var organisatieIds;
            if (organisatietype) {

                organisatieIds = _.chain(_G_.model.organisatie_organisatietype_lijst)
                    .filter(function (org_orgtype) {
                        return (organisatietype === org_orgtype.organisatietype);
                    })
                    .map(function (org_orgtype) {
                        return org_orgtype.organisatie_id;
                    })
                    .uniq()
                    .value();

                this.organisaties_dd = _.chain(_G_.model.organisatie_lijst)
                    .filter(function (org) {
                        return (org.actief_jn === 'J' &&  _.contains(organisatieIds, org.organisatie_id));
                    }.bind(this))
                    .map(function (org) {
                        return {value: org.organisatie_id, label: org.label, naam: org.naam};
                    })
                    .sortBy("naam")
                    .value();


                this.organisaties_dd.unshift({value: "", label: ""});
            } else {
                this.organisaties_dd = null;
            }
        }
    });

    _comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.currentItem).setShowErrors(ctrl.showErrors());

        return m("div", {style: {margin: "10px", "background-color": "white"}}, [
            m("h3", [
                "webloket url : ",
                m("a", {href: _G_.model.dossierUrl, target: "_blank"}, _G_.model.dossierUrl)
            ]),
            m("table",[
                m("thead", [
                    m("th", "Type"),
                    m("th", "Organisatie")
                ]),
                m("tbody", _.map(ctrl.modelLijst, function(dosOrg) {
                    return m("tr", [
                        m("td", dosOrg.get("types")),
                        m("td", dosOrg.get("label")),
                        m("td", m("button", {onclick: _.bind(ctrl.openLoginlijst, ctrl, dosOrg), class: "inputbtn"}, "Logins ...")),
                        m("td", m("button", {onclick: _.bind(ctrl.verwijder, ctrl, dosOrg), class: "inputbtn"}, "Verwijder"))
                    ]);
                })),
                m("tr", [
                    m("td", ff.select("organisatietype", ctrl.organisatietypes_dd)),
                    m("td", ctrl.organisaties_dd ? ff.select("organisatie_id", ctrl.organisaties_dd) : null),
                    m("td"),
                    ctrl.currentItem.get("organisatie_id")
                        ? m("td", m("button", {onclick: _.bind(ctrl.toevoegen, ctrl), class: "inputbtn"}, "Toevoegen"))
                        : null
                ])
            ]),
            LoginLijstDialog.view(ctrl.loginLijstDialogCtrl)
        ]);


    };

    m.mount($("#jsviewContentDiv").get(0), _comp);
});
