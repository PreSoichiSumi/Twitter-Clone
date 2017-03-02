//initially disable the button
$("#tbutton").prop("disabled", true);

//When the textarea value is changed
$("textarea").on("input", function () {
    if ($(this).val().length > 0) {
        $("#tbutton").prop("disabled", false);
    } else {
        $("#tbutton").prop("disabled", true);
    }
});

