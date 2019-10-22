package com.project.RecognitionImage.backend.SplitterImage;

import org.opencv.core.Mat;

import java.util.List;

public interface Splitter {
    /**
     * Возвращает список точек контура для буквы а так же модифицирует исходное изображение.
     * @param img исходное изображение
     * @return список точек контура букв
     */
    List<Chars> getSingleChar(Mat img);
}
