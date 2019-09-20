package com.project.NeuralNetwork.BaseVer2.Network;

import com.project.NeuralNetwork.BaseVer2.Layers.HiddenLayer;
import com.project.NeuralNetwork.BaseVer2.Layers.InputLayer;
import com.project.NeuralNetwork.BaseVer2.Layers.OutputLayer;

public class NeuralNetwork {
    private InputLayer inputLayer;
    private HiddenLayer[] hiddenLayer;
    private OutputLayer outputLayer;
    private int counterHiddenLayers;
    private int counterInputNeurons;
    private int counterHiddenNeurons;
    private int counterOutputNeurons;

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
}
