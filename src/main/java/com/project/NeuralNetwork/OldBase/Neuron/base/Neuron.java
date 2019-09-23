package com.project.NeuralNetwork.OldBase.Neuron.base;

import java.util.Arrays;

public class Neuron {
    private int countInputs;
    private int countOutputs;
    private double[] inputs; //входы. Для входного слоя 1шт.
    private double[] outputs;
    private double output;
    private double[] weights; //веса. Для входного слоя 1шт. = 1.
    private boolean sigma = true; //сигмоидальная функция активации
    private boolean tanh = false; //гиперболический тангенс
    private boolean leap = false; //функция единичного скачка

    //Parameters
    private double b = 1; //порог
    private double a = 1; //крутизна сигмы, тангенса

    public Neuron(int countInputs, int countOutputs) {
        this.countInputs = countInputs;
        this.countOutputs = countOutputs;
        this.inputs = new double[countInputs];
        this.weights = new double[countOutputs];
        this.outputs = new double[countOutputs];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
    }

    public int getCountInputs() {
        return countInputs;
    }

    public int getCountOutputs() {
        return countOutputs;
    }

    public double[] getInputs() {
        return inputs;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
        getResultComputing();
    }

    public double getInput(int index) {
        return inputs[index];
    }

    public void setInput(int index, double input) {
        this.inputs[index] = input;
        getResultComputing();
    }

    public void setInput(double input) {
        this.inputs[0] = input;
        getResultComputing();
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getWeight(int index) {
        return weights[index];
    }

    public void setWeight(int index, double weight) {
        this.weights[index] = weight;
    }

    public double getOutput() {
        return output;
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void setOutputs(double[] outputs) {
        this.outputs = outputs;
    }

    public void setSigma() {
        this.sigma = true;
        this.tanh = false;
        this.leap = false;
    }

    public void setTanh() {
        this.tanh = true;
        this.sigma = false;
        this.leap = false;
    }

    public void setLeap() {
        this.leap = true;
        this.sigma = false;
        this.tanh = false;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setA(double a) {
        this.a = a;
    }

    private void getResultComputing() {
        double sum = 0;
        for (int i = 0; i < this.countInputs; i++) {
            try {
                sum += this.inputs[i] * this.weights[i];
            } catch (ArrayIndexOutOfBoundsException ex) {
                //todo nothing
            }

        }
        if (this.sigma) {
            this.output = 1 / (1 + Math.exp(-a * sum));
        } else if (this.tanh) {
            this.output = Math.tanh(sum / a);
        } else if (this.leap) {
            if (sum >= b) {
                this.output = 1;
            } else {
                this.output = 0;
            }
        }
        this.outputs = Arrays.stream(this.outputs).map(out -> out = this.output).toArray();
    }
}
