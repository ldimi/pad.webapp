/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, _G_:true, console */

define([
    "ov/Model",
    "ov/events",
    "ov/mithril/ajax",
    "ov/mithril/dialogBuilder",
    "ov/mithril/gridConfigBuilder",
    "mithril",
    "underscore"
], function (Model, events ,ajax, dialogBuilder, gridConfigBuilder, m, _) {
    'use strict';

    var LoginLijstDialog , LoginModel;

    LoginModel = Model.extend({
        meta: Model.buildMeta([
            { name: "email", label: "Email", width: 280 }
        ])
    });

    LoginLijstDialog = {};

    LoginLijstDialog.controller = function () {

        this.title = "Personen met toegang tot dossiermodule";
        this.width = 300;
        this.height = 300;

        events.on("LoginLijstDialog:open", this.open.bind(this));
    };
    _.extend(LoginLijstDialog.controller.prototype, {
        preOpen: function (organisatie_id) {
            ajax.getJSON({
                url: "/pad/s/dossier/toegangwebloket/organisatie/" + organisatie_id + "/logins"
            }).then(function (response) {
                if (response) {
                    events.trigger("LoginLijstDialog:dataReceived", response);
                }
            });
        }
    });


    LoginLijstDialog.view = function (ctrl) {
        return m("div",
            { style: { position: "absolute", top: "2px", left: "2px", right: "2px", bottom: "28px" },
              class: "slick-grid-divv",
              config: gridConfigBuilder({
                            model: LoginModel,
                            setDataEvent: "LoginLijstDialog:dataReceived"
                        })
            }
        );
    };

    return dialogBuilder(LoginLijstDialog);
});