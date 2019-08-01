var urlForSendingQuery = 'timesheet_all_query';
var sendUrl = 'timesheet_parts_query';
var stage = 1;
var dataQuery = '';

$(document).ready(function () {
    getIP();
    getGeolocation();
    getCity();
});

function getIP() {
    $(function () {
        $.getJSON("https://api.ipify.org?format=jsonp&callback=?",
            function (json) {
                dataQuery = dataQuery + 'user_ip:' + json.ip + '}';
            }
        );
    });
}

function getGeolocation() {
    navigator.geolocation.getCurrentPosition(
        function (position) {
            console.log("Доступ разрешён.");
            var latitude = position.coords.latitude;
            var longitude = position.coords.longitude;
            var actual = position.coords.accuracy; //пока не будем использовать
            var data = 'lat : ' + latitude + ', lng : ' + longitude + '}';
            dataQuery = dataQuery + data;
        },
        function (error) {
            // если ошибка (можно проверить код ошибки)
            if (error.PERMISSION_DENIED) {
                console.log("В доступе отказано!");
                var latitude = 0;
                var longitude = 0;
                var data = 'lat : "' + latitude + '", lng : ' + longitude + '}';
                dataQuery = dataQuery + data;
            }
        });
}

function getCity() {
    window.onload = function () {
        dataQuery = dataQuery + 'user_city:' + ymaps.geolocation.city + '}';
    };
}

$('input[name="timesheetquery_button"]').click(function () {
    createQueryBySteps(stage, $('input[name="timesheetquery"]').val());
    // а) увеличиваем номер шага.
    stage++;

    // б) меняем подписи у кнопочек.
    if (stage == 2) {
        var fieldName = document.getElementById("field_name");
        fieldName.innerHTML = "Желаемое время фильма: ";

        var inputQuery = document.getElementById("timesheetquery");
        inputQuery.id = "timesheet_time";

        sendUrl = "timesheetquery_time";

    } else if (stage == 3) {
        var fieldName = document.getElementById("field_name");
        fieldName.innerHTML = "Желаемое тип сеанса: ";

        var inputQuery = document.getElementById("timesheet_time");
        inputQuery.id = "timesheet_type";

        sendUrl = "timesheetquery_type";

    } else if (stage == 4) {
        var fieldName = document.getElementById("field_name");
        fieldName.innerHTML = "Ближайшее место: ";

        var inputQuery = document.getElementById("timesheet_type");
        inputQuery.id = "timesheet_place";

        sendUrl = "timesheetquery_place";
    }
});

function createQueryBySteps(stage, query) {
    var name;
    if (stage == 1) {
        name = "movie"
    }
    if (stage == 2) {
        name = "time"
    }
    if (stage == 3) {
        name = "type"
    }
    if (stage == 4) {
        name = "place"
    }
    dataQuery = dataQuery + name + ':' + query + '}';
    if (stage == 4) {
        console.log(dataQuery);
        sendAllQuery(urlForSendingQuery, {
            allQuery: dataQuery
        });
    }
}

function sendAllQuery(url, data) {
    return $.ajax({
        url: url,
        data: data,
        datatype: "application/json",
        // По умолчанию идет GET.
        // method: 'POST',
        // Отменим кеширование.
        cache: false,
        success: function (response) {
            if (stage == 4) {
                console.log("Succsess method")
                getResponse(response);
            }
        }
    });
}