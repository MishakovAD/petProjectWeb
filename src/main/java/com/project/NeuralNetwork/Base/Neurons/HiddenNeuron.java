package com.project.NeuralNetwork.Base.Neurons;

import java.util.Arrays;

public class HiddenNeuron {
    private int countInputs;
    private double[] inputs;
    private double output;
    private int countOutputs;
    private double[] weights;
    private double[] delta;
    private double sigma;
    private boolean sigma_func = true; //сигмоидальная функция активации
    private boolean tanh_func = false; //гиперболический тангенс
    private boolean leap_func = false; //функция единичного скачка
    private double[] inputsForPreviousLayer;
    // Для обратного распространения. Подсчета ошибки, чтобы не передавать лишюю инфу в конструктор.
    // Вход с предыдущего уровня

    //Parameters
    private double b = 1; //порог
    private double a = 1; //крутизна сигмы, тангенса

    public HiddenNeuron(int countInputs) {
        if (countInputs < 1) {
            countInputs = 1;
        }
        this.countInputs = countInputs;
        this.inputs = new double[countInputs];
        this.inputsForPreviousLayer = new double[countInputs];
        this.delta = new double[countInputs];
        this.weights = new double[countInputs];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
    }

    public HiddenNeuron(int countInputs, double[] inputs) {
        if (countInputs < 1) {
            countInputs = 1;
        }
        this.countInputs = countInputs;
        this.inputs = inputs;
        this.inputsForPreviousLayer = new double[countInputs];
        this.delta = new double[countInputs];
        this.weights = new double[countInputs];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
        calculation();
    }

    public void calculation() {
        double sum = 0;
        for (int i = 0; i < this.countInputs; i++) {
            sum += this.inputs[i] * this.weights[i];

        }
        if (this.sigma_func) {
            this.output = 1 / (1 + Math.exp(-a * sum));
        } else if (this.tanh_func) {
            this.output = Math.tanh(sum / a);
        } else if (this.leap_func) {
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

    public boolean isSigma_func() {
        return sigma_func;
    }

    public void setSigma_func(boolean sigma_func) {
        this.sigma_func = sigma_func;
    }

    public boolean isTanh_func() {
        return tanh_func;
    }

    public void setTanh_func(boolean tanh_func) {
        this.tanh_func = tanh_func;
    }

    public boolean isLeap_func() {
        return leap_func;
    }

    public void setLeap_func(boolean leap_func) {
        this.leap_func = leap_func;
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


    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double[] getInputsForPreviousLayer() {
        return inputsForPreviousLayer;
    }

    public double getInputForPreviousLayer(int index) {
        return inputsForPreviousLayer[index];
    }

    public void setInputsForPreviousLayer(double[] inputsForPreviousLayer) {
        this.inputsForPreviousLayer = inputsForPreviousLayer;
    }

    public void setInputForPreviousLayer(int index, double inputForPreviousLayer) {
        this.inputsForPreviousLayer[index] = inputForPreviousLayer;
    }

    public int getCountOutputs() {
        return countOutputs;
    }

    public void setCountOutputs(int countOutputs) {
        this.countOutputs = countOutputs;
    }

    public void correctWeights() {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + delta[i];
        }
    }
}
