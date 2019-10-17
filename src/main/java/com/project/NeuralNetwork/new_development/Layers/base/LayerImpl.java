package com.project.NeuralNetwork.new_development.Layers.base;

import com.project.NeuralNetwork.new_development.Neuron.base.Neuron;
import com.project.NeuralNetwork.new_development.Neuron.base.NeuronImpl;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;

public class LayerImpl implements Layer {
    private Neuron[] neurons;
    private double[] data;

    public LayerImpl(int neuronsCount, int inputsCount) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neurons = new Neuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            neurons[i] = new NeuronImpl(inputsCount); //TODO: если делать так, то теряется весь смысл в разделении нейронов на типы. Нужно делать все в конкретном слое. продумать.
        }
    }

    public LayerImpl(int neuronsCount, int inputsCount, Functions funcType) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neurons = new Neuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            neurons[i] = new NeuronImpl(inputsCount, funcType); //TODO: если делать так, то теряется весь смысл в разделении нейронов на типы. Нужно делать все в конкретном слое. продумать.
        }
    }

    public LayerImpl(int neuronsCount, int inputsCount, Functions funcType, double a) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neurons = new Neuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            neurons[i] = new NeuronImpl(inputsCount, funcType, a); //TODO: если делать так, то теряется весь смысл в разделении нейронов на типы. Нужно делать все в конкретном слое. продумать.
        }
    }

    @Override
    public int getNeuronsCount() {
        return 0;
    }

    @Override
    public int getInputsCount() {
        return 0;
    }

    @Override
    public void setData(double[] data) {

    }

    @Override
    public Neuron[] getNeuronsArray() {
        return new Neuron[0];
    }

    @Override
    public Neuron getNeuron(int index) {
        return null;
    }
}
