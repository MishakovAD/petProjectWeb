package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Layers.base.Layer;
import com.project.NeuralNetwork.Base.Layers.base.LayerImpl;
import com.project.NeuralNetwork.Base.Layers.base.Layers;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.Base.Neuron.function_activation.functions.user_function.UserFunction;

/**
 * Выходной слой нейросети
 */
public class OutputLayer extends LayerImpl implements Layer {
    public OutputLayer(int neuronsCount, int inputsCount) {
        super(Layers.OUTPUT_LAYER, neuronsCount, inputsCount);
    }

    public OutputLayer(int neuronsCount, int inputsCount, Functions funcType) {
        super(Layers.OUTPUT_LAYER, neuronsCount, inputsCount, funcType);
    }

    public OutputLayer(int neuronsCount, int inputsCount, Functions funcType, double a) {
        super(Layers.OUTPUT_LAYER, neuronsCount, inputsCount, funcType, a);
    }

    public OutputLayer(int neuronsCount, int inputsCount, UserFunction userFunction, DerivativeUserFunction derivativeUserFunction) {
        super(Layers.OUTPUT_LAYER, neuronsCount, inputsCount, userFunction, derivativeUserFunction);
    }
}
