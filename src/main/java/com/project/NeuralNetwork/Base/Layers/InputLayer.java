package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Neuron.InputNeuron;
import com.project.NeuralNetwork.Base.Neuron.base.Neuron;

public class InputLayer {
    private int counterNeurons;
    private InputNeuron[] inputNeuronsLayer;

    public InputLayer(int counterNeurons, int countOutputs) {
        this.counterNeurons = counterNeurons;
        this.inputNeuronsLayer = new InputNeuron[counterNeurons];
        for (int i = 0; i < counterNeurons; i++) {
            inputNeuronsLayer[i] = new InputNeuron(countOutputs);
        }
    }

    public Neuron[] getInputNeuronsLayer() {
        return inputNeuronsLayer;
    }

    public void setInputNeuronsLayer(InputNeuron[] inputNeuronsLayer) {
        this.inputNeuronsLayer = inputNeuronsLayer;
    }
}
