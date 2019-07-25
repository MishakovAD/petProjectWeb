// Номер текущей стадии.
var stage = 1;
//1 - введите название фильма
//2 - введите время
//3 - введите сеанс
//4- введите место
// Конечная стадия.
var limit = 5;

var dataQuery = ' { ';

var sendUrl = "timesheetquery";

$(document).ready(function() {
    $('input[name="timesheetquery_button"]').click(function() {
        createQueryBySteps (stage, $('input[name="timesheetquery"]').val());

        sendQuery(sendUrl, stage, {
            timesheetquery: $('input[name="timesheetquery"]').val()
        })
        // В случае успешного выполнения запроса:
            .done(function(response) {
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
            })
            // В случае возникновения ошибок
            // при выполнения запроса.
            .fail(function(error) {
                //
                alert("Error: " + error);
            })
    });
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
    dataQuery = dataQuery + ' "' + name +'" : "' + query + '", ';
    if (stage == 4) {
        dataQuery.substring(0, dataQuery.length - 1);
        dataQuery = dataQuery + ' } '
        console.log(dataQuery);
        sendAllQuery("timesheet_all_query", dataQuery);
    }
}

// Отправка запроса на сервер.
function sendQuery(url, stage, data) {
    return $.ajax({
        url: url,
        data: data,
        datatype : "application/json",
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

// Отправка запроса на сервер.
function sendAllQuery(url, data) {
    return $.ajax({
        url: url,
        data: data,
        datatype : "application/json",
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

function getResponse(response) {
    try {
        console.log("Length of Array: " + response.length);
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