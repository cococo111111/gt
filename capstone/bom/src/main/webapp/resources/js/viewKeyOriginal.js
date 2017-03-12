$(document).ready(function () {
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
    $('.dataTables-example').DataTable({
        "aoColumnDefs": [
            {
                'bSortable': false,
                'aTargets': [0,3,4,5,6]
            }],
        dom: '<"html5buttons"B>lTfgitp',
        buttons: []
    });
});

