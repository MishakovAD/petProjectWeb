package com.project.NeuralNetwork.Base.Neuron;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.base.NeuronImpl;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.Base.Neuron.function_activation.functions.user_function.UserFunction;

public class HiddenNeuron extends NeuronImpl implements Neuron {
    public HiddenNeuron(int inputsCount) {
        super(inputsCount);
    }

    public HiddenNeuron(int inputsCount, Functions function) {
        super(inputsCount, function);
    }

    public HiddenNeuron(int inputsCount, Functions function, double a) {
        super(inputsCount, function, a);
    }

    public HiddenNeuron(int inputsCount, UserFunction userFunction, DerivativeUserFunction derivativeUserFunction) {
        super(inputsCount, userFunction, derivativeUserFunction);
    }
}
