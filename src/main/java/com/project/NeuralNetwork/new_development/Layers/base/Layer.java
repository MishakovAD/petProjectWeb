package com.project.NeuralNetwork.new_development.Layers.base;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;

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
}
