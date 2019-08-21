package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient.ruCaptchaAuto;

/**
 * Класс, который с помощью сервиса автоматически решает каптчу по картинке.
 */
public interface RuCaptcha {

    /**
     * Метод, отправляющий запрос на автоматическое распознавание каптчи.
     * @param captchaUrl ссылка с изображением каптчи
     * @return уникальный ключ, по которому можно получить ответ
     */
    String sendRequest(String captchaUrl);

    /**
     * Получение ответа от автоматического сервиса.
     * @param key ключ, по которому можно получить ответ
     * @return ответ на каптчу
     */
    String getResponse(String key);
}
