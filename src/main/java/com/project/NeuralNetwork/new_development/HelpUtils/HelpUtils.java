package com.project.NeuralNetwork.new_development.HelpUtils;

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
}
