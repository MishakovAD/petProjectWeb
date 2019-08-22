package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient.ruCaptchaAuto;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Класс, который с помощью сервиса автоматически решает каптчу по картинке.
 */
public interface RuCaptcha {

    /**
     * Метод, отправляющий запрос на автоматическое распознавание каптчи.
     * @param captchaUrlImage ссылка с изображением каптчи
     * @return уникальный ключ, по которому можно получить ответ
     */
    String sendRequest(String captchaUrlImage) throws IOException;

    /**
     * Получение ответа от автоматического сервиса.
     * @param key ключ, по которому можно получить ответ
     * @return ответ на каптчу
     */
    String getResponse(String key) throws IOException;
}
