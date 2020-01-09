package com.project.NeuralNetwork.Base.Networks.NeuralNetwork;

import com.project.NeuralNetwork.Base.Exceptions.NeuralNetworkExceptions.NotCorrectInitialConditions;
import com.project.NeuralNetwork.Base.Layers.HiddenLayer;
import com.project.NeuralNetwork.Base.Layers.InputLayer;
import com.project.NeuralNetwork.Base.Layers.OutputLayer;
import com.project.NeuralNetwork.Base.Layers.base.Layers;
import com.project.NeuralNetwork.Base.Networks.base.Network;
import com.project.NeuralNetwork.Base.Neuron.HiddenNeuron;
import com.project.NeuralNetwork.Base.Neuron.derivative_fa.derivative_functions.derivative_user_fa.DerivativeUserFunction;
import com.project.NeuralNetwork.Base.Neuron.function_activation.Functions;
import com.project.NeuralNetwork.Base.Neuron.function_activation.functions.user_function.UserFunction;

public class NeuralNetwork implements Network {
    private InputLayer inputLayer;
    private HiddenLayer[] hiddenLayers_array;
    private OutputLayer outputLayer;
    private Functions funcType;
    private boolean withoutHiddenLayers = false;

    private NeuralNetwork() {
    }

    /**
     * Базовый конструктор для создания НС.
     * @param countInputNeurons число входных нейронов
     * @param countHiddenLayers число скрытых слоев
     * @param countHiddenNeurons число нейронов скрытого слоя
     * @param countOutputNeurons число выходных нейронов
     */
    private NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons) {
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
    private NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons, Functions funcType) {
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
    private NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons, Functions funcType, double a) {
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
     * @param derivativeUserFunction производная пользовательской ФА
     */
    private NeuralNetwork(int countInputNeurons, int countHiddenLayers, int countHiddenNeurons, int countOutputNeurons, UserFunction userFunction, DerivativeUserFunction derivativeUserFunction) {
        this.funcType = Functions.USER;
        inputLayer = new InputLayer(countInputNeurons);
        if (countHiddenLayers < 1) {
            this.withoutHiddenLayers = true;
            outputLayer = new OutputLayer(countOutputNeurons, countInputNeurons, userFunction, derivativeUserFunction);
        } else {
            hiddenLayers_array = new HiddenLayer[countHiddenLayers];
            hiddenLayers_array[0] = new HiddenLayer(countHiddenNeurons, countInputNeurons, userFunction, derivativeUserFunction);
            for (int i = 1; i < countHiddenLayers; i++) {
                hiddenLayers_array[i] = new HiddenLayer(countHiddenNeurons, countHiddenNeurons, userFunction, derivativeUserFunction);
            }
            outputLayer = new OutputLayer(countOutputNeurons, countHiddenNeurons, userFunction, derivativeUserFunction);
        }

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

    @Override
    public void setFuncActivType(Layers layer, Functions funcType) {
        if (Layers.HIDDEN_LAYER.equals(layer)) {
            for (int i = 0; i < hiddenLayers_array.length; i++) {
                HiddenLayer h_layer = hiddenLayers_array[i];
                for (int j = 0; j < h_layer.getNeuronsCount(); j++) {
                    HiddenNeuron h_neuron = (HiddenNeuron) h_layer.getNeuron(j);
                    h_neuron.setActivFuncType(funcType);
                }
            }
        } else if (Layers.OUTPUT_LAYER.equals(layer)) {
            for (int i = 0; i < this.outputLayer.getNeuronsCount(); i++) {
                this.outputLayer.getNeuron(i).setActivFuncType(funcType);
            }
        }
    }

    public static NeuralNetworkBuilder newBuilder() {
        return new NeuralNetwork().new NeuralNetworkBuilder();
    }

    public class NeuralNetworkBuilder {
        private int countInputNeurons = 1;
        private int countHiddenLayers = 0;
        private int countHiddenNeurons = 0;
        private int  countOutputNeurons = 1;
        private Functions funcType = null;
        private double a = 1;
        private UserFunction userFunction = null;
        private DerivativeUserFunction derivativeUserFunction = null;

        private NeuralNetworkBuilder() {
        }

        public NeuralNetworkBuilder setCountInputNeurons(int count) throws NotCorrectInitialConditions {
            if (count <= 0) {
                throw new NotCorrectInitialConditions();
            }
            this.countInputNeurons = count;
            return this;
        }

        public NeuralNetworkBuilder setCountHiddenLayers(int count) throws NotCorrectInitialConditions {
            if (count < 0) {
                throw new NotCorrectInitialConditions();
            }
            if (count > 0 && this.countHiddenNeurons <= 0) {
                this.countHiddenNeurons = 1;
            }
            this.countHiddenLayers = count;
            return this;
        }

        public NeuralNetworkBuilder setCountHiddenNeurons(int count) throws NotCorrectInitialConditions {
            if (count < 0) {
                throw new NotCorrectInitialConditions();
            }
            if (this.countHiddenLayers < 1) {
                this.countHiddenLayers = 1;
            }
            this.countHiddenNeurons = count;
            return this;
        }

        public NeuralNetworkBuilder setCountOutputNeurons(int count) throws NotCorrectInitialConditions {
            if (count <= 0) {
                throw new NotCorrectInitialConditions();
            }
            this.countOutputNeurons = count;
            return this;
        }

        public NeuralNetworkBuilder setUserFunction(UserFunction userFunction, DerivativeUserFunction derivativeUserFunction) throws NotCorrectInitialConditions {
            if (userFunction == null || derivativeUserFunction == null) {
                throw new NotCorrectInitialConditions();
            }
            this.userFunction = userFunction;
            this.derivativeUserFunction = derivativeUserFunction;
            return this;
        }

        public NeuralNetworkBuilder setFunctionType(Functions funcType) {
            this.funcType = funcType;
            return this;
        }

        public NeuralNetworkBuilder setThreshold(double threshold) {
            this.a = threshold;
            return this;
        }

        public NeuralNetwork build() {
            if (this.userFunction != null && this.derivativeUserFunction != null) {
                return new NeuralNetwork(this.countInputNeurons, this.countHiddenLayers, this.countHiddenNeurons, this.countOutputNeurons, this.userFunction, this.derivativeUserFunction);
            } else if (this.funcType != null) {
                return new NeuralNetwork(this.countInputNeurons, this.countHiddenLayers, this.countHiddenNeurons, this.countOutputNeurons, this.funcType, this.a);
            } else {
                return new NeuralNetwork(this.countInputNeurons, this.countHiddenLayers, this.countHiddenNeurons, this.countOutputNeurons);
            }
        }
    }

}
