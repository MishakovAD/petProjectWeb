package com.project.NeuralNetwork.new_development.NeuralNetwork.School;

import com.project.NeuralNetwork.new_development.Layers.HiddenLayer;
import com.project.NeuralNetwork.new_development.Layers.OutputLayer;
import com.project.NeuralNetwork.new_development.Layers.base.Layer;

public interface ITeacher {
    /**
     * Метод подсчета ошибки.
     * @param ideal правильное значение
     * @param result выход нейронной сети
     * @param counter счетчик итераций (не эпох)
     * @return значение ошибки
     */
    double calculateError(double ideal, double result, int counter);

    /**
     * Метод подстчета delta для выходнго слоя и запуска корректировки весов.
     * @param ideal правильное значение
     * @param outputLayer выходной слой
     */
    void calculateDeltaOutput(double[] ideal, OutputLayer outputLayer);

    /**
     * Метод подстчета delta для всех скрытых слоев и запуска корректировки весов.
     * @param hiddenLayers_array скрытые слои в НС
     * @param outputLayer выходной слой для расчетов
     */
    void calculateDeltaHidden(HiddenLayer[] hiddenLayers_array, OutputLayer outputLayer);
}
