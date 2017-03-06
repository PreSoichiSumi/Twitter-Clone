/**
 * Created by s-sumi on 2016/04/01.
 */
// fileinput.js
//  -> http://plugins.krajee.com/file-input#installation
$(document).on('ready', function() {

    $("#input-1").fileinput({
        browseClass: "btn btn-primary btn-block",
        showCaption: false,
        showRemove: false,
        showUpload: false,
        autoReplace: true,
        allowedFileTypes:["image"]
    });
});