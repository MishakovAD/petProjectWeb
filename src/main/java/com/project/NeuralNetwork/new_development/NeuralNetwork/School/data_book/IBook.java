package com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book;

import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Lesson.ILesson;

import java.util.List;
import java.util.Map;

public interface IBook {
    /**
     * Добавляем в учебник задание(data) и ответ на него (answer).
     * @param data задание
     * @param answer ответ
     * @return true, если данные корректно добавлены.
     */
    boolean addData(double[] data, double[] answer);

    /**
     * Устанавливает новое значение на место старой страницы.
     * @param index индекс заменяемой страницы
     * @param data новое значение вопроса
     * @return true, если замена прошла успешно
     */
    boolean setData(int index, double[] data);

    /**
     * Возвращает вопрос по индексу
     * @param index индекс вопроса
     * @return вопрос
     */
    double[] getQuestion(int index);

    /**
     * Возвращает "урок" для НС.
     * @return "урок" для НС.
     */
    ILesson getLesson();

    /**
     * Возвращает количество пройденных эпох в обучении.
     * @return количество эпох
     */
    int getEpoch();
}
