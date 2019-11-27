package com.project.NeuralNetwork.Base.Layers.base;

public enum Layers {
    INPUT_LAYER("INPUT_LAYER"),
    HIDDEN_LAYER("HIDDEN_LAYER"),
    OUTPUT_LAYER("OUTPUT_LAYER");

    private String name;

    Layers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
