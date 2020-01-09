package com.project.NeuralNetwork.Base.Layers.base;

import com.project.NeuralNetwork.Base.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.Base.Neuron.InputNeuron;
import com.project.NeuralNetwork.Base.Neuron.OutputNeuron;
import com.project.NeuralNetwork.Base.Neuron.base.Neuron;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.Base.Neuron.function_activation.functions.user_function.UserFunction;

import static com.project.NeuralNetwork.Base.Layers.base.Layers.INPUT_LAYER;

/**
 * Класс предок для всех классов слоев нейронной сети.
 * Содержит в себе реализацию основных методов, а так же
 * все необходимые конструкторы для создания слоя.
 */
public abstract class LayerImpl implements Layer {
    private Neuron[] neurons;
    private double[] data;
    private double[] outputs;
    private Layers layerType;

    /**
     * Конструктор для создания одного из слоев НС.
     * Для входного слоя не важно количество входов.
     * @param layer тип уровня
     * @param neuronsCount число нейронов
     * @param inputsCount число входов в нейрон (равно количеству нейронов на предыдущем уровне)
     */
    public LayerImpl(Layers layer, int neuronsCount, int inputsCount) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neurons = new Neuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            switch (layer) {
                case INPUT_LAYER:
                    neurons[i] = new InputNeuron();
                    layerType = INPUT_LAYER;
                    break;
                case HIDDEN_LAYER:
                    neurons[i] = new HiddenNeuron(inputsCount);
                    layerType = Layers.HIDDEN_LAYER;
                    break;
                case OUTPUT_LAYER:
                    neurons[i] = new OutputNeuron(inputsCount);
                    layerType = Layers.OUTPUT_LAYER;
                    break;
                default: ;
            }
        }
    }

    /**
     * Конструктор для создания одного из слоев НС.
     * С указанием типа функции активации.
     * @param layer тип уровня
     * @param neuronsCount число нейронов
     * @param inputsCount число входов в нейрон (равно количеству нейронов на предыдущем уровне)
     * @param funcType тип функции активации (согласно доступным Enums)
     */
    public LayerImpl(Layers layer, int neuronsCount, int inputsCount, Functions funcType) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neurons = new Neuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            switch (layer) {
                case INPUT_LAYER:
                    neurons[i] = new InputNeuron();
                    layerType = INPUT_LAYER;
                    break;
                case HIDDEN_LAYER:
                    neurons[i] = new HiddenNeuron(inputsCount, funcType);
                    layerType = Layers.HIDDEN_LAYER;
                    break;
                case OUTPUT_LAYER:
                    neurons[i] = new OutputNeuron(inputsCount, funcType);
                    layerType = Layers.OUTPUT_LAYER;
                    break;
                default: ;
            }
        }
    }

    /**
     * Конструктор для создания одного из слоев НС.
     * С указанием типа функции активации и параметра "а".
     * @param layer тип уровня
     * @param neuronsCount число нейронов
     * @param inputsCount число входов в нейрон (равно количеству нейронов на предыдущем уровне)
     * @param funcType тип функции активации (согласно доступным Enums)
     * @param a крутизна или порог
     */
    public LayerImpl(Layers layer, int neuronsCount, int inputsCount, Functions funcType, double a) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neurons = new Neuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            switch (layer) {
                case INPUT_LAYER:
                    neurons[i] = new InputNeuron();
                    layerType = INPUT_LAYER;
                    break;
                case HIDDEN_LAYER:
                    neurons[i] = new HiddenNeuron(inputsCount, funcType, a);
                    layerType = Layers.HIDDEN_LAYER;
                    break;
                case OUTPUT_LAYER:
                    neurons[i] = new OutputNeuron(inputsCount, funcType, a);
                    layerType = Layers.OUTPUT_LAYER;
                    break;
                default: ;
            }
        }
    }

    /**
     * Конструктор для создания одного из слоев НС.
     * С использованием пользовательской функции активации.
     * @param layer тип уровня
     * @param neuronsCount число нейронов
     * @param inputsCount число входов в нейрон (равно количеству нейронов на предыдущем уровне)
     * @param userFunction реализованная пользовательская функция активации
     * @param derivativeUserFunction реализованная производная пользовательской функции активации
     */
    public LayerImpl(Layers layer, int neuronsCount, int inputsCount, UserFunction userFunction, DerivativeUserFunction derivativeUserFunction) {
        if (neuronsCount < 1) {
            neuronsCount = 1;
        }
        this.neurons = new Neuron[neuronsCount];
        for (int i = 0; i < neuronsCount; i++) {
            switch (layer) {
                case INPUT_LAYER:
                    neurons[i] = new InputNeuron();
                    layerType = INPUT_LAYER;
                    break;
                case HIDDEN_LAYER:
                    neurons[i] = new HiddenNeuron(inputsCount, userFunction, derivativeUserFunction);
                    layerType = Layers.HIDDEN_LAYER;
                    break;
                case OUTPUT_LAYER:
                    neurons[i] = new OutputNeuron(inputsCount, userFunction, derivativeUserFunction);
                    layerType = Layers.OUTPUT_LAYER;
                    break;
                default: ;
            }
        }
    }

    @Override
    public int getNeuronsCount() {
        return neurons.length;
    }

    @Override
    public int getInputsCount() {
        if (neurons != null && neurons[0] != null) {
            return neurons[0].getInputsCount();
        }
        return 0;
    }

    @Override
    public void setData(double[] data) {
        this.data = data;
        this.outputs = new double[neurons.length];
        if (INPUT_LAYER.equals(layerType)) {
            if (neurons.length != data.length) {
                //throw new DataIsNotCorrectException()
            } else {
                for (int i = 0; i < neurons.length; i++) {
                    neurons[i].setInput(data[i]);
                    outputs[i] = neurons[i].getOutput();
                }
            }
        } else {
            for (int i = 0; i < neurons.length; i++) {
                if (neurons[i].getInputs().length < data.length) {
                    //throw new DataIsNotCorrectException()
                } else if (neurons[i].getInputs().length > data.length) {
                    //throw new DataIsNotCorrectException()
                } else {
                    neurons[i].setInputs(data);
                    outputs[i] = neurons[i].getOutput();
                }
            }
        }
    }

    @Override
    public Neuron[] getNeuronsArray() {
        return neurons;
    }

    @Override
    public Neuron getNeuron(int index) {
        return neurons[index];
    }

    @Override
    public double[] getOutput() {
        double[] output = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            output[i] = neurons[i].getOutput();
        }
        this.outputs = output;
        return output;
    }

    @Override
    public double getOutputFromNeuron(int index) {
        return neurons[index].getOutput();
    }

    @Override
    public Functions getFuncType() {
        if (neurons.length > 0) {
            return neurons[0].getFuncType();
        }
        return Functions.SIGMA;
    }

    @Override
    public void correctWeights() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].correctWeights();
        }
    }

}
