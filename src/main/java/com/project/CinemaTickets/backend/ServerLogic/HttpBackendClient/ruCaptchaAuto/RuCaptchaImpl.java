package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient.ruCaptchaAuto;

import org.springframework.stereotype.Component;

@Component
public class RuCaptchaImpl implements RuCaptcha {
    //https://ru.stackoverflow.com/questions/496727/%D0%9E%D1%82%D0%BF%D1%80%D0%B0%D0%B2%D0%BA%D0%B0-%D0%BA%D0%B0%D0%BF%D1%87%D0%B8-%D0%BD%D0%B0-%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81-%D0%B0%D0%BD%D1%82%D0%B8%D0%B3%D0%B5%D0%B9%D1%82java-%D0%9E%D1%88%D0%B8%D0%B1%D0%BA%D0%B0-error-zero-captcha-filesize
    @Override
    public String sendRequest(String captchaUrl) {
        String key = "";
        return key;
    }

    @Override
    public String getResponse(String key) {
        String answer = "";
        return answer;
    }
}
