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
        double[] inp4 = new double[2];
        inp4[0] = 0.0;
        inp4[1] = 1.0;
        System.out.println(LocalTime.now());
        NeuralNetwork net2 = new NeuralNetwork(2, 3, 3, 1);
        System.out.println(LocalTime.now());
        Trainer trainer = new Trainer(1, 10);
        int counter = 0;
        int globalCounter = 0;
        while ((trainer.getError() > 0.02 || trainer.getError() == 0) && globalCounter < 10) {
            if (counter == 4) {
                counter = 0;
                globalCounter++;
            }
            double[] ref1 = new double[1];
            double[] ref2 = new double[1];
            ref1[0] = 1.0;
            ref2[0] = 0.0;
            if (counter == 0) {
                net2.setInputData(inp);
                trainer.calculateError(ref1[0], net2.getOutputs()[0], 0);
                trainer.calculateDeltaForOutput(ref1, net2.getOutputLayer());
                trainer.calculateDeltaForHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 1) {
                net2.setInputData(inp2);
                trainer.calculateError(ref2[0], net2.getOutputs()[0], 0);
                trainer.calculateDeltaForOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaForHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 2) {
                net2.setInputData(inp3);
                trainer.calculateError(ref2[0], net2.getOutputs()[0], 0);
                trainer.calculateDeltaForOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaForHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 3) {
                net2.setInputData(inp4);
                trainer.calculateError(ref1[0], net2.getOutputs()[0], 0);
                trainer.calculateDeltaForOutput(ref1, net2.getOutputLayer());
                trainer.calculateDeltaForHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            }
            net2.correctWeightsOfNetwork();

            counter++;
        }

        //net2.setInputData(inp2);
        System.out.println(LocalTime.now());
    }
}
