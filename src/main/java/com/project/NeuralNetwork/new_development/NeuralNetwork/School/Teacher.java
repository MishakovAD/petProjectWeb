package com.project.NeuralNetwork.new_development.NeuralNetwork.School;

import com.project.NeuralNetwork.new_development.Layers.HiddenLayer;
import com.project.NeuralNetwork.new_development.Layers.OutputLayer;
import com.project.NeuralNetwork.new_development.Layers.base.Layer;
import com.project.NeuralNetwork.new_development.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.new_development.Neuron.OutputNeuron;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivFunc;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.ActivationFunction;
import com.project.NeuralNetwork.new_development.Neuron.loss_function.LossFunc;
import com.project.NeuralNetwork.new_development.Neuron.loss_function.functions.BSE_function;
import com.project.NeuralNetwork.new_development.Neuron.loss_function.functions.MSE_function;

import java.util.LinkedList;
import java.util.List;

public class Teacher implements ITeacher {
    private double speed;

    /**
     * Конструктор для создания объекта, обучающего НС.
     * @param speed скорость обучения
     */
    public Teacher(double speed) {
        this.speed = speed;
    }

//    /**
//     * Конструктор для создания объекта, обучающего НС.
//     * @param userFunction объект, вычисляющий пользовательскую функцию активации
//     * @param derivativeUserFunction объект, вычисляющий производную пользовательской функции
//     * @param speed скорость обучения
//     */
//    public Teacher(UserFunction userFunction, DerivativeUserFunction derivativeUserFunction, double speed) {
//        this.funcType = USER;
//        this.function = new ActivationFunction(userFunction, derivativeUserFunction);
//        this.speed = speed;
//    }

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
        List<double[]> outputDelta = calculateDeltaOutput(ideal, outputLayer);
        List<List<double[]>> hiddenDelta = calculateDeltaHidden(hiddenLayers_array, outputLayer);
        for (int i = 0; i < outputDelta.size(); i++) {
            outputLayer.getNeuron(i).setDelta(outputDelta.get(i));
        }
        for (int i = 0; i < hiddenLayers_array.length; i++) {
            for (int j = 0; j < hiddenLayers_array[i].getNeuronsCount(); j++) {


                hiddenLayers_array[i].getNeuron(j).setDelta(hiddenDelta.get(hiddenLayers_array.length-1-i).get(j));
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
    private List<double[]> calculateDeltaOutput(double[] ideal, OutputLayer outputLayer) {
        ActivFunc function = new ActivationFunction(outputLayer.getFuncType());
        List<double[]> deltaList = new LinkedList<>();
        int countNeurons = outputLayer.getNeuronsCount();
        for (int i = 0; i < countNeurons; i++) {
            OutputNeuron outputNeuron = (OutputNeuron) outputLayer.getNeuron(i);
            int inputCount = outputNeuron.getInputsCount();
            double[] delta = new double[inputCount];
            double sigma = 0;
            LossFunc lossFunc = new MSE_function(); //TODO: переделать.
            double derivTotalErrorToOutput = lossFunc.calculateDerivationLossF(ideal[i], outputNeuron);
            double derivOutputToInput = function.derivative(outputNeuron.getInputs(), outputNeuron.getWeights(), outputNeuron.getParams());
            sigma = derivTotalErrorToOutput * derivOutputToInput;
            outputNeuron.setSigma(sigma);
            for (int j = 0; j < inputCount; j++) {
                delta[j] = this.speed * sigma * outputNeuron.getInput(j);
            }
            deltaList.add(delta);
        }
        return deltaList;
    }

    /**
     * Внешний List характеризует массив скрытых слоев,
     * внутренний - нейроны каждого слоя,
     * массив double - то, как изменять веса отдельного нейрона.
     * @param hiddenLayers_array массив скрытых слоев
     * @param outputLayer выходной слой
     * @return Лист листов массива double для изменения весов нейронов скрытого слоя
     */
    private List<List<double[]>> calculateDeltaHidden(HiddenLayer[] hiddenLayers_array, OutputLayer outputLayer) {
        List<List<double[]>> hiddenArrayDeltaList = new LinkedList<>();
        int hiddenLayersCount = hiddenLayers_array.length;
        int lastLevel = hiddenLayersCount - 1;
        List<double[]> deltaList = calculateDeltaHidden(hiddenLayers_array[lastLevel], outputLayer);
        hiddenArrayDeltaList.add(deltaList);
        for (int i = hiddenLayersCount-2; i >= 0; i--) {
            deltaList = calculateDeltaHidden(hiddenLayers_array[i], hiddenLayers_array[i+1]);
            hiddenArrayDeltaList.add(deltaList);
        }
        return hiddenArrayDeltaList;
    }

    private List<double[]> calculateDeltaHidden(HiddenLayer hiddenLayer, Layer previousLayer) {
        ActivFunc function = new ActivationFunction(hiddenLayer.getFuncType());
        List<double[]> deltaList = new LinkedList<>();
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

            double derivative = function.derivative(hiddenNeuron.getInputs(), hiddenNeuron.getWeights(), hiddenNeuron.getParams());
            sigmaCurrent = sigmaPrevious[i] * derivative;
            hiddenNeuron.setSigma(sigmaCurrent);

            for (int j = 0; j < inputCount; j++) {
                delta[j] = this.speed * sigmaCurrent * hiddenNeuron.getInput(j);
            }
            deltaList.add(delta);
        }
        return deltaList;
    }
}
