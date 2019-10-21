package com.project.NeuralNetwork.new_development.NeuralNetwork.School;

import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.IBook;
import com.project.NeuralNetwork.new_development.NeuralNetwork.base.Network;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;

public interface ISchool {
    /**
     * Метод для обучение нейронной сети.
     * @param net нейронная сеть, которую необходимо обучить
     * @param book книга с уроками для НС
     * @param startSpeed начальная скорость обучения НС
     * @return true если обучение прошло успешно
     */
    boolean teach(Network net, IBook book, double startSpeed);

    /**
     * Метод для обучение нейронной сети с пользовательской функцией.
     * @param net нейронная сеть, которую необходимо обучить
     * @param derivativeUserFunction класс, вычисляющий производну ФА
     * @param book книга с уроками для НС
     * @param startSpeed начальная скорость обучения НС
     * @return true если обучение прошло успешно
     */
    boolean teach(Network net, DerivativeUserFunction derivativeUserFunction, IBook book, double startSpeed);
}
