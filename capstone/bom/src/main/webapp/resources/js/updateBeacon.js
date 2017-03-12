$(document).ready(function () {
    $('.c-red').hide();
    $("#macAddress").val(macAddress)
    $("#major").val(major)
    $("#minor").val(minor)
    $("#x").val(x)
    $("#y").val(y)
    $("#z").val(z)
    if (addResult == 'false') {
        notify("Beacon Already Existed!", "warning");
    }

});
function notify(message, type){
    $.growl({
        message: message
    },{
        type: type,
        allow_dismiss: false,
        label: 'Cancel',
        className: 'btn-xs btn-inverse',
        placement: {
            from: 'top',
            align: 'right'
        },
        delay: 2500,
        animate: {
            enter: 'animated fadeInRight',
            exit: 'animated fadeOutRight'
        },
        offset: {
            x: 30,
            y: 30
        }
    });
};