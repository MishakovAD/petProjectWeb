<%--
  Created by IntelliJ IDEA.
  User: a.mishakov
  Date: 21.08.2019
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ввод каптчи</title>

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
    <script src="js/captcha/captcha_scripts.js" type="text/javascript"></script>
    <!-- Пользовательские скрипты -->
    <!-- ####################### Скрипты ####################### -->

</head>
<body>

<img src="" alt="Captcha" id="captchaImg">
<form>
    <label for="enterCaptcha">Введите текст с картинки:</label>
    <input type="text" class="form-control" id="enterCaptcha" aria-describedby="movieName" placeholder="Введите ответ">
    <button type="button" class="btn btn-primary" id="SubmitCaptcha">Submit</button>
</form>

</body>
</html>
