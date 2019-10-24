package com.project.NeuralNetwork.new_development.Neuron.function_activation;

public enum Functions {
    SIGMA("SIGMA"), //сигмодальная функция
    TANH("TANH"), //тангенсальная функция
    LEAP("LEAP"), //единичного скачка функция
    USER("USER"), //пользовательская функция
    ReLU("ReLU"), //пользовательская функция
    Softmax("Softmax"); //пользовательская функция

    private String name;

    Functions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
