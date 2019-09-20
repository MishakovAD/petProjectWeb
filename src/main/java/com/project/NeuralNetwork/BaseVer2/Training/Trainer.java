package com.project.NeuralNetwork.BaseVer2.Training;

import com.project.NeuralNetwork.BaseVer2.Layers.HiddenLayer;
import com.project.NeuralNetwork.BaseVer2.Layers.OutputLayer;

/**
 * https://habr.com/ru/post/312450/
 * https://habr.com/ru/post/313216/
 *
 * !!!https://habr.com/ru/post/198268/
 */
public class Trainer {
    private double reference; //эталон
    private double error = 0; //ошибка
    private double result;
    private double[] sigmaOutput;
    private double[] sigmaHidden;
    private double[] sigmaInput;
    private double[] deltaOutput;
    private double[][] deltaHidden;
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
        this.error = (this.error + (reference - result) * (reference - result)) / (counter + 1);
    }

    public void calculateDeltaForOutput(double[] reference, OutputLayer outputLayer) {
        int neuronsCount = outputLayer.getNeuronsCount();
        this.sigmaOutput = new double[neuronsCount];
        this.deltaOutput = new double[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            double[] weights = outputLayer.getOutputNeuron(i).getWeights();
            double[] inputs = outputLayer.getOutputNeuron(i).getInputs();
            double result = outputLayer.getOutputNeuron(i).getOutput();
            double sum = 0;
            for (int j = 0; j < inputs.length; j++) {
                sum += inputs[j] * weights[j];
            }
            double derivative = (a * Math.exp(-a * sum)) / ((1 + Math.exp(-a * sum)) * (1 + Math.exp(-a * sum)));
            this.sigmaOutput[i] = (reference[i] - result) * derivative;
            this.deltaOutput[i] = this.speed * this.sigmaOutput[i] * result;
            outputLayer.getOutputNeuron(i).setDelta(deltaOutput[i]);
        }
    }

    //TODO: разобраться, как происходит обратное распростронение в скрытых слоях.
    public void calculateDeltaForHidden(HiddenLayer[] hiddenLayer, OutputLayer outputLayer) {
        int hiddenLayerCount = hiddenLayer.length; //столбцы
        deltaHidden = new double[hiddenLayer[0].getNeuronsCount()][hiddenLayerCount];
        for (int lev = 0; lev < hiddenLayerCount; lev++) {
            if (lev != (hiddenLayerCount - 1)) {
                double[] sigmaIn = new double[hiddenLayer[lev].getNeuronsCount()];
            } else {
                //случай с последним скрытым слоем и выходным
                double[] sigmaIn = new double[outputLayer.getNeuronsCount()];
                double[] sigma = new double[outputLayer.getNeuronsCount()];
                for (int i = 0; i < sigmaIn.length; i++) {
                    for (int j = 0; j < outputLayer.getNeuronsCount(); j++) {
                        sigmaIn[i] += outputLayer.getOutputNeuron(j).getDelta() * outputLayer.getOutputNeuron(j).getWeights()[i]; //берем вес i-го нейрона, так как у нас получается к нейрону скрытого слоя идет по связи от выходных нейронов.
                    }
                    double sum = 0;
                    double[] inputs = hiddenLayer[lev].getHiddenNeuron(i).getInputs();
                    double[] weights = hiddenLayer[lev].getHiddenNeuron(i).getWeights();
                    for (int j = 0; j < inputs.length; j++) {
                        sum += inputs[j] * weights[j];
                    }
                    double derivative = (a * Math.exp(-a * sum)) / ((1 + Math.exp(-a * sum)) * (1 + Math.exp(-a * sum)));
                    sigma[i] = sigmaIn[i] * derivative;
                    for (int inpC = 0; inpC < hiddenLayer[lev].getHiddenNeuron(i).getCountInputs(); inpC++) {

                    }
                    deltaHidden[i][lev] = this.speed * sigma[i] * hiddenLayer[lev].getHiddenNeuron(i).getOutput(); //TODO: ЧТО ТУТ БЕРЕТСЯ, ВМЕСТО ВЫХОДА??????
                    hiddenLayer[lev].getHiddenNeuron(i).setDelta(deltaHidden[i][lev]);
                }
            }


        }
    }

    public double getError() {
        return this.error;
    }
}
