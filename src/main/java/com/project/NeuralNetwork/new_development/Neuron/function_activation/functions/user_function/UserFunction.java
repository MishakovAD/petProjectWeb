package com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.user_function;

@FunctionalInterface
public interface UserFunction {
    double calculation(double[] inputs, double[] weights, double... params);
}
