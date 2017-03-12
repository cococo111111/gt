/**
 * Created by Administrator on 27/10/2016.
 */
$(document).ready(function () {

    $("#cancel").click(function () {

        $("#imgUrl-product").attr("src", srcImg);
    });
    $("#submit_update").click(function () {
        if ($("#imgUrl-product").attr("src") == "") {
            document.getElementById("showImageExist").innerHTML = "Please choose image";
            event.preventDefault();
        }
    });

    var check = $("#fileImage");
    checkImageExist(check);
    function checkImageExist(inputImage) {
        if (inputImage.val() != '') {
            readURL(inputImage);
        }
    }

    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#imgUrl-product').attr('src', e.target.result);
            }
            document.getElementById("showImageExist").innerHTML = "";
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#fileImage").change(function () {
        readURL(this);
    });
    $("#codeproduct").change(function () {
        document.getElementById("code-error").innerHTML = "";
    });
});