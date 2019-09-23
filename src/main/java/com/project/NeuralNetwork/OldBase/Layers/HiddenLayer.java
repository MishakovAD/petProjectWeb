package com.project.NeuralNetwork.OldBase.Layers;

import com.project.NeuralNetwork.OldBase.Neuron.HiddenNeuron;

public class HiddenLayer {
    private int counterNeurons;
    private int counterNeuronsPreviousLayer;
    private int counterNeuronsNextLayer;
    private HiddenNeuron[] hiddenNeuronsLayer;

    public HiddenLayer(int counterNeurons, int counterNeuronsPreviousLayer, int counterNeuronsNextLayer) {
        this.counterNeurons = counterNeurons;
        this.counterNeuronsNextLayer = counterNeuronsNextLayer;
        this.counterNeuronsPreviousLayer = counterNeuronsPreviousLayer;
        this.hiddenNeuronsLayer = new HiddenNeuron[counterNeurons];
        for (int i = 0; i < counterNeurons; i++) {
            this.hiddenNeuronsLayer[i] = new HiddenNeuron(counterNeuronsPreviousLayer, counterNeuronsNextLayer);
        }
    }

    public HiddenNeuron[] getHiddenNeurons() {
        return this.hiddenNeuronsLayer;
    }
}
