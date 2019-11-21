package com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.DerivativeActivFunc;

import static com.project.NeuralNetwork.new_development.HelpUtils.HelpUtils.adder;

public class DerivativeSigmaFunction implements DerivativeActivFunc {
    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        double sum = adder(inputs, weights);
        double derivative = (params[0] * Math.exp(-params[0] * sum)) / ((1 + Math.exp(-params[0] * sum)) * (1 + Math.exp(-params[0] * sum)));
        return derivative;
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        return derivative(inputs, weights, 1);
    }

    @Override
    public double derivative(Neuron neuron) {
        return neuron.getOutput() * (1 - neuron.getOutput());
    }
}
