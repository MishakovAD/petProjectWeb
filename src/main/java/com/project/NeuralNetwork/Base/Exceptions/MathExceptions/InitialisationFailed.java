package com.project.NeuralNetwork.Base.Exceptions.MathExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitialisationFailed extends Exception {
    private Logger logger = LoggerFactory.getLogger(InitialisationFailed.class);

    public InitialisationFailed() {
        logger.error("Initialisation failed. Data is not correct");
    }

}
