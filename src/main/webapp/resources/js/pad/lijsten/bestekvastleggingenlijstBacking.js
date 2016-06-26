/*jslint nomen: true, debug: true, browser: true */
/*global define: false, require: false, $: false, _: false, console: false, alert: false */


define(["ov/ajax", "ov/Meta", "ov/GridComp"], function (ajax, Meta, GridComp) {
    "use strict";
    var detailVastleggingDialog, vastleggingsAanvragenGrid, optioneleBestandenGrid, vastleggingMeta, bestandenMeta,
        vastleggingFm, betalingMeta, vastleggingsBetalingsFm, optioneleBestandenFm, vastleggingsBetalingsGrid, vastleggingsBetalingsDialog,
        bestekId, postData, optioneleBestandenDialog, aanvraagId;

    aanvraagId = 0;
    vastleggingMeta = new Meta([
        {
            name: "id",
            hidden: true
        },
        {
            name: "bestekid",
            hidden: true
        },
        {
            name: "aanvraagid",
            hidden: true
        },
        {
            name: "wbsBestek",
            hidden: true
        },
        {
            name: "opdrachthouder_id",
            type: "int",
            hidden: true,
            required: true
        },
        {
            name: "status",
            label: "Status",
            width: 100,
            hidden: false
        },
        {
            name: "project_id",
            label: " ",
            width: 6,
            hidden: false,
            gridFormatter: function (value) {
                return '<img src="/pad/resources/images/edit.gif" alt="Details Projectfiche" style="margin: 0px" onclick="popupSapProjectDetails(' + value + ')">';
            }
        },
        {
            name: "bestek_nr",
            label: "Besteknummer",
            width: 100,
            hidden: false
        },
        {
            name: "type_b",
            label: "Type.",
            width: 100,
            hidden: false
        },
        {
            name: "procedure_b",
            label: "Procedure",
            hidden: false
        },
        {
            name: "project_id",
            label: "Fiche Nr.",
            width: 100,
            hidden: false
        },
        {
            name: "initieel_acht_nr",
            label: "Vastlegging nr",
            width: 100,
            hidden: false
        },
        {
            name: "project_b",
            label: "Omschrijving",
            width: 100,
            href: "www.google.com",
            hidden: false
        },
        {
            name: "budgetair_artikel",
            label: "Artikel",
            width: 100,
            hidden: true
        },
        {
            name: "budgetair_artikel_b",
            label: "Artikel",
            width: 100,
            hidden: false
        },
        {
            name: "schuldeiser",
            label: "Schuldeiser",
            width: 100,
            hidden: false
        },
        {
            name: "contactpersoon",
            label: "Dossierhouder",
            width: 100
        },
        {
            name: "initieel_bedrag",
            label: "Initeel bedrag",
            width: 100,
            type: "double",
            hidden: false
        },
        {
            name: "verbruik",
            label: "Verbruik",
            width: 100,
            type: "double",
            hidden: false
        },
        {
            name: "commentaar",
            hidden: true
        },
        {
            name: "raamcontract_s",
            hidden: true
        },
        {
            name: "planningsitem",
            hidden: true,
            required: true
        },
        {
            name: "inspectievanfinancien",
            type: "date",
            hidden: true
        },
        {
            name: "voogdijminister",
            type: "date",
            hidden: true
        },
        {
            name: "ministervanbegroting",
            type: "date",
            hidden: true
        },
        {
            name: "vlaamseregering",
            type: "date",
            hidden: true
        },
        {
            name: "vast_bedrag",
            type: "double",
            hidden: true,
            required: true
        },
        {
            name: "budgetairartikel",
            hidden: true,
            required: true
        },
        {
            name: "kostenplaats",
            hidden: true,
            required: true
        },
        {
            name: "gunningsverslag",
            hidden: true,
            required: false
        },
        {
            name: "gunningsbeslissing",
            hidden: true,
            required: false
        },
        {
            name: "overeenkomst",
            hidden: true,
            required: false
        },
        {
            name: "verlenging_s",
            label: "verlenging_s",
            width: 100,
            hidden: true
        },
        {
            name: "omschrijving",
            hidden: true
        },
        {
            name: "watcher",
            hidden: true
        }
    ]);

    betalingMeta = new Meta([
        {
            name: "jaar",
            label: "Jaar.",
            width: 60,
            required: true
        },
        {
            name: "bedrag",
            label: "Bedrag.",
            width: 120,
            type: "double",
            required: true
        },
        {
            name: "aanvraagid",
            label: "aanvraagid.",
            width: 120,
            type: "int",
            hidden: true
        }
    ]);
    bestandenMeta = new Meta([
        {
            name: "brief_nr",
            label: "Brief nr.",
            width: 80
        },
        {
            name: "betreft",
            label: "Betreft.",
            width: 100
        },
        {
            name: "commentaar",
            label: "Commentaar.",
            width: 120
        },
        {
            name: "aanvraagid",
            type: "int",
            hidden: true
        },
        {
            name: "brief_id",
            type: "int",
            hidden: true,
            required: true
        },
        {
            name: "dms_is",
            hidden: true,
            required: true
        }
    ]);

    window.popupSapProjectDetails = function (id) {
        window.popupWindow('/pad/sapprojectdetails.do?popup=yes&project_id=' + id, 'Projectfiche');
    };
    function SortByYear(a, b) {
        var aName = a.jaar,
            bName = b.jaar;
        return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
    }

    function setPlanningLijnen() {
        ajax.getJson({
            url: '/pad/s/lijsten/planning_lijnen?bestekId=' + bestekId + '&aanvraagId=' + aanvraagId
        }).success(function (data) {
            data.result.unshift({
                value: "",
                label: ""
            });
            vastleggingFm.$planningsitem.select('setOptionList', data.result);
        });

    }

    function setGunningsverslagen() {
        ajax.getJson({
            url: '/pad/s/lijsten/gunningsverslagScans?bestekId=' + bestekId + '&aanvraagId=' + aanvraagId
        }).success(function (data) {
            data.result.unshift({
                value: "",
                label: ""
            });
            vastleggingFm.$gunningsverslag.select('setOptionList', data.result);
        });
    }

    function setGunningsbeslissingen() {
        ajax.getJson({
            url: '/pad/s/lijsten/gunningsbeslissingsScans?bestekId=' + bestekId + '&aanvraagId=' + aanvraagId
        }).success(function (data) {
            data.result.unshift({
                value: "",
                label: ""
            });
            vastleggingFm.$gunningsbeslissing.select('setOptionList', data.result);
        });
    }
    function setOvereenkomsten() {
        ajax.getJson({
            url: '/pad/s/lijsten/overeenkomstScans?bestekId=' + bestekId + '&aanvraagId=' + aanvraagId
        }).success(function (data) {
            data.result.unshift({
                value: "",
                label: ""
            });
            vastleggingFm.$overeenkomst.select('setOptionList', data.result);
        });
    }

    function setAllScans() {
        ajax.getJson({
            url: '/pad/s/lijsten/allScans?bestekId=' + bestekId + '&aanvraagId=' + aanvraagId
        }).success(function (data) {
            data.result.unshift({
                value: "",
                label: ""
            });
            optioneleBestandenFm.$bestand.select('setOptionList', data.result);
        });
    }

    function setOpdrachthouderData() {
        var opdrachthouder_id, opdrachthouders, selectedItem;

        opdrachthouder_id = vastleggingFm.values().opdrachthouder_id;

        if (opdrachthouder_id) {
            opdrachthouders = vastleggingFm.$opdrachthouder_id.select('getOptionList');

            selectedItem = _.find(opdrachthouders, function(item){ return item.value === opdrachthouder_id; });
            vastleggingFm.$straat.val(selectedItem.straat);
            vastleggingFm.$gemeente.val(selectedItem.gemeente);
        } else {
            vastleggingFm.$straat.val(null);
            vastleggingFm.$gemeente.val(null);
        }
    }

    function setOpdrachthouders() {
        ajax.getJson({
            url: '/pad/s/lijsten/opdrachthouders?bestekId=' + bestekId
        }).success(function (data) {
            data.result.unshift({
                value: "",
                label: ""
            });
            vastleggingFm.$opdrachthouder_id.select('setOptionList', data.result);
            setOpdrachthouderData();
        });
    }

    function controleerBedrag() {
        var data, totaalBedrag, result, bedrag;
        totaalBedrag = 0;
        result = true;
        data = vastleggingsBetalingsGrid.getData();
        $.each(data, function (i, item) {
            totaalBedrag = totaalBedrag + item.bedrag;
        });
        $('#bedragTeKlein').hide();
        $("#bedragTeGroot").hide();
        if (vastleggingFm.values().vast_bedrag) {
            bedrag = vastleggingFm.values().vast_bedrag;
            if (totaalBedrag > bedrag + 0.0001) {
                $("#bedragTeGroot").removeAttr("hidden");
                $("#bedragTeGroot").show();
                result = false;
            } else if (totaalBedrag < bedrag - 0.0001) {
                $("#bedragTeKlein").removeAttr("hidden");
                $("#bedragTeKlein").show();
                result = false;
            }
        } else if (totaalBedrag > 0) {
            $("#bedragTeGroot").removeAttr("hidden");
            $("#bedragTeGroot").show();
            result = false;
        }
        return result;
    }

    function setSpreiding(aanvraagId) {
        if (aanvraagId) {
            ajax.getJson({
                url: '/pad/s/lijsten/spreidingen?aanvraagId=' + aanvraagId
            }).success(function (response) {
                vastleggingsBetalingsGrid.setData(response.result);
                if (response.result) {
                    controleerBedrag();
                }
            });
        } else {
            vastleggingsBetalingsGrid.setData([]);
        }
    }

    function setOptioneleBestanden(aanvraagId) {
        if (aanvraagId) {
            ajax.getJson({
                url: '/pad/s/lijsten/optioneleBrieven?aanvraagId=' + aanvraagId
            }).success(function (response) {
                optioneleBestandenGrid.setData(response.result);
            });
        } else {
            optioneleBestandenGrid.setData([]);
        }
    }

    function setBasicScreen() {
        setPlanningLijnen();
        setOpdrachthouders();
        setGunningsbeslissingen();
        setGunningsverslagen();
        setOvereenkomsten();
    }

    function saveVastlegging(url, close) {
        var item = vastleggingFm.values();
        item.spreiding = vastleggingsBetalingsGrid.getData();
        item.optineleBestanden = optioneleBestandenGrid.getData();
        postData(url, item, close);
    }

    function controleerbestandInLijstOptieBestanden(briefid) {
        var data, result;
        result = false;
        data = optioneleBestandenGrid.getData();
        $.each(data, function (i, row) {
            if ('' + row.brief_id === '' + briefid) {
                result = true;
            }
        });
        return result;
    }

    function controleerBestanden() {
        if ((!vastleggingFm.values().gunningsverslag || !vastleggingFm.values().gunningsbeslissing) && !vastleggingFm.values().overeenkomst ){
            alert("U moet een  overeenkomst selecteren of een gunningsbeslissing en een gunningsverslag selecteren");
            return false;
        }
        if (vastleggingFm.values().gunningsverslag && vastleggingFm.values().gunningsbeslissing && vastleggingFm.values().gunningsverslag === vastleggingFm.values().gunningsbeslissing) {
            alert("U mag niet het zelfde bestand selecteren als gunningsbeslissing en gunningsverslag");
            return false;
        }
        if (controleerbestandInLijstOptieBestanden(vastleggingFm.values().overeenkomst)) {
            alert("U overeenkomst mag niet voorkomen bij u optionele brieven");
            return false;
        }
        if (controleerbestandInLijstOptieBestanden(vastleggingFm.values().gunningsverslag)) {
            alert("U gunningsverslag mag niet voorkomen bij u optionele brieven");
            return false;
        }
        if (controleerbestandInLijstOptieBestanden(vastleggingFm.values().gunningsbeslissing)) {
            alert("U gunningsbeslissing mag niet voorkomen bij u optionele brieven");
            return false;
        }
        return true;
    }

    function openVastlegging(item) {
        aanvraagId = item.id || 0;

        $('#detailVastleggingForm')[0].reset();

        setBasicScreen();
        vastleggingFm.populate(item);
        setSpreiding(item.id);
        setOptioneleBestanden(item.id);
        detailVastleggingDialog.dialog("open");

        if ((!item.status) || (item.status.toLowerCase() !== 'Goedgekeurd'.toLowerCase() && item.status.toLowerCase() !== 'In aanvraag'.toLowerCase())) {
            $("#detailVastleggingDialog :button").show();
            $("select").removeAttr("disabled");
            $("select").removeAttr("readonly");
            vastleggingFm.$inspectievanfinancien.removeAttr("disabled");
            vastleggingFm.$inspectievanfinancien.removeAttr("readonly");
            vastleggingFm.$voogdijminister.removeAttr("disabled");
            vastleggingFm.$voogdijminister.removeAttr("readonly");
            vastleggingFm.$vlaamseregering.removeAttr("disabled");
            vastleggingFm.$vlaamseregering.removeAttr("readonly");
            vastleggingFm.$ministervanbegroting.removeAttr("disabled");
            vastleggingFm.$ministervanbegroting.removeAttr("readonly");
            vastleggingFm.$vast_bedrag.removeAttr("disabled");
            vastleggingFm.$vast_bedrag.removeAttr("readonly");

        } else {
            $("#detailVastleggingDialog :button").hide();
            $("select").attr('disabled', 'disabled');
            $("select").attr('readonly', 'readonly');
            vastleggingFm.$inspectievanfinancien.attr('disabled', 'disabled');
            vastleggingFm.$inspectievanfinancien.attr('readonly', 'readonly');
            vastleggingFm.$voogdijminister.attr('disabled', 'disabled');
            vastleggingFm.$voogdijminister.attr('readonly', 'readonly');
            vastleggingFm.$vlaamseregering.attr('disabled', 'disabled');
            vastleggingFm.$vlaamseregering.attr('readonly', 'readonly');
            vastleggingFm.$ministervanbegroting.attr('disabled', 'disabled');
            vastleggingFm.$ministervanbegroting.attr('readonly', 'readonly');
            vastleggingFm.$vast_bedrag.attr('disabled', 'disabled');
            vastleggingFm.$vast_bedrag.attr('readonly', 'readonly');
        }

    }

    function deleteRowFromSpreidingGrid(data, item) {
        $.each(data, function (i, row) {
            if (row && row.jaar === item.jaar) {
                data.splice(i, 1);
            }
        });
        data = data.sort(SortByYear);
        vastleggingsBetalingsGrid.setData(data);
        controleerBedrag();
    }

    function onReady() {
        bestekId = window.bestek_id;
        $('input[name=vast_bedrag]').change(function () {
            controleerBedrag();
        });

        vastleggingFm = $('#detailVastleggingForm').ov_formManager({
            meta: vastleggingMeta
        });

        vastleggingsBetalingsFm = $('#vastleggingsBetalingsForm').ov_formManager({
            meta: betalingMeta
        });
        optioneleBestandenFm = $('#optioneleBestandenForm').ov_formManager({
            meta: betalingMeta
        });
        vastleggingFm.$inspectievanfinancien.datepicker({
            onSelect: function () {
                $(this).valid();
            }
        });
        vastleggingFm.$voogdijminister.datepicker({
            onSelect: function () {
                $(this).valid();
            }
        });
        vastleggingFm.$ministervanbegroting.datepicker({
            onSelect: function () {
                $(this).valid();
            }
        });
        vastleggingFm.$vlaamseregering.datepicker({
            onSelect: function () {
                $(this).valid();
            }
        });
        vastleggingFm.$opdrachthouder_id.change(function () {
            setOpdrachthouderData();
        });

        detailVastleggingDialog = $('#detailVastleggingDialog').dialog({
            autoOpen: false,
            modal: true,
            width: 1050,
            open: function (event) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '5px');
                $(event.target).parent().css('left', '90px');
            }
        });

        vastleggingsBetalingsDialog = $('#vastleggingsBetalingsDialog').dialog({
            autoOpen: false,
            modal: true,
            width: 380,
            open: function (event) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '45px');
                $(event.target).parent().css('left', '105px');
            }
        });
        optioneleBestandenDialog = $('#optioneleBestandenDialog').dialog({
            autoOpen: false,
            modal: true,
            width: 380,
            open: function (event) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '45px');
                $(event.target).parent().css('left', '105px');
            }
        });

        vastleggingsBetalingsGrid = new GridComp({
            el: "#avastleggingsBetalingsGrid",
            meta: betalingMeta,
            newBtn: true,
            deleteBtn: true,
            onNewClicked: function (item, data, ev) {
                ev.preventDefault();
                vastleggingsBetalingsDialog.dialog("open");
                $('#vastleggingsBetalingsForm')[0].reset();
            },
            onDeleteClicked: function (item, data, ev) {
                ev.preventDefault();
                data = vastleggingsBetalingsGrid.getData();
                if (!data) {
                    data = [];
                }
                if (item.aanvraagid) {
                    ajax.postJson({
                        url: '/pad/s/spreiding/delete',
                        content: item
                    }).success(function () {
                        deleteRowFromSpreidingGrid(data, item);
                    });
                } else {
                    deleteRowFromSpreidingGrid(data, item);
                }
            }
        });

        $('#bewaarBestandBtn').click(function () {
            var item, data;
            if (optioneleBestandenFm.validate()) {
                item = optioneleBestandenFm.values();
                if ('' + item.bestand === '' + vastleggingFm.values().gunningsverslag) {
                    alert("U mag niet het zelfde bestand selecteren als gunningsgunningsverslag en optionele brief");
                } else if ('' + item.bestand === '' + vastleggingFm.values().gunningsbeslissing) {
                    alert("U mag niet het zelfde bestand selecteren als gunningsbeslissing en optionele brief");
                } else if (controleerbestandInLijstOptieBestanden(item.bestand)) {
                    alert("Bestand bestaat al in optie lijst");
                } else {
                    data = optioneleBestandenGrid.getData();
                    if (!data) {
                        data = [];
                    }
                    ajax.getJson({
                        url: '/pad/s/vastlegging/brief?briefId=' + item.bestand
                    }).success(function (response) {
                        data.push(response.result);
                        optioneleBestandenGrid.setData(data);
                        optioneleBestandenDialog.dialog("close");
                    });
                }
            }
        });
        $('#annuleerBestandBtn').click(function () {
            optioneleBestandenDialog.dialog("close");
        });

        $('#bewaarBetalingBtn').click(function () {
            var item, data, jaartalBestaat;
            jaartalBestaat = false;

            if (vastleggingsBetalingsFm.validate()) {
                item = vastleggingsBetalingsFm.values();
                data = vastleggingsBetalingsGrid.getData();
                if (!data) {
                    data = [];
                }

                $.each(data, function (i, row) {
                    if ('' + row.jaar === '' + item.jaar) {
                        alert("U heeft al een bedrag opgegeven voor dit jaartal!");
                        jaartalBestaat = true;
                    }
                });
                if (jaartalBestaat === false) {
                    data.push(item);
                    data.sort(SortByYear);
                    vastleggingsBetalingsGrid.setData(data);
                    vastleggingsBetalingsDialog.dialog("close");
                    controleerBedrag();
                }
            }
        });
        $('#annuleerBetalingBtn').click(function () {
            vastleggingsBetalingsDialog.dialog("close");
        });
        optioneleBestandenGrid = new GridComp({
            el: "#optioneleBestandenGrid",
            meta: bestandenMeta,
            newBtn: true,
            deleteBtn: true,
            onNewClicked: function (item, data, ev) {
                ev.preventDefault();
                $('#optioneleBestandenForm')[0].reset();
                setAllScans(0);
                optioneleBestandenDialog.dialog("open");
            },
            onDeleteClicked: function (item, data, ev) {
                ev.preventDefault();
                data = optioneleBestandenGrid.getData();
                if (!data) {
                    data = [];
                }
                if (item.aanvraagid) {
                    ajax.postJson({
                        url: '/pad/s/brief/delete',
                        content: item
                    }).success(_.noop);
                }
                $.each(data, function (i, row) {
                    if (row.brief_id === item.brief_id) {
                        data.splice(i, 1);
                    }
                });
                optioneleBestandenGrid.setData(data);
            }
        });

        vastleggingsAanvragenGrid = new GridComp({
            el: "#vastleggingsAanvragenGrid",
            meta: vastleggingMeta,
            editBtn: true,
            newBtn: true,
            deleteBtn: true,
            onEditClicked: function (item) {
                if (item.id) {
                    openVastlegging(item);
                } else {
                    alert("Geen aanvraagformulier voor deze vastlegging beschikbaar");
                }
            },
            onDeleteClicked: function (item) {
                if (!item.project_id && !item.aanvraagid) {
                    postData('/pad/s/vastlegging/delete', item, true);
                } else {
                    alert("U kan deze vastlegging niet verwijderen");
                }
            },
            onNewClicked: function () {
                ajax.getJson({
                    url: '/pad/s/lijsten/nieuwVastleggingsData?bestekId=' + bestekId
                }).success(function (response) {
                    if (response && response.success) {
                        openVastlegging(response.result);
                    } else {
                        alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                    }
                });
            }
        });
        $('#verzendBtn').click(function (e) {
            var item;
            e.preventDefault();
            item = vastleggingFm.values();
            if (!item.wbsBestek) {
                alert("Omdat u nog geen wbs nummer heeft kan U nog geen aanvraag indienen.");
            } else if (vastleggingFm.validate()) {
                if (controleerBedrag() && controleerBestanden()) {
                    saveVastlegging('/pad/s/vastlegging/verzend', true);
                }
            }
        });
        $('#bewaarBtn').click(function (e) {
            e.preventDefault();
            saveVastlegging('/pad/s/vastlegging/save', false);
        });
        $('#annuleerBtn').click(function (e) {
            detailVastleggingDialog.dialog("close");
        });

        ajax.getJson({
            url: '/pad/s/lijsten/getAllVastleggingen?bestekId=' + bestekId
        }).success(function (response) {
            if (response && response.result) {
                vastleggingsAanvragenGrid.setData(response.result);
            } else {
                alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            }
        });

        postData = function (url, data, close) {
            ajax.postJson({
                url: url,
                content: data
            }).success(function (response) {
                if (response && response.success) {
                    if (close) {
                        detailVastleggingDialog.dialog("close");
                    } else {
                        detailVastleggingDialog.dialog("close");
                        openVastlegging(response.result[response.result.length - 1]);
                    }
                    vastleggingsAanvragenGrid.setData(response.result);
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            });
        };
    }

    return {
        onReady: onReady
    };
});