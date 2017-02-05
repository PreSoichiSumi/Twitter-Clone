/**
 * Created by s-sumi on 2016/04/01.
 */
var endpoint='/';
$(function() {
    $("#submit").on('click', function(evt) {

        if(!window.FormData)
            alert("InternetExproler9以前には対応していません");

        if(!$("#input-20")[0].files[0]) //typeof a=="undefined"でもよい
            alert("ファイルを選択してください");

        var form = $("#settingForm").get()[0];
        var formData = new FormData(form);
        
        $.ajax({
            url: endpoint,
            method: 'post',
            dataType: 'json',
            data: formData,
            processData: false,
            contentType: false
        }).done(function (data) {
            console.log('SUCCESS', data);
            editor.setValue(data.aa);
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR', jqXHR, textStatus, errorThrown);
        });


        return false;
    });
});