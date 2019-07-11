package com.project.CinemaTickets.backend.UserLogic;

import com.project.CinemaTickets.CinemaEntity.Cinema;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для предоставления списка фильмов из интернета
 * (в дальнейшем с помощью данной функциональности можно будет заполнять БД)
 * Так и получением списка из БД путем более быстрых запросов и сортировки.
 */
public interface PliUserLogic {
    /**
     * Метод, который возвращает список кинотеатров, где
     * можно увидеть запрашиваемый фильм, а так же расписание фильма,
     * которое можно получить из объекста Cinema
     * @param userQuery - Название фильма (пользовательский запрос)
     * @return Список кинотеатров, где есть этот фильм + все сеансы
     */
    public List<Cinema> getCinemaListWithMovie(String userQuery) throws IOException;

    /**
     * Метод удаляет из списка кинотетров те кмнотеатры, которые
     * не могут предложить нужный тип просмотра, а так же удаляет все
     * неподходящие варианты со всременем
     * @param cinemaList - Список кинотеатров со всеми сеансами фильма
     * @param type - Тип сеанса (2D, 3D, IMAX, Dolby Atmos)
     * @return Отсортированный по типу кино список кинотеатров
     */
    public List<Cinema> updateCinemaListFromTypeShow (List<Cinema> cinemaList, String type);

    /**
     * Метод, который определеяет, какие кинотеатры могут предоставить сеансы
     * согласно времени, указанному пользователем. И удаляет неподходящие.
     * Выборка ведется плюс полтора часа, минус час от указанного времени
     * @param cinemaList - Список кинотеатров со всеми сеансами фильма
     * @param time - Интересующее пользователя время
     * @return Список кинотеатров, которые удовлетворяют запросу по времени
     */
    public List<Cinema> updateCinemaListFromTimeShow (List<Cinema> cinemaList, String time);

    /**
     * Метод, который удаляет кинотеатры, расположенные
     * слишком далеко от пользователя или которые
     * не удовлетворяют его требованиям по месторасположению
     * @param cinemaList
     * @param place - Список кинотеатров со всеми сеансами фильма
     * @return - Интересующее пользователя месторасположение
     */
    public List<Cinema> updateCinemaListFromPlace (List<Cinema> cinemaList, String place);

//    /**
//     * Получение списка новинок на день вместе с кинотеатрами
//     * и заполнения базы данных.
//     * Возможен запуск отдельным потоком по расписанию
//     */
//    public void getNewFilmsAndUpdateDB();
}
