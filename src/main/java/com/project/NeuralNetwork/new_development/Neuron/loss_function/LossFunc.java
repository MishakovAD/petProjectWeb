package com.project.NeuralNetwork.new_development.Neuron.loss_function;

public interface LossFunc {

    /**
     * Вычисляем функцию потерь для выхода нашей НС.
     * @param ideal ожидаемый результат
     * @param result получившийся результат
     * @return значение функции потерь
     */
    double calculateLossF(double ideal[], double[] result);

    /**
     * Вычисляем производную функции потерь для выхода нашей НС.
     * @param ideal ожидаемый результат
     * @param result получившийся результат
     * @param numOfNeuron номер нейрона, по которому вычисляется частная производная.
     * @return значение функции потерь
     */
    double calculateDerivationLossF(double ideal[], double[] result, int numOfNeuron);
}
