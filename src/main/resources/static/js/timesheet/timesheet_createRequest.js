//Скрипт, для получения данных с формы и отправки на сервер.
function getMovieNameForAjax() {
    $('#movieName').blur( function() {
        var movie_name = $('#movieName').val();
        if (available_movie.indexOf(movie_name) != -1 & movie_name != "") {
            sendAjaxForPrepareSessions("prepareSessionList", movie_name);
        } else {
            //показать всплывающее окно, что название фильма некорректно
        }
    });
}

function clickSubmit() {
    $('#Submit').click( function () {
        var formDiv = $('#formDiv');
        var animation = $('#animation');
        formDiv.css('visibility', 'hidden');
        animation.insertBefore(formDiv);
        animation.css('visibility', 'visible');
        var movieTime = $('#movieTime').val();
        var movieType = $('#movieType').val();
        var moviePrice = $('#moviePrice').val();
        var moviePlace = $('#moviePlace').val();
        var requestData = 'time:' + movieTime + ', type:' + movieType + ', price:' + moviePrice + ', place' + moviePlace;
        console.log("requestData: " + requestData);
        sendAjaxForFilterSessions("prepareResult", requestData);
    });
}

//Появляющаяся подсказка
function createTooltip(text) {
    $('#movieNameHelp').text(text);
}

function sendAjaxForPrepareSessions(sendUrl, requestData) {
    $.ajax({
        url: sendUrl,
        data: { 'movieName' : requestData },
        datatype: "application/json",
        cache: false,
        success: function (response) {
            var text = response.toString();
            if (text.indexOf("не найдено") != -1) {
                createTooltip("Выберите, пожалуйста, другой фильм. Сеансов не найдено.");
            } else {
                createTooltip(text);
            }
        },
        error: function (response) {
            //какая то ошибка? какая? пока хз
        }
    });
}

var responseData;
function sendAjaxForFilterSessions(sendUrl, requestData) {
    $.ajax({
        url: sendUrl,
        data: { 'query' : requestData },
        datatype: "application/json",
        cache: false,
        success: function (response) {
            $('#formDiv').remove();
            $('#animation').remove();
            responseData = response[0];
            var resultDiv = $('#result');
            resultDiv.css('visibility', 'visible');
            $('#movieTitle').text(responseData["movie"]);
            $('#cinemaTitle').text(responseData["cinema"]);
            $('#date').text(responseData["sessionDate"]);
            $('#time').text(responseData["sessionTime"]);
            $('#price').text("от " + responseData["sessionPrice"]);
            $('#type').text(responseData["sessionType"]);
            $('#url').html('<a class="btn btn-primary btn-large" href="' + responseData["sesssionUrl"] + '">Купить билет</a>');
        },
        error: function (response) {
            alert("ERROR!!!");
        }
    });
}