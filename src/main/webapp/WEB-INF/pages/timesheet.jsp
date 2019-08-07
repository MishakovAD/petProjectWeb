<%--
  Created by IntelliJ IDEA.
  User: a.mishakov
  Date: 27.06.2019
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;
                         charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- ####################### Скрипты ####################### -->
    <!-- css -->
    <link rel="stylesheet" href="css/suggest.css">
    <!-- css end -->

    <!-- Подключение библиотек -->
    <!-- LOCAL -->
    <script src="${pageContext.request.contextPath} webjars/jquery/3.3.1-1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/js/bootstrap.bundle.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap.min.css">

    <script src="js/timesheet/src/jquery.autocomplete.js" type="text/javascript"></script>
    <script src="js/timesheet/src/jquery.suggest.js" type="text/javascript"></script>
    <script src="js/timesheet/src/jquery-ui.js" type="text/javascript"></script>
    <!-- Подключение библиотек -->

    <!-- Для определения города по IP -->
    <script src="http://api-maps.yandex.ru/2.0-stable/?load=package.standard&lang=ru-RU" type="text/javascript"></script>
    <!-- Для определения города по IP -->

    <!-- Пользовательские скрипты -->
    <script src="js/timesheet/timesheet_controller.js" type="text/javascript"></script>
    <script src="js/timesheet/timesheet_autocomplete.js" type="text/javascript"></script>
    <script src="js/timesheet/timesheet_user_interface.js" type="text/javascript"></script>
    <!-- Пользовательские скрипты -->
    <!-- ####################### Скрипты ####################### -->

    <title>Билеты в кино</title>
</head>
<body>
Тут билеты
<br/>
<div class="query">
    <h4 class="field_name" id="field_name">Название фильма: </h4>
    <input type="text" name="timesheetquery" id="timesheetquery"/><br/>
    <input type="button" name="timesheetquery_button" id="timesheetquery_button" value="Далее"><br/>
</div>

<form>
    <div class="form-row">
        <div class="col-2">
        </div>
        <div class="col-3">
            <!-- timesheet_user_interface.js createDynamicInterface() -->
            <label for="movieName">Название фильма:</label>
            <input type="text" class="form-control" id="movieName" aria-describedby="movieName" placeholder="Введите название фильма..">
            <small id="movieNameHelp" class="form-text text-muted">Введите фильм, который Вы хотели бы посмотреть в кинотеатре.</small>
            <br>
        </div>
        <div class="col-3">
            <label for="movieTime">Желаемое время сеанса:</label>
            <input type="datetime-local" class="form-control" id="movieTime" aria-describedby="movieTime" placeholder="Введите желаемое время..">
            <small id="movieTimeHelp" class="form-text text-muted">Введите желаемое время. Будет выбран ближайший сеанс.</small>
            <br>
        </div>
        <div class="col-3">
            <label for="movieType">Тип сеанса:</label>
            <!-- timesheet_user_interface.js cjooseType() -->
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="movieType" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Выберете тип
                </button>
                <div class="dropdown-menu" aria-labelledby="movieType">
                    <a class="dropdown-item active" href="#">2D</a>
                    <a class="dropdown-item" href="#">3D</a>
                    <a class="dropdown-item" href="#">IMax 3D</a>
                    <a class="dropdown-item" href="#">Dolby Atmos</a>
                </div>
            </div>
            <br>
        </div>
        <div class="col-1">
        </div>
    </div>
    <div class="form-row">
        <div class="col-2">
        </div>
        <div class="col-3">
            <label for="moviePrice">Максимальная стоимость билета:</label>
            <input type="text" class="form-control" id="moviePrice" aria-describedby="moviePrice" placeholder="Введите стоимость..">
            <small id="moviePriceHelp" class="form-text text-muted">Введите максимальную стоимость билета.</small>
            <br>
        </div>
        <div class="col-3">
            <label for="moviePlace">Ближайшее метро:</label>
            <input type="text" class="form-control" id="moviePlace" aria-describedby="moviePrice" placeholder="Введите ближайшее метро..">
            <small id="moviePlaceHelp" class="form-text text-muted">Введите ближайшее метро или место.</small>
            <br>
        </div>
        <div class="col-3">

        </div>
        <div class="col-1">
        </div>
    </div>
    <div class="form-row">
        <div class="col-2">
        </div>
        <div class="col-3">
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
        <div class="col-3">
        </div>
        <div class="col-3">
        </div>
        <div class="col-1">
        </div>
    </div>
</form>


<!-- <div class="response" id="response">
    <strong>Ответ сервлета </strong>:
    <span id="ajaxTimesheetQuery"></span>
    <br/>
    <br/>
    <ul id="list">

    </ul>
</div> -->

<div id="user-city"></div>
<div id="user-region"></div>
<div id="user-country"></div>

</body>

</html>
