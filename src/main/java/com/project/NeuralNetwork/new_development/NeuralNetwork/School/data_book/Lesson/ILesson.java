package com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Lesson;

public interface ILesson {
    /**
     * Возвращает задание для НС.
     * @return задание/вопрос
     */
    double[] getQuestion();

    /**
     * Возвращает ответ на задание/вопрос.
     * @return ответ
     */
    double[] getAnswer();
}
