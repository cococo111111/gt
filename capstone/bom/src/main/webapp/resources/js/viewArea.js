$(function () {
    function reposition() {
        var modal = $(this),
            dialog = modal.find('#loadingModal');
        modal.css('display', 'block');

        // Dividing by two centers the modal exactly, but dividing by three
        // or four works better for larger screens.
        dialog.css("margin-top", Math.max(0, ($(window).height() - dialog.height()) / 2));
    }

    // Reposition when a modal is shown
    $('.modal').on('show.bs.modal', reposition);
    // Reposition when the window is resized
    $(window).on('resize', function () {
        $('.modal:visible').each(reposition);
    });
});

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
                'aTargets': [6]
            }],
        dom: '<"html5buttons"B>lTfgitp',
        buttons: []
    });

});

