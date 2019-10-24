package com.project.NeuralNetwork.new_development.Neuron.loss_function;

public enum TypeLossFunction {
    MSE("MSE");

    private String name;

    TypeLossFunction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
