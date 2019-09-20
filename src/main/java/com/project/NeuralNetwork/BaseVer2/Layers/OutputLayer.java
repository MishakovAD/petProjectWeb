package com.project.NeuralNetwork.BaseVer2.Layers;

import com.project.NeuralNetwork.BaseVer2.Neurons.OutputNeuron;

public class OutputLayer {
    private int neuronsCount;
    private int inputCount;
    private OutputNeuron[] outputNeuronsArray;
    private double[] data;

    public OutputLayer(int neuronsCount, int inputCount, double[] data) {
        if (neuronsCount < 1 || inputCount < 1) {
            neuronsCount = 5;
            inputCount = 1;
        }
        if (inputCount < data.length) {
//            throw new Exception(); //TODO: Сделать рабочий вариант.
        }
        this.neuronsCount = neuronsCount;
        this.inputCount = inputCount;
        this.outputNeuronsArray = new OutputNeuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            OutputNeuron outputNeuron = new OutputNeuron(inputCount, data);
            outputNeuronsArray[i] = outputNeuron;
        }
    }

    public OutputNeuron[] getOutputNeuronsArray() {
        return outputNeuronsArray;
    }

    public OutputNeuron getHiddenNeuron(int index) {
        return outputNeuronsArray[index];
    }

    public int getNeuronsCount() {
        return neuronsCount;
    }
}
