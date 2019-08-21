$(document).ready(function () {
    clickSubmitCaptcha();
    setTimeout(function (){
        getCaptchaImg()
    }, 5000);
});


function clickSubmitCaptcha() {
    $('#SubmitCaptcha').click( function () {
        var answer = $('#enterCaptcha').val();
        console.log("requestData: " + answer);
        $.ajax({
            url: "enter_answer_captcha",
            data: { 'answerCaptcha' : answer },
            datatype: "application/json",
            cache: false,
            success: function (response) {
                console.log("Answer send successfull");
            },
            error: function (response) {

            }
        });
    });
}


function getCaptchaImg() {
    $.ajax({
        url: "get_captcha_question",
        data: { 'noData' : "noData" },
        datatype: "application/json",
        cache: false,
        success: function (response) {
            var img = response.toString();
            $('#captchaImg').attr("src", img);
            console.log(img);
        },
        error: function (response) {

        },
        complete: function () {
            setTimeout(function (){
                getCaptchaImg()
            }, 15000);
        }
    });
}