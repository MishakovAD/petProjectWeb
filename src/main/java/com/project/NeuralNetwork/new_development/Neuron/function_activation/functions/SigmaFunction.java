package com.project.NeuralNetwork.new_development.Neuron.function_activation.functions;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

import static com.project.NeuralNetwork.new_development.HelpUtils.HelpUtils.adder;

public class SigmaFunction implements ActivFunc {
    private double[] params;
    @Override
    public double calculation(double[] inputs, double[] weights, double... a) {
        params = a;
        double sum = adder(inputs, weights);
        return (1 / (1 + Math.exp(-a[0] * sum)));
    }

    @Override
    public double calculation(double[] inputs, double[] weights) {
        return calculation(inputs, weights, 1);
    }

    @Override
    public Functions getFuncType() {
        return Functions.SIGMA;
    }

    @Override
    public double[] getParams() {
        return params;
    }
}
