package com.project.NeuralNetwork.BaseVer2.Layers;

import com.project.NeuralNetwork.BaseVer2.Neurons.HiddenNeuron;
import org.apache.commons.lang3.ArrayUtils;

import java.util.LinkedList;
import java.util.List;

public class HiddenLayer {
    private int neuronsCount; //Количество нейронов в каждом слое
    private int inputCount; //Количество входов каждого нейрона
    private HiddenNeuron[] hiddenNeuronsArray; //Нейроны скрытого слоя
    private double[] data; //Данные с предыдущего слоя

    public HiddenLayer(int neuronsCount, int inputCount, double[] data) {
        if (neuronsCount < 1 || inputCount < 1) {
            neuronsCount = 5;
            inputCount = 1;
        }
        if (inputCount < data.length) {
//            throw new Exception(); //TODO: Сделать рабочий вариант.
        }
        this.neuronsCount = neuronsCount;
        this.inputCount = inputCount;
        this.hiddenNeuronsArray = new HiddenNeuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            HiddenNeuron hiddenNeuron = new HiddenNeuron(inputCount, data);
            hiddenNeuronsArray[i] = hiddenNeuron;
        }
    }

    public HiddenNeuron[] getHiddenNeuronsArray() {
        return hiddenNeuronsArray;
    }

    public HiddenNeuron getHiddenNeuron(int index) {
        return hiddenNeuronsArray[index];
    }

    public int getNeuronsCount() {
        return neuronsCount;
    }

    public List<Double[]> getWeightsList() {
        List<Double[]> hiddenWeights = new LinkedList<>();
        for (int i = 0; i < this.hiddenNeuronsArray.length; i++) {
            double[] weights = getHiddenNeuron(i).getWeights();
            hiddenWeights.add(ArrayUtils.toObject(weights));
        }
        return hiddenWeights;
    }

    public void setWeights(List<Double[]> weights) {
        for (int i = 0; i < this.hiddenNeuronsArray.length; i++) {
            this.hiddenNeuronsArray[i].setWeights(ArrayUtils.toPrimitive(weights.get(i)));
        }
    }

    public void setInputs(double[] inputs) {
        for (int i = 0; i < this.hiddenNeuronsArray.length; i++) {
            this.hiddenNeuronsArray[i].setInputs(inputs);
        }
    }
}
