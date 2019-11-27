package com.project.NeuralNetwork.Base.HelpUtils;

import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectData;
import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectInitialConditions;
import com.project.NeuralNetwork.Base.Networks.ConvolutionalNeuralNetwork.kernel.Kernel;
import com.project.NeuralNetwork.Base.Networks.ConvolutionalNeuralNetwork.kernel.KernelImpl;
import com.project.RecognitionImage.backend.OpenCV.OpenCVImpl;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class HelpUtils {
    /**
     * Сумматор.
     * @param inputs входные значения
     * @param weights веса
     * @return сумму со смещением
     */
    public static double adder(double[] inputs, double[] weights) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }
        int offset = 1;
        sum += offset;
        return sum;
    }

    /**
     * Свертка изображения
     * @param img
     * @param kernel
     * @return изображение, после одного уровня свертки
     */
    public static Mat convolution(Mat img, Kernel kernel) throws NotCorrectData, NotCorrectInitialConditions {
        if (img == null || kernel == null) {
            throw new NotCorrectData();
        }
        int kernelSize = kernel.size();
        Mat partOfImg = new Mat(kernelSize, kernelSize, img.type());
        Mat result = new Mat((img.rows()-(kernelSize-1)), (img.cols()-(kernelSize-1)), img.type());

        for (int i = 0; i < img.rows() - kernelSize; i++) {
            for (int j = 0; j < img.cols() - kernelSize; j++) {
                partOfImg = img.rowRange(i, i+kernelSize).colRange(j, j+kernelSize);
                double[] multipli = kernel.multiplication(partOfImg);
                result.put(i, j, multipli);
            }
        }
        return result;
    }

    public static void main(String[] args) throws NotCorrectInitialConditions, NotCorrectData {
        OpenCVImpl cv = new OpenCVImpl();
        cv.init();
        double[][] test = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
        Kernel testKernel = new KernelImpl(test, 1);
        Mat img = cv.loadImage("src/main/java/com/project/RecognitionImage/backend/OpenCV/test/" + "russian_text.png", Imgcodecs.IMREAD_COLOR);
        convolution(img, testKernel);
    }


}
