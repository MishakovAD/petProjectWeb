package com.project.NeuralNetwork.new_development.Neuron.loss_function.functions;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivationFunction;
import com.project.NeuralNetwork.new_development.Neuron.loss_function.LossFunc;

//Бинарная кросс энтропия - для бинарной классификации (где один выход)
//TODO: реализовать
//BCE(p, t) = -t*log(p) - (1-t)*log(1-p), где p - наша ФА (еще вариант есть домноженный на -1. где есть верный?)
//Для сигмоиды производная BCE = sigma - t
public class BSE_function implements LossFunc {
    @Override
    public double calculateLossF(double ideal, Neuron neuron) {
        double result = neuron.getOutput();
        double bse = (-1) * ideal * Math.log(result) - (1 - ideal) * Math.log(1 - result);
        return bse;
    }

    @Override
    public double calculateDerivationLossF(double ideal, Neuron neuron) {
        double result = neuron.getOutput();
        if (result == ideal) {
            return 0;
        } else if (result == 1) {
            result = result - 0.01;
        }
        ActivFunc function = new ActivationFunction(neuron.getFuncType());
        double derivative_fa = function.derivative(neuron.getInputs(), neuron.getWeights(), neuron.getParams());
        double deriv_bse = (-1) * (ideal/neuron.getOutput()) * derivative_fa + ((1 - ideal)/(1-result))*derivative_fa;
        return deriv_bse;
    }
}
