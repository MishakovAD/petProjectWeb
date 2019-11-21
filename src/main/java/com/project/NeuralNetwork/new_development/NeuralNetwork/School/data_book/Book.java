package com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book;

import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Lesson.ILesson;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Lesson.Lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Book implements IBook {
    private List<double[]> questions;
    private List<double[]> answers;
    private List<Integer> canUsedLesson;
    private int size;
    private int epochCount = 0;

    public Book() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        canUsedLesson = new ArrayList<>();
        size = 0;
    }

    @Override
    public boolean addData(double[] data, double[] answer) {
        canUsedLesson.add(questions.size());
        questions.add(data);
        size++;
        return answers.add(answer);
    }

    @Override
    public boolean setData(int index, double[] data) {
        questions.set(index, data);
        return true;
    }

    @Override
    public double[] getQuestion(int index) {
        return questions.get(index);
    }

    @Override
    public ILesson getLesson() {
        if (canUsedLesson.size() == 0) {
            epochCount++;
            for (int i = 0; i < questions.size(); i++) {
                canUsedLesson.add(i);
            }
        }
        Random r = new Random(System.currentTimeMillis());
        int randomIndex = canUsedLesson.remove(r.nextInt(canUsedLesson.size()));
        double[] d = questions.get(randomIndex);
        double[] a = answers.get(randomIndex);
        return new Lesson(d, a);
    }

    @Override
    public int getEpoch() {
        return epochCount;
    }
}
