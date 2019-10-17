package com.project.NeuralNetwork.new_development.Neuron.function_activation;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.LeapFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.SigmaFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.TanhFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.UserFunction;

public class ActivationFunction implements ActivFunc {
    private ActivFunc function;
    private Functions fuctType;

    public ActivationFunction(Functions func) {
        this.fuctType = func;
        switch (func) {
            case SIGMA :
                this.function = new SigmaFunction();
            case TANH :
                this.function = new TanhFunction();
            case LEAP :
                this.function = new LeapFunction();
            case USER :
                this.function = new UserFunction();
            default:
                this.function = new SigmaFunction();

        }
    }

    @Override
    public double calculation(double[] inputs, double[] weights, double a) {
        return function.calculation(inputs, weights, a);
    }

    @Override
    public double calculation(double[] inputs, double[] weights) {
        return function.calculation(inputs, weights);
    }

    @Override
    public Functions getFuctType() {
        return fuctType;
    }
}
