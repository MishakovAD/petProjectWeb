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
    var day = date.getDay();
    var month = date.getMonth();
    var year = date.getFullYear();
    var hour = new Date().getHours();
    var minutes = new Date().getMinutes();
    $('#movieTime').val(day + '-' + month + '-' + year + 'T' + hour + ':' + minutes);
}

function chooseTime() {
    $(function () {
        $('#movieTime').datetimepicker({
            inline: true,
            sideBySide: true
        });
    });
}