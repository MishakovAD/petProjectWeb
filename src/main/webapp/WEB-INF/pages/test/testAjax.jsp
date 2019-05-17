<%@ page language="java" contentType="text/html;
                         charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type"
          content="text/html; charset=UTF-8">
    <title>Интеграция JSP и jQuery/Servlet</title>

    <script src="http://code.jquery.com/jquery-2.2.4.js"
            type="text/javascript"></script>
    <script src="js/app-ajax.js" type="text/javascript"></script>
</head>
<body>
<p />
Ваше имя : <input type="text" id="userName" /><br />
<span style="font-style:italic; font-size:75%">
                сервлет ответит после потери полем курсора</span>
<p />
<strong>Ответ сервлета </strong>:
<span id="ajaxUserServletResponse"></span>
</body>
</html>