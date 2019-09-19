package com.project.NeuralNetwork.Base;

import java.util.Arrays;
import java.util.Random;

public class Neuron {
    private int countInputs;
    private double[] inputs; //входы. Для входного слоя 1шт.
    private double[] weights; //веса. Для входного слоя 1шт. = 1.
    private boolean sigma = true; //сигмоидальная функция активации
    private boolean tanh = false; //гиперболический тангенс
    private boolean leap = false; //функция единичного скачка
    private double output;

    //Parameters
    private double b = 1; //порог
    private double a = 1; //крутизна сигмы, тангенса

    public Neuron(int countInputs) {
        this.countInputs = countInputs;
        this.inputs = new double[countInputs];
        this.weights = new double[countInputs];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
    }

    public int getCountInputs() {
        return countInputs;
    }

    public double[] getInputs() {
        return inputs;
    }

    public double getInput(int index) {
        return inputs[index];
    }

    public double[] getWeights() {
        return weights;
    }

    public double getWeight(int index) {
        return weights[index];
    }

    public double getOutput() {
        double sum = 0;
        for (int i = 0; i < countInputs; i++) {
            sum += inputs[i] * weights[i];
        }
        if (sigma) {
            output = 1 / (1 + Math.exp(-a * sum));
        } else if (tanh) {
            output = Math.tanh(sum / a);
        } else if (leap) {
            if (sum >= b) {
                output = 1;
            } else {
                output = 0;
            }
        }
        return output;
    }

    public void setInputs(double[] inputs) {
        this.inputs = inputs;
    }

    public void setInput(int index, double input) {
        this.inputs[index] = input;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public void setWeight(int index, double weight) {
        this.weights[index] = weight;
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
}
