// вызов функции по завершению загрузки страницы
$(document).ready(function sendQuery() {
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

                var fieldName = document.getElementById("field_name");
                fieldName.innerHTML = "Желаемое время фильма: ";

                var inputQuery = document.getElementById("timesheetquery");
                inputQuery.id = "timesheet_time";

                sendInputTime.call();
            }
        });
    });
}
);

var sendInputTime = function sendTime() {
$('#timesheet_time').blur( function() {
    $.ajax({
        url: 'timesheetquery_time',     // URL - сервлет
        data: {                 // передаваемые сервлету данные
            timesheetquery_time: $('#timesheet_time').val(),
            timesheetquery : "",
        },
        success: function (response) {
            // обработка ответа от сервера
            $('#ajaxTimesheetQuery').text(response);

            var fieldName = document.getElementById("field_name");
            fieldName.innerHTML = "Желаемое тип сеанса: ";

            var inputQuery = document.getElementById("timesheet_time");
            inputQuery.id = "timesheet_type";

            sendInputType.call();
        }
    })
});
};

var sendInputType = function sendType() {
    $('#timesheet_type').focusout( function() {
        $.ajax({
            url: 'timesheetquery_type',     // URL - сервлет
            data: {                 // передаваемые сервлету данные
                timesheetquery_type: $('#timesheet_type').val(),
                timesheetquery_time: "",
                timesheetquery : "",
            },
            success: function (response) {
                // обработка ответа от сервера
                $('#ajaxTimesheetQuery').text(response);

                var fieldName = document.getElementById("field_name");
                fieldName.innerHTML = "Ближайшее место: ";

                var inputQuery = document.getElementById("timesheet_type");
                inputQuery.id = "timesheet_place";
            }
        })
    });
};

