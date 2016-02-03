/*jslint nomen: false, debug: true, browser: true, nomen: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console */


define([
    "ov/ajax",
    "ov/Meta",
    "ov/GridComp",
    "util/UploaderDialog",
    "budget/meetstaat/MeetstaatRegelDialog"
], function (ajax, Meta, GridComp, UploaderDialog, MeetstaatRegelDialog) {
    "use strict";
    var meetstaatMeta, MeetstaatMediator;

    meetstaatMeta = new Meta([
        {
            name: "postnr",
            hidden: true,
            required: true
        }, {
            name: "oldPostnr",
            hidden: true
        }, {
            name: "childs",
            hidden: true,
            required: true,
            type: "int"
        },  {
            name: "templateId",
            hidden: true
        }, {
            name: "crudStatus",
            hidden: true
        }, {
            name: "regelTotaal",
            hidden: true,
            type: "double"
        }, {
            name: "totaal",
            hidden: true
        }, {
            name: "cleanPostnr",
            label: "Postnr",
            width: 55
        }, {
            name: "taak",
            hidden: true
        }, {
            name: "gridTaak",
            label: "Taak",
            width: 160
        }, {
            name: "details",
            hidden: true
        }, {
            name: "gridDetails",
            label: "Details",
            width: 200
        }, {
            name: "type",
            label: "Type",
            width: 50
        }, {
            name: "eenheid",
            label: "Eenheid",
            width: 90
        }, {
            name: "aantal",
            label: "Aantal",
            width: 60,
            type: "double"
        }, {
            name: "eenheidsprijs",
            label: "Eenheidsprijs",
            width: 80,
            type: "double"
        }, {
            name: "detailTotaal",
            label: "Detail totaal",
            width: 100,
            type: "double"
        }, {
            name: "subTotaal",
            label: "Subtotaal",
            width: 100,
            type: "double"
        }, {
            name: "postTotaal",
            label: "Totaal per post",
            width: 100,
            type: "double"
        }
    ]);
    var uploudUrl;

    MeetstaatMediator = function (bestekOrTemplateId, uploudUrlMeetstaat) {
        var self = this;
        self._bestekOrMeetstaatId = bestekOrTemplateId;
        self.uploudUrl = uploudUrlMeetstaat;
        window.onbeforeunload = function (e) {
            if($("#waarschuwingNietOpgeslagenMeetstaat").is(':hidden')){
                return;
            }
            var message = "De meetstaat is niet opgeslagen, bent U zeker dat u deze pagina wil verlaten?",
                e = e || window.event;
            // For IE and Firefox
            if (e) {
                e.returnValue = message;
            }

            // For Safari
            return message;
        };
        self.meetstatenGrid = new GridComp({
            el: "#meetstatenGrid",
            meta: meetstaatMeta,
            editBtn: !window.isLocked,
            newBtn: !window.isLocked,
            deleteBtn: !window.isLocked,
            onEditClicked: function (item) {
                self._openItem(item);
            },
            onDeleteClicked: function (item) {
                var data;
                if (item.totaal || item.childs > 0) {
                    alert("Deze regel kan niet verwijderd worden!");
                } else {
                    data = self.meetstatenGrid.getData() || [];
                    data = _.reject(data, function (row) {
                        return (row && row.postnr === item.postnr);
                    });
                    self._herbereken(data);
                    $("#waarschuwingNietOpgeslagenMeetstaat").show();
                }
            },
            onNewClicked: function () {
                self._openItem();
            }
        });


        $('#saveMeetstaatBtn').click(function () {
            var data = self.meetstatenGrid.getData();
            ajax.postJson({
                url: self.getSaveUrl(),
                content: data
            }).success(function (response) {
                self._vulMeetstaat(response);
                alert("Meetstaat succesvol opgeslagen");
                $('#waarschuwingNietOpgeslagenMeetstaat').hide();
            });
        });

        self._initUploadCSVBtn();
        self._initLaadTemplateBtn();
        self._initLaadAnderBestekBtn();

        self.meetstaatRegelDialog = MeetstaatRegelDialog.init(meetstaatMeta, $.proxy(self.bewaarItem, this));

        self._getMeetstaat(self._bestekOrMeetstaatId);

    };

    _.extend(MeetstaatMediator.prototype, {

        createNewItem: function () {
            return {
                bestekId: this._bestekOrMeetstaatId,
                crudStatus: 'C'
            };
        },

        getMeetstaatUrl: function () {
            return '/pad/s/meetstaat/getMeetstaat?bestekId=' + this._bestekOrMeetstaatId;
        },

        getHerberekenUrl: function () {
            return "/pad/s/bestek/" + this._bestekOrMeetstaatId + "/meetstaat/herbereken";
        },

        getVerplaatsUrl: function (huidig, nieuw) {
            return "/pad/s/meetstaat/verplaats/" + huidig + "/" + nieuw + "/";
        },

        getAddUrl: function() {
            return "/pad/s/bestek/" + this._bestekOrMeetstaatId + "/meetstaat/add";
        },

        getSaveUrl: function() {
            return "/pad/s/bestek/" + this._bestekOrMeetstaatId + "/meetstaat/save";
        },


        _vulMeetstaat: function (response) {
            if (response && response.result) {
                this.meetstatenGrid.setData(response.result);
            } else {
                alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
            }
        },

        _getMeetstaat: function () {
            var self= this;
            if (self._bestekOrMeetstaatId) {
                ajax.getJson({
                    url: self.getMeetstaatUrl()
                }).success(function (response) {
                    self._vulMeetstaat(response);
                    $('#waarschuwingNietOpgeslagenMeetstaat').hide();
                });
            }
        },

        _initUploadCSVBtn: function () {
            var self = this;
            $('#uploadCSVBtn').click(function () {
                var uploadDialog = new UploaderDialog("#uploadDialog");
                uploadDialog.showWithResult(self.uploudUrl, function (response) {
                    if (response && response.result) {
                        self.meetstatenGrid.setData(response.result.meetstaatRegels);
                        if(response.result.errors  && response.result.errors.length>0){
                            window.alert(response.result.errors);
                        }
                        $("#waarschuwingNietOpgeslagenMeetstaat").show();
                    } else {
                        alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
                    }
                });
            });
        },

        _initLaadTemplateBtn: function () {
            var self= this;
            $('#laadTemplateBtn').click(function () {
                var templateId, selectTemplate;
                selectTemplate = document.getElementById("template");
                templateId = selectTemplate.options[selectTemplate.selectedIndex].value;
                ajax.postJson({
                    url: "/pad/s/meetstaat/getTemplate?templateId=" + templateId + "&bestekId=" + self._bestekOrMeetstaatId
                }).success(function (response) {
                    self._vulMeetstaat(response);
                    $("#waarschuwingNietOpgeslagenMeetstaat").show();
                });
            });
        },
        _initLaadAnderBestekBtn: function () {
            var self= this;
            $('#laadAnderBestekBtn').click(function () {
                var bestekNr, inputTextBestekNr;
                inputTextBestekNr = document.getElementById("anderbestek");
                bestekNr = inputTextBestekNr.value;
                ajax.postJson({
                    url: "/pad/s//meetstaat/getFromOtherBestek?bestekNr=" + bestekNr + "&bestekId=" + self._bestekOrMeetstaatId
                }).success(function (response) {
                    if(response.result==null){
                        alert("Geen meetstaat gevonden!");
                    }else{
                        self._vulMeetstaat(response);
                        $("#waarschuwingNietOpgeslagenMeetstaat").show();
                    }
                });
            });
        },

        _openItem: function (item) {
            if (window.isLocked) {
                alert("U kan geen wijzigingen maken in deze definitieve meetstaat!");
                return;
            }
            if (item) {
                if (item.totaal) {
                    alert("Deze regel kan niet worden aangepast!");
                    return;
                }
                item.oldPostnr = item.postnr;
                item.crudStatus = '';
            } else {
                // nieuw item
                item = this.createNewItem();
            }
            this.meetstaatRegelDialog.open(item);
        },

        _herbereken: function (data) {
            var self = this;
            ajax.postJson({
                url: self.getHerberekenUrl(),
                content: data
            }).success(function (response) {
                self._vulMeetstaat(response);
            });
        },

        _verplaats: function (data, huidig, nieuw) {
            var self = this;
            ajax.postJson({
                url: self.getVerplaatsUrl(huidig, nieuw),
                content: data
            }).success(function (response) {
                self._vulMeetstaat(response);
                data = self.meetstatenGrid.getData() || [];
                $.each(data, function (i, row) {
                    if (row && row.crudStatus === "V") {
                        self._openItem(row);
                    }
                });
            });
        },

        _add : function(item) {
            var data, self = this;

            data = {
                meetstaatRegels: self.meetstatenGrid.getData(),
                newMeetstaatRegel: item
            };
            ajax.postJson({
                url: self.getAddUrl(),
                content: data
            }).success(function (response) {
                self._vulMeetstaat(response);
            });
            self._openItem();
        },

        _setItemData: function(data, item) {
            $.each(data, function (i, row) {
                if (row && row.postnr === item.postnr) {
                    data[i] = item;
                }
            });
        },

        bewaarItem: function (item, naarVolgende) {
            var data, nieuw, huidig, gevonden = false, self = this;

            if (item.crudStatus === 'C') {
                self._add(item);
            } else {
                data = self.meetstatenGrid.getData() || [];
                if (item.oldPostnr ===  item.postnr) {
                    self._setItemData(data, item);
                    self._herbereken(data);
                } else {
                    nieuw = item.postnr;
                    huidig = item.oldPostnr;
                    item.postnr = item.oldPostnr;
                    self._setItemData(data, item);
                    self._verplaats(data, huidig, nieuw);
                }
                if (naarVolgende) {
                    $.each(data, function (i, row) {
                        if (row && !(row.totaal)) {
                            if (gevonden) {
                                self._openItem(row);
                                return false;
                            }
                            if (row.postnr === item.postnr) {
                                gevonden = true;
                            }
                        }
                    });
                }
            }
            $("#waarschuwingNietOpgeslagenMeetstaat").show();
        }
    });

    return MeetstaatMediator;
});