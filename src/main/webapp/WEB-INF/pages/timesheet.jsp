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
    <link rel="stylesheet" href="css/loading.css">
    <link rel="stylesheet" href="css/loading2.css">
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
    <script src="js/timesheet/timesheet_createRequest.js" type="text/javascript"></script>
    <!-- Пользовательские скрипты -->
    <!-- ####################### Скрипты ####################### -->

    <title>Билеты в кино</title>
</head>
<body>

<!-- FORM -->
<div id="formDiv">
    <form>
        <div class="form-row">
            <div class="col-2">
            </div>
            <div class="col-3" id="suggestDiv">
                <!-- timesheet_user_interface.js createDynamicInterface() -->
                <label for="movieName">Название фильма:</label>
                <input type="text" class="form-control" id="movieName" aria-describedby="movieName" placeholder="Введите название фильма..">
                <small id="movieNameHelp" class="form-text text-muted"></small>
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
                        <a class="dropdown-item" href="#">D-Box</a>
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
                <input type="range" class="custom-range" min="0" max="2000" step="50" value="0" id="moviePriceRange">
                <small id="moviePriceHelp" class="form-text text-muted">Введите/выберете максимальную стоимость билета.</small>
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
                <button type="button" class="btn btn-primary" id="Submit">Submit</button>
            </div>
            <div class="col-3">
            </div>
            <div class="col-3">
            </div>
            <div class="col-1">
            </div>
        </div>
    </form>
</div>
<!-- END FORM -->

<div id="user-city"></div>
<div id="user-region"></div>
<div id="user-country"></div>

<!-- ANIMATIONS https://icons8.com/cssload/ru/-->
<div id="animation" style="visibility: hidden;">
    <div class="cssload-thecube">
        <div class="cssload-cube cssload-c1"></div>
        <div class="cssload-cube cssload-c2"></div>
        <div class="cssload-cube cssload-c4"></div>
        <div class="cssload-cube cssload-c3"></div>
    </div>

    <!-- ********************************** -->
    <div id="cssload-pgloading">
        <div class="cssload-loadingwrap">
            <ul class="cssload-bokeh">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
    </div>
</div>

<!-- END ANIMATIONS -->


<!-- RESULT -->
<div id="result" style="visibility: hidden;">
    <div class="hero-unit">
        <div class="page-header">
            <h1 id="movieTitle"><h4 id="cinemaTitle"></h4></h1>
        </div>

        <div id="resultTableDiv">

        </div>
    </div>
</div>
<!-- END RESULT -->

</body>

</html>
