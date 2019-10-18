package com.project.NeuralNetwork.new_development.Neuron.derivative_fa;


public interface DerivativeActivFunc {
    /**
     * Метод, вычисляющий производную функции активации.
     * @param inputs входные значения
     * @param weights веса
     * @param params параметры крутизны/порог
     * @return производную ФА
     */
    double calculateDerivative(double[] inputs, double[] weights, double ... params);

    /**
     * Метод, вычисляющий производную функции активации.
     * @param inputs входные значения
     * @param weights веса
     * @return производную ФА
     */
    double calculateDerivative(double[] inputs, double[] weights);
}
