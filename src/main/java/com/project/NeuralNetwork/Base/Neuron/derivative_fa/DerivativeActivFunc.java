package com.project.NeuralNetwork.Base.Neuron.derivative_fa;


import com.project.NeuralNetwork.Base.Neuron.base.Neuron;

public interface DerivativeActivFunc {
    /**
     * Метод, вычисляющий производную функции активации.
     * @param inputs входные значения
     * @param weights веса
     * @param params параметры крутизны/порог
     * @return производную ФА
     */
    double derivative(double[] inputs, double[] weights, double ... params);

    /**
     * Метод, вычисляющий производную функции активации.
     * @param inputs входные значения
     * @param weights веса
     * @return производную ФА
     */
    double derivative(double[] inputs, double[] weights);

    /**
     * Метод, вычисляющий производную функции активации.
     * @param neuron нейрон, для которого вычисляется производная
     * @return производную ФА
     */
    double derivative(Neuron neuron);
}
