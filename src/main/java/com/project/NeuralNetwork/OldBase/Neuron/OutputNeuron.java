package com.project.NeuralNetwork.OldBase.Neuron;

import com.project.NeuralNetwork.OldBase.Neuron.base.Neuron;

import java.util.Arrays;

public class OutputNeuron extends Neuron {

    public OutputNeuron(int countInputs) {
        super(countInputs, 1);
        double[] weights = new double[1];
        weights = Arrays.stream(weights).map(weight -> weight = 1.0).toArray();
        this.setWeights(weights);
    }
}