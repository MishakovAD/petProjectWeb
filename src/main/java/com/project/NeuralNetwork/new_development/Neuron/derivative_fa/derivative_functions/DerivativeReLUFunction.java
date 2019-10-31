package com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions;

import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.DerivativeActivFunc;

import static com.project.NeuralNetwork.new_development.HelpUtils.HelpUtils.adder;

//TODO: реализовать
//Можно взять производную для реальной ReLU ФА:
//производная = 1, если х больше 0 и = 0, если х меньше или равен 0.
public class DerivativeReLUFunction implements DerivativeActivFunc {
    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        double sum = adder(inputs, weights);
        return (Math.exp(params[0] * sum)/(1+Math.exp(params[0] * sum)));
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        return derivative(inputs, weights, 1);
    }
}
