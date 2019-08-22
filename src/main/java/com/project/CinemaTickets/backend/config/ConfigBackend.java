package com.project.CinemaTickets.backend.config;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Класс, предназначенный для конфигурационных параметров приложения, чтобы в git не пушить лишних паролей и ключей.
 */
public interface ConfigBackend {

    /**
     * Инициализирует проперти файл, получая доступ к конфигурациям.
     * Устанавливает приватное поле Properties.
     */
    void initPropertyFile() throws IOException;

    /**
     * Получение уникального ключа-идентификатора пользователя на RuCaptcha
     * @return userKey
     */
    String getRuCaptchaUserKey();

    /**
     * Переключатель - использовать или нет сервис RuCaptcha
     * @return true, если используем
     */
    boolean isRuCaptchaEnable() ;

    /**
     * Получаем логин для подключения к БД
     * @return логин
     */
    String getDbUser();

    /**
     * Получаем пароль для подключения к БД
     * @return пароль
     */
    String getDbPassword();

    /**
     * Получаем ссылку для подключения к БД
     * @return ссылка для подключения к БД
     */
    String getDbUrl();
}
