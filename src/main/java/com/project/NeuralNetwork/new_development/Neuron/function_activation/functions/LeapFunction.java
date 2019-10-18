package com.project.NeuralNetwork.new_development.Neuron.function_activation.functions;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

public class LeapFunction implements ActivFunc {
    private double[] params;
    @Override
    public double calculation(double[] inputs, double[] weights, double... a) {
        params = a;
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }
        int offset = 1;
        sum += offset;
        if (sum >= a[0]) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public double calculation(double[] inputs, double[] weights) {
        return calculation(inputs, weights, 1);
    }

    @Override
    public Functions getFuncType() {
        return Functions.LEAP;
    }

    @Override
    public double[] getParams() {
        return params;
    }
}
