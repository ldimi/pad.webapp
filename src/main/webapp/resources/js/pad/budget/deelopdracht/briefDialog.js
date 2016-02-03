/*jslint nomen: true, debug: true, browser: true */
/*global define: false, console: false, $: false, _:  false, m: false, alert: false, _G_ */

define([
    "budget/deelopdracht/BriefModel",
    "ov/mithril/formhelperFactory",
    "ov/mithril/ajax",
    "ov/events"
], function (BriefModel, fhf,ajax, event) {
    'use strict';
    var _comp;

    _comp = {};

    _comp.controller = function () {

        event.on("briefDialog:open", this.open.bind(this));

        this.brief =  new BriefModel();
        this.showErrors = m.prop(false);
    };
    _.extend(_comp.controller.prototype, {
        open: function (brief) {
            this.brief = brief;
            this.showErrors(false);
            m.redraw();
            this.$detailDialog.dialog('open');
        },
        close: function () {
            this.$detailDialog.dialog('close');
        },

        bewaar: function () {
            var self = this;

            this.showErrors(true);
            if (!this.brief.isValid()) {
                $.notify("Er zijn validatie fouten.");
                return;
            }

            ajax.postJson({
                url: '/pad/s/budget/deelopdracht/updateBrief',
                content: this.brief
            }).then(function (response) {
                if (response && response.success) {
                    self.close();
                    event.trigger("brieven:fetch");
                } else {
                    alert("De actie niet gelukt (server error :" + response.errorMsg + ")");
                }
            });

        },
        _configDialog: function (el, isInitialized) {
            if (!isInitialized) {
                this.$detailDialog = $(el).dialog({
                    title: "Deelopdracht brief",
                    autoOpen: false,
                    modal: true,
                    width: 560
                });
            }
        }
    });

    _comp.view = function(ctrl) {
        var ff;

        ff = fhf.get().setModel(ctrl.brief).setShowErrors(ctrl.showErrors());

        return [
            m(".hidden", {config: _.bind(ctrl._configDialog, ctrl) }, [
                m("form[autocomplete='off'][novalidate='']", [
                    m("table", [
                        m("tbody", [
                            m("tr", [
                                m("td[width='100px']", "Commentaar"),
                                m("td[width='420px']", [
                                    ff.input("commentaar")
                                ])
                            ])
                        ])
                    ])
                ]),
                m("div", {style: { marginBottom: "10px" }}, [
                    m("button", {onclick: _.bind(ctrl.bewaar, ctrl)}, "Bewaar"),
                    m("button", {onclick: _.bind(ctrl.close, ctrl)}, "Annuleer")
                ])

            ]
        )];
    };

    return _comp;
});