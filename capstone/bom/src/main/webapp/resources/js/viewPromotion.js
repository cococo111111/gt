$(document).ready(function () {
    $('.dataTables-example').DataTable({
        "aoColumnDefs": [
            {
                'bSortable': false,
                'aTargets': [6,7,8]
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

