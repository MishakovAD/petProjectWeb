package com.project.NeuralNetwork.Base.Neuron.base;

import com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.Base.Neuron.function_activation.ActivationFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.Base.Neuron.function_activation.functions.SigmaFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.functions.user_function.UserFunction;

import java.util.Arrays;

/**
 * Класс-предок для всех нейронов с реализацией основных методов.
 */
public abstract class NeuronImpl implements Neuron {
    private double[] inputs;
    private double[] weights;
    private double output;
    private Functions funcType;
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
        this.weights = Arrays.stream(this.weights).map(weight -> weight += (Math.random())).toArray();
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
        this.weights = Arrays.stream(this.weights).map(weight -> weight += (Math.random())).toArray();
        this.delta = new double[inputsCount];
        this.funcType = function;
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
        this.weights = Arrays.stream(this.weights).map(weight -> weight += (Math.random())).toArray();
        this.delta = new double[inputsCount];
        this.funcType = function;
        this.function = new ActivationFunction(function);
        this.a = a;
    }

    /**
     * Создание нейрона с использованием пользовательской функции активации.
     * @param inputsCount число входов
     * @param userFunction пользовательская функция активации
     */
    public NeuronImpl(int inputsCount, UserFunction userFunction, DerivativeUserFunction derivativeUserFunction) {
        if (inputsCount < 1) {
            inputsCount = 1;
        }
        this.inputs = new double[inputsCount];
        this.weights = new double[inputsCount];
        this.weights = Arrays.stream(this.weights).map(weight -> weight += (Math.random())).toArray();
        this.delta = new double[inputsCount];
        this.funcType = Functions.USER;
        this.function = new ActivationFunction(userFunction, derivativeUserFunction);
    }

    @Override
    public void setInputs(double[] inputs) {
        //inputs = normalize(inputs);
        this.inputs = inputs;
        calculation();
    }

    //Исключительно для входного слоя
    @Override
    public void setInput(double inputData) {
        this.inputs[0] = inputData;
        //this.output = normalize(inputData);
        this.output = inputData;
    }

    private double normalize(double input) {
        double[] i = new double[1];
        double[] w = new double[1];
        i[0] = input;
        w[0] = 1;
//        if (input < 127) {
//            return 0;
//        } else {
//            return 1;
//        }
        return new SigmaFunction().activate(i, w);
    }

    private double[] normalize(double[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            if ((inputs[i] > 1)) {
                inputs[i] = normalize(inputs[i]);
            }
        }
        return inputs;
    }

    private void calculation() {
        this.output = this.function.activate(inputs, weights, this.a);
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
        this.funcType = funcType;
        this.function = new ActivationFunction(funcType);
    }

    @Override
    public Functions getFuncType() {
        return funcType != null ? funcType: Functions.SIGMA;
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
    }

    @Override
    public void correctWeights() {
        for (int i = 0; i < this.weights.length; i++) {
            //this.weights[i] = this.weights[i] + delta[i]; TODO: как правильно идти при градиентном спуске?
            this.weights[i] = this.weights[i] - delta[i];
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
