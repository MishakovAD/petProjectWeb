package com.project.NeuralNetwork.new_development.Neuron.function_activation.functions;

import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeReLUFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

import static com.project.NeuralNetwork.new_development.HelpUtils.HelpUtils.adder;

//|TODO: реализовать данную функцию, ее производную, добавит ьв типы функций и везде, где может использоваться в switch
//f(x) = ln(1+e^x) - это приближение к ReLU - softplus
//https://stepik.org/lesson/24679/step/11?unit=7041
public class ReLUFunctiom implements ActivFunc {
    private double[] params;
    @Override
    public double activate(double[] inputs, double[] weights, double... a) {
        params = a;
        double sum = adder(inputs, weights);
        double activation = (Math.log(1+Math.exp(a[0]*sum)));
        if (activation >= 1) {
            return 1;
        } else if (activation == 0) {
            return 0.000000001;
        } else {
            return activation;
        }
    }

    @Override
    public double activate(double[] inputs, double[] weights) {
        this.params = new double[1];
        params[0] = 1;
        return activate(inputs, weights, 1);
    }

    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        return new DerivativeReLUFunction().derivative(inputs, weights, params);
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        return new DerivativeReLUFunction().derivative(inputs, weights, this.params);
    }

    @Override
    public Functions getFuncType() {
        return Functions.ReLU;
    }

    @Override
    public double[] getParams() {
        return params;
    }
}
