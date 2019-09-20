package com.project.NeuralNetwork.Base.Network;

import com.project.NeuralNetwork.Base.Layers.HiddenLayer;
import com.project.NeuralNetwork.Base.Layers.InputLayer;
import com.project.NeuralNetwork.Base.Layers.OutputLayer;

public class NeuronNetwork {
    private InputLayer inputLayer;
    private HiddenLayer[] hiddenLayer;
    private OutputLayer outputLayer;
    private int counterHiddenLayers;
    private int counterInputNeurons;
    private int counterHiddenNeurons;
    private int counterOutputNeurons;

    public NeuronNetwork(int counterHiddenLayers, int counterInputNeurons, int counterHiddenNeurons, int counterOutputNeurons) {
        if (counterHiddenLayers < 1 || counterHiddenNeurons < 1) {
            counterHiddenLayers = 1;
            counterHiddenNeurons = 5;
        }
        this.counterHiddenLayers = counterHiddenLayers;
        this.counterInputNeurons = counterInputNeurons;
        this.counterHiddenNeurons = counterHiddenNeurons;
        this.counterOutputNeurons = counterOutputNeurons;
        this.inputLayer = new InputLayer(counterInputNeurons, counterHiddenNeurons);
        this.hiddenLayer = new HiddenLayer[counterHiddenLayers];
        if (counterHiddenLayers > 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, counterHiddenNeurons);
            this.hiddenLayer[counterHiddenLayers - 1] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, counterOutputNeurons);
            for (int i = 1; i < counterHiddenLayers - 1; i++) {
                this.hiddenLayer[i] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, counterHiddenNeurons);
            }
        } else if (counterHiddenLayers == 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, counterOutputNeurons);
        }
        this.outputLayer = new OutputLayer(counterHiddenNeurons, counterOutputNeurons);
    }

    public void setInputs(double[] inputs) {
        int sizeInput = this.counterInputNeurons;
        for (int i = 0; i < sizeInput; i++) {
            this.inputLayer.getInputNeurons()[i].setInput(0, inputs[i]);
        }

        //Связь входного слоя с первым скрытым (output предыдущего -> input следующего)
        int inputLayerNeuronsCount = this.inputLayer.getInputNeurons().length;
        for (int i = 0; i < inputLayerNeuronsCount; i++) {
            int outputsInputNeuronsCount = this.inputLayer.getInputNeurons()[i].getOutputs().length;
            for (int j = 0; j < outputsInputNeuronsCount; j++) {
                this.hiddenLayer[0].getHiddenNeurons()[j].setInput(i, this.inputLayer.getInputNeurons()[i].getOutputs()[j]);
            }
        }

        //Связь между скрытыми слоями
        int hiddenLayersCount = this.hiddenLayer.length;
        for (int countHLayers = 1; countHLayers < hiddenLayersCount; countHLayers++) {
            int hiddenLayerNeuronsCount = this.hiddenLayer[countHLayers].getHiddenNeurons().length;
            for (int i = 0; i < hiddenLayerNeuronsCount; i++) {
                int outputsHiddenNeuronsCount = this.hiddenLayer[countHLayers - 1].getHiddenNeurons()[i].getOutputs().length;
                for (int j = 0; j < outputsHiddenNeuronsCount; j++) {
                    this.hiddenLayer[countHLayers].getHiddenNeurons()[j].setInput(i, this.hiddenLayer[countHLayers - 1].getHiddenNeurons()[i].getOutputs()[j]);
                }
            }
        }

        //Связь между последним скрытым слоем и выходными нейронами
        int hiddenLayerLastNeuronsCount = this.hiddenLayer[hiddenLayersCount-1].getHiddenNeurons().length;
        for (int i = 0; i < hiddenLayerLastNeuronsCount; i++) {
            int outputsHiddenNeuronsCount = this.hiddenLayer[hiddenLayersCount - 1].getHiddenNeurons()[i].getOutputs().length;
            for (int j = 0; j < outputsHiddenNeuronsCount; j++) {
                this.outputLayer.getOutputNeurons()[j].setInput(i, this.hiddenLayer[hiddenLayersCount - 1].getHiddenNeurons()[i].getOutputs()[j]);
            }
        }
    }

    public double[] getResult() {
        return this.outputLayer.getOutputResult();
    }
}
