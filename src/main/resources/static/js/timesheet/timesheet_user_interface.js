var user_city = '';
var available_movie = [];

$(document).ready(function () {
    paint();
});

function paint() {
    window.onload = function () {
        user_city = ymaps.geolocation.city;
        sendAjax("get_available_movie", user_city);
        sendAjax("get_cinema_in_city", user_city);
        chooseType();
        filltime();
        createPriceRange();
        //chooseTime();
    };
}

function sendAjax(sendUrl, requestData) {
    $.ajax({
        url: sendUrl,
        data: { 'user_city' : requestData },
        datatype: "application/json",
        cache: false,
        success: function (response) {
            createDynamicInterface(sendUrl, response);
        }
    });
}

function createDynamicInterface(url, response) {
    if (url == "get_available_movie") {
        var moviesString = response["movies"];
        available_movie = moviesString.split("}");
        autocompleteSuggestMovie();
    } else if (url == "get_cinema_in_city") {

    }

}

function chooseType() {
    $(".dropdown-menu a").click(function(){
        $(this).parents(".dropdown").find('.btn').html($(this).text() + ' <span class="caret"></span>');
        $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
    });
}

function filltime() {
    var date = new Date();
    var dateArr = date.toLocaleDateString().split(".");
    var day = dateArr[0];
    var month = dateArr[1];
    var year = dateArr[2];
    if (day.length == 1) {
        day = '0' + day;
    }
    if (month.length == 1) {
        month = '0' + month;
    }
    var hour = new Date().getHours().toString();
    var minutes = new Date().getMinutes().toString();
    if (hour.length == 1) {
        hour = '0' + hour;
    }
    if (minutes.length == 1) {
        minutes = '0' + minutes;
    }
    $('#movieTime').val(year + '-' + month + '-' + day + 'T' + hour + ':' + minutes);
}

function createPriceRange() {
    var x = document.getElementById("moviePriceRange").value;
    document.getElementById("moviePrice").value = x;
    $('#moviePriceRange').on("input", function () {
        $('#moviePrice').val(this.value)
    });

    $('#moviePrice').on("input", function () {
        $('#moviePriceRange').val(this.value)
    });
}

function chooseTime() {
    $(function () {
        $('#movieTime').datetimepicker({
            inline: true,
            sideBySide: true
        });
    });
}
