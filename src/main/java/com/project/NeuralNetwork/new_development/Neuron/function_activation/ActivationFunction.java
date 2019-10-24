package com.project.NeuralNetwork.new_development.Neuron.function_activation;

import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.DerivativeActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeLeapFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeReLUFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeSigmaFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeSoftmaxFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.DerivativeTanhFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.LeapFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.ReLUFunctiom;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.SigmaFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.SoftmaxFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.TanhFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.user_function.UserFunction;

import static com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions.USER;

public class ActivationFunction implements ActivFunc {
    private ActivFunc function;
    private UserFunction userFunction;
    DerivativeActivFunc derivativeFunction;
    DerivativeUserFunction derivativeUserFunction;
    private Functions funcType;
    private double[] params;

    public ActivationFunction(Functions func) {
        this.funcType = func;
        switch (func) {
            case SIGMA :
                this.function = new SigmaFunction();
                this.derivativeFunction = new DerivativeSigmaFunction();
                break;
            case TANH :
                this.function = new TanhFunction();
                this.derivativeFunction = new DerivativeTanhFunction();
                break;
            case LEAP :
                this.function = new LeapFunction();
                this.derivativeFunction = new DerivativeLeapFunction();
                break;
            case ReLU :
                this.function = new ReLUFunctiom();
                this.derivativeFunction = new DerivativeReLUFunction();
                break;
            case Softmax :
                this.function = new SoftmaxFunction();
                this.derivativeFunction = new DerivativeSoftmaxFunction();
                break;
            default:
                this.function = new SigmaFunction();
                this.derivativeFunction = new DerivativeSigmaFunction();
                break;
        }
    }

    public ActivationFunction(UserFunction userFunction, DerivativeUserFunction derivativeUserFunction) {
        this.funcType = USER;
        this.userFunction = userFunction;
        this.derivativeUserFunction = derivativeUserFunction;
    }

    @Override
    public double activate(double[] inputs, double[] weights, double... a) {
        params = a;
        if (USER.equals(funcType)) {
            return userFunction.calculation(inputs, weights, a);
        } else {
            return function.activate(inputs, weights, a[0]);
        }
    }

    @Override
    public double activate(double[] inputs, double[] weights) {
        params = new double[1];
        params[0] = 1;
        if (USER.equals(funcType)) {
            return userFunction.calculation(inputs, weights);
        } else {
            return function.activate(inputs, weights, params);
        }
    }

    @Override
    public double derivative(double[] inputs, double[] weights, double... params) {
        if (USER.equals(funcType)) {
            return derivativeUserFunction.derivative(inputs, weights);
        } else {
            return derivativeFunction.derivative(inputs, weights, params);
        }
    }

    @Override
    public double derivative(double[] inputs, double[] weights) {
        if (USER.equals(funcType)) {
            return derivativeUserFunction.derivative(inputs, weights);
        } else {
            return derivativeFunction.derivative(inputs, weights);
        }
    }

    @Override
    public Functions getFuncType() {
        return funcType;
    }

    @Override
    public double[] getParams() {
        return params;
    }
}
