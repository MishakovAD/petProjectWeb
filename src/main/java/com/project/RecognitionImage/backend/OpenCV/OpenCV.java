package com.project.RecognitionImage.backend.OpenCV;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.awt.image.BufferedImage;

public interface OpenCV {

    /**
     * Инициализация библиотеки и сопутсвующих классов.
     */
    void init();

    /**
     * Загружает оригинальное изображение.
     * @param filePath путь к изображению
     * @return матрицу Mat изображения
     */
    Mat loadImage(String filePath);

    /**
     * Загружает изображение с первоначальной обработкой.
     * @param filePath путь к изображению
     * @param params параметры обработки изображения
     * @return матрицу Mat обработанного изображения
     */
    Mat loadImage(String filePath, int params);

    /**
     * Обрабатываеет изображение для четкого разделения надписи и фона.
     * @param img исходное изображение
     * @return обработанное изображение
     */
    Mat processingImage(Mat img);

    /**
     * Преобразование изображения в формате Mat в BufferedImage
     * @param m изображение в формате Mat
     * @return изображение в формате BufferedImage
     */
    BufferedImage convertMatToBuffImg (Mat m);

    /**
     * Преобразование изображения в формате BufferedImage в Mat
     * @param img изображение в формате BufferedImage
     * @return изображение в формате Mat
     */
    Mat convertBuffImgToMat (BufferedImage img);

    /**
     * Задаем цвет RGB для объекта Scalar
     * @param red
     * @param green
     * @param blue
     * @return объект Scalar с цветом
     */
    Scalar colorRGBFromScalar (double red, double green, double blue);
    Scalar colorRGBFromScalar (double red, double green, double blue, double alpha);

    /**
     * Сохраняет матрицу Mat изображения в бинарный файл с расширением mat
     * @param m матрица Mat изображения
     * @param filePath путь к файлу + файл
     * @return true, если сохранение прошло успешно
     */
    boolean saveMatToFile(Mat m, String filePath);

    /**
     * Загружает матрицу изображения Mat из бинарного файла с расширением mat
     * @param filePath путь к файлу + файл
     * @return матрицу Mat изображения
     */
    Mat loadMatFRomFile(String filePath);
}
