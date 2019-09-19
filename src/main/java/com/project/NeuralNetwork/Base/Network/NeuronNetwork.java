package com.project.NeuralNetwork.Base.Network;

import com.project.NeuralNetwork.Base.Layers.HiddenLayer;
import com.project.NeuralNetwork.Base.Layers.InputLayer;
import com.project.NeuralNetwork.Base.Layers.OutputLayer;
import com.project.NeuralNetwork.Base.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.Base.Neuron.InputNeuron;

public class NeuronNetwork {
    private InputLayer inputLayer;
    private HiddenLayer[] hiddenLayer;
    private OutputLayer outputLayer;
    private int counterHiddenLayers;
    private int counterInputNeurons;
    private int counterHiddenNeurons;
    private int counterOutputNeurons;

    public NeuronNetwork(int counterHiddenLayers, int counterInputNeurons, int counterHiddenNeurons, int counterOutputNeurons) {
        this.counterHiddenLayers = counterHiddenLayers;
        this.counterInputNeurons = counterInputNeurons;
        this.counterHiddenNeurons = counterHiddenNeurons;
        this.counterOutputNeurons = counterOutputNeurons;
        this.inputLayer = new InputLayer(counterInputNeurons, counterHiddenNeurons);
        this.hiddenLayer = new HiddenLayer[counterHiddenLayers];
        this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, counterHiddenNeurons);
        this.hiddenLayer[counterHiddenLayers - 1] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, counterOutputNeurons);
        for (int i = 1; i < counterHiddenLayers - 1; i++) {
            this.hiddenLayer[i] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, counterHiddenNeurons);
        }
        this.outputLayer = new OutputLayer(counterHiddenNeurons, counterOutputNeurons);
    }

    public void setInputs(double[] inputs) {
        int sizeInput = this.counterInputNeurons;
        for (int i = 0; i < sizeInput; i++) {
            this.inputLayer.;
        }
    }

    public double[] getResult() {
        return this.outputLayer.getOutputResult();
    }
}
