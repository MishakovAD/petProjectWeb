package com.project.NeuralNetwork.new_development.NeuralNetwork.School;

import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.IBook;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Lesson.ILesson;
import com.project.NeuralNetwork.new_development.NeuralNetwork.base.Network;
import com.project.NeuralNetwork.new_development.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.user_function.UserFunction;

public class School implements ISchool {
    private int epochCount;

    public School(int epochCount) {
        this.epochCount = epochCount;
    }

    @Override
    public boolean teach(Network net, IBook book, double startSpeed) {
        ITeacher teacher = new Teacher(startSpeed);
        t(teacher, net, book);
        return true;
    }

    @Override
    public boolean teach(Network net, UserFunction userFunction, DerivativeUserFunction derivativeUserFunction, IBook book, double startSpeed) {
        Teacher teacher = new Teacher(startSpeed);
        t(teacher, net, book);
        return true;
    }

    private void t(ITeacher teacher, Network net, IBook book) {
        while (epochCount > book.getEpoch()) {
            epochCount--;
            ILesson lesson = book.getLesson();
            net.setInputData(lesson.getQuestion());
            double error = teacher.calculateError(lesson.getAnswer(), net.getOutput());
            teacher.calculateDelta(lesson.getAnswer(), net.getOutputLayer(), net.getHiddenLayerArray());
        }
    }
}
