<%@ page language="java" contentType="text/html;
                         charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Авторизация</title>
	<link rel="stylesheet" href="/auth/css/style.css" media="screen" type="text/css" />
	<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
	<link rel="icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" href="http://vladmaxi.net/favicon.ico" type="image/x-icon">
	<!-- Подключение Bootstrap, JQuery and Popper.js
	<link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap.min.css"> -->
	<script src="${pageContext.request.contextPath} webjars/jquery/3.3.1-1/jquery.min.js"></script>

	<script src="js/auth-ajax.js" type="text/javascript"></script>
</head>

<body>

	<div class="main-signin">
		<div class="main-signin__head">
			<p>АВТОРИЗАЦИЯ</p>
		</div>
		<div class="main-signin__middle">
			<div class="middle__form">
				<input type="text" id="userName" placeholder="Логин">
				<input type="password" id="password" placeholder="Пароль">
				<input type="submit" value="ВОЙТИ">
			</div>
		</div>
		<div class="main-signin__foot">
			<div class="foot__left">
				<p>Войти через:</p>
			</div>
			<div class="foot__right">
				<div class="twit"><a href="#"></a></div>
				<div class="face"><a href="#"></a></div>
			</div>
		</div>
	</div>

	<strong>Ответ сервлета </strong>:
	<span id="ajaxUserServletResponse"></span>

</body>
</html>