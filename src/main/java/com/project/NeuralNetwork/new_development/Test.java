package com.project.NeuralNetwork.new_development;

import com.project.NeuralNetwork.new_development.Layers.base.Layers;
import com.project.NeuralNetwork.new_development.NeuralNetwork.NeuralNetwork;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.ISchool;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.School;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.Teacher;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.IBook;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;
import com.project.RecognitionImage.backend.OpenCV.OpenCV;
import com.project.RecognitionImage.backend.OpenCV.OpenCVImpl;
import com.project.RecognitionImage.backend.SplitterImage.SplitImageToChar;

import java.time.LocalTime;


public class Test {
    public static void main(String[] args) {
        new Test().xor();
        OpenCV openCV = new OpenCVImpl();
        openCV.init();

        NeuralNetwork net2 = new NeuralNetwork(100, 2, 33, 33, Functions.SIGMA);
        ISchool school = new School(10000000);
        IBook book = new SplitImageToChar().prepareTestSet("src/main/java/com/project/RecognitionImage/backend/OpenCV/test/big_chars.jpg");
        //IBook book = new SplitImageToChar().prepareTestSet("src/main/java/com/project/RecognitionImage/backend/OpenCV/test/nums.jpg");
        //IBook bookTest = new SplitImageToChar().prepareTestSet("src/main/java/com/project/RecognitionImage/backend/OpenCV/test/nums_test.jpg");

        System.out.println(LocalTime.now());
        school.teach(net2, book, 1);
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
        NeuralNetwork net2 = new NeuralNetwork(2, 1, 3, 1, Functions.SIGMA);
        net2.setFuncActivType(Layers.OUTPUT_LAYER, Functions.SIGMA);
        Teacher trainer = new Teacher(1);
        double[] ref1 = new double[1];
        double[] ref2 = new double[1];
        ref1[0] = 1.0;
        ref2[0] = 0.0;
        int globalCounter = 0;
        int counter = 0;
        System.out.println(LocalTime.now());
        while (globalCounter < 10000000/0.001) {
            if (counter == 4) {
                counter = 0;
                globalCounter++;
            }
            if (counter == 0) {
                net2.setInputData(inp1);
                trainer.calculateDelta(ref1, net2.getOutputLayer(), net2.getHiddenLayerArray());
            } else if (counter == 1) {
                net2.setInputData(inp2);
                trainer.calculateDelta(ref2, net2.getOutputLayer(), net2.getHiddenLayerArray());
            } else if (counter == 2) {
                net2.setInputData(inp3);
                trainer.calculateDelta(ref2, net2.getOutputLayer(), net2.getHiddenLayerArray());
            } else if (counter == 3) {
                net2.setInputData(inp4);
                trainer.calculateDelta(ref1, net2.getOutputLayer(), net2.getHiddenLayerArray());
            }
            if (!(net2.getOutput()[0] < 0) && !(net2.getOutput()[0] > 0) && !(net2.getOutput()[0] == 0)) {
                //1028835 -> 1521140 -> 1531444 -> 1735633 -> 418394
                System.out.println();
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
