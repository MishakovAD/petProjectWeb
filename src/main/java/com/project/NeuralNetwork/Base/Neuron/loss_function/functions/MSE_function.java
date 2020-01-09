package com.project.NeuralNetwork.Base.Neuron.loss_function.functions;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.loss_function.LossFunc;

//Среднеквадратичная ошибка
public class MSE_function implements LossFunc {

    @Override
    public double calculateLossF(double ideal, Neuron neuron) {
        double result = neuron.getOutput();
        return (ideal - result) * (ideal - result) / 2;
    }

    @Override
    public double calculateDerivationLossF(double ideal, Neuron neuron) {
        return neuron.getOutput() - ideal;
    }
}
