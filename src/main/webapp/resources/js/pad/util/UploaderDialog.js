/*jslint debug: true, browser: true */
/*global define: false, require: false, $: false, console: false, alert: false , window*/

define([
    "plupload"
], function () {
    "use strict";
    var UploadDialog;

    UploadDialog = function (el) {
        $(el).dialog({
            autoOpen: false,
            modal: true,
            width: 560,
            open: function (event) {
                $(event.target).parent().css('position', 'fixed');
                $(event.target).parent().css('top', '45px');
                $(event.target).parent().css('left', '105px');
            }
        });
    };

    UploadDialog.prototype.show = function (url, afterUpload) {
        var uploader = $("#uploader").plupload({
            // General settings

            runtimes : 'html5',
            url : url,
            max_file_size : '100mb',
            chunk_size : '100mb',
            unique_names : true,

            // Resize images on clientside if we can
            resize : {width : 320, height : 240, quality : 90},

            // Specify what files to browse for
            filters : [
                {title : "Image files", extensions : "jpg,gif,png,bat"},
                {title : "Zip files", extensions : "zip"},
                {title : "Doc files", extensions : "xdoc,doc,xls,xml,odt,ods,csv,txt,docx, docm, dotx, dotm"},
                {title : "XML files", extensions : "xlsx, xlsm, xltx, xltm"},
                {title : "PDF files", extensions : "pdf"}
            ],

            preinit : {
                UploadComplete: function (up, files) {
                    afterUpload();
                    uploader.plupload('getUploader').splice();
                    $('.plupload_filelist_content', uploader).empty();
                },
                Error: function (up, args) {
                    alert("Dit bestand kon niet worden opgeladen! ");
                    $('.plupload_filelist_content', uploader).empty();
                }
            }

        });
        $('#uploadDialog').dialog("open");

    };

    UploadDialog.prototype.showWithResult = function (url, afterUpload) {
        var uploader = $("#uploader").plupload({
            // General settings

            runtimes : 'html5',
            url : url,
            max_file_size : '100mb',
            chunk_size : '100mb',
            unique_names : true,
            max_file_count: 1,

            // Resize images on clientside if we can
            resize : {width : 320, height : 240, quality : 90},

            // Specify what files to browse for
            filters : [
                {title : "CSV", extensions : "csv"},
                {title : "PDF", extensions : "pdf"}
            ],

            preinit : {
                FileUploaded: function (up, files, object) {
                    console.log("Opgeladen .......................");
                    var myData;
                    try {
                        myData = eval(object.response);
                    } catch (err) {
                        myData = eval('(' + object.response + ')');
                    }
                    afterUpload(myData);
                    uploader.plupload('getUploader').splice();
                    $('.plupload_filelist_content', uploader).empty();
                },
                Error: function (up, args) {
                    alert("Dit bestand kon niet worden opgeladen! ");
                    $('.plupload_filelist_content', uploader).empty();
                }
            }

        });
        $('#uploadDialog').dialog("open");

    };

    return UploadDialog;
});