package com.project.NeuralNetwork.BaseVer2.Training;

public class Gym {
    private int counter; //одна пройденная тренировка (тренировка состоит из сетов, т.е. заранее подготовленных данных) Один пройденный сет - одна эпоха.
    private int epoch; //количество эпох(пройденных сетов тренировок). С ростом эпохи счетчик сета не увеличивается
    private double[] trainingSet;
}
