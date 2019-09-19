package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Neuron;

public class InputLayer {
    private int counterNeurons;
    private Neuron[] neuronsLayer;

    public InputLayer(int counterNeurons) {
        this.counterNeurons = counterNeurons;
        this.neuronsLayer = new Neuron[counterNeurons];
        for (int i = 0; i < counterNeurons; i++) {
            neuronsLayer[i] = new Neuron(1);
        }
    }

    public Neuron[] getNeuronsLayer() {
        return neuronsLayer;
    }

    public void setNeuronsLayer(Neuron[] neuronsLayer) {
        this.neuronsLayer = neuronsLayer;
    }
}
