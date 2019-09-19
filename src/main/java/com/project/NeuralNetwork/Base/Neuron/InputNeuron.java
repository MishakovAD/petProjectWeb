package com.project.NeuralNetwork.Base.Neuron;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;

import java.util.Arrays;

public class InputNeuron extends Neuron {

    public InputNeuron(int countOutputs) {
        super(1, countOutputs);
        double[] weights = new double[countOutputs];
        weights = Arrays.stream(weights).map(weight -> weight = 1.0).toArray();
        this.setWeights(weights);
    }

}
