$(document).ready(function () {
    $("#submit_update").click(function () {
        var startDate = new Date($('#startDate').val());
        var endDate = new Date($('#endDate').val());
        if (startDate.getTime() > endDate.getTime()) {
            document.getElementById("error-date").innerHTML = "end date is greater than start date";
            event.preventDefault();
        }else{
            document.getElementById("error-date").innerHTML="";
        }
    });
});