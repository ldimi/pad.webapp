/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, RGraph */

define([
    "ov/ajax",
    "ov/Meta",
    "RGraph"
], function (ajax, Meta) {
    'use strict';

    var init, setData, fillProgrammaCodeSelect, ophalenProgrammaGrafiek, genereerGrafiek, getProgrammaBudgetData, maakProgrammaMijlpaalArr = null,
        _mijlpalenData, _programmaBudgettenData, _paramGrafiekMeta, formatDate, _paramGrafiekFM;

    _paramGrafiekMeta = new Meta([
        { name: "jaar", type: "int" },
        { name: "budget_code" },
        { name: "markering_id", type: "int" }
    ]);

    init = function () {
        _paramGrafiekFM = $('#paramGrafiekForm').ov_formManager({
            meta: _paramGrafiekMeta
        });

        $("#genereerGrafiekBtn").click(function () {
            var paramsGrafiek = _paramGrafiekFM.values();
            ophalenProgrammaGrafiek(paramsGrafiek.jaar, paramsGrafiek.programma_code, paramsGrafiek.markering_id);
        });
    };

    setData = function (jaar, mijlpalenData, programmaBudgettenData) {
        _paramGrafiekFM.$jaar.ov_value(jaar);

        _mijlpalenData = mijlpalenData;
        _programmaBudgettenData = programmaBudgettenData;
        fillProgrammaCodeSelect(programmaBudgettenData);
    };

    fillProgrammaCodeSelect = function (programmaBudgettenData) {
        var optionList;

        _paramGrafiekFM.$programma_code.ov_value(null);

        optionList = [];
        _.each(programmaBudgettenData, function (item) {
            if (item.programma_code) {
                optionList.push({
                    label: item.programma_code,
                    value: item.programma_code
                });
            }
        });
        _paramGrafiekFM.$programma_code.select('setOptionList', optionList);

    };

    formatDate = function (date) {
        return $.datepicker.formatDate('yy-mm-dd', date);
    };


    ophalenProgrammaGrafiek = function (jaar, programma_code, markering_id) {
        var programmaGeplandPromise, programmaBenutPromise, programmaGemarkeerdPromise;

        programmaGeplandPromise = ajax.postJSON({
            url: '/pad/s/planning/jaar/getProgrammaGeplandData',
            content: {
                "jaar": jaar,
                "programma_code": programma_code
            }
        });

        programmaBenutPromise = ajax.postJSON({
            url: '/pad/s/planning/jaar/getProgrammaBenutData',
            content: {
                "jaar": jaar,
                "programma_code": programma_code
            }
        });

        programmaGemarkeerdPromise = ajax.postJSON({
            url: '/pad/s/planning/jaar/getProgrammaGemarkeerdePlanningData',
            content: {
                "jaar": jaar,
                "programma_code": programma_code,
                "markering_id": markering_id
            }
        });

        $.when(programmaGeplandPromise, programmaBenutPromise, programmaGemarkeerdPromise)
            .then(function (geplandResp, benutResp, gemarkeerdResp) {
                var programmaMijlpaalArr, jaar;

                jaar = _paramGrafiekFM.$jaar.ov_value();
                programmaMijlpaalArr = maakProgrammaMijlpaalArr(jaar, getProgrammaBudgetData(programma_code), _mijlpalenData);
                genereerGrafiek(geplandResp[0], benutResp[0], programmaMijlpaalArr, gemarkeerdResp[0]);
            });
    };

    genereerGrafiek = function (programmaGeplandData, programmaBenutData, programmaMijlpaalArr, programmaGemarkeerdData) {
        var jaar, scatter, preparedata, sommeer;

        // voegt per lijn het oplopend totaal toe
        //   en bouwt een dataArray [ [datumStr, totaal], ...] op voor de grafiek.
        sommeer = function (data) {
            var dataArr = [],
                som = 0;
            _.each(data, function (item) {
                var datumStr = formatDate(item.datum);

                som = som + item.bedrag;
                item.totaal = som;
                dataArr.push([datumStr, item.totaal]);
            });
            data.dataArr = dataArr;
        };

        sommeer(programmaGeplandData);
        sommeer(programmaBenutData);
        sommeer(programmaGemarkeerdData);

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

        preparedata(programmaGeplandData.dataArr);
        preparedata(programmaBenutData.dataArr);
        preparedata(programmaGemarkeerdData.dataArr);

        $('#canvas_div').addClass("invisible");
        $('#canvas_div').html('<canvas id="cvs" width="900" height="350">[No canvas support]</canvas>' +
            '<div style=" margin-left: 100px;">' +
            '<div style="width: 20px; height:10px; background-color: red; float: left;"></div>' +
            '<div style="float: left; margin: 0px 50px 0px 10px; color: red;">mijlpalen</div>' +
            '<div style="width: 20px; height:10px; background-color: green; float: left; "></div>' +
            '<div style="float: left; margin: 0px 50px 0px 10px; color: green;">gepland budget</div>' +
            '<div style="width: 20px; height:10px; background-color: blue; float: left; "></div>' +
            '<div style="float: left; margin: 0px 50px 0px 10px; color: blue;">vastgelegd budget</div>' +
            '<div style="width: 20px; height:10px; background-color: black; float: left; "></div>' +
            '<div style="float: left; margin: 0px 50px 0px 10px; color: black;">goedgekeurd budget</div>' +
            '</div>');

        jaar = _paramGrafiekFM.$jaar.ov_value();

        scatter = new RGraph.Scatter('cvs', programmaGeplandData.dataArr, programmaBenutData.dataArr, programmaMijlpaalArr, programmaGemarkeerdData.dataArr)
            .Set('title', 'Beschikbaar, gepland, vastgelegd jaarbudget')
            .Set('chart.line', true)
            .Set('chart.line.colors', ['green', 'blue', 'red', 'black'])
            .Set('chart.line.stepped', true)
            .Set('chart.background.color', 'white')
            .Set('chart.background.grid.autofit.align', true)
            .Set('chart.background.grid.autofit.numvlines', 12)
            .Set('tickmarks', 'circle')
            .Set('ticksize', 2)
            //.Set('tickmarks', function () {}) // no tickmark
            .Set('xmin', jaar + '-01-01')
            .Set('xmax', jaar + '-12-31')
            .Set('labels', ['Jan', 'Feb', 'Maa', 'Apr', 'Mei', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'])
            .Set('gutter.left', 80)
            .Set('gutter.top', 30);

        // bugfix chromium : dd. 06-11-2013
        //   (zie ook http://www.rgraph.net/blog/2013/january/html5-canvas-dashed-lines.html)
        scatter.context.setLineDash = function () {};
        scatter.Draw();
        $('#canvas_div').removeClass('invisible');
    };

    maakProgrammaMijlpaalArr = function (jaar, budgetData, mijlpalenData) {
        var result, datumStr, budget;
        budget = budgetData.budget;
        result = [];
        datumStr = jaar + "-01-01";
        _(mijlpalenData).map(function (item, index) {
            result.push([datumStr, budgetData["mijlpaal_" + (index + 1)]]);
            datumStr = formatDate(item.mijlpaal_d);
        });
        result.push([datumStr, budget]);
        result.push([jaar + "-12-31", budget]);
        return result;
    };

    getProgrammaBudgetData = function (programma_code) {
        return _(_programmaBudgettenData).find(function (item) {
            return (item.programma_code === programma_code);
        });
    };

    return {
        init: init,
        setData: setData
    };
});