package com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.derivative_user_fa;

@FunctionalInterface
public interface DerivativeUserFunction {
    /**
     * Метод, вычисляющий производную пользовательской функции активации.
     * @param inputs входные значения
     * @param weights веса
     * @param params параметры крутизны/порог
     * @return производную ФА
     */
    double derivative(double[] inputs, double[] weights, double ... params);
}
