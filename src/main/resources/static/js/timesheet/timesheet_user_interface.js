var user_city = '';
var available_movie = [];

$(document).ready(function () {
    paint();
});

function paint() {
    window.onload = function () {
        user_city = ymaps.geolocation.city;
        sendAjax("get_available_movie", user_city);
        //sendAjax("get_cinema_in_city", user_city);
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
        console.log("response = " + response);
        var moviesString = response["movies"];
        available_movie = moviesString.split(",");
    } else if (url == "get_cinema_in_city") {

    }

}

var a;
jQuery(function(){
    a = $('#query').autocomplete({source: available_movie});	/*назначаем автозаполнение объекту с id="query"*/
});