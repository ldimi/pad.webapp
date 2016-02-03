/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory"
], function (Model, events, ajax, fhf) {
    'use strict';

    var _comp, DossierOrganisatieEmailModel;

    DossierOrganisatieEmailModel = Model.extend({
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
            }, {
                name: "email",
                required: true
        }]),
        enforceInvariants: function () {
            if (this.hasChanged("organisatietype")) {
                this.attributes.organisatie_id = null;
                this.attributes.email = null;
                this.trigger("change:organisatietype", this.get("organisatietype"));
            }
            if (this.hasChanged("organisatie_id")) {
                this.attributes.email = null;
                this.trigger("change:organisatie_id", this.get("organisatie_id"));
            }
        }
    });



    _comp = {};
    _comp.controller = function () {

        // lijst van DossierOrganisatieEmailModel opbouwen.
        //     label zetten op basis van gegevens organisatiesVoorDossiers
        //     bedrijfstype per organisatie groeperen en als string toevoegen
        this.modelLijst = _.map(_G_.model.dossierOrganisatieEmail_lijst, function (dosOrgEmail) {
            var org, typesMap;

            // label zetten op basis van gegevens organisatiesVoorDossiers
            org = _.find(_G_.model.organisatiesVoorDossiers, function (it) {
                return dosOrgEmail.organisatie_id === it.organisatie_id;
            });
            dosOrgEmail.label = (org && org.label) || "ERROR";

            // bedrijfstype per organisatie groeperen en als string toevoegen
            typesMap = {};
            _.each(_G_.model.organisatiesVoorDossiers, function (it) {
                if (dosOrgEmail.organisatie_id === it.organisatie_id)  {
                    typesMap[it.organisatietype] = 1;
                }
            });
            dosOrgEmail.types = _.keys(typesMap).join(", ");

            return new DossierOrganisatieEmailModel(dosOrgEmail);
        });

        this.organisatietypes_dd = _.map(_G_.model.organisatietypes, function(type) {
            return {value: type.organisatietype, label: type.organisatietype};
        });
        this.organisatietypes_dd.unshift({value: "", label: ""});

        this.organisaties_dd = null;
        this.email_dd = null;

        this.currentItem = new DossierOrganisatieEmailModel();
        this.currentItem.set("dossier_id", _G_.model.dossier_id);

        this.currentItem.on("change:organisatietype", this.filter_organisaties_dd.bind(this));
        this.currentItem.on("change:organisatie_id", this.fetch_email_dd.bind(this));

        this.showErrors = m.prop(false);
    };
    _.extend(_comp.controller.prototype, {
        toevoegen: function() {
            this.showErrors(true);
            if (!this.currentItem.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }
            ajax.postJson({
                url: '/pad/s/dossier/add/organisatieAndEmail',
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
        verwijder: function(item) {
            ajax.postJson({
                url: '/pad/s/dossier/remove/organisatieAndEmail',
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
        filter_organisaties_dd: function (organisatietype) {
            if (organisatietype) {
                // TODO : leege selectie  geeft "null" string ???
                this.organisaties_dd = _.chain(_G_.model.organisatiesVoorDossiers)
                    .filter(function (org) {
                        return (organisatietype === org.organisatietype);
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
        },
        fetch_email_dd: function(organisatie_id) {
            if (organisatie_id) {
                ajax.getJson({
                    url: '/pad/s/dossier/emailsVanOrganisatie/' + organisatie_id,
                    background: true   // redraw moet niet wachten op resultaat.
                }).then(function (response) {
                    if (response && response.success) {
                        this.email_dd = response.result;
                        this.email_dd.unshift({value: "", label: ""});
                    } else {
                        alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                    }
                    m.redraw();
                }.bind(this));
            } else {
                this.email_dd = null;
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
                    m("th", "Organisatie"),
                    m("th", {width: "150px"}, "email")
                ]),
                m("tbody", _.map(ctrl.modelLijst, function(dosOrgEmail) {
                    return m("tr", [
                        m("td", dosOrgEmail.get("types")),
                        m("td", dosOrgEmail.get("label")),
                        m("td", dosOrgEmail.get("email")),
                        m("td", m("button", {onclick: _.bind(ctrl.verwijder, ctrl, dosOrgEmail)}, "Verwijder"))
                    ]);
                })),
                m("tr", [
                    m("td", ff.select("organisatietype", ctrl.organisatietypes_dd)),
                    m("td", ctrl.organisaties_dd ? ff.select("organisatie_id", ctrl.organisaties_dd) : null),
                    m("td", ctrl.email_dd ? ff.select("email", ctrl.email_dd) : null),
                    m("td", m("button", {onclick: _.bind(ctrl.toevoegen, ctrl)}, "Toevoegen"))
                ])
            ])
        ]);


    };

    m.mount($("#jsviewContentDiv").get(0), _comp);
});
