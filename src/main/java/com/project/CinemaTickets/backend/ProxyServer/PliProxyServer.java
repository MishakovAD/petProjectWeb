package com.project.CinemaTickets.backend.ProxyServer;

import com.project.CinemaTickets.backend.ProxyServer.ProxyEntity.ProxyEntity;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface PliProxyServer {
    /**
     * Метод, получающий список прокси-серверов (ip - port)
     * из интернета по ссылке, где "лежат" сервера
     * @param url - ссылка списка прокси-серверов
     * @return - список прокси серверов вида ip:port
     */
    public List<String> getProxyFromInternet (String url);

    /**
     * Метод, получающий список прокси-серверов (ip - port)
     * из Базы Данных (id - ip - port)
     * @return - список прокси серверов вида ip:port
     */
    public List<ProxyEntity> getProxyFromDatabase();

//    /**
//     * Получение ip адреса из пары ip:port
//     * @param proxyEntity - элемент из БД
//     * @return ip адрес
//     */
//    public String getIpFromProxy(ProxyEntity proxyEntity);
//
//    /**
//     * Получение port адреса из пары ip:port
//     * @param proxyEntity - элемент из БД
//     * @return порт
//     */
//    public String getPortFromProxy(ProxyEntity proxyEntity);

    /**
     * Добавление прокси-листа в БД
     * ВАЖНО: исключить добавление повторяющихся
     * Проверка в отдельном методе
     * @param proxyList - лист с прокси-серверами
     * @return true, если добавление прошло успешно
     */
    public boolean addProxyToDatabase(List<String> proxyList);

    /**
     * Метод, проверяющий наличие прокси-сервера
     * в Базе данных
     * @param proxy прокси-сервер вида "ip:port"
     * @return true, если данный сервер уже есть в базе
     */
    public boolean isExistProxyOnDatabase(String proxy);

    /**
     * Удаление неработающего сервера из Базы Данных
     * @param proxy прокси-сервер вида "ip:port"
     * @return true, если сервер удален успешно
     */
    public boolean deleteProxyFromDatabase(String proxy);

    /**
     * Получение содержимого страницы, которая
     * находятся по передаваему url без помощи прокси
     * NB! использовать только этот метод, т.к.
     * в его теле при неудачном скачивании есть
     * вызов метода с прокси.
     * @param url - сайт, который нужно распарсить при помощи Jsoup
     * @return - сайт в формате Document
     */
    public Document getHttpDocumentFromInternet(String url) throws IOException;

    /**
     * Получение содержимого страницы, которая
     * находятся по передаваему url с помощью прокси-сервера
     * @param url - сайт, который нужно распарсить при помощи Jsoup
     * @return - сайт в формате Document
     */
    public Document getHttpDocumentFromInternetWithProxy(String url);

    /**
     * Так как сначала мы скачиваем страницу в виде строки,
     * то можем проверить в строке на содержание "ой, вы заблокированы"
     * и тд. И у в случае отсутсвия таковых "паразитов"
     * можно считать, что ссылка скачана корректно.
     * @param document - входящий "поток" данных с сайта
     * @return true - если страница корректна
     */
    public boolean isCorrectDownloadDocument(String document);
}
