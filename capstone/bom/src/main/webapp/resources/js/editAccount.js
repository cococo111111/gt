$(document).ready(function () {
    $('.c-red').hide();
});
function validateForm() {
    $('.c-red').hide();
    var flag = 0;

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
    if (phoneNum.length <= 6 || phoneNum.length > 11) {
        $('.phoneErr').show();
        flag++;
    }

    if (flag > 0) {
        return false;
    } else {
        return true;
    }
}