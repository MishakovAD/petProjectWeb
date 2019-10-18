package com.project.NeuralNetwork.new_development;

import com.project.NeuralNetwork.new_development.NeuralNetwork.NeuralNetwork;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.Teacher;

import java.time.LocalTime;

public class Test {
    public static void main(String[] args) {
        double[] inp1 = new double[2];
        inp1[0] = 1.0;
        inp1[1] = 0.0;
        double[] inp2 = new double[2];
        inp2[0] = 0.0;
        inp2[1] = 0.0;
        double[] inp3 = new double[2];
        inp3[0] = 1.0;
        inp3[1] = 1.0;
        double[] inp4 = new double[2];
        inp4[0] = 0.0;
        inp4[1] = 1.0;
        NeuralNetwork net2 = new NeuralNetwork(2, 2, 5, 1);
        Teacher trainer = new Teacher(net2.getFunctionType(), 10);

        int globalCounter = 0;
        int counter = 0;
        System.out.println(LocalTime.now());
        while (globalCounter < 10000000) {
            if (counter == 4) {
                counter = 0;
                globalCounter++;
            }
            double[] ref1 = new double[1];
            double[] ref2 = new double[1];
            ref1[0] = 1.0;
            ref2[0] = 0.0;
            if (counter == 0) {
                net2.setInputData(inp1);
                trainer.calculateDeltaOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 1) {
                net2.setInputData(inp2);
                trainer.calculateDeltaOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 2) {
                net2.setInputData(inp3);
                trainer.calculateDeltaOutput(ref1, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 3) {
                net2.setInputData(inp4);
                trainer.calculateDeltaOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            }
            counter++;
        }
        System.out.println(LocalTime.now());
        System.out.println();
    }
}
