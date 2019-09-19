package com.project.NeuralNetwork;

import com.project.NeuralNetwork.Base.Layers.InputLayer;
import com.project.NeuralNetwork.Base.Neuron;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Neuron neuron = new Neuron(5);
        neuron.setInputs(Arrays.stream(neuron.getInputs()).map(input -> input += Math.random()).toArray());
        neuron.getOutput();
        System.out.println(neuron);
        InputLayer inpL = new InputLayer(10);
        Arrays.stream(inpL.getNeuronsLayer()).forEach(neuron1 -> System.out.println(neuron1));
        System.out.println();
    }
}
