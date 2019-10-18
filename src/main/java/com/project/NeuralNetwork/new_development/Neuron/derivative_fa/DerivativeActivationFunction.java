package com.project.NeuralNetwork.new_development.Neuron.derivative_fa;

import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeLeapFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeSigmaFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeTanhFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

import static com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions.USER;

/**
 * Класс, для вычисления производной функции, которая передается в конструкторе.
 * Функция для производной должна быть такой же, как и ФА у нейрона.
 */
public class DerivativeActivationFunction implements DerivativeActivFunc {
    private DerivativeActivFunc derivativeFunction;
    private DerivativeUserFunction derivativeUserFunction;
    private Functions funcType;

    public DerivativeActivationFunction(Functions funcType) {
        this.funcType = funcType;
        switch (funcType) {
            case SIGMA :
                this.derivativeFunction = new DerivativeSigmaFunction();
                break;
            case TANH :
                this.derivativeFunction = new DerivativeTanhFunction();
                break;
            case LEAP :
                this.derivativeFunction = new DerivativeLeapFunction();
                break;
            default:
                this.derivativeFunction = new DerivativeSigmaFunction();
                break;
        }
    }

    public DerivativeActivationFunction(DerivativeUserFunction derivativeUserFunction) {
        this.funcType = USER;
        this.derivativeUserFunction = derivativeUserFunction;
    }

    @Override
    public double calculateDerivative(double[] inputs, double[] weights, double... params) {
        if (USER.equals(funcType)) {
            return derivativeUserFunction.calculateDerivative(inputs, weights, params);
        } else {
            return derivativeFunction.calculateDerivative(inputs, weights, params);
        }
    }

    @Override
    public double calculateDerivative(double[] inputs, double[] weights) {
        double[] params = new double[1];
        params[0] = 1;
        if (USER.equals(funcType)) {
            return derivativeUserFunction.calculateDerivative(inputs, weights);
        } else {
            return derivativeFunction.calculateDerivative(inputs, weights, params);
        }
    }
}
