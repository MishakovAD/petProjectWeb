package com.project.NeuralNetwork.Base.Networks.School.teacher;

import com.project.NeuralNetwork.Base.Layers.HiddenLayer;
import com.project.NeuralNetwork.Base.Layers.OutputLayer;
import com.project.NeuralNetwork.Base.Layers.base.Layer;
import com.project.NeuralNetwork.Base.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.Base.Neuron.OutputNeuron;
import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.Base.Neuron.function_activation.ActivationFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.Base.Neuron.loss_function.LossFunc;
import com.project.NeuralNetwork.Base.Neuron.loss_function.LossFunction;
import com.project.NeuralNetwork.Base.Neuron.loss_function.LossFunctionType;

public class Teacher implements ITeacher {
    private double speed;
    private LossFunctionType lossFunctionType;

    /**
     * Конструктор для создания объекта, обучающего НС.
     * @param speed скорость обучения
     */
    public Teacher(double speed) {
        this.speed = speed;
    }

    /**
     * Конструктор для создания объекта, обучающего НС.
     * @param speed скорость обучения
     */
    public Teacher(double speed, LossFunctionType lossFunctionType) {
        this.speed = speed;
        this.lossFunctionType = lossFunctionType;
    }

    @Override
    public double calculateError(double[] ideal, double[] result) {
        if (ideal.length != result.length) {
            //throw new DataIsNotCorrectException()
        }
        double error = 0;
        double sum = 0;
        int len = result.length;
        for (int i = 0; i < len; i++) {
            double delta = ideal[i]-result[i];
            sum += delta*delta;
        }
        error = sum/len;
        return error;
    }

    @Override
    public void calculateDelta(double[] ideal, OutputLayer outputLayer, HiddenLayer[] hiddenLayers_array) {
        calculateDeltaOutput(ideal, outputLayer);
        calculateDeltaHidden(hiddenLayers_array, outputLayer);
        //Корректировка весов, согласно посчитанным delta.
        outputLayer.correctWeights();
        for (int i = 0; i < hiddenLayers_array.length; i++) {
            for (int j = 0; j < hiddenLayers_array[i].getNeuronsCount(); j++) {
                hiddenLayers_array[i].getNeuron(j).correctWeights();
            }
        }
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /*
    https://mattmazur.com/2015/03/17/a-step-by-step-backpropagation-example/
     */
    private void calculateDeltaOutput(double[] ideal, OutputLayer outputLayer) {
        ActivFunc function = new ActivationFunction(outputLayer.getFuncType());
        int countNeurons = outputLayer.getNeuronsCount();
        for (int i = 0; i < countNeurons; i++) {
            OutputNeuron outputNeuron = (OutputNeuron) outputLayer.getNeuron(i);
            int inputCount = outputNeuron.getInputsCount();
            double[] delta = new double[inputCount];
            double sigma = 0;
            LossFunc lossFunc = new LossFunction(lossFunctionType);
            double derivTotalErrorToOutput = lossFunc.calculateDerivationLossF(ideal[i], outputNeuron);
            double derivOutputToInput = getDerivOutputToInput(function, outputNeuron);
            sigma = derivTotalErrorToOutput * derivOutputToInput;
            outputNeuron.setSigma(sigma);
            for (int j = 0; j < inputCount; j++) {
                delta[j] = this.speed * sigma * outputNeuron.getInput(j);
            }
            outputNeuron.setDelta(delta);
        }
    }

    /**
     * Внешний List характеризует массив скрытых слоев,
     * внутренний - нейроны каждого слоя,
     * массив double - то, как изменять веса отдельного нейрона.
     * @param hiddenLayers_array массив скрытых слоев
     * @param outputLayer выходной слой
     */
    private void calculateDeltaHidden(HiddenLayer[] hiddenLayers_array, OutputLayer outputLayer) {
        int hiddenLayersCount = hiddenLayers_array.length;
        int lastLevel = hiddenLayersCount - 1;
        calculateDeltaHidden(hiddenLayers_array[lastLevel], outputLayer);
        for (int i = hiddenLayersCount-2; i >= 0; i--) {
            calculateDeltaHidden(hiddenLayers_array[i], hiddenLayers_array[i+1]);
        }
    }

    private void calculateDeltaHidden(HiddenLayer hiddenLayer, Layer previousLayer) {
        ActivFunc function = new ActivationFunction(hiddenLayer.getFuncType());
        int prevNeuronsCount = previousLayer.getNeuronsCount();
        int currentNeuronsCount = hiddenLayer.getNeuronsCount();
        for (int i = 0; i < currentNeuronsCount; i++) {
            HiddenNeuron hiddenNeuron = (HiddenNeuron) hiddenLayer.getNeuron(i);
            int inputCount = hiddenNeuron.getInputsCount();
            double[] delta = new double[inputCount];
            double sigmaPrevious = 0;
            double sigmaCurrent;
            for (int j = 0; j < prevNeuronsCount; j++) {
                Neuron prevNeuron = previousLayer.getNeuron(j);
                sigmaPrevious += prevNeuron.getSigma() * prevNeuron.getWeight(i);
            }
            double derivOutputToInput = getDerivOutputToInput(function, hiddenNeuron);
            sigmaCurrent = sigmaPrevious * derivOutputToInput;
            hiddenNeuron.setSigma(sigmaCurrent);

            for (int j = 0; j < inputCount; j++) {
                delta[j] = this.speed * sigmaCurrent * hiddenNeuron.getInput(j);
            }
            hiddenNeuron.setDelta(delta);
        }
    }


    private double getDerivOutputToInput(ActivFunc function, Neuron neuron) {
        if (Functions.SIGMA.equals(function.getFuncType())) {
            return function.derivative(neuron);
        } else {
            return function.derivative(neuron.getInputs(), neuron.getWeights(), neuron.getParams());
        }

    }
}
