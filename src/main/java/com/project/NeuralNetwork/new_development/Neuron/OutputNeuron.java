package com.project.NeuralNetwork.new_development.Neuron;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.base.NeuronImpl;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

public class OutputNeuron extends NeuronImpl implements Neuron {
    public OutputNeuron(int inputsCount) {
        super(inputsCount);
    }

    public OutputNeuron(int inputsCount, Functions function) {
        super(inputsCount, function);
    }

    public OutputNeuron(int inputsCount, Functions function, double a) {
        super(inputsCount, function, a);
    }
}
