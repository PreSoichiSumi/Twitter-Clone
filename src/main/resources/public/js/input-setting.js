/**
 * Created by s-sumi on 2016/04/01.
 */
// fileinput.js
//  -> http://plugins.krajee.com/file-input#installation
//$(document).on('ready', function() {

    $("#input-1").fileinput({
        uploadUrl:'/files/icon/upload',
        maxFileSize:5000,
        maxFilesNum:1,
        browseClass: "btn btn-primary btn-block",
        showCaption: false,
        showRemove: false,
        showUpload: true,
        autoReplace: true,
        allowedFileExtensions:['jpg','jpeg','png','gif']
    });
//});