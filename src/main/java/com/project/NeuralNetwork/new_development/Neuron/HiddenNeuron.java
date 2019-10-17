package com.project.NeuralNetwork.new_development.Neuron;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.base.NeuronImpl;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

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
}
