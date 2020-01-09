package com.project.NeuralNetwork.Base.Neuron.loss_function;

public enum LossFunctionType {
    MSE("MSE"),
    BSE("BSE");

    private String name;

    LossFunctionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
