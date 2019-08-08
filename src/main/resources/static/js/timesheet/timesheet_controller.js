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
                var data = 'lat : ' + latitude + ', lng : ' + longitude + '}';
                dataQuery = dataQuery + data;
            }
        });
}

function getCity() {
    window.onload = function () {
        dataQuery = dataQuery + 'user_city:' + ymaps.geolocation.city + '}';
    };
}

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
            console.log("Succsess method")
            getResponse(response);
        }
    });
}

function getResponse(response) {
    try {
        console.log("Length of Array: " + response.length);
        console.log(response);
        var JSONresponse = JSON.parse(response);
        console.log("@@@@@@JSON@@@@@@@@@ " + JSONresponse);
        var count = 0;
        for (var i = 0; i < response.length; i++) {
            console.log("####Counter#### : " + count);
            var data = response[i];

            var cinemaName = data["cinemaName"];
            var underground = data["cinemaUnderground"];
            var movieList = data["movieList"];


            //var session = data["movieList"].session;
            var movieName = [];
            var price = [];
            var type = [];
            var time = [];
            var url = [];

            for (var j = 0; j < movieList.length; j++) {
                var session = movieList[j]["session"];
                movieName[j] = movieList[j]["movieName"];
                price[j] = session["sessionPrice"];
                type[j] = session["sessionType"];
                time[j] = session["sessionTime"];
                url[j] = session["sessionUrl"];

                // элемент-список UL
                var list = document.getElementById('list');

                // новый элемент
                var li = document.createElement('LI');
                li.innerHTML = cinemaName + " - " + underground + " - " + " - " + movieName[j]
                    + " - " + price[j] + " - " + type[j] + " - " + time[j] + url[j];
                var liUrl = document.createElement('li');
                liUrl.href = url[j];

                // добавление в конец
                list.appendChild(li);
                list.appendChild(liUrl);


                console.log(cinemaName + " - " + underground + " - " + " - " + movieName[j]
                    + " - " + price[j] + " - " + type[j] + " - " + time[j] + "\n");
            }
            count++;

        }
    } catch(err) {
        console.log(err.message + " in " + response);
        return;
    }

}