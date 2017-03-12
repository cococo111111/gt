$(document).ready(function () {
    var config = {
        '.chosen-select': {},
        '.chosen-select-deselect': {allow_single_deselect: true},
        '.chosen-select-no-single': {disable_search_threshold: 10},
        '.chosen-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chosen-select-width': {width: "100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

    $("#submit_add").click(function () {
        if ($("#fileImage").val() == "") {
            document.getElementById("showImageExist").innerHTML = "Please choose image";
            event.preventDefault();
        }
    });
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
        document.getElementById("showImageExist").innerHTML = "";
        readURL(this);

    });
});

