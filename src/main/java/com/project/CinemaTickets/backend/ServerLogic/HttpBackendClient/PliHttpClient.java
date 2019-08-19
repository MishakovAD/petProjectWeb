package com.project.CinemaTickets.backend.ServerLogic.HttpBackendClient;

import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public interface PliHttpClient {
    /**
     * Получение html документа по ссылке.
     * @param url ссылка на страницу сайта
     * @return Document страницы
     */
    Document getDocumentFromInternet(String url) throws IOException;

    /**
     * Решаем проблему каптчи.
     * @param capthaDocument документ с капчей
     * @return ссылка с ответом
     */
    String getAnswerUrlForCaptcha (StringBuilder capthaDocument);

    /**
     * Возвращает набор куков ключ-значение
     * @param response ответ от сервера
     * @return карта cookies
     */
    Map<String, String> getCookies(HttpResponse response);
}
