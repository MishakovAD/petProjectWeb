package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Neuron.OutputNeuron;

public class OutputLayer {
    private int counterNeurons;
    private int counterPreviousNeurons;
    private OutputNeuron[] outputNeuronsLayer;

    public OutputLayer(int counterPreviousNeurons, int counterNeurons) {
        this.counterNeurons = counterNeurons;
        this.outputNeuronsLayer = new OutputNeuron[counterNeurons];
        for (int i = 0; i < counterNeurons; i++) {
            outputNeuronsLayer[i] = new OutputNeuron(counterPreviousNeurons);
        }
    }

    public double[] getOutputResult() {
        double[] result = new double[this.counterNeurons];
        for (int i = 0; i < this.counterNeurons; i++) {
            result[i] = this.outputNeuronsLayer[i].getOutput();
        }
        return result;
    }
}
