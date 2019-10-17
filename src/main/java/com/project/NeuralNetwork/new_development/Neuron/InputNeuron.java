package com.project.NeuralNetwork.new_development.Neuron;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.base.NeuronImpl;

public class InputNeuron extends NeuronImpl implements Neuron {

    public InputNeuron() {
        super(1);
        double[] weights = new double[1];
        weights[0] = 1;
        this.setWeights(weights);
    }
}
