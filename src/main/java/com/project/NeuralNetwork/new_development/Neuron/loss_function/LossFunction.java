package com.project.NeuralNetwork.new_development.Neuron.loss_function;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.loss_function.functions.MSE_function;

//TODO: реализовать
public class LossFunction implements LossFunc {
    private TypeLossFunction type;
    private LossFunc lossFunc;

    public LossFunction(TypeLossFunction type) {
        this.type = type;
        if (TypeLossFunction.MSE.equals(type)) {
            lossFunc = new MSE_function();
        } else {
            //TODO: добавить другие функции потерь.
        }
    }

    @Override
    public double calculateLossF(double ideal, Neuron neuron) {
        return lossFunc.calculateLossF(ideal, neuron);
    }

    @Override
    public double calculateDerivationLossF(double ideal, Neuron neuron) {
        return lossFunc.calculateDerivationLossF(ideal, neuron);
    }
}
