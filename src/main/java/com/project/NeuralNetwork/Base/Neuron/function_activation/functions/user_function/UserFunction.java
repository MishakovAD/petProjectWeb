package com.project.NeuralNetwork.Base.Neuron.function_activation.functions.user_function;

@FunctionalInterface
public interface UserFunction {
    double calculation(double[] inputs, double[] weights, double... params);
}
