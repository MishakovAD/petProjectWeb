package com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.DerivativeActivFunc;

//TODO: реализовать
public class DerivativeSoftmaxFunction implements DerivativeActivFunc {
    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        return 0;
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        return 0;
    }

    @Override
    public double derivative(Neuron neuron) {
        return 0;
    }
}