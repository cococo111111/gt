/**
 * Created by Administrator on 10/11/2016.
 */
$(document).ready(function () {
    $("#btn-delete-chatlog").click(function () {
        if( $('input[name="chatLogId"]:checked').length!=0) {
            $('#myModal').modal('show');
        }
    });
    $("#delete-submit").click(function () {
            $(".form-delete").submit();
    });
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
    $('.dataTables-example').DataTable({
        "aoColumnDefs": [
            {
                'bSortable': false,
                'aTargets': [0,2]
            }],
        dom: '<"html5buttons"B>lTfgitp',
        buttons: []
    });
});
