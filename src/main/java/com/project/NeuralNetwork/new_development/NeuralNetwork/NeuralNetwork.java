package com.project.NeuralNetwork.new_development.NeuralNetwork;

import com.project.NeuralNetwork.new_development.Layers.HiddenLayer;
import com.project.NeuralNetwork.new_development.Layers.InputLayer;
import com.project.NeuralNetwork.new_development.Layers.OutputLayer;
import com.project.NeuralNetwork.new_development.NeuralNetwork.base.Network;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.new_development.Neuron.function_activation.functions.user_function.UserFunction;

public class NeuralNetwork implements Network {
    private InputLayer inputLayer;
    private HiddenLayer[] hiddenLayers_array;
    private OutputLayer outputLayer;
    private Functions funcType;
    private boolean withoutHiddenLayers = false;

    /**
     * Базовый конструктор для создания НС.
     * @param countInputNeurons число входных нейронов
     * @param countHiddenLayers число скрытых слоев
     * @param countHiddenNeurons число нейронов скрытого слоя
     * @param countOutputNeurons число выходных нейронов
     */
    public NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons) {
        if (!isCorrectInitialConditions(countInputNeurons, countOutputNeurons)) {
            //throw new NotCorrectInitialConditions();
        }
        this.funcType = Functions.SIGMA;
        inputLayer = new InputLayer(countInputNeurons);
        if (countHiddenLayers < 1) {
            this.withoutHiddenLayers = true;
            outputLayer = new OutputLayer(countOutputNeurons, countInputNeurons);
        } else {
            hiddenLayers_array = new HiddenLayer[countHiddenLayers];
            hiddenLayers_array[0] = new HiddenLayer(countHiddenNeurons, countInputNeurons);
            for (int i = 1; i < countHiddenLayers; i++) {
                hiddenLayers_array[i] = new HiddenLayer(countHiddenNeurons, countHiddenNeurons);
            }
            outputLayer = new OutputLayer(countOutputNeurons, countHiddenNeurons);
        }

    }

    /**
     * Конструктор для создания НС с указанием функции активации.
     * @param countInputNeurons число входных нейронов
     * @param countHiddenLayers число скрытых слоев
     * @param countHiddenNeurons число нейронов скрытого слоя
     * @param countOutputNeurons число выходных нейронов
     * @param funcType тип функции активации
     */
    public NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons, Functions funcType) {
        if (!isCorrectInitialConditions(countInputNeurons, countOutputNeurons)) {
            //throw new NotCorrectInitialConditions();
        }
        this.funcType = funcType;
        inputLayer = new InputLayer(countInputNeurons);
        if (countHiddenLayers < 1) {
            this.withoutHiddenLayers = true;
            outputLayer = new OutputLayer(countOutputNeurons, countInputNeurons, funcType);
        } else {
            hiddenLayers_array = new HiddenLayer[countHiddenLayers];
            hiddenLayers_array[0] = new HiddenLayer(countHiddenNeurons, countInputNeurons, funcType);
            for (int i = 1; i < countHiddenLayers; i++) {
                hiddenLayers_array[i] = new HiddenLayer(countHiddenNeurons, countHiddenNeurons, funcType);
            }
            outputLayer = new OutputLayer(countOutputNeurons, countHiddenNeurons, funcType);
        }

    }

    /**
     * Конструктор для создания НС с указанием функции активации и входным параметром для ФА.
     * @param countInputNeurons число входных нейронов
     * @param countHiddenLayers число скрытых слоев
     * @param countHiddenNeurons число нейронов скрытого слоя
     * @param countOutputNeurons число выходных нейронов
     * @param funcType тип функции активации
     * @param a крутизна/порог
     */
    public NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons, Functions funcType, double a) {
        if (!isCorrectInitialConditions(countInputNeurons, countOutputNeurons)) {
            //throw new NotCorrectInitialConditions();
        }
        this.funcType = funcType;
        inputLayer = new InputLayer(countInputNeurons);
        if (countHiddenLayers < 1) {
            this.withoutHiddenLayers = true;
            outputLayer = new OutputLayer(countOutputNeurons, countInputNeurons, funcType, a);
        } else {
            hiddenLayers_array = new HiddenLayer[countHiddenLayers];
            hiddenLayers_array[0] = new HiddenLayer(countHiddenNeurons, countInputNeurons, funcType, a);
            for (int i = 1; i < countHiddenLayers; i++) {
                hiddenLayers_array[i] = new HiddenLayer(countHiddenNeurons, countHiddenNeurons, funcType, a);
            }
            outputLayer = new OutputLayer(countOutputNeurons, countHiddenNeurons, funcType, a);
        }

    }

    /**
     * Конструктор для создания НС с использование пользовательской функции активации.
     * @param countInputNeurons число входных нейронов
     * @param countHiddenLayers число скрытых слоев
     * @param countHiddenNeurons число нейронов скрытого слоя
     * @param countOutputNeurons число выходных нейронов
     * @param userFunction пользовательская ФА
     */
    public NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons, UserFunction userFunction) {
        if (!isCorrectInitialConditions(countInputNeurons, countOutputNeurons)) {
            //throw new NotCorrectInitialConditions();
        }
        this.funcType = Functions.USER;
        inputLayer = new InputLayer(countInputNeurons);
        if (countHiddenLayers < 1) {
            this.withoutHiddenLayers = true;
            outputLayer = new OutputLayer(countOutputNeurons, countInputNeurons, userFunction);
        } else {
            hiddenLayers_array = new HiddenLayer[countHiddenLayers];
            hiddenLayers_array[0] = new HiddenLayer(countHiddenNeurons, countInputNeurons, userFunction);
            for (int i = 1; i < countHiddenLayers; i++) {
                hiddenLayers_array[i] = new HiddenLayer(countHiddenNeurons, countHiddenNeurons, userFunction);
            }
            outputLayer = new OutputLayer(countOutputNeurons, countHiddenNeurons, userFunction);
        }

    }

    private boolean isCorrectInitialConditions(int countInputNeurons, int countOutputNeurons) {
        if (countInputNeurons < 1 || countOutputNeurons < 1) {
            return false;
        }
        return true;
    }

    @Override
    public void setInputData(double[] data) {
        inputLayer.setData(data);
        double[] outDataFromInputL = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            outDataFromInputL[i] = inputLayer.getNeuron(i).getOutput();
        }
        if (this.withoutHiddenLayers) {
            outputLayer.setData(outDataFromInputL);
        } else {
            int lenHiddenLayers = hiddenLayers_array.length;
            hiddenLayers_array[0].setData(outDataFromInputL);
            for (int i = 1; i < lenHiddenLayers; i++) {
                hiddenLayers_array[i].setData(hiddenLayers_array[i-1].getOutput()); //передаем на вход уровня данные с предыдущего уровня
            }
            outputLayer.setData(hiddenLayers_array[lenHiddenLayers-1].getOutput());
        }
    }

    @Override
    public OutputLayer getOutputLayer() {
        return outputLayer;
    }

    @Override
    public HiddenLayer[] getHiddenLayerArray() {
        return hiddenLayers_array;
    }

    @Override
    public HiddenLayer getHiddenLayer(int index) {
        return hiddenLayers_array[index];
    }

    @Override
    public double[] getOutput() {
        return outputLayer.getOutput() ;
    }

    @Override
    public Functions getFunctionType() {
        return this.funcType;
    }

}
