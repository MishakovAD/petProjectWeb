var cityVar = '';
var urlSendUserCity = "getUserCityFromCite";

window.onload = function () {
    cityVar = ymaps.geolocation.city;
    jQuery("#user-city").text(ymaps.geolocation.city);
    jQuery("#user-region").text(ymaps.geolocation.region);
    jQuery("#user-country").text(ymaps.geolocation.country);

    sendUserCity(urlSendUserCity, {
        getUserCityFromCite: cityVar
    });
};

// Отправка запроса на сервер.
function sendUserCity(url, data) {
    return $.ajax({
        url: url,
        data: data,
        datatype : "application/json",
        cache: false,
        success: function (response) {

        }
    });
}