package com.project.NeuralNetwork.Base.Networks.ConvolutionalNeuralNetwork.kernel;

import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectInitialConditions;
import org.opencv.core.Mat;

public class KernelImpl implements Kernel {
    private int size; //рассматриваем пока только квадратные фильтры (ядра), поэтому число строк или колонок = size
    private double[][] kernel;

    public KernelImpl(double[][] kernel) throws NotCorrectInitialConditions {
        if (kernel == null || kernel.length <= 0 || kernel[0].length <= 0) {
            throw new NotCorrectInitialConditions();
        }
        this.kernel = kernel;
        this.size = kernel[0].length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public double[] multiplication(Mat partOfImg) throws NotCorrectInitialConditions {
        if (kernel == null) {
            throw new NotCorrectInitialConditions();
        }
        int channels = partOfImg.channels();
        double[] result = new double[channels];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int c = 0; c < channels; c++) {
                    result[c] += partOfImg.get(i, j)[c] * kernel[i][j];
                    //TODO: проверить везде, где есть работа с матрицами в НС, что есть строка, а что колонка
                }
            }
        }
        return result;
    }

    @Override
    public double[][] getKernelArray() {
        return this.kernel;
    }
}
