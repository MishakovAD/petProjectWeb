package com.project.NeuralNetwork;

import com.project.NeuralNetwork.Base.Layers.InputLayer;
import com.project.NeuralNetwork.Base.Network.NeuronNetwork;
import com.project.NeuralNetwork.Base.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.Base.Neuron.InputNeuron;
import com.project.NeuralNetwork.Base.Neuron.OutputNeuron;
import com.project.NeuralNetwork.Base.Neuron.base.Neuron;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        NeuronNetwork net = new NeuronNetwork(2, 2, 5, 1);
        double[] inp = new double[2];
        inp[0] = 1.0;
        inp[1] = 0.0;
        net.setInputs(inp);
        net.getResult();
        InputNeuron input1 = new InputNeuron(5);
        InputNeuron input2 = new InputNeuron(5);
        HiddenNeuron hidden1 = new HiddenNeuron(2, 1);
        HiddenNeuron hidden2 = new HiddenNeuron(2, 1);
        HiddenNeuron hidden3 = new HiddenNeuron(2, 1);
        HiddenNeuron hidden4 = new HiddenNeuron(2, 1);
        HiddenNeuron hidden5 = new HiddenNeuron(2, 1);
        OutputNeuron output = new OutputNeuron(5);
        input1.setInput(1);
        input2.setInput(0);
        double outFromInput1 = input1.getOutput();
        double outFromInput2 = input2.getOutput();

        Neuron neuron = new Neuron(5, 5);
        neuron.setInputs(Arrays.stream(neuron.getInputs()).map(input -> input += Math.random()).toArray());
        neuron.getOutput();
        System.out.println(neuron);
        InputLayer inpL = new InputLayer(10, 10);
        Arrays.stream(inpL.getInputNeuronsLayer()).forEach(neuron1 -> System.out.println(neuron1));
        System.out.println();
    }
}
