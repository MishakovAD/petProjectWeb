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

<div class="response" id="response">
    <strong>Ответ сервлета </strong>:
    <span id="ajaxTimesheetQuery"></span>
    <br/>
    <br/>
    <ul id="list">

    </ul>
</div>

<div id="user-city"></div>
<div id="user-region"></div>
<div id="user-country"></div>

</body>

<!-- ####################### Скрипты ####################### -->
<!-- Подключение библиотек -->
<link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath} webjars/jquery/3.3.1-1/jquery.min.js"></script>
<!-- Подключение библиотек -->

<!-- Для определения города по IP -->
<script src="http://api-maps.yandex.ru/2.0-stable/?load=package.standard&lang=ru-RU" type="text/javascript"></script>
<!-- Для определения города по IP -->

<!-- Пользовательские скрипты -->
<script src="js/timesheet/timesheetqyery-ajax_with_button.js" type="text/javascript"></script>
<script src="js/timesheet/geolocation.js" type="text/javascript"></script>
<script src="js/timesheet/takeIP.js" type="text/javascript"></script> <!-- TODO: разобраться, почему не работает скрипт. Раньше работал. -->
<script src="js/timesheet/getUserCountry.js" type="text/javascript"></script>
<!-- Пользовательские скрипты -->

<!-- Получение IP пользователя через сторонний сервис -->
<script type="application/javascript">
    function getIP(json) {
        console.log("My public IP address is: ", json.ip);
        sendIPToServer("getUserIP", {
            getUserIP : json.ip
        });
    }
</script>

<script type="application/javascript" src="https://api.ipify.org?format=jsonp&callback=getIP"></script>
<!-- Получение IP пользователя через сторонний сервис -->

<!-- ####################### Скрипты ####################### -->

</html>
