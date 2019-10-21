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
