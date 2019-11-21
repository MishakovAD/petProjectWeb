package com.project.NeuralNetwork.new_development.Neuron.loss_function.functions;

import com.project.NeuralNetwork.new_development.Neuron.loss_function.LossFunc;

//Среднеквадратичная ошибка
//TODO: реализовать
public class MSE_function implements LossFunc {
    @Override
    public double calculateLossF(double[] ideal, double[] result) {
        return 0;
    }

    @Override
    public double calculateDerivationLossF(double[] ideal, double[] result, int numOfNeuron) {
        return 0;
    }
}
