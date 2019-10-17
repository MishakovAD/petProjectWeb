package com.project.NeuralNetwork.new_development.Neuron.function_activation.functions;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

public class SigmaFunction implements ActivFunc {
    @Override
    public double calculation(double[] inputs, double[] weights, double a) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];

        }
        return (1 / (1 + Math.exp(-a * sum)));
    }

    @Override
    public double calculation(double[] inputs, double[] weights) {
        return calculation(inputs, weights, 1);
    }

    @Override
    public Functions getFuctType() {
        return Functions.SIGMA;
    }
}
