/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _:false, RGraph, m,  _G_ */

define([
    "dropdown/dossierhouders",
    "dropdown/jaren",
    "ov/mithril/ajax",
    "ov/mithril/formhelperFactory",
    "ov/Model2",
    "ov/GridComp",
    "ov/events",
    "underscore",
    "RGraph"
], function (dossierhouders, jaren, ajax, fhf, Model, GridComp, events) {
    'use strict';

    var ParamsModel, comp, paramsComp, _getData, _configGeplandGrid, _configVastgelegdGrid, _drawData;


    _getData = function (params) {
        $('#data_div').addClass('invisible');
        ajax.postJSON({
            url: "/pad/s/planning/individueel/grafiek/jaarData",
            content: params
        }).then(function (response) {
            var sommeer;

            // voegt per lijn het oplopend totaal toe
            //   en bouwt een dataArray [ [datumStr, totaal], ...] op voor de grafiek.
            sommeer = function (data) {
                var dataArr = [],
                    som = 0;
                _.each(data, function (item) {
                    var datumStr = $.datepicker.formatDate('yy-mm-dd', item.datum);

                    som = som + item.bedrag;
                    item.totaal = som;
                    dataArr.push([datumStr, item.totaal]);
                });
                data.dataArr = dataArr;
            };

            if (response) {
                sommeer(response.gepland);
                sommeer(response.vastgelegd);
                sommeer(response.gemarkeerd);
                events.trigger("gepland:dataReceived", response.gepland);
                events.trigger("vastgelegd:dataReceived", response.vastgelegd);

                _drawData(params.get("jaar"), response.gepland.dataArr, response.vastgelegd.dataArr, response.gemarkeerd.dataArr);

                $('#data_div').removeClass('invisible');
            }
        });
    };

    _configGeplandGrid = function (el, isInitialized) {
        var grid;
        if (!isInitialized) {
            grid = new GridComp({
                el: el,
                model: Model.extend({
                    meta: Model.buildMeta([
                        {
                            name: "datum",
                            type: "date"
                        }, {
                            name: "bedrag",
                            label: "gepland",
                            type: "double"
                        }, {
                            name: "totaal",
                            type: "double"
                    }])
                })
            });
            events.on("gepland:dataReceived", function (data) {
                grid.setData(data);
            });
        }
    };


    _configVastgelegdGrid = function (el, isInitialized) {
        var grid;
        if (!isInitialized) {
            grid = new GridComp({
                el: el,
                model: Model.extend({
                    meta: Model.buildMeta([
                        {
                            name: "datum",
                            type: "date"
                        }, {
                            name: "bedrag",
                            label: "vastgelegd",
                            type: "double"
                        }, {
                            name: "totaal",
                            type: "double"
                    }])
                })
            });
            events.on("vastgelegd:dataReceived", function (data) {
                grid.setData(data);
            });
        }
    };

    _drawData = function (jaar, data_gepland, data_vastgelegd, data_gemarkeerd) {
        var scatter, preparedata;
        //data_vastgelegd = [['2013-01-01', 45000, 'blue'], ['2013-11-30', 65000, 'blue'], ['2013-12-31', 100000, 'yellow']];

        preparedata = function (data) {
            var eerste_item, laatste_item;
            if (data && data.length > 0) {
                eerste_item = data[0];
                laatste_item = data[data.length - 1];
                data.unshift([
                    eerste_item[0].substring(0, 4) + '-01-01',
                    0
                ]);
                data.push([
                    laatste_item[0].substring(0, 4) + '-12-31',
                    laatste_item[1]
                ]);
            }
        };

        preparedata(data_gepland);
        preparedata(data_vastgelegd);
        preparedata(data_gemarkeerd);

        $('#canvas_div').html('<canvas id="cvs" width="900" height="250">[No canvas support]</canvas>' +
            '<div style=" margin-left: 100px;">' +
            '<div style="width: 20px; height:10px; background-color: green; float: left; "></div>' +
            '<div style="float: left; margin: 0px 50px 0px 10px; color: green;">gepland budget</div>' +
            '<div style="width: 20px; height:10px; background-color: blue; float: left; "></div>' +
            '<div style="float: left; margin: 0px 50px 0px 10px; color: blue;">vastgelegd budget</div>' +
            '<div style="width: 20px; height:10px; background-color: black; float: left; "></div>' +
            '<div style="float: left; margin: 0px 50px 0px 10px; color: black;">goedgekeurd budget</div>' +
            '</div>');

        scatter = new RGraph.Scatter('cvs', data_gepland, data_vastgelegd, data_gemarkeerd)
            .Set('title', 'Individueel gepland/vastgelegd budget')
            .Set('chart.line', true)
            .Set('chart.line.colors', ['green', 'blue', 'black'])
            .Set('chart.line.stepped', true)
            .Set('chart.background.color', 'white')
            .Set('chart.background.grid.autofit.align', true)
            .Set('chart.background.grid.autofit.numvlines', 12)
            //.Set('tickmarks', 'circle')
            .Set('tickmarks', _.noop) // no tickmark
            .Set('xmin', jaar + '-01-01')
            .Set('xmax', jaar + '-12-31')
            .Set('labels', ['Jan', 'Feb', 'Maa', 'Apr', 'Mei', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'])
            .Set('gutter.left', 80)
            .Set('gutter.top', 50);

        // bugfix chromium : dd. 06-11-2013
        //   (zie ook http://www.rgraph.net/blog/2013/january/html5-canvas-dashed-lines.html)
        scatter.context.setLineDash = _.noop;
        scatter.Draw();
    };

    ParamsModel = Model.extend({
        meta: Model.buildMeta([{
            name: "doss_hdr_id",
            required: true,
            default: _G_.model.doss_hdr_id
        }, {
            name: "jaar",
            type: "int",
            required: true,
            default: new Date().getFullYear()
        }, {
            name: "markering_id",
            type: "int"
        }])
    });

    paramsComp = {
        controller: function () {
            this.params = new ParamsModel();

            this.markeringen = _.chain(_G_.model.markeringen)
                .map(_.identity)
                .unshift({
                    value: "",
                    label: ""
                })
                .value();

            this.showErrors = m.prop(false);

            this.ophalen = function () {
                this.showErrors(true);
                if (!this.params.isValid()) {
                    $.notify("Er zijn validatie fouten.");
                    return;
                }
                _getData(this.params);
            };
        },
        view: function (ctrl) {
            var ff;
            ff = fhf.get().setModel(ctrl.params).setShowErrors(ctrl.showErrors());

            return m("table", [
                m("tr", [
                    m("td", ff.select("doss_hdr_id", dossierhouders)),
                    m("td", "Jaar :"),
                    m("td", ff.select("jaar", jaren)),
                    m("td", "Goedgekeurde planning:"),
                    m("td", ff.select("markering_id", ctrl.markeringen)),
                    m("td", m("button.inputBtn", { onclick: _.bind(ctrl.ophalen, ctrl)}, "Ophalen"))
                ])
            ]);
        }
    };


    comp = {
        controller: function () {
            this.paramsCtrl = new paramsComp.controller();
        },
        view: function (ctrl) {
            return [
                m("div", {style: {height: "30px", paddingTop: "5px"}}, [
                    paramsComp.view(ctrl.paramsCtrl)
                ]),
                m("#data_div.invisible", {style: {width: "800px"}}, [
                    m("#gepland_grid_div", {
                        config: _configGeplandGrid,
                        style: {height: "300px", width: "350px", marginTop: "20px", marginLeft: "80px", cssFloat: "left"}
                    }),
                    m("#vastgelegd_grid_div", {
                        config: _configVastgelegdGrid,
                        style: {height: "300px", width: "350px", marginTop: "20px", marginLeft: "5px", cssFloat: "left"}
                    }),
                    m("#canvas_div")
                ])
            ];
        }
    };

    m.mount($("#jsviewContentDiv").get(0), comp);
});