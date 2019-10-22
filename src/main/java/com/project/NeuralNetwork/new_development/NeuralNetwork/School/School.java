package com.project.NeuralNetwork.new_development.NeuralNetwork.School;

import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.IBook;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Lesson.ILesson;
import com.project.NeuralNetwork.new_development.NeuralNetwork.base.Network;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;

public class School implements ISchool {
    private int epochCount;

    public School(int epochCount) {
        this.epochCount = epochCount;
    }

    @Override
    public boolean teach(Network net, IBook book, double startSpeed) {
        ITeacher teacher = new Teacher(net.getFunctionType(), startSpeed);
        t(teacher, net, book);
        return true;
    }

    @Override
    public boolean teach(Network net, DerivativeUserFunction derivativeUserFunction, IBook book, double startSpeed) {
        Teacher teacher = new Teacher(derivativeUserFunction, startSpeed);
        t(teacher, net, book);
        return true;
    }

    private void t(ITeacher teacher, Network net, IBook book) {
        while (epochCount > book.getEpoch()) {
            epochCount--;
            ILesson lesson = book.getLesson();
            net.setInputData(lesson.getQuestion());
            teacher.calculateError(lesson.getAnswer(), net.getOutput());
            teacher.calculateDelta(lesson.getAnswer(), net.getOutputLayer(), net.getHiddenLayerArray());
        }
    }
}
