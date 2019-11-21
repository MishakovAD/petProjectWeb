package com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions;

import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.DerivativeActivFunc;

import static com.project.NeuralNetwork.new_development.HelpUtils.HelpUtils.adder;

//TODO: реализовать
public class DerivativeReLUFunction implements DerivativeActivFunc {
    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        double sum = adder(inputs, weights);
        return (Math.exp(params[0] * sum)/(1+Math.exp(params[0] * sum)));
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        return 0;
    }
}
