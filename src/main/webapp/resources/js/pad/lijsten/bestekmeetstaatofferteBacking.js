/*jslint nomen: true, debug: true, browser: true */
/*global define: false, Slick: false, $: false, _: false, alert: false, console */


define(["util/UploaderDialog"], function (UploaderDialog) {
    "use strict";
    var bestek_id, offerte_id;

    function changeByType() {
        if ($('#nieuweOfferteRegelExtraRegelType').val() === 'VH') {
            $('#nieuweOfferteRegelextraRegelEenheid').removeAttr("hidden");
            $('#nieuweOfferteRegelofferteAantal').removeAttr("hidden");
            $('#nieuweOfferteRegelofferteEenheidsprijs').removeAttr("hidden");
            $('#nieuweOfferteRegelRegelTotaal').attr("hidden", "hidden");
        } else {
            $('#nieuweOfferteRegelextraRegelEenheid').attr("hidden", "hidden");
            $('#nieuweOfferteRegelofferteAantal').attr("hidden", "hidden");
            $('#nieuweOfferteRegelofferteEenheidsprijs').attr("hidden", "hidden");
            $('#nieuweOfferteRegelRegelTotaal').removeAttr("hidden");
        }
    }

    function setUploud() {
        $('#uploadBtn').click(function () {
            var uploadDialog = new UploaderDialog("#uploadDialog");
            uploadDialog.showWithResult('/pad/s/bestek/'+bestek_id+'/meetstaat/offertes/'+ offerte_id + '/upload/', function (response) {
                if(response.result.length>0){
                    var massage = "";
                    $.each(response.result, function(index, value) {
                        massage = massage + value + "\n";
                    });
                    alert(massage);
                }
                window.location = window.location;
            });
        });
    }
    function setAfwijzen() {
        $('#buttonAfsluiten').click(function () {
            window.location = "http://" + window.location.host + "/pad/s/bestek/"+bestek_id+"/meetstaat/offertes/"+offerte_id+"/afwijzen/";
        });
    }
    function setToewijzen() {
        $('#buttonToewijzen').click(function () {
            window.location = "http://" + window.location.host + "/pad/s/bestek/"+bestek_id+"/meetstaat/offertes/"+offerte_id+"/toekennen/";
        });
    }
    function setRegelToevoegen() {
        changeByType();
        $('#nieuweOfferteRegelExtraRegelType').change(function(){
            changeByType();
        });

    }

    function onReady() {
        offerte_id = window.offerte_id;
        bestek_id = window.bestek_id;
        setUploud();
        setToewijzen();
        setAfwijzen();
        setRegelToevoegen();
    }

    return {
        onReady: onReady
    };
});