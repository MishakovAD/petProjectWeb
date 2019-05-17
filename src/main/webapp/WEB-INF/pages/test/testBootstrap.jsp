<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Подключение Bootstrap, JQuery and Popper.js -->
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/jquery/3.3.1-1/jquery.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/popper.js/1.14.1/popper.js">
    <!-- ${pageContext.request.contextPath} == META-INF/resources/ -->

    <title>Twitter Bootstrap Example</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- See configuration in WebMvConfig.java -->
    <link th:href="@{/bootstrap/css/bootstrap.min.css}"
          rel="stylesheet" media="screen"/>


    <script th:src="@{/jquery/jquery.min.js}"></script>
    <script th:src="@{/popper/popper.min.js}"></script>
    <script th:src="@{/bootstrap/js/bootstrap.min.js}"></script>

</head>

<body>

<h2>Hello Twitter Bootstrap</h2>

<div class="btn-group">
    <button type="button" class="btn btn-success">This is a success button</button>
    <button type="button" class="btn btn-warning">This is a warning button</button>
    <button type="button" class="btn btn-danger">This is a danger button</button>
</div>
</body>

</html>