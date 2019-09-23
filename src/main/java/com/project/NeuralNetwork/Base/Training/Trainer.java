package com.project.NeuralNetwork.Base.Training;

import com.project.NeuralNetwork.Base.Layers.HiddenLayer;
import com.project.NeuralNetwork.Base.Layers.OutputLayer;

/**
 * https://habr.com/ru/post/312450/
 * https://habr.com/ru/post/313216/
 *
 * !!!https://habr.com/ru/post/198268/
 */

//На предыдущий слой передаю дельту, а надо сигму! Ввести новое поле в нейронах и брать для расчетов именно его.
public class Trainer {
    //TODO: Подумать, может корректировка весов будет лучше тут
    private double reference; //эталон
    private double error = 0; //ошибка
    private double result;
    private double[] sigmaOutput;
    private double[] sigmaHidden;
    private double[] sigmaInput;
    private double[] deltaOutput;
    private double[][][] deltaHidden;
    private double[] deltaInput;
    private double speed;
    private int counter; //счетчик пройденных сетов тренировок.
    private int a;

    public Trainer(int a, double speed) {
        this.a = a;
        this.speed = speed;
        //так же можно сюда передать булеан функцию, которую производную считать
    }

    public void calculateError(double reference, double result, int counter) {
        if (counter == 0) {
            this.error = 0;
        }
        this.error = (this.error + (reference - result) * (reference - result)) / (counter + 1);
    }

    public void calculateDeltaForOutput(double[] reference, OutputLayer outputLayer) {
        int neuronsCount = outputLayer.getNeuronsCount();
        for (int i = 0; i < neuronsCount; i++) {
            this.sigmaOutput = new double[neuronsCount];
            this.deltaOutput = new double[outputLayer.getOutputNeuron(i).getCountInputs()];
            double[] weights = outputLayer.getOutputNeuron(i).getWeights();
            double[] inputs = outputLayer.getOutputNeuron(i).getInputs();
            double result = outputLayer.getOutputNeuron(i).getOutput();
            double sum = 0;
            for (int j = 0; j < inputs.length; j++) {
                sum += inputs[j] * weights[j];
            }
            double derivative = (a * Math.exp(-a * sum)) / ((1 + Math.exp(-a * sum)) * (1 + Math.exp(-a * sum)));
            this.sigmaOutput[i] = (reference[i] - result) * derivative;
            for (int inputCount = 0; inputCount < outputLayer.getOutputNeuron(i).getCountInputs(); inputCount++) {
                this.deltaOutput[inputCount] = this.speed * this.sigmaOutput[i] * outputLayer.getOutputNeuron(i).getInput(inputCount);
            }
            outputLayer.getOutputNeuron(i).setDelta(deltaOutput);
        }
    }

    public void calculateDeltaForHidden(HiddenLayer[] hiddenLayerArr, OutputLayer outputLayer) {
        int hiddenLayerCount = hiddenLayerArr.length;
        for (int lev = hiddenLayerCount - 1; lev >= 0; lev--) {
            HiddenLayer hiddenLayer = hiddenLayerArr[lev];
            int countOutputs = hiddenLayer.getHiddenNeuron(0).getCountOutputs();
            int countNeurons = hiddenLayer.getNeuronsCount();
            for (int neuronsCount = 0; neuronsCount < countNeurons; neuronsCount++) { //проходим по всем нейронам в слое
                if (lev == (hiddenLayerCount - 1)) {
                    //Последний скрытый слой
                    double[] sigmaPrevious = new double[countNeurons];
                    double[] sigmaCurrent = new double[countNeurons];
                    for (int i = 0; i < countNeurons; i++) { //считаем для каждого нейрона
                        for (int j = 0; j < countOutputs; j++) { //сумму ошибок на предыдущем уровне
                            sigmaPrevious[i] += outputLayer.getOutputNeuron(j).getDelta()[i] * outputLayer.getOutputNeuron(j).getWeights()[i];
                        }
                    }
                    //вычисляем sigma для скрытого слоя перед выходным.
                    double sum = 0;
                    double[] inputs = hiddenLayer.getHiddenNeuron(neuronsCount).getInputs();
                    double[] weights = hiddenLayer.getHiddenNeuron(neuronsCount).getWeights();
                    for (int j = 0; j < inputs.length; j++) {
                        sum += inputs[j] * weights[j];
                    }
                    double derivative = (a * Math.exp(-a * sum)) / ((1 + Math.exp(-a * sum)) * (1 + Math.exp(-a * sum)));
                    sigmaCurrent[neuronsCount] = sigmaPrevious[neuronsCount] * derivative;

                    //вычисляем изменение весов для текущего слоя.
                    double[] deltaWeights = new double[inputs.length];
                    for (int i = 0; i < deltaWeights.length; i++) {
                        deltaWeights[i] = this.speed * sigmaCurrent[neuronsCount] * hiddenLayer.getHiddenNeuron(neuronsCount).getInput(i);
                    }
                    hiddenLayer.getHiddenNeuron(neuronsCount).setDelta(deltaWeights);
                } else {
                    //Скрытыи слои от входного до последнего (не включая последний)
                    HiddenLayer previousHiddenLayer = hiddenLayerArr[lev + 1];
                    int countInput = hiddenLayer.getHiddenNeuron(0).getCountInputs();
                    double[] sigmaPrevious = new double[countOutputs];
                    double[] sigmaCurrent = new double[countOutputs];
                    for (int i = 0; i < countNeurons; i++) { //считаем для каждого нейрона
                        for (int j = 0; j < countOutputs; j++) { //сумму ошибок на предыдущем уровне
                            sigmaPrevious[i] += previousHiddenLayer.getHiddenNeuron(j).getDelta()[i] * previousHiddenLayer.getHiddenNeuron(j).getWeights()[i];
                        }
                    }
                    //вычисляем sigma для скрытого слоя.
                    double sum = 0;
                    double[] inputs = hiddenLayer.getHiddenNeuron(neuronsCount).getInputs();
                    double[] weights = hiddenLayer.getHiddenNeuron(neuronsCount).getWeights();
                    for (int j = 0; j < inputs.length; j++) {
                        sum += inputs[j] * weights[j];
                    }
                    double derivative = (a * Math.exp(-a * sum)) / ((1 + Math.exp(-a * sum)) * (1 + Math.exp(-a * sum)));
                    sigmaCurrent[neuronsCount] = sigmaPrevious[neuronsCount] * derivative;

                    //вычисляем изменение весов для текущего слоя.
                    double[] deltaWeights = new double[inputs.length];
                    for (int i = 0; i < deltaWeights.length; i++) {
                        deltaWeights[i] = this.speed * sigmaCurrent[neuronsCount] * hiddenLayer.getHiddenNeuron(neuronsCount).getInput(i);
                    }
                    hiddenLayer.getHiddenNeuron(neuronsCount).setDelta(deltaWeights);
                }
            }
        }
    }

    //TODO: разобраться, как происходит обратное распростронение в скрытых слоях. Не рабочий метод.
    public void calculateDeltaForHiddenOld(HiddenLayer[] hiddenLayer, OutputLayer outputLayer) {
        int hiddenLayerCount = hiddenLayer.length; //столбцы
        for (int lev = 0; lev < hiddenLayerCount; lev++) {
            deltaHidden = new double[hiddenLayerCount][hiddenLayer[lev].getNeuronsCount()][hiddenLayer[lev].getHiddenNeuron(0).getCountInputs()];
            if (lev != (hiddenLayerCount - 1)) {
                double[] sigmaIn = new double[hiddenLayer[lev].getNeuronsCount()];
                for (int i = 0; i < sigmaIn.length; i++) {
                    hiddenLayer[lev].getHiddenNeuron(i).setDelta(deltaHidden[lev][i]);
                }
            } else {
                //случай с последним скрытым слоем и выходным
                double[] sigmaIn = new double[outputLayer.getNeuronsCount()];
                double[] sigma = new double[outputLayer.getNeuronsCount()];
                for (int i = 0; i < sigmaIn.length; i++) {
                    for (int j = 0; j < hiddenLayer[lev].getNeuronsCount(); j++) {
                        sigmaIn[i] += outputLayer.getOutputNeuron(i).getDelta()[j] * outputLayer.getOutputNeuron(i).getWeights()[j]; //берем вес i-го нейрона, так как у нас получается к нейрону скрытого слоя идет по связи от выходных нейронов.
                    }
                } //все верно до этого момента. Дальше проверять.
                for (int i = 0; i < sigmaIn.length; i++) {
                    double sum = 0;
                    double[] inputs = hiddenLayer[lev].getHiddenNeuron(i).getInputs();
                    double[] weights = hiddenLayer[lev].getHiddenNeuron(i).getWeights();
                    for (int j = 0; j < inputs.length; j++) {
                        sum += inputs[j] * weights[j];
                    }
                    double derivative = (a * Math.exp(-a * sum)) / ((1 + Math.exp(-a * sum)) * (1 + Math.exp(-a * sum)));
                    sigma[i] = sigmaIn[i] * derivative;
                    double[] deltaCalculationForNeuron = new double[hiddenLayer[lev].getHiddenNeuron(i).getCountInputs()];
                    for (int inpC = 0; inpC < hiddenLayer[lev].getHiddenNeuron(i).getCountInputs(); inpC++) {
                        deltaCalculationForNeuron[inpC] = this.speed * sigma[i] * hiddenLayer[lev].getHiddenNeuron(i).getInputForPreviousLayer(i);
                    }
                    deltaHidden[lev][i] = deltaCalculationForNeuron;
                    hiddenLayer[lev].getHiddenNeuron(i).setDelta(deltaHidden[lev][i]);
                }
            }
        }
    }

    public double getError() {
        return this.error;
    }
}
