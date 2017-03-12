/**
 * Created by manlm on 9/10/2016.
 */

$(document).ready(function () {
    if($("#errorMessage").val()!=""){
        $('#errorModal').modal('show');
        $("#errorMessage").val("");
    }

   /* if (deleteResult == 'false') {
        notify("This beacon is mapping with a area, Please edit area first !", "warning");
    }*/
    // $('#btn-submit').on('click',function(e){
    //     e.preventDefault();
    //     var form = $(this).parents('form');
    //     swal({
    //         title: "Are you sure?",
    //         text: "You will not be able to recover this imaginary file!",
    //         type: "warning",
    //         showCancelButton: true,
    //         confirmButtonColor: "#DD6B55",
    //         confirmButtonText: "Yes, delete it!",
    //         closeOnConfirm: false
    //     }, function(isConfirm){
    //         if (isConfirm) form.submit();
    //     });
    // })
    var minor;
    $("button").click(function () {
        if ($(this).val() != 0) {
            minor = $(this).val();
            // show Modal
            $('#myModal').modal('show');
        }
    });
    $("#delete-submit").click(function () {
        $(".form-delete-" + minor).submit();
    });
});

function notify(message, type) {
    $.growl({
        message: message
    }, {
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


