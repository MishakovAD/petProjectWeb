package com.project.NeuralNetwork;

import com.project.NeuralNetwork.Base.Network.NeuronNetwork;
import com.project.NeuralNetwork.BaseVer2.Network.NeuralNetwork;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        double[] inp = new double[2];
        inp[0] = 1.0;
        inp[1] = 0.0;
        double[] inp2 = new double[2];
        inp2[0] = 0.0;
        inp2[1] = 0.0;
        double[] inp3 = new double[2];
        inp3[0] = 1.0;
        inp3[1] = 1.0;
        System.out.println(LocalTime.now());
        NeuralNetwork net2 = new NeuralNetwork(inp, 1, 5, 1);
        System.out.println(LocalTime.now());
        //net2.setInputData(inp2);
        System.out.println(LocalTime.now());
    }
}
