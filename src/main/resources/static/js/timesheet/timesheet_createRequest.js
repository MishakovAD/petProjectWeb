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

function sendAjaxForFilterSessions(sendUrl, requestData) {
    $.ajax({
        url: sendUrl,
        data: { 'query' : requestData },
        datatype: "application/json",
        cache: false,
        success: function (response) {
            timer([0,0,6], function (hours, minutes, seconds) {
                $('#timer').text(seconds);
            });
            setTimeout(function (respose) {
                var responsFrom = response;
                $('#formDiv').remove();
                $('#animation').remove();
                $('#movieTitle').text(response[0][0]["movie"]);
                for (var i = 0; i < response.length; i++) {
                    var respData = response[i];
                    createTable(i, respData);
                    // for (var j = 0; j<respData.length; j++) {
                    //     // $('#url').html('<a class="btn btn-primary btn-large" href="' + responseData["sesssionUrl"] + '">Купить билет</a>');
                    // }
                }
                var resultDiv = $('#result');
                resultDiv.css('visibility', 'visible');
            }, 5000);
        },
        error: function (response) {
            $('#titleText1').text("УПС!");
            $('#titleText2').text("Кажется, что то пошло не так!");
            $('#titleText3').text("Но вы не волнуйтесь! "
                + "Просто обновите страницу \n"
                + "и введите правильное название кино. Вам подскажут.");
        }
    });
}

function createTable(tableId, respData) {
    var body = document.getElementById("resultTableDiv");
    var h4 = document.createElement('h4');
    h4.innerHTML = respData[0]["cinema"];
    body.appendChild(h4);
    var table = document.createElement("table");
    table.classList.add("table");
    table.classList.add("table-striped");
    var tHead = table.createTHead('thead');
    var tr = document.createElement('tr');
    table.appendChild(tHead);
    tHead.appendChild(tr);
    var th1 = document.createElement('th');
    th1.appendChild(document.createTextNode("#"));
    th1.setAttribute("scope", "col");
    var th2 = document.createElement('th');
    th2.appendChild(document.createTextNode("Дата"));
    th2.setAttribute("scope", "col");
    var th3 = document.createElement('th');
    th3.appendChild(document.createTextNode("Время"));
    th3.setAttribute("scope", "col");
    var th4 = document.createElement('th');
    th4.appendChild(document.createTextNode("Цена"));
    th4.setAttribute("scope", "col");
    var th5 = document.createElement('th');
    th5.appendChild(document.createTextNode("Тип сеанса"));
    th5.setAttribute("scope", "col");
    var th6 = document.createElement('th');
    th6.appendChild(document.createTextNode("Ссылка для покупки"));
    th6.setAttribute("scope", "col");
    tr.appendChild(th1);
    tr.appendChild(th2);
    tr.appendChild(th3);
    tr.appendChild(th4);
    tr.appendChild(th5);
    tr.appendChild(th6);
    var tableBody = document.createElement("tbody");

    for (var j = 0; j<respData.length; j++) {
        var responseData = respData[j];
        addRow(tableBody, tableId, j+1, responseData["sessionDate"], responseData["sessionTime"], responseData["sessionPrice"], responseData["sessionType"], responseData["sesssionUrl"]);
    }

    table.appendChild(tableBody);
    body.appendChild(table);
}

function addRow(tableBody, tableId, id, date, time, price, type, url){
    var row = document.createElement("TR");
    var td1 = document.createElement("TD");
    td1.appendChild(document.createTextNode(id));
    var td2 = document.createElement("TD");
    td2.appendChild (document.createTextNode(date));
    var td3 = document.createElement("TD");
    td3.appendChild (document.createTextNode(time));
    var td4 = document.createElement("TD");
    td4.appendChild (document.createTextNode("от " + price));
    var td5 = document.createElement("TD");
    td5.appendChild (document.createTextNode(type));
    var td6 = document.createElement("TD");


    var button = document.createElement("button");
    button.setAttribute('type', 'button');
    button.classList.add('btn');
    button.classList.add('btn-primary');
    button.classList.add('btn-large');
    button.innerHTML = "Купить билет";
    var a_url = document.createElement('a');
    a_url.setAttribute('href', url);
    a_url.appendChild(button);


    td6.appendChild (a_url);
    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    row.appendChild(td5);
    row.appendChild(td6);
    tableBody.appendChild(row);
}

function timer(_time, _call){
    timer.lastCall = _call;
    timer.lastTime = _time;
    timer.timerInterval = setInterval(function(){
        _call(_time[0],_time[1],_time[2]);
        _time[2]--;
        if(_time[0]==0 && _time[1]==0 && _time[2]==0){
            timer.pause();
            _call(0,0,0);
        }
        if(_time[2]==0){
            _time[2] = 59;
            _time[1]--;
            if(_time[1]==0){
                _time[1] = 59;
                _time[0]--
            }
        }
        timer.lastTime = _time
    }, 1000)
}
timer.pause = function(){
    clearInterval(timer.timerInterval)
};
timer.start = function(){
    timer(timer.lastTime, timer.lastCall)
};
