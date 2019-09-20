package com.project.NeuralNetwork.BaseVer2.Network;

import com.project.NeuralNetwork.BaseVer2.Layers.HiddenLayer;
import com.project.NeuralNetwork.BaseVer2.Layers.InputLayer;
import com.project.NeuralNetwork.BaseVer2.Layers.OutputLayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NeuralNetwork {
    private InputLayer inputLayer;
    private HiddenLayer[] hiddenLayer;
    private OutputLayer outputLayer;
    private int counterHiddenLayers;
    private int counterInputNeurons;
    private int counterHiddenNeurons;
    private int counterOutputNeurons;

    public NeuralNetwork(int counterInputNeurons, int counterHiddenLayers, int counterHiddenNeurons, int counterOutputNeurons) {
        if (counterHiddenLayers < 1 || counterHiddenNeurons < 1) {
            counterHiddenLayers = 1;
            counterHiddenNeurons = 5;
        }
        this.counterInputNeurons = counterInputNeurons;
        this.counterHiddenLayers = counterHiddenLayers;
        this.counterHiddenNeurons = counterHiddenNeurons;
        this.counterOutputNeurons = counterOutputNeurons;
        this.inputLayer = new InputLayer(counterInputNeurons);
        double[] outputDataFromInputLayer = new double[counterInputNeurons];
        for (int i = 0; i < counterInputNeurons; i++) {
            outputDataFromInputLayer[i] = this.inputLayer.getInputNeuron(i).getOutput();
        }
        this.hiddenLayer = new HiddenLayer[counterHiddenLayers];
        if (counterHiddenLayers > 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, outputDataFromInputLayer);
            for (int i = 1; i < counterHiddenLayers; i++) {
                double[] outputDataFromPreviousHiddenLayer = new double[counterHiddenNeurons];
                for (int j = 0; j < counterHiddenNeurons; j++) {
                    outputDataFromPreviousHiddenLayer[j] = this.hiddenLayer[i - 1].getHiddenNeuron(j).getOutput();
                }
                this.hiddenLayer[i] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, outputDataFromPreviousHiddenLayer);
            }
        } else if (counterHiddenLayers == 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, outputDataFromInputLayer);
        }
        double[] outputDataFromLastHiddenLayer = new double[counterHiddenNeurons];
        for (int j = 0; j < counterHiddenNeurons; j++) {
            outputDataFromLastHiddenLayer[j] = this.hiddenLayer[counterHiddenLayers - 1].getHiddenNeuron(j).getOutput();
        }
        this.outputLayer = new OutputLayer(counterOutputNeurons, counterHiddenNeurons, outputDataFromLastHiddenLayer);
    }

    public NeuralNetwork(double[] inputData, int counterHiddenLayers, int counterHiddenNeurons, int counterOutputNeurons) {
        if (counterHiddenLayers < 1 || counterHiddenNeurons < 1) {
            counterHiddenLayers = 1;
            counterHiddenNeurons = 5;
        }
        this.counterInputNeurons = inputData.length;
        this.counterHiddenLayers = counterHiddenLayers;
        this.counterHiddenNeurons = counterHiddenNeurons;
        this.counterOutputNeurons = counterOutputNeurons;
        this.inputLayer = new InputLayer(counterInputNeurons, inputData);
        double[] outputDataFromInputLayer = new double[counterInputNeurons];
        for (int i = 0; i < counterInputNeurons; i++) {
            outputDataFromInputLayer[i] = this.inputLayer.getInputNeuron(i).getOutput();
        }
        this.hiddenLayer = new HiddenLayer[counterHiddenLayers];
        if (counterHiddenLayers > 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, outputDataFromInputLayer);
            for (int i = 1; i < counterHiddenLayers; i++) {
                double[] outputDataFromPreviousHiddenLayer = new double[counterHiddenNeurons];
                for (int j = 0; j < counterHiddenNeurons; j++) {
                    outputDataFromPreviousHiddenLayer[j] = this.hiddenLayer[i - 1].getHiddenNeuron(j).getOutput();
                }
                this.hiddenLayer[i] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, outputDataFromPreviousHiddenLayer);
            }
        } else if (counterHiddenLayers == 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, outputDataFromInputLayer);
        }
        double[] outputDataFromLastHiddenLayer = new double[counterHiddenNeurons];
        for (int j = 0; j < counterHiddenNeurons; j++) {
            outputDataFromLastHiddenLayer[j] = this.hiddenLayer[counterHiddenLayers - 1].getHiddenNeuron(j).getOutput();
        }
        this.outputLayer = new OutputLayer(counterOutputNeurons, counterHiddenNeurons, outputDataFromLastHiddenLayer);
    }

    public void setInputData(double[] data) {
        for (int i = 0; i < this.inputLayer.getNeuronsCount(); i++) {
            this.inputLayer.getInputNeuron(i).setInput(data[i]);
        }
        double[] outputDataFromInputLayer = new double[counterInputNeurons];
        for (int i = 0; i < counterInputNeurons; i++) {
            outputDataFromInputLayer[i] = this.inputLayer.getInputNeuron(i).getOutput();
        }
        if (counterHiddenLayers > 1) {
            this.hiddenLayer[0].setInputs(outputDataFromInputLayer);
            for (int i = 1; i < counterHiddenLayers; i++) {
                double[] outputDataFromPreviousHiddenLayer = new double[counterHiddenNeurons];
                for (int j = 0; j < counterHiddenNeurons; j++) {
                    outputDataFromPreviousHiddenLayer[j] = this.hiddenLayer[i - 1].getHiddenNeuron(j).getOutput();
                }
                this.hiddenLayer[i].setInputs(outputDataFromPreviousHiddenLayer);
            }
        } else if (counterHiddenLayers == 1) {
            this.hiddenLayer[0].setInputs(outputDataFromInputLayer);
        }
        double[] outputDataFromLastHiddenLayer = new double[counterHiddenNeurons];
        for (int j = 0; j < counterHiddenNeurons; j++) {
            outputDataFromLastHiddenLayer[j] = this.hiddenLayer[counterHiddenLayers - 1].getHiddenNeuron(j).getOutput();
        }
        this.outputLayer.setInputs(outputDataFromLastHiddenLayer);
    }

    public List<Double[]> getHiddenWeights() {
        List<Double[]> hiddenWeight = new LinkedList<>();
        for (int i = 0; i < this.hiddenLayer.length; i++) {
            hiddenWeight.addAll(this.hiddenLayer[i].getWeightsList());
        }
        return hiddenWeight;
    }

    public List<Double[]> getOutputWeights() {
        List<Double[]> outputWeight = this.outputLayer.getWeightsList();
        return outputWeight;
    }
}
