$(document).ready(function () {
    if($("#errorMessage").val()!=""){
        $('#errorModal').modal('show');
        $("#errorMessage").val("");
    }

    $('.dataTables-example').DataTable({
        "aoColumnDefs": [
            {
                'bSortable': false,
                'aTargets': [2,3]
            }],
        dom: '<"html5buttons"B>lTfgitp',
        buttons: []
    });
    var deleteid;
    $("button").click(function () {
        if ($(this).val() != 0) {
            deleteid = $(this).val();
            // show Modal
            $('#myModal').modal('show');
        }
    });
    $("#delete-submit").click(function () {
        $(".form-delete-"+deleteid).submit();
    });
});

