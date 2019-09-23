package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Neurons.InputNeuron;

public class InputLayer {
    private int neuronsCount;
    private double[] data;
    private InputNeuron[] inputNeuronsArray;

    public InputLayer(int neuronsCount) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neuronsCount = neuronsCount;
        this.inputNeuronsArray = new InputNeuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            InputNeuron inputNeuron = new InputNeuron(0);
            this.inputNeuronsArray[i] = inputNeuron;
        }
    }

    public InputLayer(int neuronsCount, double[] data) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neuronsCount = neuronsCount;
        inputNeuronsArray = new InputNeuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            InputNeuron inputNeuron;
            if (data.length < i) {
                inputNeuron = new InputNeuron(0);
            } else {
                inputNeuron = new InputNeuron(data[i]);
            }
            inputNeuronsArray[i] = inputNeuron;
        }
    }

    //TODO: Сделать конструктор без входных данных, а так же методы сеттера для них

    public InputNeuron[] getInputNeuronsArray() {
        return inputNeuronsArray;
    }

    public InputNeuron getInputNeuron(int index) {
        return inputNeuronsArray[index];
    }

    public int getNeuronsCount() {
        return neuronsCount;
    }
}
