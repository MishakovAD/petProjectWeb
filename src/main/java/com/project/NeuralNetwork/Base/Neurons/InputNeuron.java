package com.project.NeuralNetwork.Base.Neurons;


public class InputNeuron {
    private double input;
    private double output;

    public InputNeuron() {
    }

    public InputNeuron(double input) {
        this.input = input;
        normalize();
    }

    public double getOutput() {
        return output;
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
        normalize();
    }

    private void normalize() {
        //double output = 1 / (1 + Math.exp(this.input));
        //double output = 1 / this.input; //если input == 0 не прокатит
        double output = this.input;
        this.output = output;
    }

    //Методы обратного распространения
}
