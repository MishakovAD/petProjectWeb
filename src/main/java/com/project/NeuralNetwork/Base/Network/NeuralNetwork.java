package com.project.NeuralNetwork.Base.Network;

import com.project.NeuralNetwork.Base.Layers.HiddenLayer;
import com.project.NeuralNetwork.Base.Layers.InputLayer;
import com.project.NeuralNetwork.Base.Layers.OutputLayer;

import java.util.LinkedList;
import java.util.List;
//TODO: добавить нейрон смещения(активации) в каждый уровень.
//TODO: добавить добавление предыдущих уровней в том случае, если скрытый слой 1
//а так же, для первого скрытого слоя, чтобы он работал со входным слоем.
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

            double[] inputDataFromInputLayer = this.hiddenLayer[0].getHiddenNeuron(0).getInputs();
            this.hiddenLayer[0].setPreviousInputs(inputDataFromInputLayer);

            for (int i = 1; i < counterHiddenLayers; i++) {
                //Для обратного распространения ошибки. Возможно в будущем будет смысл записывать и значения.
                for(int neurIndex = 0; neurIndex < this.hiddenLayer[i-1].getNeuronsCount(); neurIndex++) {
                    this.hiddenLayer[i-1].getHiddenNeuron(neurIndex).setCountOutputs(counterHiddenNeurons);
                }

                double[] outputDataFromPreviousHiddenLayer = new double[counterHiddenNeurons];
                for (int j = 0; j < counterHiddenNeurons; j++) {
                    outputDataFromPreviousHiddenLayer[j] = this.hiddenLayer[i - 1].getHiddenNeuron(j).getOutput();
                }
                this.hiddenLayer[i] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, outputDataFromPreviousHiddenLayer);
                double[] inputDataFromPreviousHiddenLayer = this.hiddenLayer[i - 1].getHiddenNeuron(0).getInputs();
                this.hiddenLayer[i].setPreviousInputs(inputDataFromPreviousHiddenLayer);
            }
            //Для обратного распространения ошибки. Возможно в будущем будет смысл записывать и значения.
            for(int neurIndex = 0; neurIndex < this.hiddenLayer[counterHiddenLayers-1].getNeuronsCount(); neurIndex++) {
                this.hiddenLayer[counterHiddenLayers-1].getHiddenNeuron(neurIndex).setCountOutputs(counterOutputNeurons);
            }

        } else if (counterHiddenLayers == 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, outputDataFromInputLayer);
            double[] inputDataFromInputLayer = this.hiddenLayer[0].getHiddenNeuron(0).getInputs();
            this.hiddenLayer[0].setPreviousInputs(inputDataFromInputLayer);
            //Для обратного распространения ошибки. Возможно в будущем будет смысл записывать и значения.
            for(int neurIndex = 0; neurIndex < this.hiddenLayer[0].getNeuronsCount(); neurIndex++) {
                this.hiddenLayer[0].getHiddenNeuron(neurIndex).setCountOutputs(counterOutputNeurons);
            }

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
            double[] inputDataFromInputLayer = this.hiddenLayer[0].getHiddenNeuron(0).getInputs();
            this.hiddenLayer[0].setPreviousInputs(inputDataFromInputLayer);
            for (int i = 1; i < counterHiddenLayers; i++) {
                //Для обратного распространения ошибки. Возможно в будущем будет смысл записывать и значения.
                for(int neurIndex = 0; neurIndex < this.hiddenLayer[i-1].getNeuronsCount(); neurIndex++) {
                    this.hiddenLayer[i-1].getHiddenNeuron(neurIndex).setCountOutputs(counterHiddenNeurons);
                }

                double[] outputDataFromPreviousHiddenLayer = new double[counterHiddenNeurons];
                for (int j = 0; j < counterHiddenNeurons; j++) {
                    outputDataFromPreviousHiddenLayer[j] = this.hiddenLayer[i - 1].getHiddenNeuron(j).getOutput();
                }
                this.hiddenLayer[i] = new HiddenLayer(counterHiddenNeurons, counterHiddenNeurons, outputDataFromPreviousHiddenLayer);
                double[] inputDataFromPreviousHiddenLayer = this.hiddenLayer[i - 1].getHiddenNeuron(0).getInputs();
                this.hiddenLayer[i].setPreviousInputs(inputDataFromPreviousHiddenLayer);
            }
            //Для обратного распространения ошибки. Возможно в будущем будет смысл записывать и значения.
            for(int neurIndex = 0; neurIndex < this.hiddenLayer[counterHiddenLayers-1].getNeuronsCount(); neurIndex++) {
                this.hiddenLayer[counterHiddenLayers-1].getHiddenNeuron(neurIndex).setCountOutputs(counterOutputNeurons);
            }

        } else if (counterHiddenLayers == 1) {
            this.hiddenLayer[0] = new HiddenLayer(counterHiddenNeurons, counterInputNeurons, outputDataFromInputLayer);
            double[] inputDataFromInputLayer = this.hiddenLayer[0].getHiddenNeuron(0).getInputs();
            this.hiddenLayer[0].setPreviousInputs(inputDataFromInputLayer);

            //Для обратного распространения ошибки. Возможно в будущем будет смысл записывать и значения.
            for(int neurIndex = 0; neurIndex < this.hiddenLayer[0].getNeuronsCount(); neurIndex++) {
                this.hiddenLayer[0].getHiddenNeuron(neurIndex).setCountOutputs(counterOutputNeurons);
            }
        }
        double[] outputDataFromLastHiddenLayer = new double[counterHiddenNeurons];
        for (int j = 0; j < counterHiddenNeurons; j++) {
            outputDataFromLastHiddenLayer[j] = this.hiddenLayer[counterHiddenLayers - 1].getHiddenNeuron(j).getOutput();
        }
        this.outputLayer = new OutputLayer(counterOutputNeurons, counterHiddenNeurons, outputDataFromLastHiddenLayer);
    }

    public void setInputData(double[] data) {
        //TODO: сделать проверку на размер входных данных и уже созданную сеть. Если разный, сообщить.
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

    public double[] getOutputs() {
        double[] outputs = new double[this.counterOutputNeurons];
        for (int i = 0; i < this.counterOutputNeurons; i++) {
            outputs[i] = this.outputLayer.getOutputNeuron(i).getOutput();
        }
        return outputs;
    }

    public OutputLayer getOutputLayer() {
        return this.outputLayer;
    }

    public HiddenLayer[] getHiddenLayerArray() {
        return hiddenLayer;
    }

    public void correctWeightsOfNetwork() {
        outputLayer.correctWeightsOfOutputLayer();
        for (int i = 0; i < counterHiddenLayers; i++) {
            hiddenLayer[i].correctWeightsOfHiddenLayer();
        }
    }
}
