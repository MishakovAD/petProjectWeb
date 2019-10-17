package com.project.NeuralNetwork.new_development;

import com.project.NeuralNetwork.new_development.Layers.HiddenLayer;
import com.project.NeuralNetwork.new_development.Layers.InputLayer;
import com.project.NeuralNetwork.new_development.Layers.OutputLayer;
import com.project.NeuralNetwork.new_development.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.new_development.Neuron.InputNeuron;
import com.project.NeuralNetwork.new_development.Neuron.OutputNeuron;
import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.base.NeuronImpl;

public class Test {
    public static void main(String[] args) {
        Neuron n = new NeuronImpl(5);
        Neuron input = new InputNeuron();
        input.setInput(1);
        double[] inputs = new double[5];
        inputs[0] = 1;
        inputs[1] = 0;
        inputs[2] = 1;
        inputs[3] = 0;
        inputs[4] = 1;
        Neuron hidden = new HiddenNeuron(5);
        Neuron output = new OutputNeuron(5);
        hidden.setInputs(inputs);
        output.setInputs(inputs);

        InputLayer inputLayer = new InputLayer(5);
        inputLayer.setData(inputs);
        HiddenLayer hiddenLayer = new HiddenLayer(5, 5);
        OutputLayer outputLayer = new OutputLayer(5, 5);
        hiddenLayer.setData(inputs);
        outputLayer.setData(inputs);
        System.out.println();
    }
}
