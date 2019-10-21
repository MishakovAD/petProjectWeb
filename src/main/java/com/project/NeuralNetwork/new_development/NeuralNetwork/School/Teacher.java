package com.project.NeuralNetwork.new_development.NeuralNetwork.School;

import com.project.NeuralNetwork.new_development.Layers.HiddenLayer;
import com.project.NeuralNetwork.new_development.Layers.OutputLayer;
import com.project.NeuralNetwork.new_development.Layers.base.Layer;
import com.project.NeuralNetwork.new_development.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.new_development.Neuron.OutputNeuron;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.DerivativeActivationFunction;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions.USER;

public class Teacher implements ITeacher {
    private List<Double> prevDeltaFromErrors;
    private List<Double> prevErrors;
    private DerivativeActivationFunction derivativeFunction;
    private double speed;
    private Functions funcType;

    /**
     * Конструктор для создания объекта, обучающего НС.
     * @param funcType тип функции активации, для корректного вычисления производной
     * @param speed скорость обучения
     */
    public Teacher(Functions funcType, double speed) {
        prevDeltaFromErrors = new LinkedList<>();
        prevErrors = new ArrayList<>();
        this.funcType = funcType;
        derivativeFunction = new DerivativeActivationFunction(funcType);
        this.speed = speed;
    }

    /**
     * Конструктор для создания объекта, обучающего НС.
     * @param derivativeUserFunction объект, вычисляющий производную пользовательской функции
     * @param speed скорость обучения
     */
    public Teacher(DerivativeUserFunction derivativeUserFunction, double speed) {
        this.funcType = USER;
        this.derivativeFunction = new DerivativeActivationFunction(derivativeUserFunction);
        this.speed = speed;
    }

    @Override
    public double calculateError(double[] ideal, double[] result) {
        if (ideal.length != result.length) {
            //throw new DataIsNotCorrectException()
        }
        if (prevErrors.size() > 5000) {
            double lastError = prevErrors.get(prevErrors.size() - 1);
            clearErrors(); //TODO: придумать, как лучше очищать занятую память ошибками.
            prevErrors.add(lastError);
        }
        double error = 0;
        double sum = 0;
        int len = result.length;
        for (int i = 0; i < len; i++) {
            double delta = ideal[i]-result[i];
            sum = sum + delta*delta;
        }
        prevDeltaFromErrors.add(sum);
        double sum_delta = 0;
        for (Double n : prevDeltaFromErrors) {
            sum_delta += n;
        }
        error = sum_delta/prevDeltaFromErrors.size();
        prevErrors.add(error);
        return error;
    }

    @Override
    public List<Double> getPreviousErrors() {
        return prevErrors;
    }

    @Override
    public void clearErrors() {
        prevDeltaFromErrors = new LinkedList<>();
        prevErrors = new ArrayList<>();
    }

    @Override
    public void calculateDeltaOutput(double[] ideal, OutputLayer outputLayer) {
        int countNeurons = outputLayer.getNeuronsCount();
        for (int i = 0; i < countNeurons; i++) {
            OutputNeuron outputNeuron = (OutputNeuron) outputLayer.getNeuron(i);
            int inputCount = outputNeuron.getInputsCount();
            double result = outputNeuron.getOutput();
            double sigma;
            double[] delta = new double[inputCount];
            double derivative = derivativeFunction.calculateDerivative(outputNeuron.getInputs(), outputNeuron.getWeights(), outputNeuron.getParams());
            sigma = (ideal[i] - result) * derivative;
            outputNeuron.setSigma(sigma);
            for (int j = 0; j < inputCount; j++) {
                delta[j] = this.speed * sigma * outputNeuron.getInput(j);
            }
            outputNeuron.setDelta(delta);
        }
    }

    @Override
    public void calculateDeltaHidden(HiddenLayer[] hiddenLayers_array, OutputLayer outputLayer) {
        int hiddenLayersCount = hiddenLayers_array.length;
        int lastLevel = hiddenLayersCount - 1;
        calculateDeltaHidden(hiddenLayers_array[lastLevel], outputLayer);
        for (int i = hiddenLayersCount-2; i >= 0; i--) {
            calculateDeltaHidden(hiddenLayers_array[i], hiddenLayers_array[i+1]);
        }
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }


    private void calculateDeltaHidden(HiddenLayer hiddenLayer, Layer previousLayer) {
        int prevNeuronsCount = previousLayer.getNeuronsCount();
        int neuronsCount = hiddenLayer.getNeuronsCount();
        for (int i = 0; i < neuronsCount; i++) {
            HiddenNeuron hiddenNeuron = (HiddenNeuron) hiddenLayer.getNeuron(i);
            int inputCount = hiddenNeuron.getInputsCount();
            double[] delta = new double[inputCount];
            double[] sigmaPrevious = new double[neuronsCount];
            double sigmaCurrent;
            for (int k = 0; k < neuronsCount; k++) { //считаем для каждого нейрона
                for (int m = 0; m < prevNeuronsCount; m++) { //сумму ошибок на предыдущем уровне
                    sigmaPrevious[k] += previousLayer.getNeuron(m).getSigma() * previousLayer.getNeuron(m).getWeight(k);
                }
            }

            double derivative = derivativeFunction.calculateDerivative(hiddenNeuron.getInputs(), hiddenNeuron.getWeights(), hiddenNeuron.getParams());
            sigmaCurrent = sigmaPrevious[i] * derivative;
            hiddenNeuron.setSigma(sigmaCurrent);

            for (int j = 0; j < inputCount; j++) {
                delta[j] = this.speed * sigmaCurrent * hiddenNeuron.getInput(j);
            }
            hiddenNeuron.setDelta(delta);
        }
    }
}
