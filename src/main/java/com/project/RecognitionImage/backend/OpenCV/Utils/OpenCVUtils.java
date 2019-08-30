package com.project.RecognitionImage.backend.OpenCV.Utils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;

import static org.opencv.imgproc.Imgproc.filter2D;

public class OpenCVUtils {
    /**
     * Метод, повышающий резкость изображения
     * @param img первоначальное изображение
     * @return обработанное изображение
     */
    public static Mat sharpeningToMat (Mat img) {
        Mat dstMat = new Mat();
        double k = 0.5;
        Mat kernel2 = new Mat(3, 3, CvType.CV_32FC1);
        kernel2.put(0, 0,
                -k, k - 1, -k,
                k - 1, k + 5, k - 1,
                -k, k - 1, -k);
        Core.divide(kernel2, new Scalar(k + 1), kernel2);
        filter2D(img, dstMat, -1, kernel2);
        return dstMat;
    }

    /**
     * Преобразование матрицы чернобелого изображения в цветное.
     * @param img ЧБ изображение
     * @return цветное изображение
     */
    public static Mat getColorMat (Mat img) {
        Mat dstMat = new Mat();
        Imgproc.cvtColor(img, dstMat, Imgproc.COLOR_GRAY2BGR);
        return dstMat;
    }

    /**
     * Преобразование матрицы цветного изображения в чб.
     * @param img цветное изображение
     * @return ЧБ изображение
     */
    public static Mat getGrayMat (Mat img) {
        Mat dstMat = new Mat();
        Imgproc.cvtColor(img, dstMat, Imgproc.COLOR_BGR2GRAY);
        return dstMat;
    }

    /**
     * Метод, объединяющий две матрицы.
     * dstMat(i) = firstMat(i) * alpha + secondMat(i) * beta + gamma;
     * @param firstMat матрица первого изображения
     * @param secondMat матрица второго изображения
     * @param alpha коэффициент (может быть null)
     * @param beta коэффициент (может быть null)
     * @param gamma коэффициент (может быть null)
     * @return результирующая матрица
     */
    public static Mat joinMat(Mat firstMat, Mat secondMat, Double alpha, Double beta, Double gamma) {
        alpha = alpha == null ? 1 : alpha;
        beta = beta == null ? 1 : beta;
        gamma = gamma == null ? 1 : gamma;
        Mat dstMat = new Mat();
        if (firstMat != null && secondMat != null) {
            Core.addWeighted(firstMat, alpha, secondMat, beta, gamma, dstMat);
        }
        return dstMat;
    }

    public static Mat joinMat(Mat firstMat, Mat secondMat) {
        return joinMat(firstMat, secondMat, null, null, null);
    }

    /**
     * Метод, применяющий преобразование Лапласса к матрице изображения.
     * @param img матрица исходного изображения
     * @return результирующая матрица
     */
    public static Mat getMatAfterLaplassian(Mat img) {
        Mat dstMat = new Mat(img.rows(), img.cols(), CvType.CV_8U);
        Imgproc.Laplacian(img, dstMat, CvType.CV_8U);
        return dstMat;
    }

    /**
     * Метод, выделяющий границы объектов у изображения.
     * @param img матрица исходного изображения
     * @return результирующая матрица
     */
    public static Mat getMatWithBordersFromSobel(Mat img) {
        Mat dstMat = new Mat(img.rows(), img.cols(), CvType.CV_8U);
        Mat dstSobelX = new Mat();
        Imgproc.Sobel(img, dstSobelX, CvType.CV_32F, 1, 0);
        Mat dstSobelY = new Mat();
        Imgproc.Sobel(img, dstSobelY, CvType.CV_32F, 0, 1);
        Mat imgSobelX = new Mat();
        Core.convertScaleAbs(dstSobelX, imgSobelX);
        Mat imgSobelY = new Mat();
        Core.convertScaleAbs(dstSobelY, imgSobelY);
        Core.addWeighted(imgSobelX, 1, imgSobelY, 1, 0, dstMat);
        return dstMat;
    }

    /**
     * Метод, выделяющий границы объектов у изображения.
     * @param img матрица исходного изображения
     * @return результирующая матрица
     */
    public static Mat getMatWithBordersFromScharr(Mat img) {
        Mat dstMat = new Mat(img.rows(), img.cols(), CvType.CV_8U);
        Mat dstScharrX = new Mat();
        Imgproc.Scharr(img, dstScharrX, CvType.CV_32F, 1, 0);
        Mat dstScharrY = new Mat();
        Imgproc.Scharr(img, dstScharrY, CvType.CV_32F, 0, 1);
        Mat imgScharrX = new Mat();
        Core.convertScaleAbs(dstScharrX, imgScharrX);
        Mat imgScharrY = new Mat();
        Core.convertScaleAbs(dstScharrY, imgScharrY);
        Core.addWeighted(imgScharrX, 1, imgScharrY, 1, 0, dstMat);
        return dstMat;
    }

    /**
     * Метод, преобразующий изображение только в трехцветное 0, 128, 255.
     * @param img Матрица изображения
     * @return обработаннуб матрицу изображения
     */
    public static Mat blackGreyAndWhiteMat(Mat img) {
        Mat dstMat = new Mat(img.rows(), img.cols(), CvType.CV_8U);

        int rows = img.rows();
        int cols = img.cols();

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                double[] arr = new double[9];
                double currentPixel = img.get(i, j)[0];
                double upLeft = img.get(i - 1, j - 1)[0];                    // * + +    * - up left
                double upCenter = img.get(i - 1, j)[0];                          // + ! $    ! - element,   $ - center right
                double upRight = img.get(i - 1, j + 1)[0];                  // + + %    % - down right
                double centerLeft = img.get(i, j - 1)[0];
                double centerRight = img.get(i, j + 1)[0];
                double downLeft = img.get(i + 1, j - 1)[0];
                double downCenter = img.get(i + 1, j)[0];
                double downRight = img.get(i + 1, j + 1)[0];

                arr[0] = upLeft;
                arr[1] = upCenter;
                arr[2] = upRight;
                arr[3] = centerLeft;
                arr[4] = currentPixel;
                arr[5] = centerRight;
                arr[6] = downLeft;
                arr[7] = downCenter;
                arr[8] = downRight;
                double avgArr = avgArray(arr);

                int delta;
                if (j < cols/2) {
                    delta = 7;
                } else {
                    delta = 17;
                }
                delta = (int) avgArr / 10;
                int finalDelta = delta;

                int counterOfSimilar = (int) Arrays.stream(arr).filter(elem -> Math.abs(avgArr - elem) > finalDelta).count();
                if (counterOfSimilar > 4) {
                    if (currentPixel > 170) {
                        dstMat.put(i, j, 255.0);
                    } else {
                        dstMat.put(i, j, 0.0);
                    }
                } else {
                    dstMat.put(i, j, 128);
                }
            }
        }
        return dstMat;
    }

    /**
     * Метод, преобразующий изображение только в двухцветное 0, 255.
     * @param srcMat Матрица изображения
     * @return обработаннуб матрицу изображения
     */
    public static Mat blackAndWhiteMat(Mat srcMat) {
        Mat dstMat = new Mat(srcMat.rows(), srcMat.cols(), CvType.CV_8U);
        int rows = srcMat.rows();
        int cols = srcMat.cols();

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                double[] arr = new double[9];
                double currentPixel = srcMat.get(i, j)[0];
                double upLeft = srcMat.get(i - 1, j - 1)[0];                    // * + +    * - up left
                double upCenter = srcMat.get(i - 1, j)[0];                          // + ! $    ! - element,   $ - center right
                double upRight = srcMat.get(i - 1, j + 1)[0];                  // + + %    % - down right
                double centerLeft = srcMat.get(i, j - 1)[0];
                double centerRight = srcMat.get(i, j + 1)[0];
                double downLeft = srcMat.get(i + 1, j - 1)[0];
                double downCenter = srcMat.get(i + 1, j)[0];
                double downRight = srcMat.get(i + 1, j + 1)[0];

                arr[0] = upLeft;
                arr[1] = upCenter;
                arr[2] = upRight;
                arr[3] = centerLeft;
                arr[4] = currentPixel;
                arr[5] = centerRight;
                arr[6] = downLeft;
                arr[7] = downCenter;
                arr[8] = downRight;
                double avgArr = avgArray(arr);

                int delta;
                if (j < cols/2) {
                    delta = 7;
                } else {
                    delta = 17;
                }

                int counterOfSimilar = (int) Arrays.stream(arr).filter(elem -> Math.abs(avgArr - elem) > delta).count();
                if (counterOfSimilar > 4) {
                    dstMat.put(i, j, 0);
                } else {
                    dstMat.put(i, j, 255);
                }
            }
        }
        return  dstMat;
    }

    /**
     * Метод, предназначенный для выделения границ текста.
     * @param img Матрица изображения
     * @return изображение с фоном и границами текста.
     */
    public static Mat drawBorderOfElements(Mat img) {
        Mat dstMat = new Mat(img.rows(), img.cols(), CvType.CV_8U);
        boolean changeValue = false;
        double value = 255;
        double delta = 25; //сначала рассчитывать максимальные и минимальные значения и их количество и на основании данных выбирать дельту. Делать это для каждой колонки
        for (int i = 0; i < img.cols(); i++) {
            double borderPixel = img.get(0, i)[0];
            for (int j = 0; j < img.rows(); j++) {
                double currentPixel = img.get(j, i)[0];
                if (Math.abs(currentPixel - borderPixel) < delta) {
                    if (changeValue) {
                        changeValue = false;
                        value = 255;
                    }
                    dstMat.put(j, i, value);
                } else {
                    if (!changeValue) {
                        changeValue = true;
                        value = 0;
                    }
                    borderPixel = currentPixel;
                    dstMat.put(j, i, 0);
                }

            }
        }
        return  dstMat;
    }

    /**
     * Метод, определяющий, является ли градиентом колонка.
     * @param column колонка матрицы изображения
     * @return true, если колонка - градиент
     */
    public static boolean isGradient(double[] column) {
        boolean gradient = false;
        int firstIndex = findFirstNonEmptyPixel(column);
        double startElem = column[firstIndex];
        for (int i = firstIndex + 1; i < column.length; i++) {
            if (i == column.length - 1) {
                gradient = true;
                return gradient;
            }
            if (column[i] > startElem) {
                startElem = column[i];
                continue;
            } else {
                break;
            }
        }
        startElem = column[firstIndex];
        for (int i = 1; i < column.length; i++) {
            if (i == column.length - 1) {
                gradient = true;
                return gradient;
            }
            if (column[i] < startElem) {
                startElem = column[i];
                continue;
            } else {
                break;
            }
        }
        return gradient;
    }

    /**
     * Проверят, одного ли цвета колонка.
     * @param column колонка матрицы изображения
     * @return true, если одного цвета
     */
    public static boolean isOneColor(double[] column) {
        boolean oneColor = false;
        int firstIndex = findFirstNonEmptyPixel(column);
        double startElem = column[firstIndex];
        for (int i = firstIndex + 1; i < column.length; i++) {
            if (i == column.length - 1) {
                oneColor = true;
                return oneColor;
            }
            if (Math.abs(column[i] - startElem) < 30) {
                startElem = column[i];
                continue;
            } else {
                break;
            }
        }
        return oneColor;
    }

    /**
     * Проверяет, разные ли точки в колонке.
     * @param column колонка матрицы изображения
     * @return true, если точки разные
     */
    public static boolean isDifferentPixels(double[] column) {
        boolean diffPixels = false;
        int delta = column.length / 5;
        long distinctPixels = Arrays.stream(column).distinct().count();
        if (!isGradient(column) && column.length - distinctPixels <= delta) {
            diffPixels = true;
        }
        return diffPixels;
    }

    /**
     * Проверяет, есть ли резкие границы фона и буквы.
     * @param column колонка матрицы изображения
     * @return true, если границы есть.
     */
    public static boolean isHaveBorder(double[] column) {
        boolean haveBorder = false;
        int firstIndex = findFirstNonEmptyPixel(column);
        double startElem = column[firstIndex];
        double maxElem = Arrays.stream(column)
                .skip(firstIndex)
                .filter(elem -> elem != 255 && elem != 0)
                .max()
                .orElse(1);
        int delta = Math.abs((int) (maxElem - maxElem / 1.5 - 15));
        delta = (int) avgArray(column) / 2;
        for (int i = firstIndex + 1; i < column.length; i++) {
            if (i == column.length - 1) {
                return haveBorder;
            }
            if (Math.abs(startElem - column[i]) > delta) {
                haveBorder = true;
                return haveBorder;
            } else {
                startElem = column[i];
            }
        }
        return haveBorder;
    }

    /**
     * Возвращает первый индекс, отличный от 255 или 0.
     * @param arr колонка матрицы изображения
     * @return первый "непустой" индекс
     */
    public static int findFirstNonEmptyPixel(double[] arr) {
        int firstIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 255.0 || arr[i] == 0) {
                continue;
            } else {
                firstIndex = i;
                break;
            }
        }
        return firstIndex;
    }

    /**
     * Возвращает среднее значение значений в колонке.
     * @param arr колонка матрицы изображения
     * @return среднее значение
     */
    public static double avgArray(double[] arr) {
        double avg = 0;
        double sum = Arrays.stream(arr).sum();
        avg = sum / arr.length;
        return avg;
    }
}
