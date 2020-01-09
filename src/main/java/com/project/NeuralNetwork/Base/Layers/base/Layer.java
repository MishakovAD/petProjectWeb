package com.project.NeuralNetwork.Base.Layers.base;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;

public interface Layer {
    /**
     * Возвращает количество нейронов в слое.
     * @return количество нейронов
     */
    int getNeuronsCount();

    /**
     * Возвращает количество входов у нейрона в слое.
     * @return количество входов нейрона
     */
    int getInputsCount();

    /**
     * Устанавливает значения входов для нейронов слоя.
     * Т.е. выходы нейронов предыдущего слоя подаются на входы нейронов текущего.
     * Количество нейронов предыдущего слоя == количество входов нейрона текущего слоя.
     * @param data выходные данные текущего слоя - выходные предыдущего
     */
    void setData(double[] data);

    /**
     * Возвращает все нейроны слоя.
     * @return нейроны в слое
     */
    Neuron[] getNeuronsArray();

    /**
     * Возвращет нейрон с конкретным индексом из массива слоя.
     * @param index индекс нейрона
     * @return нейрон
     */
    Neuron getNeuron(int index);

    /**
     * Возвращает выход нейронов.
     * @return выход нейрона
     */
    double[] getOutput();

    /**
     * Возвращает выход конкретного нейрона
     * @param index индекс нейрона
     * @return выход нейрона
     */
    double getOutputFromNeuron(int index);

    /**
     * Возвращает тип функции активации указанного слоя.
     * @return тип функции активации
     */
    Functions getFuncType();

    /**
     * Корректирует веса нейронов в слое.
     */
    void correctWeights();
}
