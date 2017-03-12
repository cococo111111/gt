/**
 * Created by manlm on 9/10/2016.
 */
$(document).ready(function () {
    updateWidth();
});

$(window).resize(function () {
    updateWidth();
});

function updateWidth() {
    var width = $("#nav-menu").width();
    console.log(width)
    if (width == 220) {
        $("#nav-btn").css({"padding-left": "240px"})
    } else if ($(window).width() < 769 && width != 70) {
        $("#nav-btn").css({"padding-left": "20px"})
    } else {
        $("#nav-btn").css({"padding-left": "90px"})
    }
}

function updateWidthOnClick() {
    var width = $("#nav-menu").width();
    console.log(width)
    if ($(window).width() < 769) {
        if (width == 220) {
            $("#nav-btn").css({"padding-left": "240px"})
        } else if (width != 70) {
            $("#nav-btn").css({"padding-left": "20px"})
        } else {
            $("#nav-btn").css({"padding-left": "90px"})
        }
    } else {
        if (width == 220) {
            $("#nav-btn").css({"padding-left": "90px"})
        } else if (width != 70) {
            $("#nav-btn").css({"padding-left": "90px"})
        } else {
            $("#nav-btn").css({"padding-left": "240px"})
        }
    }

}