package com.project.NeuralNetwork;

import com.project.NeuralNetwork.Base.Network.NeuralNetwork;
import com.project.NeuralNetwork.Base.Training.Trainer;

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
        NeuralNetwork net2 = new NeuralNetwork(inp, 2, 3, 2);
        System.out.println(LocalTime.now());
        Trainer trainer = new Trainer(1, 10);
        trainer.calculateError(1, net2.getOutputs()[0], 0);
        double[] ref = new double[2];
        ref[0] = 1.0;
        ref[1] = 1.0;
        trainer.calculateDeltaForOutput(ref, net2.getOutputLayer());
        trainer.calculateDeltaForHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
        //net2.setInputData(inp2);
        System.out.println(LocalTime.now());
    }
}
