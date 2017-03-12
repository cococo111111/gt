/**
 * Created by Administrator on 11/11/2016.
 */
$(document).ready(function () {
    $("#submit_update").click(function() {
        var beacon1=$("#beacon1").val();
        var beacon2=$("#beacon2").val();
        var beacon3=$("#beacon3").val();
        var beacon4=$("#beacon4").val();
        if(beacon1==beacon2||beacon1==beacon3||beacon1==beacon4
        ||beacon2==beacon3||beacon2==beacon4
        ||beacon3==beacon4){
            document.getElementById("beacon-error").innerHTML = "Beacon minor 1, Beacon minor 2, Beacon minor 3, Beacon minor 4 must be different!";
            event.preventDefault();
        }
    });
});
