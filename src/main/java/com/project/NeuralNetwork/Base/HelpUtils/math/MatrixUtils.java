package com.project.NeuralNetwork.Base.HelpUtils.math;

import com.project.NeuralNetwork.Base.Exceptions.MathExceptions.IncorrectParameters;
import com.project.NeuralNetwork.Base.Exceptions.MathExceptions.InitialisationFailed;
import com.project.NeuralNetwork.Base.Exceptions.MathExceptions.OutOfChannelsIndex;
import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectData;
import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectInitialConditions;
import com.project.NeuralNetwork.Base.HelpUtils.math.base.Matrix;
import com.project.NeuralNetwork.Base.Networks.ConvolutionalNeuralNetwork.kernel.Kernel;

import java.util.List;

public class MatrixUtils {
    /**
     * Свертка изображения по нескольким ядрам
     * @param mat исходное изображение
     * @param kernels ядра, по которым идет свертка
     * @param count число "сверток"
     * @return изображение, после одного уровня свертки
     * @throws NotCorrectData
     * @throws InitialisationFailed
     * @throws NotCorrectInitialConditions
     * @throws OutOfChannelsIndex
     * @throws IncorrectParameters
     */
    public static Matrix convolution(Matrix mat, List<Kernel> kernels, int count)
            throws NotCorrectData, InitialisationFailed, NotCorrectInitialConditions, OutOfChannelsIndex, IncorrectParameters {
        if (count == 0) {
            return mat;
        }
        checkKernelsSize(kernels);
        int rows = mat.rows();
        int cols = mat.cols();
        int kernelSize = kernels.get(0).size();
        Matrix result = new Matrix((rows-(kernelSize-1)), (cols-(kernelSize-1)));
        for (Kernel k : kernels) {
            Matrix r = convolution(mat, k, count);
            result.add(r);
        }
        return convolution(result, kernels, --count);
    }

    /**
     * Свертка изображения
     * @param mat исходное изображение
     * @param kernel ядро, по которому идет свертка
     * @param count число "сверток"
     * @return изображение, после одного уровня свертки
     */
    public static Matrix convolution(Matrix mat, Kernel kernel, int count)
            throws NotCorrectData, NotCorrectInitialConditions, InitialisationFailed, OutOfChannelsIndex, IncorrectParameters {
        if (mat == null || kernel == null) {
            throw new NotCorrectData();
        }
        if (count < 0) {
            throw new NotCorrectInitialConditions();
        }
        if (count == 0) {
            return mat;
        }
        int rows = mat.rows();
        int cols = mat.cols();
        int kernelSize = kernel.size();
        Matrix result = new Matrix((rows-(kernelSize-1)), (cols-(kernelSize-1)));
        double[][] kernelArray = kernel.getKernelArray();

        for (int channel = 0; channel < mat.channels(); channel++) {
            double[][] matrixAfter = new double[rows-(kernelSize-1)][cols-(kernelSize-1)];
            for (int i = 0; i < rows - kernelSize; i++) {
                for (int j = 0; j < cols - kernelSize; j++) {
                    double[][] partOfMatrix = mat
                            .getPartOfMatrix(i, i+kernelSize, j, j+kernelSize)
                            .getMatrix(channel);
                    double point = consistentMultiplication(partOfMatrix, kernelArray);
                    matrixAfter[i][j] = point;
                }
            }
            result.add(matrixAfter);
        }
        return convolution(result, kernel, --count);
    }

    /**
     * Метод, который выбирает самое большое значение из области размером size
     * @param matrix исходное изображение
     * @param size размер "скользящей" области
     * @param count число "сверток"(пуллингов)
     * @return свернутое изображение
     */
    public static Matrix pooling(Matrix matrix, int size, int count)
            throws NotCorrectInitialConditions, IncorrectParameters, InitialisationFailed, OutOfChannelsIndex, NotCorrectData {
        if (matrix == null || size < 0 || count < 0) {
            throw new NotCorrectInitialConditions();
        }
        if (count == 0 || size == 0) {
            return matrix;
        }
        Matrix result = new Matrix((int)(matrix.rows()/size), (int)(matrix.cols()/size));
        int row = 0;
        int col = 0;
        for (int i = 0; i < matrix.rows()-size; i+=size) {
            for (int j = 0; j < matrix.cols()-size; j+=size) {
                for (int c = 0; c < matrix.channels(); c++) {
                    double[][] partOfImg = matrix
                            .getPartOfMatrix(i, i+size, j, j+size)
                            .getMatrix(c);
                    result.put(row, col, c, getMinElement(partOfImg));
                }
                col++;
            }
            col = 0;
            row++;
        }
        return pooling(result, size, --count);
    }

    public static double[][] multiplication(double[][] mat1, double[][] mat2) throws NotCorrectInitialConditions, NotCorrectData {
        if (mat1 == null || mat2 == null
                || mat1.length <= 0 || mat2.length <= 0
                || mat1[0].length <= 0 || mat2[0].length <= 0) {
            throw new NotCorrectData();
        }
        if (mat1[0].length != mat2.length) {
            throw new NotCorrectInitialConditions();
        }
        //l x m && m x n
        int l = mat1.length; //строк в первой матрице
        int m = mat1[0].length; //столбцов в первой матрице == строк во второй матрице
        int n = mat2[0].length; //столбцов во второй матрице
        double[][] result = new double[l][n];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < n; j++) {
                double[] row1 = mat1[i];
                double[] col2 = getCol(mat2, j);
                double multipli = 0;
                for (int k = 0; k < m; k++) {
                    multipli += row1[k] * col2[k];
                }
                result[i][j] = multipli;
            }
        }
        return result;
    }

    private static double[] getCol(double[][] array, int colNumber) {
        double[] col = new double[array[colNumber].length];
        for (int i = 0; i < array.length; i++) {
            col[i] = array[i][colNumber];
        }
        return col;
    }

    private static double consistentMultiplication(double[][] mat1, double[][] mat2) {
        double result = 0;
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[0].length; j++) {
                result += mat1[i][j] * mat2[i][j];
            }
        }
        return result;
    }

    /**
     * Возвращает массив с минимальными элементами из всей матрицы
     * размер массива определяет число каналов матрицы.
     * Актуален для черного цвета, так как он == 0, а белый == 255.
     * @param partOfImg матрица - часть изображения
     * @return массив по каналам минимальных элементов
     */
    private static double getMinElement(double[][] partOfImg) {
        double result = partOfImg[0][0];
        for (int i = 0; i < partOfImg.length; i++) {
            for (int j = 0; j < partOfImg[0].length; j++) {
                if (partOfImg[i][j] < result){
                    result = partOfImg[i][j];
                }
            }
        }
        return result;
    }

    private static void checkKernelsSize(List<Kernel> kernels) throws NotCorrectData {
        if (kernels.size() == 0) {
            throw new NotCorrectData();
        }
        int rows = kernels.get(0).getKernelArray().length;
        if (rows <= 0) {
            throw new NotCorrectData();
        }
        int cols = kernels.get(0).getKernelArray()[0].length;
        if (cols <= 0) {
            throw new NotCorrectData();
        }
        boolean error = kernels
                .stream()
                .anyMatch(kernel -> kernel.getKernelArray().length != rows
                        && kernel.getKernelArray()[0].length != cols);
        if (error) {
            throw new NotCorrectData();
        }
    }
}
