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

                var input = document.createElement('input');
                input.type = "text";
                input.className = "timesheet_time";
                input.id = "timesheet_time";
                document.body.appendChild(input);
            }
        });
    });
}
);


// вызов функции после потери полем 'timesheetquery' фокуса
$('#timesheet_time').focusout(function(){
    $.ajax({
        url : 'timesheetquery_test',     // URL - сервлет
        data : {                 // передаваемые сервлету данные
            timesheetquery : $('#timesheet_time').val(),
        },
        success : function(response) {
            // обработка ответа от сервера
            $('#ajaxTimesheetQuery').text(response);

            var input = document.createElement('input');
            input.type = "text";
            input.className = "timesheet_time";
            document.body.appendChild(input);
        }
    });
});
