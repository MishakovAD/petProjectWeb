package com.project.NeuralNetwork.Base.Neuron.function_activation.functions;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions.DerivativeSigmaFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;

import static com.project.NeuralNetwork.Base.HelpUtils.HelpUtils.adder;

public class SigmaFunction implements ActivFunc {
    private double[] params;
    @Override
    public double activate(double[] inputs, double[] weights, double... a) {
        params = a;
        double sum = adder(inputs, weights);
        return (1 / (1 + Math.exp(-a[0] * sum)));
    }

    @Override
    public double activate(double[] inputs, double[] weights) {
        return activate(inputs, weights, 1);
    }

    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        return new DerivativeSigmaFunction().derivative(inputs, weights, params);
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        return new DerivativeSigmaFunction().derivative(inputs, weights);
    }

    @Override
    public double derivative(Neuron neuron) {
        return new DerivativeSigmaFunction().derivative(neuron);
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
