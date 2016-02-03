/*jslint nomen: false, debug: true, browser: true, nomen: true */
/*global define: false, $: false, _: false, alert: false, console, window */

define([
    "util/UploaderDialog"
], function (UploaderDialog) {
    "use strict";

    window.scanOpladen = function (url) {
        var uploadDialog = new UploaderDialog("#uploadDialog");
        uploadDialog.showWithResult(url, function (response) {
            var href_url;
            if (response && response.result) {
                href_url = window.location.href;
                window.location.href = href_url;
            } else {
                alert("De actie is niet gelukt (server error :" + response.errorMsg + ")");
            }
        });
    };

});
