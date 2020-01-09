package com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.DerivativeActivFunc;

import static com.project.NeuralNetwork.Base.HelpUtils.HelpUtils.adder;

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

    @Override
    public double derivative(Neuron neuron) {
        return 0;
    }
}
