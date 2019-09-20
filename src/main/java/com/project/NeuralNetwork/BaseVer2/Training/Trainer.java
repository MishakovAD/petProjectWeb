package com.project.NeuralNetwork.BaseVer2.Training;

/**
 * https://habr.com/ru/post/312450/
 * https://habr.com/ru/post/313216/
 */
public class Trainer {
    private double reference; //эталон
    private double error = 0; //ошибка
    private double result;
    private double delta;
    private int counter; //счетчик пройденных сетов тренировок.

    public Trainer() {
    }

    public void calculateError(double reference, double result, int counter) {
        this.error = (this.error + (reference - result) * (reference - result)) / (counter + 1);
    }

    public void calculateDelta(boolean outputNeuron) {
        //считаем производную сигмы. Нужно еще передать, какая функция активации используется.
    }

    public double getError() {
        return this.error;
    }
}
