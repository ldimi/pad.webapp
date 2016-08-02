/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    // "dropdown/provincies",
    "ov/Model",
    "ov/mithril/formhelperFactory",
    "ov/mithril/ajax"
], function (Model, fhf, ajax) {
    'use strict';

    var _comp, ZoekParamsModel, provincies;

    ZoekParamsModel = Model.extend({
        meta: Model.buildMeta([
            { name: "naam_adres" },
            { name: "naam_contact" },
            { name: "gemeente" },
            { name: "provincie" },
            { name: "type_id" },
            { name: "actief_s" }
        ])
    });

    provincies = [
        {value: "", label: ""},
        {value: "ANTWERPEN", label: "ANTWERPEN"},
        {value: "LIMBURG", label: "LIMBURG"},
        {value: "OOST VLAANDEREN", label: "OOST VLAANDEREN"},
        {value: "VLAAMS BRABANT", label: "VLAAMS BRABANT"},
        {value: "WEST VLAANDEREN", label: "WEST VLAANDEREN"}
    ];


    _comp = {};
    _comp.controller = function () {
        if (_G_.model.params) {
            this.params = new ZoekParamsModel(_G_.model.params);
        } else {
            this.params = new ZoekParamsModel();
        }
        this.results = m.prop("");
        this.showErrors = m.prop(false);


        this.zoeken = function () {
            ajax.getHtml({
                url: '/pad/s/adres/zoeken',
                content: this.params.toJSON()
            }).then(function (response) {
                //alert(response);
                this.setResults(response);
            }.bind(this));
        };
        
        this.sorteren = function (url) {
            m.redraw.strategy("all");
            ajax.getHtml({
                url: url
            }).then(function (response) {
                console.log(response);
                this.setResults(response);
            }.bind(this));
        };
        
        
        this.setResults = function (resultHtml) {
            this.results(resultHtml);
            window.setTimeout(function() {
                console.log("set click handlers");
                console.log($("th.sortable>a").length);
                $("th.sortable>a").click(function (evt) {
                    evt.preventDefault();
                    console.log("start sorteren");
                    this.sorteren(evt.target.href);
                    console.log("stop sorteren");
                    return false;
                }.bind(this));
                console.log("stop set click handlers");
            }.bind(this));
        };
    };

    _comp.view = function (ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

        return m("div", {style: {margin: "50px"}}, [
                    (_G_.model.errorMsg) ?
                        m("div", {style: {color : "red"}} , _G_.model.errorMsg) : null,
                    m("h3", "Zoek adres"),
                    m("table.formlayout", [
                        m("tbody", [
                            m("tr", [
                                m("td", "Naam"),
                                m("td[width=250px]", ff.input("naam_adres"))
                            ]),
                            m("tr", [
                                m("td", "Naam contactpersoon"),
                                m("td", ff.input("naam_contact"))
                            ]),
                            m("tr", [
                                m("td", "Gemeente"),
                                m("td", ff.input("gemeente"))
                            ]),
                            m("tr", [
                                m("td", "Provincie"),
                                m("td", ff.select("provincie", {class: "input"}, provincies))
                            ]),
                            m("tr", [
                                m("td", "Type"),
                                m("td", ff.input("type_id"))
                            ]),
                            m("tr", [
                                m("td[colspan=2]", ff.checkbox("actief_s", "Inclusief niet actief"))
                            ]),
                            m("tr",
                                m("td[colspan=4][align=right]",
                                    m("button.inputbtn", {onclick: ctrl.zoeken.bind(ctrl)}, "Zoeken2" )
                                )
                            )
                        ])
                    ]),
                    m.trust(ctrl.results())
                ]);


    };

    m.mount($("#jsviewContentDiv").get(0), _comp);
});
