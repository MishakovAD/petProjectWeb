package com.project.NeuralNetwork.Base.Neuron.loss_function;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;

public interface LossFunc {

    /**
     * Вычисляем функцию потерь для выхода нашей НС.
     * @param ideal ожидаемый результат
     * @param neuron нейрон
     * @return значение функции потерь
     */
    double calculateLossF(double ideal, Neuron neuron);

    /**
     * Вычисляем производную функции потерь для выхода нашей НС.
     * @param ideal ожидаемый результат
     * @param neuron нейрон, по которому вычисляется частная производная.
     * @return значение функции потерь
     */
    double calculateDerivationLossF(double ideal, Neuron neuron);
}
