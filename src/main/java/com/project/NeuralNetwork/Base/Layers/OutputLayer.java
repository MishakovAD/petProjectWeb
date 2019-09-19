package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;

public class OutputLayer {
    private int counterNeurons;
    private Neuron[] neuronsLayer;

    public OutputLayer(int counterNeurons, int countOutputs) {
        this.counterNeurons = counterNeurons;
        this.neuronsLayer = new Neuron[counterNeurons];
        for (int i = 0; i < counterNeurons; i++) {
            neuronsLayer[i] = new Neuron(1, countOutputs);
        }
    }
}
