package com.project.NeuralNetwork.Base.Networks.ConvolutionalNeuralNetwork.kernel;

import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectInitialConditions;
import org.opencv.core.Mat;

public interface Kernel {
    /**
     * Возвращает размер ядра
     * @return размер ядра
     */
    int size();

    /**
     * Поэлементное умножение части изображения на ядро.
     * @param partOfImg часть изображения
     * @return результат поэлементного умножения.
     */
    double[] multiplication(Mat partOfImg) throws NotCorrectInitialConditions;

    /**
     * Получаем матрицу ядра.
     * @return матрицу ядра
     */
    double[][] getKernelArray();
}
