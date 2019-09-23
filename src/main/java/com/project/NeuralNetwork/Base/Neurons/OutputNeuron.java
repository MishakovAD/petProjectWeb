package com.project.NeuralNetwork.Base.Neurons;

import java.util.Arrays;

public class OutputNeuron {
    private int countInputs;
    private double[] inputs;
    private double output;
    private double[] weights;
    private double[] delta;
    private boolean sigma = true; //сигмоидальная функция активации
    private boolean tanh = false; //гиперболический тангенс
    private boolean leap = false; //функция единичного скачка

    //Parameters
    private double b = 1; //порог
    private double a = 1; //крутизна сигмы, тангенса

    public OutputNeuron(int countInputs) {
        if (countInputs < 1) {
            countInputs = 1;
        }
        this.countInputs = countInputs;
        this.inputs = new double[countInputs];
        this.weights = new double[countInputs];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
    }

    public OutputNeuron(int countInputs, double[] inputs) {
        if (countInputs < 1) {
            countInputs = 1;
        }
        this.countInputs = countInputs;
        this.inputs = inputs;
        this.weights = new double[countInputs];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
        calculation();
    }

    public void calculation() {
        double sum = 0;
        for (int i = 0; i < this.countInputs; i++) {
            sum += this.inputs[i] * this.weights[i];

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
    }

    public int getCountInputs() {
        return countInputs;
    }

    public double getOutput() {
        return output;
    }

    public double[] getInputs() {
        return inputs;
    }

    public double getInput(int index) {
        return inputs[index];
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
        calculation();
    }

    public void setInput(int index, double input) {
        this.inputs[index] = input;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public void setWeight(int index, double weight) {
        this.weights[index] = weight;
    }

    public boolean isSigma() {
        return sigma;
    }

    public void setSigma(boolean sigma) {
        this.sigma = sigma;
    }

    public boolean isTanh() {
        return tanh;
    }

    public void setTanh(boolean tanh) {
        this.tanh = tanh;
    }

    public boolean isLeap() {
        return leap;
    }

    public void setLeap(boolean leap) {
        this.leap = leap;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double[] getDelta() {
        return delta;
    }

    public void setDelta(double[] delta) {
        this.delta = delta;
    }

    //Методы обратного распространения

    public void correctWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + delta[i];
        }
    }
}
