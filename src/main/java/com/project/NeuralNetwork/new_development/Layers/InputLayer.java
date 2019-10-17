package com.project.NeuralNetwork.new_development.Layers;

import com.project.NeuralNetwork.new_development.Layers.base.Layer;
import com.project.NeuralNetwork.new_development.Layers.base.LayerImpl;
import com.project.NeuralNetwork.new_development.Layers.base.Layers;

/**
 * Входной слой нейросети
 */
public class InputLayer extends LayerImpl implements Layer {
    public InputLayer(int neuronsCount) {
        super(Layers.INPUT_LAYER, neuronsCount, 0);
    }
}
