package com.project.NeuralNetwork.Base.Neuron.function_activation.functions;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;

//TODO: реализовать
public class SoftmaxFunction implements ActivFunc {
    @Override
    public double activate(double[] inputs, double[] weights, double... a) {
        return 0;
    }

    @Override
    public double activate(double[] inputs, double[] weights) {
        return 0;
    }

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

    @Override
    public Functions getFuncType() {
        return null;
    }

    @Override
    public double[] getParams() {
        return new double[0];
    }
}
