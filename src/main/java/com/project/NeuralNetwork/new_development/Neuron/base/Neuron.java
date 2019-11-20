package com.project.NeuralNetwork.new_development.Neuron.base;

import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

public interface Neuron {
    /**
     * Задает входные значения нейрона.
     * @param inputs входные данные
     */
    void setInputs(double[] inputs);

    /**
     * Задает данные для входного нейрона.
     * @param inputData входные данные
     */
    void setInput(double inputData);

    /**
     * Задает веса входов нейрона.
     * @param weights веса
     */
    void setWeights(double[] weights);

    /**
     * Возвращает выходной сигнал нейрона.
     * @return выходной сигнал
     */
    double getOutput();

    /**
     * Возвращает массив входных сигналов нейрона.
     * @return массив входных сигналов
     */
    double[] getInputs();

    /**
     * Возвращает входной сигнал согласно индексу.
     * @param index номер входного сигнала
     * @return входной сигнал
     */
    double getInput(int index);

    /**
     * Возвращает количество входов нейрона.
     * @return количество входов
     */
    int getInputsCount();

    /**
     * Возвращает массив весов нейрона.
     * @return массив весов
     */
    double[] getWeights();

    /**
     * Возвращает вес определенного входа.
     * @param index номер входа
     * @return вес
     */
    double getWeight(int index);

    /**
     * Возвращает вид функции активации.
     * @return вид функции активации
     */
    Functions whichActivationFunction();

    /**
     * Устанавливает тип функции активации.
     * @param funcType тип функции активации
     */
    void setActivFuncType(Functions funcType);

    /**
     * Возвращает тип функции активации нейрона.
     * @return тип ФА
     */
    Functions getFuncType();

    /**
     * Устанавливает значение параметра a - порога функции скачка/крутизна сигмы(тангенса)
     * @param a параметр/крутизна
     */
    void setParameterA(double a);

    /**
     * Возвращает параметры функции активации.
     * @return параметр "а"
     */
    double[] getParams();

    //-----------------------------------Метод обратного распространения ошибки-----------------------------------\\

    /**
     * Устанавливает значения delta для корректировки весов.
     * @param delta значения для корректировки
     */
    void setDelta(double[] delta);

    /**
     * Пересчитывает веса, согласно установленным delta у нейронов.
     */
    void correctWeights();

    /**
     * Возвращает массив значений для корректировки весов
     * @return массив для корректировки
     */
    double[] getDelta();

    /**
     * Устанавливает значение sigma ошибки для нейрона.
     * @param sigma ошибка
     */
    void setSigma(double sigma);

    /**
     * Возвращает значение ошибки нейрона
     * @return sigma
     */
    double getSigma();
}
