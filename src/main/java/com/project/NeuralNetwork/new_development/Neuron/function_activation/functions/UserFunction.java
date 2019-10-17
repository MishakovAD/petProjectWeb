package com.project.NeuralNetwork.new_development.Neuron.function_activation.functions;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

public class UserFunction implements ActivFunc {
    @Override
    public double calculation(double[] inputs, double[] weights, double a) {
        return 0;
    }

    @Override
    public double calculation(double[] inputs, double[] weights) {
        return 0;
    }

    @Override
    public Functions getFuctType() {
        return Functions.TANH;
    }
}
