package com.project.NeuralNetwork.Base.Layers;

import com.project.NeuralNetwork.Base.Layers.base.Layer;
import com.project.NeuralNetwork.Base.Layers.base.LayerImpl;
import com.project.NeuralNetwork.Base.Layers.base.Layers;

/**
 * Входной слой нейросети
 */
public class InputLayer extends LayerImpl implements Layer {
    public InputLayer(int neuronsCount) {
        super(Layers.INPUT_LAYER, neuronsCount, 0);
    }
}
