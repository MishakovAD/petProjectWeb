package com.project.NeuralNetwork.new_development;

import com.project.NeuralNetwork.new_development.NeuralNetwork.NeuralNetwork;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.Teacher;
import com.project.RecognitionImage.backend.OpenCV.OpenCV;
import com.project.RecognitionImage.backend.OpenCV.OpenCVImpl;
import org.opencv.core.Mat;

import java.time.LocalTime;

import static com.project.RecognitionImage.backend.OpenCV.Utils.OpenCVUtils.getGrayMat;


public class Test {
    public static void main(String[] args) {
        //new Test().xor();
        OpenCV openCV = new OpenCVImpl();
        openCV.init();
        Mat picture_a = getGrayMat(openCV.loadImage("C:/Java_Projects/petProjectWeb/src/main/java/com/project/NeuralNetwork/new_development/NeuralNetwork/School/test_set/a_10x10.png"));
        Mat etalon_a_ = getGrayMat(openCV.loadImage("C:/Java_Projects/petProjectWeb/src/main/java/com/project/NeuralNetwork/new_development/NeuralNetwork/School/test_set/a.png"));
        Mat picture_b = getGrayMat(openCV.loadImage("C:/Java_Projects/petProjectWeb/src/main/java/com/project/NeuralNetwork/new_development/NeuralNetwork/School/test_set/b_10x10.png"));
        Mat etalon_b_ = getGrayMat(openCV.loadImage("C:/Java_Projects/petProjectWeb/src/main/java/com/project/NeuralNetwork/new_development/NeuralNetwork/School/test_set/b.png"));
        Mat picture_B = getGrayMat(openCV.loadImage("C:/Java_Projects/petProjectWeb/src/main/java/com/project/NeuralNetwork/new_development/NeuralNetwork/School/test_set/v_10x10.png"));
        Mat etalon_B_ = getGrayMat(openCV.loadImage("C:/Java_Projects/petProjectWeb/src/main/java/com/project/NeuralNetwork/new_development/NeuralNetwork/School/test_set/v.png"));
        double[] pic_a = new double[100];
        double[] etalon_a = new double[100];
        double[] pic_b = new double[100];
        double[] etalon_b = new double[100];
        double[] pic_V = new double[100];
        double[] etalon_V = new double[100];
        int index = 0;
        for (int i = 0; i < picture_a.rows(); i++) {
            for (int j = 0; j < picture_a.cols(); j++) {
                double currentPixel_a = picture_a.get(i, j)[0] + 1;
                double currentPixel_a_etalon = etalon_a_.get(i, j)[0] + 1;
                double currentPixel_b = picture_b.get(i, j)[0] + 1;
                double currentPixel_b_etalon = etalon_b_.get(i, j)[0] + 1;
                double currentPixel_B = picture_B.get(i, j)[0] + 1;
                double currentPixel_B_etalon = etalon_B_.get(i, j)[0] + 1;
                pic_a[index] = 1/currentPixel_a;
                etalon_a[index] = 1/currentPixel_a_etalon;
                pic_b[index] = 1/currentPixel_b;
                etalon_b[index] = 1/currentPixel_b_etalon;
                pic_V[index] = 1/currentPixel_B;
                etalon_V[index] = 1/currentPixel_B_etalon;
//                pic_a[index] = currentPixel_a;
//                etalon_a[index] = currentPixel_a_etalon;
//                pic_b[index] = currentPixel_b;
//                etalon_b[index] = currentPixel_b_etalon;
//                pic_V[index] = currentPixel_B;
//                etalon_V[index] = currentPixel_B_etalon;
                index++;
            }
        }
        NeuralNetwork net2 = new NeuralNetwork(100, 4, 16, 3);
        Teacher trainer = new Teacher(net2.getFunctionType(), 1);
        int globalCounter = 0;
        int counter = 0;
        double[] a = new double[3];
        a[0] = 1;
        a[1] = 0;
        a[2] = 0;
        double[] b = new double[3];
        b[0] = 0;
        b[1] = 1;
        b[2] = 0;
        double[] v = new double[3];
        v[0] = 0;
        v[1] = 0;
        v[2] = 1;
        System.out.println(LocalTime.now());
        while (globalCounter < 10000000) {
            if (counter == 3) {
                counter = 0;
                globalCounter++;
            }
            if (counter == 0) {
                net2.setInputData(pic_a);
                trainer.calculateDeltaOutput(a, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 1) {
                net2.setInputData(pic_b);
                trainer.calculateDeltaOutput(b, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 2) {
                net2.setInputData(pic_V);
                trainer.calculateDeltaOutput(v, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            }
            counter++;
        }
        System.out.println(LocalTime.now());
        System.out.println();
    }

    private void xor() {
        double[] inp1 = new double[2];
        inp1[0] = 1.0;
        inp1[1] = 0.0;
        double[] inp2 = new double[2];
        inp2[0] = 0.0;
        inp2[1] = 0.0;
        double[] inp3 = new double[2];
        inp3[0] = 1.0;
        inp3[1] = 1.0;
        double[] inp4 = new double[2];
        inp4[0] = 0.0;
        inp4[1] = 1.0;
        NeuralNetwork net2 = new NeuralNetwork(2, 1, 3, 1);
        Teacher trainer = new Teacher(net2.getFunctionType(), 10);

        int globalCounter = 0;
        int counter = 0;
        System.out.println(LocalTime.now());
        while (globalCounter < 10000000) {
            if (counter == 4) {
                counter = 0;
                globalCounter++;
            }
            double[] ref1 = new double[1];
            double[] ref2 = new double[1];
            ref1[0] = 1.0;
            ref2[0] = 0.0;
            if (counter == 0) {
                net2.setInputData(inp1);
                trainer.calculateDeltaOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 1) {
                net2.setInputData(inp2);
                trainer.calculateDeltaOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 2) {
                net2.setInputData(inp3);
                trainer.calculateDeltaOutput(ref1, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            } else if (counter == 3) {
                net2.setInputData(inp4);
                trainer.calculateDeltaOutput(ref2, net2.getOutputLayer());
                trainer.calculateDeltaHidden(net2.getHiddenLayerArray(), net2.getOutputLayer());
            }
            counter++;
        }
        System.out.println(LocalTime.now());
    }

    //Метод, основа в ДемоОпенЦВ для обрисовки контруа букв
    //важно обратить внимание на то, откуда импортируется Mat
    private void contursFind() {
//        org.bytedeco.opencv.opencv_core.Mat p = new org.bytedeco.opencv.opencv_core.Mat(picture_a.rows(), picture_a.cols(), CV_THRESH_BINARY);
//        MatVector contours = new MatVector();
//        threshold(p, p, 64, 255, CV_THRESH_BINARY);
//        findContours(p, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
//        long n = contours.size();
//        for (long i = 0; i < n; i++) {
//            org.bytedeco.opencv.opencv_core.Mat contour = contours.get(i);
//            org.bytedeco.opencv.opencv_core.Mat points = new org.bytedeco.opencv.opencv_core.Mat();
//            approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
//            drawContours(p, new MatVector(points), -1, Scalar.BLUE);
//        }
    }
}
