package com.project.NeuralNetwork.Base.Networks.base;

import com.project.NeuralNetwork.Base.Layers.HiddenLayer;
import com.project.NeuralNetwork.Base.Layers.OutputLayer;
import com.project.NeuralNetwork.Base.Layers.base.Layers;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;

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

    /**
     * Устанавливаем тип функции активации для определенного слоя.
     * @param funcType тип функции активации
     * @param layer уровень, на котором хотим установить другую ФА
     */
    void setFuncActivType(Layers layer, Functions funcType);
}
