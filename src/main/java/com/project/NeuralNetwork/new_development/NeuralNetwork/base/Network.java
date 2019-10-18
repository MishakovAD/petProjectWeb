package com.project.NeuralNetwork.new_development.NeuralNetwork.base;

import com.project.NeuralNetwork.new_development.Layers.HiddenLayer;
import com.project.NeuralNetwork.new_development.Layers.OutputLayer;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

public interface Network {
    /**
     * Передача данных в нейронную сеть.
     * @param data входные данные
     */
    void setInputData(double[] data);

    /**
     * Возвращает выходной слой нейронной сети.
     * @return выходной слой
     */
    OutputLayer getOutputLayer();

    /**
     * Возвращает массив скрытых слоев нейронной сети.
     * @return массив скрытыв слоев
     */
    HiddenLayer[] getHiddenLayerArray();

    /**
     * Возвращает конкретный скрытый слой.
     * @param index номер слоя
     * @return скрытый слой
     */
    HiddenLayer getHiddenLayer(int index);

    /**
     * Возвращает результат работы нейронной сети.
     * @return результат
     */
    double[] getOutput();

    /**
     * Возвращает тип функции активации у нейронов сети.
     * @return тип ФА
     */
    Functions getFunctionType();
}
