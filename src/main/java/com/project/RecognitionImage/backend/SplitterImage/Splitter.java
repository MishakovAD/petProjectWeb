package com.project.RecognitionImage.backend.SplitterImage;

import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.IBook;
import org.opencv.core.Mat;

import java.util.List;

public interface Splitter {
    /**
     * Возвращает список точек контура для буквы а так же модифицирует исходное изображение.
     * @param img исходное изображение
     * @return список точек контура букв
     */
    List<Chars> getSingleChar(Mat img);

    /**
     * Возвращает тестовый сет для обучения НС.
     * @param path путь к картинке с буквами.
     * @return книгу с уроками
     */
    IBook prepareTestSet(String path);

    /**
     * Сжимает матрицу до нужных размеров.
     * @param src исходная матрица
     * @param width ширина
     * @param height высота
     * @return сжатую матрицу
     */
    Mat zipMatToSize(Mat src, int width, int height);
}
