package com.project.NeuralNetwork.Base.Neuron.function_activation;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;

public interface ActivFunc {
    /**
     * Вычисляет выход нейрона, согласно выбранной функции.
     * @param inputs входные данные нейрона
     * @param weights веса каждого входа нейрона
     * @param a крутизна (для сигмоидальной и тангенсальной функции) или порог для скачкаобразной
     * @return выходной сигнал нейрона
     */
    double activate(double[] inputs, double[] weights, double... a);

    /**
     * Вычисляет выход нейрона, согласно выбранной функции.
     * (если не сигмоидальная или тангенсальная)
     * @param inputs входные данные нейрона
     * @param weights веса каждого входа нейрона
     * @return выходной сигнал нейрона
     */
    double activate(double[] inputs, double[] weights);

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

    /**
     * Возвращает тип функции активации.
     * @return тип функции активации
     */
    Functions getFuncType();

    /**
     * Возвращает параметры, с которыми вычислялась функции активации.
     * @return параметры ФА
     */
    double[] getParams();
}