$(document).ready(function () {
    var id;
    $(".btn-activate").click(function () {
        if ($(this).val() != 0) {
            id = $(this).val();
            // show Modal
            $('#activateModal').modal('show');
        }
    });

    $(".btn-deactivate").click(function () {
        if ($(this).val() != 0) {
            id = $(this).val();
            // show Modal
            $('#deactivateModal').modal('show');
        }
    });

    $("#deactivate-submit").click(function () {
        $(".form-deactivate-"+id).submit();
    });
    $("#activate-submit").click(function () {
        $(".form-activate-"+id).submit();
    });
    $('.dataTables-example').DataTable({
        "aoColumnDefs": [
            {
                'bSortable': false,
                'aTargets': [4, 5, 6,7]
            }],
        dom: '<"html5buttons"B>lTfgitp',
        buttons: []
    });

});