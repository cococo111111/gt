/**
 * Created by Administrator on 19/11/2016.
 */
$(document).ready(function () {
    $("#btn_add").click(function () {
        if($("#select_item").val()==null){
            event.preventDefault();
        }
    });
});
