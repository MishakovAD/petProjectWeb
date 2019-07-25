var urlSendingPosition = "getCurrentPosition"

navigator.geolocation.getCurrentPosition(function(position) {
    // Текущие координаты.
    var latitude = position.coords.latitude;
    var longitude = position.coords.longitude;
    var actual = position.coords.accuracy;
    var data = '{ "lat" : "' + latitude + '", "lng" : "' + longitude + '", "actual" : "' + actual + '" }';

    sendPositionToServer(urlSendingPosition, {
        getCurrentPosition: data
    });
    //console.log(data);

});

// Отправка запроса на сервер.
function sendPositionToServer(url, data) {
    return $.ajax({
        url: url,
        data: data,
        datatype : "application/json",
        // По умолчанию идет GET.
        // method: 'POST',
        // Отменим кеширование.
        cache: false,
        success: function (response) {

        }
    });
}