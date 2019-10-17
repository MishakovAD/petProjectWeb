package com.project.NeuralNetwork.new_development.Neuron.function_activation;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.LeapFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.SigmaFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.TanhFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.user_function.UserFunction;

import static com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions.USER;

public class ActivationFunction implements ActivFunc {
    private ActivFunc function;
    private UserFunction userFunction;
    private Functions funcType;

    public ActivationFunction(Functions func) {
        this.funcType = func;
        switch (func) {
            case SIGMA :
                this.function = new SigmaFunction();
                break;
            case TANH :
                this.function = new TanhFunction();
                break;
            case LEAP :
                this.function = new LeapFunction();
                break;
            default:
                this.function = new SigmaFunction();
                break;

        }
    }

    public ActivationFunction(UserFunction userFunction) {
        this.funcType = USER;
        this.userFunction = userFunction;
    }

    @Override
    public double calculation(double[] inputs, double[] weights, double... a) {
        if (USER.equals(funcType)) {
            return userFunction.calculation(inputs, weights, a);
        } else {
            return function.calculation(inputs, weights, a[0]);
        }
    }

    @Override
    public double calculation(double[] inputs, double[] weights) {
        if (USER.equals(funcType)) {
            return userFunction.calculation(inputs, weights);
        } else {
            return function.calculation(inputs, weights);
        }
    }

    @Override
    public Functions getFuncType() {
        return funcType;
    }
}
