package com.project.CinemaTickets.backend.config;

/**
 * Класс, предназначенный для конфигурационных параметров приложения, чтобы в git не пушить лишних паролей и ключей.
 */
public interface ConfigBackend {
    /**
     * Получение уникального ключа-идентификатора пользователя на RuCaptcha
     * @return userKey
     */
    String getRuCaptchaUserKey();
}
