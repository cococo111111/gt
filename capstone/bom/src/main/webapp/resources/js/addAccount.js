/**
 * Created by PhucNT on 9/22/2016.
 */
$(document).ready(function () {
    $('.c-red').hide();

    if (addResult == 'false') {
        $("#username").val(attUsername)
        $("#firstName").val(attFirstName)
        $("#lastName").val(attLastName)
        $("#phone").val(attPhone)
        //$("#chkAdmin").val() = attUsername;
        notify("Username Already Existed!", "warning");
    }

});

function validateForm() {
    $('.c-red').hide();
    var flag = 0;
    // Validate username
    var username = $("#username").val();
    if (username.length >= 9 || username == null || username.length < 3) {
        $('.usernameErr').show();
        flag++;
    }

    // Validate firstName
    var firstName = $("#firstName").val();
    if (firstName == "") {
        $('.firstNameErr').show();
        flag++;
    }

    // Validate lastName
    var lastName = $("#lastName").val();
    if (lastName == "") {
        $('.lastNameErr').show();
        flag++;
    }

    // Validate phone
    var phone = $("#phone").val();
    var phoneNum = phone.replace(/[^\d]/g, '');
    if (phoneNum.length <= 7 || phoneNum.length > 11) {
        $('.phoneErr').show();
        flag++;
    }

    if (flag > 0) {
        return false;
    } else {
        return true;
    }
}

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