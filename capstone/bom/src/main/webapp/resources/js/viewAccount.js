$(document).ready(function () {
    var username;
    $(".btn-activate").click(function () {
        if ($(this).val() != 0) {
            username = $(this).val();
            // show Modal
            $('#activateModal').modal('show');
        }
    });

    $(".btn-deactivate").click(function () {
        if ($(this).val() != 0) {
            username = $(this).val();
            // show Modal
            $('#deactivateModal').modal('show');
        }
    });

    $("#deactivate-submit").click(function () {
        $(".form-deactivate-"+username).submit();
    });
    $("#activate-submit").click(function () {
        $(".form-activate-"+username).submit();
    });

});