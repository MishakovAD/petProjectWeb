package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Neurons.OutputNeuron;
import org.apache.commons.lang3.ArrayUtils;

import java.util.LinkedList;
import java.util.List;

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

    public OutputNeuron getOutputNeuron(int index) {
        return outputNeuronsArray[index];
    }

    public int getNeuronsCount() {
        return neuronsCount;
    }

    public List<Double[]> getWeightsList() {
        List<Double[]> outputWeights = new LinkedList<>();
        for (int i = 0; i < this.outputNeuronsArray.length; i++) {
            double[] weights = getOutputNeuron(i).getWeights();
            outputWeights.add(ArrayUtils.toObject(weights));
        }
        return outputWeights;
    }

    public void setWeights(List<Double[]> weights) {
        for (int i = 0; i < this.outputNeuronsArray.length; i++) {
            this.outputNeuronsArray[i].setWeights(ArrayUtils.toPrimitive(weights.get(i)));
        }
    }

    public void setInputs(double[] inputs) {
        for (int i = 0; i < this.outputNeuronsArray.length; i++) {
            this.outputNeuronsArray[i].setInputs(inputs);
        }
    }
}
