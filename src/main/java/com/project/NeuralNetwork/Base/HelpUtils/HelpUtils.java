package com.project.NeuralNetwork.Base.HelpUtils;

import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectData;
import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectInitialConditions;
import com.project.NeuralNetwork.Base.Networks.ConvolutionalNeuralNetwork.kernel.Kernel;
import com.project.NeuralNetwork.Base.Networks.ConvolutionalNeuralNetwork.kernel.KernelImpl;
import com.project.RecognitionImage.backend.OpenCV.OpenCVImpl;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import static com.project.RecognitionImage.backend.OpenCV.OpenCVImpl.showImage;
import static com.project.RecognitionImage.backend.OpenCV.Utils.OpenCVUtils.zipMatToSize;

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
     * @param img исходное изображение
     * @param kernel ядро, по которому идет свертка
     * @param count число "сверток"
     * @return изображение, после одного уровня свертки
     */
    public static Mat convolution(Mat img, Kernel kernel, int count) throws NotCorrectData, NotCorrectInitialConditions {
        if (img == null || kernel == null) {
            throw new NotCorrectData();
        }
        if (count == 0) {
            return img;
        }
        int kernelSize = kernel.size();
        Mat partOfImg = null;
        Mat result = new Mat((img.rows()-(kernelSize-1)), (img.cols()-(kernelSize-1)), img.type());

        for (int i = 0; i < img.rows() - kernelSize; i++) {
            for (int j = 0; j < img.cols() - kernelSize; j++) {
                partOfImg = img.rowRange(i, i+kernelSize).colRange(j, j+kernelSize);
                double[] multipli = kernel.multiplication(partOfImg);
                result.put(i, j, multipli);
            }
        }
        return convolution(result, kernel, --count);
    }

    /**
     * Метод, который выбирает самое большое значение из области размером size
     * @param img исходное изображение
     * @param size размер "скользящей" области
     * @param count число "сверток"(пуллингов)
     * @return свернутое изображение
     */
    public static Mat pooling(Mat img, int size, int count) throws NotCorrectInitialConditions {
        if (img == null || size < 0 || count < 0) {
            throw new NotCorrectInitialConditions();
        }
        if (count == 0 || size == 0) {
            return img;
        }
        Mat result = new Mat((int)(img.rows()/size), (int)(img.cols()/size), img.type());
        Mat partOfImg = null;
        int row = 0;
        int col = 0;
        for (int i = 0; i < img.rows()-size; i+=size) {
            for (int j = 0; j < img.cols()-size; j+=size) {
                partOfImg = img.rowRange(i, i+size).colRange(j, j+size);
                result.put(row, col, getMinElement(partOfImg));
                col++;
            }
            col = 0;
            row++;
        }
        return pooling(result, size, --count);
    }

    /**
     * Возвращает массив с минимальными элементами из всей матрицы
     * размер массива определяет число каналов матрицы.
     * Актуален для черного цвета, так как он == 0, а белый == 255.
     * @param partOfImg матрица - часть изображения
     * @return массив по каналам минимальных элементов
     */
    private static double[] getMinElement(Mat partOfImg) {
        int channels = partOfImg.channels();
        double[] result = new double[channels];
        for (int c = 0; c < channels; c++) {
            result[c] = partOfImg.get(0, 0)[c];
        }
        for (int i = 0; i < partOfImg.rows(); i++) {
            for (int j = 0; j < partOfImg.cols(); j++) {
                for (int c = 0; c < channels; c++) {
                    if (partOfImg.get(i, j)[c] < result[c]){
                        result[c] = partOfImg.get(i, j)[c];
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws NotCorrectInitialConditions, NotCorrectData {
        OpenCVImpl cv = new OpenCVImpl();
        cv.init();
        double[][] test = {{0, 1, 0}, {1, -1, 1}, {0, 1, 0}};
        Kernel testKernel = new KernelImpl(test);
        Mat img = cv.loadImage("src/main/java/com/project/RecognitionImage/backend/OpenCV/test/" + "nums.jpg", Imgcodecs.IMREAD_COLOR);
        //Mat img = cv.loadImage("src/main/java/com/project/RecognitionImage/backend/OpenCV/test/" + "captcha/1.jpg", Imgcodecs.IMREAD_COLOR);
        showImage(img, "Before");
        Mat zipImg = zipMatToSize(img, 300, 50);
        Mat res = pooling(img, 2, 1);
        Mat result = //convolution(
                        //convolution(
                        convolution(res,
                                //testKernel),
                                //testKernel),
                                testKernel, 1);
        showImage(result, "After");
    }


}
