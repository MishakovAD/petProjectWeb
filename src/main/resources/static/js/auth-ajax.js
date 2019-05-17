// вызов функции по завершению загрузки страницы
$(document).ready(function() {
    // вызов функции после потери полем 'password' фокуса
    $('#password').blur(function() {
        $.ajax({
            url : 'testAuthPrettyResp',     // URL - сервлет
            data : {                 // передаваемые сервлету данные
                userName : $('#userName').val(),
                password : $('#password').val()
            },
            success : function(response) {
                // обработка ответа от сервера
                $('#ajaxUserServletResponse').text(response);
            }
        });
    });
});