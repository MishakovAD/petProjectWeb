package com.project.NeuralNetwork.Base.Neuron.loss_function;

import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.loss_function.functions.BSE_function;
import com.project.NeuralNetwork.Base.Neuron.loss_function.functions.MSE_function;

//TODO: реализовать
public class LossFunction implements LossFunc {
    private LossFunctionType type;
    private LossFunc lossFunc;

    public LossFunction(LossFunctionType type) {
        if (type == null) {
            type = LossFunctionType.MSE;
        }
        this.type = type;
        if (LossFunctionType.MSE.equals(type)) {
            lossFunc = new MSE_function();
        } else if (LossFunctionType.BSE.equals(type)) {
            lossFunc = new BSE_function();
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
