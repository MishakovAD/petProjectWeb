<%@ page language="java" contentType="text/html;
                         charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Подключение Bootstrap, JQuery and Popper.js -->
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath} webjars/jquery/3.3.1-1/jquery.min.js"></script>

    <title>Интеграция JSP и jQuery/Servlet - Авторизация</title>

    <script src="js/auth-ajax.js" type="text/javascript"></script>
</head>

<body>
<p/>
Логин : <input type="text" id="userName"/><br/>
Пароль : <input type="password" id="password"/><br/>

<p/>
<strong>Ответ сервлета </strong>:
<span id="ajaxUserServletResponse"></span>
</body>
</html>