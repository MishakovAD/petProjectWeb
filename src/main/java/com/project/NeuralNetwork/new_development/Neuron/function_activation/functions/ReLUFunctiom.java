package com.project.NeuralNetwork.new_development.Neuron.function_activation.functions;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

//|TODO: реализовать данную функцию, ее производную, добавит ьв типы функций и везде, где может использоваться в switch
//f(x) = ln(1+e^x)
public class ReLUFunctiom implements ActivFunc {
    @Override
    public double calculation(double[] inputs, double[] weights, double... a) {
        return 0;
    }

    @Override
    public double calculation(double[] inputs, double[] weights) {
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
