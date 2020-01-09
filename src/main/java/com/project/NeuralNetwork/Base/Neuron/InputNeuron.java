package com.project.NeuralNetwork.Base.Neuron;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.base.NeuronImpl;

public class InputNeuron extends NeuronImpl implements Neuron {

    public InputNeuron() {
        super(1);
        double[] weights = new double[1];
        weights[0] = 1;
        this.setWeights(weights);
    }
}
