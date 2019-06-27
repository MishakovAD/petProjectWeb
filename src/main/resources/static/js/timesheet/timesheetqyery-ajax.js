// вызов функции по завершению загрузки страницы
$(document).ready(function() {
    // вызов функции после потери полем 'timesheetquery' фокуса
    $('#timesheetquery').blur(function() {
        $.ajax({
            url : 'timesheetquery',     // URL - сервлет
            data : {                 // передаваемые сервлету данные
                timesheetquery : $('#timesheetquery').val(),
            },
            success : function(response) {
                // обработка ответа от сервера
                $('#ajaxTimesheetQuery').text(response);
            }
        });
    });
});