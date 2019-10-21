package com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Lesson;

public class Lesson implements ILesson {
    private double[] question;
    private double[] answer;

    public Lesson(double[] question, double[] answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public double[] getQuestion() {
        return question;
    }

    @Override
    public double[] getAnswer() {
        return answer;
    }
}
