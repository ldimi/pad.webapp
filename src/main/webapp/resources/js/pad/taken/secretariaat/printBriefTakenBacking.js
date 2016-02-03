/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, confirm: false,  _: false, m: false, console: false, _G_ */

define([
    "ov/Model"
], function (Model) {
    'use strict';

    var BriefModel, comp, isGeprint;

    BriefModel = Model.extend({
        meta: Model.buildMeta([
            {
                name: "brief_id",
                type: "int"
            }, {
                name: "brief_nr"
            }, {
                name: "inschrijf_d",
                type: "date"
            }, {
                name: "uit_d",
                type: "date"
            }, {
                name: "uit_aard_b"
            }, {
                name: "type_b_vos"
            }, {
                name: "dossier_nr"
            }, {
                name: "doss_hdr_id"
            }, {
                name: "gemeente_b"
            }, {
                name: "naam"
            }
        ])}
    );

    isGeprint = function (brief_id, brief_nr) {
        if (confirm('Brief ' + brief_nr + ' is correct afgedrukt?') === true) {
            window.location = "http://" + window.location.host + "/pad/s/brief/afgedrukt/" + brief_id;
        }
        return false;
    };

    comp = {};
    comp.controller = function() {
        this.tePrintenBrieven = m.prop();
        this.tePrintenBrieven(_.map(_G_.model.tePrintenBrieven, function (it) {
            return new BriefModel(it);
        }));

        this.dms_webdrive_base = m.prop(_G_.model.dms_webdrive_base);
    };


    comp.view = function (ctrl) {
        return [
            m("table", [
                m("tr", [
                    m("th", "Inschrijving"),
                    m("th", "Uit Datum"),
                    m("th", "uit Aard"),
                    m("th", "Type VOS"),
                    m("th", "Ovam Briefnummer"),
                    m("th", "Dossier"),
                    m("th", "Doshdr"),
                    m("th", "Gemeente"),
                    m("th", "Correspondent"),
                    m("th", ""),
                    m("th", "")
                ]),

                _.map(ctrl.tePrintenBrieven(), function (it) {
                    return m("tr", [
                        m("td", it.str("inschrijf_d")),
                        m("td", it.str("uit_d")),
                        m("td", it.str("uit_aard_b")),
                        m("td", it.str("uit_type_vos_b")),
                        m("td", it.str("brief_nr")),
                        m("td", it.str("dossier_nr")),
                        m("td", it.str("doss_hdr_id")),
                        m("td", it.str("gemeente_b")),
                        m("td", it.str("adres_naam")),
                        m("td", m("a", {
                                         href: ctrl.dms_webdrive_base() + it.str("dms_folder") + "/" + it.str("dms_filename"),
                                         tartget: "blank"
                                       },
                                       m("img", {src: "resources/images/AlfrescoLogo32.png", width: 16, height: 16 , border: 0 , alt: "Brief bekijken", title: "Brief bekijken"})
                                )
                        ),
                        m("td", m("a", {
                                    href: "#",
                                    onclick: _.partial(isGeprint, it.get("brief_id"), it.get("brief_nr"))
                                }, "Correct afgedrukt")
                        )
                    ]);
                })
        ])];
    };

    m.mount($("#jsviewContentDiv").get(0), comp);

    return null;
});
