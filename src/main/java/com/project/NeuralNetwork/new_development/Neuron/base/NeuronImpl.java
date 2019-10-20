package com.project.NeuralNetwork.new_development.Neuron.base;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivationFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.user_function.UserFunction;

import java.util.Arrays;

/**
 * Класс-предок для всех нейронов с реализацией основных методов.
 */
public class NeuronImpl implements Neuron {
    private double[] inputs;
    private double[] weights;
    private double output;
    private ActivFunc function;
    private double a;
    //Обратное распространение ошибки
    private double sigma;
    private double[] delta;

    /**
     * Создание нейрона с указание количества входов.
     * @param inputsCount число входов
     */
    public NeuronImpl(int inputsCount) {
        if (inputsCount < 1) {
            inputsCount = 1;
        }
        this.inputs = new double[inputsCount];
        this.weights = new double[inputsCount];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
        this.delta = new double[inputsCount];
        this.function = new ActivationFunction(Functions.SIGMA);
        this.a = 1;
    }

    /**
     * Создание нейрона с указанием типа функции активации.
     * @param inputsCount число входов
     * @param function тип функции активации (из доступных в Enums)
     */
    public NeuronImpl(int inputsCount, Functions function) {
        if (inputsCount < 1) {
            inputsCount = 1;
        }
        this.inputs = new double[inputsCount];
        this.weights = new double[inputsCount];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
        this.delta = new double[inputsCount];
        this.function = new ActivationFunction(function);
        this.a = 1;
    }

    /**
     * Создание нейрона с указанием типа функции активации и параметра "а".
     * @param inputsCount число входов
     * @param function тип функции активации (из доступных в Enums)
     * @param a крутизна/порог
     */
    public NeuronImpl(int inputsCount, Functions function, double a) {
        if (inputsCount < 1) {
            inputsCount = 1;
        }
        this.inputs = new double[inputsCount];
        this.weights = new double[inputsCount];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
        this.delta = new double[inputsCount];
        this.function = new ActivationFunction(function);
        this.a = a;
    }

    /**
     * Создание нейрона с использованием пользовательской функции активации.
     * @param inputsCount число входов
     * @param userFunction пользовательская функция активации
     */
    public NeuronImpl(int inputsCount, UserFunction userFunction) {
        if (inputsCount < 1) {
            inputsCount = 1;
        }
        this.inputs = new double[inputsCount];
        this.weights = new double[inputsCount];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += Math.random()).toArray();
        this.delta = new double[inputsCount];
        this.function = new ActivationFunction(userFunction);
    }

    @Override
    public void setInputs(double[] inputs) {
        this.inputs = inputs;
        calculation();
    }

    //Исключительно для входного слоя
    @Override
    public void setInput(double inputData) {
        this.inputs[0] = inputData;
        this.output = inputData;
    }

    private void calculation() {
        this.output = this.function.calculation(inputs, weights, this.a);
    }

    @Override
    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    @Override
    public double getOutput() {
        return this.output;
    }

    @Override
    public double[] getInputs() {
        return this.inputs;
    }

    @Override
    public double getInput(int index) {
        return this.inputs[index];
    }

    @Override
    public int getInputsCount() {
        return inputs.length;
    }

    @Override
    public double[] getWeights() {
        return this.weights;
    }

    @Override
    public double getWeight(int index) {
        return this.weights[index];
    }

    @Override
    public Functions whichActivationFunction() {
        return function.getFuncType();
    }

    @Override
    public void setActivFuncType(Functions funcType) {
        this.function = new ActivationFunction(funcType);
    }

    @Override
    public void setParameterA(double a) {
        this.a = a;
    }

    @Override
    public double[] getParams() {
        return function.getParams();
    }

    //-----------------------------------Метод обратного распространения ошибки-----------------------------------\\

    @Override
    public void setDelta(double[] delta) {
        this.delta = delta;
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] = this.weights[i] + delta[i];
        }
    }

    @Override
    public double[] getDelta() {
        return this.delta;
    }

    @Override
    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public double getSigma() {
        return this.sigma;
    }
}
