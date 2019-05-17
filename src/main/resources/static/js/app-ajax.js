// вызов функции по завершению загрузки страницы
$(document).ready(function() {
    // вызов функции после потери полем 'userName' фокуса
    $('#userName').blur(function() {
        $.ajax({
            url : 'userServlet',     // URL - сервлет
            data : {                 // передаваемые сервлету данные
                userName : $('#userName').val()
            },
            success : function(response) {
                // обработка ответа от сервера
                $('#ajaxUserServletResponse').text(response);
            }
        });
    });
});