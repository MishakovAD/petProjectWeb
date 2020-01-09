package com.project.NeuralNetwork.Base.Neuron.function_activation.functions;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions.DerivativeTanhFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;

public class TanhFunction implements ActivFunc {
    private double[] params;
    @Override
    public double activate(double[] inputs, double[] weights, double... a) {
        params = a;
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];

        }
        int offset = 1;
        sum += offset;
        return (Math.tanh(sum / a[0]));
    }

    @Override
    public double activate(double[] inputs, double[] weights) {
        return activate(inputs, weights, 1);
    }

    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        return new DerivativeTanhFunction().derivative(inputs, weights, params);
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        return new DerivativeTanhFunction().derivative(inputs, weights, params);
    }

    @Override
    public double derivative(Neuron neuron) {
        return new DerivativeTanhFunction().derivative(neuron);
    }

    @Override
    public Functions getFuncType() {
        return Functions.TANH;
    }

    @Override
    public double[] getParams() {
        return params;
    }
}
