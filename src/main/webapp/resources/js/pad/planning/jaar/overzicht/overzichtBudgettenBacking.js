/*jslint nomen: true, debug: true, browser: true */
/*global define: false, $: false, alert: false, _: false, console: false, RGraph, _G_ */

define([
    "ov/ajax",
    "ov/Meta",
    "ov/GridComp",
    "ov/formatters",
    "./budgetPerProgrammaDetailDialog",
    "./markeerPlanningDialog",
    "./grafiekComp",
    "underscore"
], function (ajax, Meta, GridComp, formatters, budgetPerProgrammaDetailDialog, markeerPlanningDialog, grafiekComp) {
    'use strict';

    var _programmaBudgettenData, _mijlpalenData, _mijlpalenProgrammaData, _dateFormatter, _intFormatter, _customBedragFormatter = null,
        toonBudgetten, toonProgrammaBudgetten, ophalen, voegTotaalBudgetToe, voegMijlpaalBedragenToe, onReady;

    _dateFormatter = formatters("date");
    _intFormatter = formatters("int");

    _customBedragFormatter = function (totaalBedrag, deelBedrag) {
        var percentage = "";

        if (totaalBedrag) {
            percentage = deelBedrag * 100 / totaalBedrag;
            if (percentage > 100) {
                percentage = '<span style="color: red;" >(' + _intFormatter(percentage) + "%)</span>";
            } else {
                percentage = '(' + _intFormatter(percentage) + "%)";
            }
        } else {
            percentage = '&nbsp;';
        }
        return _intFormatter(deelBedrag, ".") + '<span style="width: 50px; display: inline-block; text-align: right;" >' + percentage + "</span>";
    };

    toonBudgetten = function (budgetData) {
        var budgetPerBudgetcodeGrid, budgetPerBudgetcodeMeta;

        $('#budget_per_budgetcode_div').empty();

        budgetPerBudgetcodeMeta = new Meta([{
            name: "jaar",
            hidden: true
        }, {
            name: "budget_code",
            label: "Budget code"
        }, {
            name: "budget",
            label: "Beschikbaar budget volgens SAP",
            type: "int",
            width: 180
        }, {
            name: "effectief_budget",
            label: "Effectief beschikbaar budget",
            type: "int",
            width: 170
        }, {
            name: "gepland_bedrag_per_budgetcode",
            label: "Gepland budget",
            type: "int",
            width: 130,
            gridFormatter: function () {
                return _customBedragFormatter(this.budget, this.gepland_bedrag_per_budgetcode);
            }
        }, {
            name: "vastgelegd_bedrag_per_budgetcode",
            label: "Vastgelegd budget",
            type: "int",
            width: 130,
            gridFormatter: function () {
                return _customBedragFormatter(this.budget, this.vastgelegd_bedrag_per_budgetcode);
            }
        }]);

        budgetPerBudgetcodeGrid = new GridComp({
            el: "#budget_per_budgetcode_div",
            meta: budgetPerBudgetcodeMeta,
            slickOptions: {
                autoHeight: true
            }
        });
        budgetPerBudgetcodeGrid.setData(budgetData);
    };

    voegTotaalBudgetToe = function (programmaBudgettenData) {
        var totaal_budget = 0,
            totaal_gepland = 0,
            totaal_vastgelegd = 0,
            jaar;

        _(programmaBudgettenData).each(function(item) {
            jaar = item.jaar;
            totaal_budget = totaal_budget + item.budget;
            totaal_gepland = totaal_gepland + item.gepland_bedrag_per_programmacode;
            totaal_vastgelegd = totaal_vastgelegd + item.vastgelegd_bedrag_per_programmacode;
        });

        programmaBudgettenData.push({
            jaar: jaar,
            programma_code: "TOTAAL",
            budget: totaal_budget,
            gepland_bedrag_per_programmacode: totaal_gepland,
            vastgelegd_bedrag_per_programmacode: totaal_vastgelegd
        });
    };

    voegMijlpaalBedragenToe = function (programmaBudgettenData, mijlpalenData, mijlpalenProgrammaData) {
        // programmaBudgettenData aanvullen met mijlpaal waarden.
        _.each(programmaBudgettenData, function (item) {
            // met mijlpaal waarden.
            _.each(mijlpalenData, function (mijlpaal, index) {
                var name, mijlpaalProgramma;
                mijlpaalProgramma = _.find(mijlpalenProgrammaData, function (mp) {
                    return (mp.programma_code === item.programma_code && (mp.mijlpaal_d - mijlpaal.mijlpaal_d) === 0);
                });
                mijlpaal = mijlpaalProgramma || mijlpaal;
                name = "mijlpaal_" + (index + 1);
                if (item.budget) {
                    item[name] = item.budget / 100 * mijlpaal.percentage;
                } else {
                    item[name] = null;
                }
            });
        });
    };

    toonProgrammaBudgetten = function (programmaBudgettenData, mijlpalenData) {
        var budgetPerProgrammacodeGrid, metaArr;

        $('#budget_per_programmacode_div').empty();

        metaArr = [{
            name: "jaar",
            hidden: true
        }, {
            name: "programma_code",
            label: "Programma"
        }, {
            name: "budget",
            label: "Programma budget",
            type: "int",
            width: 120
        }, {
            name: "gepland_bedrag_per_programmacode",
            label: "Gepland budget",
            type: "int",
            width: 130,
            gridFormatter: function () {
                return _customBedragFormatter(this.budget, this.gepland_bedrag_per_programmacode);
            }
        }, {
            name: "vastgelegd_bedrag_per_programmacode",
            label: "Vastgelegd budget",
            type: "int",
            width: 130,
            gridFormatter: function () {
                return _customBedragFormatter(this.budget, this.vastgelegd_bedrag_per_programmacode);
            }
        }];

        // meta aanvullen met mijlpalen
        _.each(mijlpalenData, function (mijlpaal, index) {
            var name, label;
            name = "mijlpaal_" + (index + 1);
            label = _dateFormatter(mijlpaal.mijlpaal_d);
            metaArr.push({
                name: name,
                label: label,
                type: "int",
                width: 120,
                gridFormatter: function () {
                    return _customBedragFormatter(this.budget, this[name]);
                }
            });
        });


        budgetPerProgrammacodeGrid = new GridComp({
            el: "#budget_per_programmacode_div",
            meta: new Meta(metaArr),
            slickOptions: {
                autoHeight: true
            },
            newBtn: window._G_.isAdminArt46,
            editBtn: window._G_.isAdminArt46,
            deleteBtn: window._G_.isAdminArt46,
            onNewClicked: function (item, data) {
                budgetPerProgrammaDetailDialog.open({
                    jaar: $('#paramForm').find("[name=jaar]").val(),
                    programma_code: null,
                    budget: null,
                    status_crud: "C"
                }, data, ophalen);
            },
            onEditClicked: function (item, data) {
                if (window._G_.isAdminArt46) {
                    if (item.budget) {
                        item.status_crud = "R";
                    } else {
                        item.status_crud = "C";
                    }
                    budgetPerProgrammaDetailDialog.open(item, data, ophalen);
                }
            },
            onDeleteClicked: function (item) {
                budgetPerProgrammaDetailDialog.verwijder(item, ophalen);
            }
        });
        budgetPerProgrammacodeGrid.setData(programmaBudgettenData);
    };


    ophalen = function () {
        var jaar, budgettenPromise, programmaBudgettenPromise, mijlpalenPromise, mijlpalenProgrammaPromise, getJSON;

        jaar = $('#paramForm').find("[name=jaar]").val();
        $('#jb_content_div').addClass('invisible');
        $('#canvas_div').addClass("invisible");

        if (!jaar) {
            return; // no action
        }

        getJSON = function (url) {
            return ajax.getJSON({
                url: url,
                content: {
                    "jaar": jaar
                }
            });
        };

        budgettenPromise = getJSON('/pad/s/planning/jaar/budgetPerBudgetcode');
        programmaBudgettenPromise = getJSON('/pad/s/planning/jaar/budgetPerProgrammacode');
        mijlpalenPromise = getJSON('/pad/s/beheer/getJaarMijlpalen');
        mijlpalenProgrammaPromise = getJSON('/pad/s/beheer/getJaarMijlpalenProgramma');

        $.when(budgettenPromise, programmaBudgettenPromise, mijlpalenPromise, mijlpalenProgrammaPromise)
            .done(function (jaarbudgetResp, programmaBudgettenResp, mijlpalenResp, mijlpalenProgrammaResp) {
                var budgetData;
                budgetData = jaarbudgetResp[0];
                _programmaBudgettenData = programmaBudgettenResp[0];
                _mijlpalenData = mijlpalenResp[0];
                _mijlpalenProgrammaData = mijlpalenProgrammaResp[0];

                console.log(JSON.stringify(_programmaBudgettenData));
                console.log(JSON.stringify(_mijlpalenData));
                console.log(JSON.stringify(_mijlpalenProgrammaData));

                $('#jb_content_div').removeClass('invisible');

                toonBudgetten(budgetData);

                voegTotaalBudgetToe(_programmaBudgettenData);
                voegMijlpaalBedragenToe(_programmaBudgettenData, _mijlpalenData, _mijlpalenProgrammaData);
                toonProgrammaBudgetten(_programmaBudgettenData, _mijlpalenData);

                grafiekComp.setData(jaar, _mijlpalenData, _programmaBudgettenData);
            });
    };

    onReady = function () {
        $('#paramForm').find("[name=jaar]").val(new Date().getFullYear());


        $("#ophalenBtn").click(function () {
            ophalen();
        }).removeClass("invisible");

        $('#markeerBtn').click(function () {
            markeerPlanningDialog.open();
        });

        grafiekComp.init();
    };

    return {
        onReady: onReady
    };
});