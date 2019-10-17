package com.project.NeuralNetwork.new_development.Layers;

import com.project.NeuralNetwork.new_development.Layers.base.Layer;
import com.project.NeuralNetwork.new_development.Layers.base.LayerImpl;
import com.project.NeuralNetwork.new_development.Layers.base.Layers;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.user_function.UserFunction;

/**
 * Скрытый слой нейросети
 */
public class HiddenLayer extends LayerImpl implements Layer {
    public HiddenLayer(int neuronsCount, int inputsCount) {
        super(Layers.HIDDEN_LAYER, neuronsCount, inputsCount);
    }

    public HiddenLayer(int neuronsCount, int inputsCount, Functions funcType) {
        super(Layers.HIDDEN_LAYER, neuronsCount, inputsCount, funcType);
    }

    public HiddenLayer(int neuronsCount, int inputsCount, Functions funcType, double a) {
        super(Layers.HIDDEN_LAYER, neuronsCount, inputsCount, funcType, a);
    }

    public HiddenLayer(int neuronsCount, int inputsCount, UserFunction userFunction) {
        super(Layers.HIDDEN_LAYER, neuronsCount, inputsCount, userFunction);
    }
}
