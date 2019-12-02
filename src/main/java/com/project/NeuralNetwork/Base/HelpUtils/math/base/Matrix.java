package com.project.NeuralNetwork.Base.HelpUtils.math.base;

import com.project.NeuralNetwork.Base.Exceptions.MathExceptions.IncorrectParameters;
import com.project.NeuralNetwork.Base.Exceptions.MathExceptions.InitialisationFailed;
import com.project.NeuralNetwork.Base.Exceptions.MathExceptions.OutOfChannelsIndex;
import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectData;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

//TODO: Решить, что делать с цветными изображениями с 3 каналами, т.к. после применения к ним ядра, необходимо разделенные на 3 канала матрицы собрать снова в одну.
//https://medium.com/@balovbohdan/%D0%B3%D0%BB%D1%83%D0%B1%D0%BE%D0%BA%D0%BE%D0%B5-%D0%BE%D0%B1%D1%83%D1%87%D0%B5%D0%BD%D0%B8%D0%B5-%D1%80%D0%B0%D0%B7%D0%B1%D0%B8%D1%80%D0%B0%D0%B5%D0%BC%D1%81%D1%8F-%D1%81%D0%BE-%D1%81%D0%B2%D0%B5%D1%80%D1%82%D0%BA%D0%B0%D0%BC%D0%B8-6e47bfc27792
public class Matrix {
    private List<double[][]> mat;
    private int rows;
    private int cols;

    public Matrix(int rows, int cols) throws InitialisationFailed {
        if (rows <= 0 || cols <= 0) {
            throw new InitialisationFailed();
        }
        this.rows = rows;
        this.cols = cols;
        mat = new ArrayList<>();
    }

    public Matrix(Mat mat) throws InitialisationFailed {
        if (mat == null) {
            throw new InitialisationFailed();
        }
        this.rows = mat.rows();
        this.cols = mat.cols();
        this.mat = new ArrayList<>();
        add(mat);
    }

    public void add(double[][] data) throws InitialisationFailed {
        if (data.length != rows || data[0].length != cols) {
            throw new InitialisationFailed();
        }
        mat.add(data);
    }

    public void add(double[][][] data) throws InitialisationFailed {
        if (data.length != rows || data[0].length != cols) {
            throw new InitialisationFailed();
        }
        List<double[][]> helpList = new ArrayList<>();
        for (int c = 0; c < data[0][0].length; c++) {
            double[][] newArray = new double[rows][cols];
            helpList.add(newArray);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int c = 0; c < data[0][0].length; c++) {
                    helpList.get(c)[i][j] = data[i][j][c];
                }
            }
        }
        mat.addAll(helpList);
    }

    public void add(Mat data) throws InitialisationFailed {
        if (data.rows() != rows || data.cols() != cols) {
            throw new InitialisationFailed();
        }
        List<double[][]> helpList = new ArrayList<>();
        for (int c = 0; c < data.channels(); c++) {
            double[][] newArray = new double[rows][cols];
            helpList.add(newArray);
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int c = 0; c < data.channels(); c++) {
                    helpList.get(c)[i][j] = data.get(i, j)[c];
                }
            }
        }
        mat.addAll(helpList);
    }

    public void add(Matrix matrix) throws InitialisationFailed {
        if (matrix == null) {
            throw new InitialisationFailed();
        }
        List<double[][]> mat = matrix.getMatrix();
        for (double[][] arr : mat) {
            this.mat.add(arr);
        }
    }

    public void put(int row, int col, int channel, double data) throws NotCorrectData {
        if (channel >= channels()) {
            this.mat.add(new double[rows][cols]);
        }
        if (channel < 0 || row >= rows() || row < 0 || col >= cols() || col < 0) {
            throw new NotCorrectData();
        }
        this.mat.get(channel)[row][col] = data;
    }

    public double[][] getMatrix(int channel) throws OutOfChannelsIndex {
        if (channel >= mat.size()) {
            throw new OutOfChannelsIndex();
        }
        return mat.get(channel);
    }

    public Matrix rowRange(int startIndex, int endIndex) throws InitialisationFailed, IncorrectParameters {
        if (startIndex < 0 || endIndex < 0 || endIndex < startIndex) {
            throw new IncorrectParameters();
        }
        int rows = endIndex - startIndex;
        Matrix partOfRows = new Matrix(rows, this.cols);
        for (int c = 0; c < channels(); c++) {
            int row = 0;
            double[][] matrix = mat.get(c);
            double[][] newMatrix = new double[rows][this.cols];
            for (int i = startIndex; i < endIndex; i++) {
                for (int j = 0; j < this.cols; j++) {
                    newMatrix[row][j] = matrix[i][j];
                }
                row++;
            }
            partOfRows.add(newMatrix);
        }
        return partOfRows;
    }

    public Matrix colRange(int startIndex, int endIndex) throws InitialisationFailed, IncorrectParameters {
        if (startIndex < 0 || endIndex < 0 || endIndex < startIndex) {
            throw new IncorrectParameters();
        }
        int cols = endIndex - startIndex;
        Matrix partOfCols = new Matrix(this.rows, cols);
        for (int c = 0; c < channels(); c++) {
            double[][] matrix = mat.get(c);
            double[][] newMatrix = new double[this.rows][cols];
            for (int i = 0; i < this.rows; i++) {
                int col = 0;
                for (int j = startIndex; j < endIndex; j++) {
                    newMatrix[i][col] = matrix[i][j];
                    col++;
                }
            }
            partOfCols.add(newMatrix);
        }
        return partOfCols;
    }

    public Matrix getPartOfMatrix(int startIndexRow, int endIndexRow, int startIndexCol, int endIndexCol)
            throws IncorrectParameters, InitialisationFailed {
        if (startIndexRow < 0 || endIndexRow < 0 || endIndexRow < startIndexRow
        || startIndexCol < 0 || endIndexCol < 0 || endIndexCol < startIndexCol) {
            throw new IncorrectParameters();
        }
        int rows = endIndexRow - startIndexRow;
        int cols = endIndexCol - startIndexCol;
        Matrix part = new Matrix(rows, cols);
        for (int c = 0; c < channels(); c++) {
            double[][] matrix = mat.get(c);
            double[][] newMatrix = new double[rows][cols];
            int row = 0;
            for (int i = startIndexRow; i < endIndexRow; i++) {
                int col = 0;
                for (int j = startIndexCol; j < endIndexCol; j++) {
                    newMatrix[row][col] = matrix[i][j];
                    col++;
                }
                row++;
            }
            part.add(newMatrix);
        }
        return part;
    }

    public Mat convertMatrixToMat(Matrix matrix) throws OutOfChannelsIndex {
        //TODO: сделать проверки на тип матрицы, число каналов и прочее. Черновой вариант.
        Mat res = new Mat(matrix.rows(), matrix.cols(), 0);
        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                double point[] = new double[matrix.channels()];
                for (int c = 0; c < matrix.channels(); c++) {
                    point[c] = matrix.getMatrix(c)[i][j];
                }
                res.put(i, j, point);
            }
        }
        return res;
    }

    public List<double[][]> getMatrix() {
        return mat;
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public int channels() {
        return mat.size();
    }

}
