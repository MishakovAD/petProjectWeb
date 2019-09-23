package com.project.NeuralNetwork.OldBase.Layers;

import com.project.NeuralNetwork.OldBase.Neuron.InputNeuron;

public class InputLayer {
    private int counterNeurons;
    private InputNeuron[] inputNeuronsLayer;

    public InputLayer(int counterNeurons, int countOutputs) {
        this.counterNeurons = counterNeurons;
        this.inputNeuronsLayer = new InputNeuron[counterNeurons];
        for (int i = 0; i < counterNeurons; i++) {
            this.inputNeuronsLayer[i] = new InputNeuron(countOutputs);
        }
    }

    public InputNeuron[] getInputNeurons() {
        return this.inputNeuronsLayer;
    }

    public void setInputNeuronsLayer(InputNeuron[] inputNeuronsLayer) {
        this.inputNeuronsLayer = inputNeuronsLayer;
    }
}
