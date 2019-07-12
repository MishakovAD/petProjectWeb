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

    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath} webjars/jquery/3.3.1-1/jquery.min.js"></script>
    <script src="js/timesheet/timesheetqyery-ajax.js" type="text/javascript"></script>
</head>
<body>
Тут билеты
<br/>
<div class="query">
    Название фильма: <input type="text" id="timesheetquery"/><br/>

    Тип фильма: <input type="hidden" id="timesheet_type"/><br/>
    Кинотеатр: <input type="hidden" id="timesheet_cinmea"/><br/>
</div>
Время сеанса: <input type="text" id="timesheet_time"/><br/>

<strong>Ответ сервлета </strong>:
<span id="ajaxTimesheetQuery"></span>
</body>
</html>
