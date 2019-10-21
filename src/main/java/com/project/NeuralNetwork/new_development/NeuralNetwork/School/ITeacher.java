package com.project.NeuralNetwork.new_development.NeuralNetwork.School;

import com.project.NeuralNetwork.new_development.Layers.HiddenLayer;
import com.project.NeuralNetwork.new_development.Layers.OutputLayer;
import com.project.NeuralNetwork.new_development.Layers.base.Layer;

import java.util.List;

public interface ITeacher {
    /**
     * Метод подсчета ошибки.
     * @param ideal вектор верного ответа НС (размер == числу выходных нейронов)
     * @param result вектор выходных значений НС
     * @return значение ошибки
     */
    double calculateError(double ideal[], double[] result);

    /**
     * Возвращает лист ранее посчитанных ошибок.
     * Можно отследить уменьшение или увеличение ошибки.
     * @return лист всех посчитанных ошибок.
     */
    List<Double> getPreviousErrors();

    /**
     * Выполняет очисту "записанных" ошибок НС.
     */
    void clearErrors();

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

    /**
     * Устанавливает скорость обучения НС.
     * @param speed скорость обучения
     */
    void setSpeed(double speed);
}
