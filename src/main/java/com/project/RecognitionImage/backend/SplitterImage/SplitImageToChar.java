package com.project.RecognitionImage.backend.SplitterImage;

import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.Book;
import com.project.NeuralNetwork.new_development.NeuralNetwork.School.data_book.IBook;
import com.project.RecognitionImage.backend.OpenCV.OpenCVImpl;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static com.project.RecognitionImage.backend.OpenCV.OpenCVImpl.showImage;
import static org.opencv.imgproc.Imgproc.line;
import static org.opencv.imgproc.Imgproc.rectangle;

public class SplitImageToChar implements Splitter {
    private static String path = "src/main/java/com/project/RecognitionImage/backend/OpenCV/test/";

    public static void main(String[] args) {
        SplitImageToChar s = new SplitImageToChar();
        OpenCVImpl cv = new OpenCVImpl();
        cv.init();

        //Mat imgGray = cv.loadImage(path + "russian_text.png", Imgcodecs.IMREAD_ANYCOLOR);
        //Mat imgGray = cv.loadImage(path + "chars.jpg", Imgcodecs.IMREAD_ANYCOLOR);
        //Mat imgGray = cv.loadImage(path + "big_chars.jpg", Imgcodecs.IMREAD_ANYCOLOR);
        Mat imgGray = cv.loadImage(path + "nums.jpg", Imgcodecs.IMREAD_ANYCOLOR);

        List<Chars> l = s.getSingleChar(imgGray);
        s.prepareTestDataForChars(imgGray, l);

        for (int i = 0; i < l.size(); i++) {
//            line(imgGray, l.get(i).getLeftUp(), l.get(i).getRightUp(), new Scalar(7, 7, 7));
//            line(imgGray, l.get(i).getRightUp(), l.get(i).getRightDown(), new Scalar(255, 7, 7));
//            line(imgGray, l.get(i).getRightDown(), l.get(i).getLeftDown(), new Scalar(7, 7, 7));
//            line(imgGray, l.get(i).getLeftDown(), l.get(i).getLeftUp(), new Scalar(7, 7, 255));
            rectangle(imgGray, new Point(l.get(i).getLeftUp().x, l.get(i).getLeftUp().y), new Point(l.get(i).getRightDown().x, l.get(i).getRightDown().y), new Scalar(255, 7, 7));
        }

        showImage(imgGray, "Result");
    }

    @Override
    public List<Chars> getSingleChar(Mat img) {
        List<Chars> points = new ArrayList<>();
        List<Point> pointsUp = new ArrayList<>();
        List<Point> pointsDown = new ArrayList<>();

        List<Point> pointsLeftUp = new ArrayList<>();
        List<Point> pointsLeftDown = new ArrayList<>();
        List<Point> pointsRightUp = new ArrayList<>();
        List<Point> pointsRightDown = new ArrayList<>();

        //Create Up and Down lines
        boolean drawLineUp = true;
        boolean drawLineDown = false;
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols() - 1; j++) {
                double[] currentPixel = img.get(i, j);
                double[] nextPixel = img.get(i, j + 1);
                if (Math.abs(currentPixel[0] - nextPixel[0]) < 10) {
                    if (j == img.cols() - 2) {
                        drawLineUp = true;
                        if (drawLineDown) {
                            drawLineDown = false;
                            Point start = new Point();
                            start.x = 0;
                            start.y = i;
                            pointsDown.add(start);
                            Point end = new Point();
                            end.x = img.cols() - 1;
                            end.y = i;
                        }
                    }
                    continue;
                } else if (drawLineUp) {
                    drawLineUp = false;
                    drawLineDown = true;
                    Point start = new Point();
                    start.x = 0;
                    start.y = i;
                    pointsUp.add(start);
                    Point end = new Point();
                    end.x = img.cols() - 1;
                    end.y = i;
                    break;
                } else {
                    break;
                }
            }
        }

        //Delete excess up and down lines
        int size = pointsUp.size() - 1;
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += (pointsDown.get(i).y - pointsUp.get(i).y);
        }
        int avg = (int) (sum/size);
        for (int i = 0; i < size-1; i++) {
            if (pointsUp.get(i+1).y - pointsUp.get(i).y < avg) {
                pointsDown.remove(i);
                pointsUp.remove(i+1);
                i--;
                size--;
            }
        }

        //Create Left and Right lines
        boolean drawLineLeft = true;
        boolean drawLineRight = false;
        for (int k = 0; k < pointsUp.size(); k++) {
            for (int j = 0; j < img.cols(); j++) {
                int countWhite = 0;
                int countIteration = 0;
                for (int i = (int) pointsUp.get(k).y + 1; i < (int) pointsDown.get(k).y - 1; i++) {
                    countIteration++;
                    double[] currentPixel = img.get(i, j);
                    double[] nextPixel = img.get(i + 1, j);
                    if (Math.abs(currentPixel[0] - nextPixel[0]) < 50) {
                        countWhite++;
                        if ((countIteration == countWhite) && (countIteration == ((int) pointsDown.get(k).y - (int) pointsUp.get(k).y - 2)) && drawLineRight) {
                            drawLineRight = false;
                            drawLineLeft = true;
                            Point start = new Point();
                            start.x = j + 1;
                            start.y = pointsUp.get(k).y;
                            pointsRightUp.add(start);
                            Point end = new Point();
                            end.x = j + 1;
                            end.y = pointsDown.get(k).y;
                            pointsRightDown.add(end);
                            j++;
                            break;
                        }
                        continue;
                    } else if (drawLineLeft) {
                        drawLineLeft = false;
                        drawLineRight = true;
                        Point start = new Point();
                        start.x = j - 1;
                        start.y = pointsUp.get(k).y;
                        pointsLeftUp.add(start);
                        Point end = new Point();
                        end.x = j - 1;
                        end.y = pointsDown.get(k).y;
                        pointsLeftDown.add(end);
                        break;
                    }
                }
            }
        }

        //Delete excess lines
        int height = 10;
        if (pointsUp.size() > 1 && pointsDown.size() > 1) {
            height = (int) (pointsDown.get(1).y - pointsUp.get(1).y);
        } else if (pointsUp.size() > 0 && pointsDown.size() > 0) {
            height = (int) (pointsDown.get(0).y - pointsUp.get(0).y);
        }
        int width = (int) (0.25 * height);
        int len = pointsLeftUp.size();
        for (int i = 0; i < len; i++) {
            if (Math.abs(pointsLeftUp.get(i).x - pointsRightUp.get(i).x) < width) {
                pointsLeftUp.remove(i);
                pointsLeftDown.remove(i);
                pointsRightUp.remove(i-1);
                pointsRightDown.remove(i-1);
                len--;
            }
        }

        //Create List of points for split
        for (int i = 0; i < pointsLeftUp.size(); i++) {
            AreaChar chars = new AreaChar();
            chars.setLeftUp(pointsLeftUp.get(i));
            chars.setRightUp(pointsRightUp.get(i));
            chars.setRightDown(pointsRightDown.get(i));
            chars.setLeftDown(pointsLeftDown.get(i));
            points.add(chars);
        }

        //Split text to words and find AVG words to split from character
        Mat words = img.clone();
        for (int i = 0; i < points.size(); i++) {
            line(words, points.get(i).getLeftUp(), points.get(i).getRightUp(), new Scalar(7, 7, 7));
            line(words, points.get(i).getRightDown(), points.get(i).getLeftDown(), new Scalar(7, 7, 7));
        }
        for (int k = 0; k < pointsUp.size(); k++) {
            for (int i = 0; i < words.cols()-1; i++) {
                double[] currentPixel = words.get((int) pointsUp.get(k).y, i);
                double[] nextPixel = words.get((int) pointsUp.get(k).y, i+1);
                if (currentPixel[0] != nextPixel[0]) {
                    //Сначала нужно нарисаовать эти линии, а уже потом идти по ним.
                    line(words, new Point(i, pointsUp.get(k).y), new Point(i, pointsDown.get(k).y), new Scalar(7, 7, 7));
                }
            }
        }

        return points;
    }

    @Override
    public IBook prepareTestSet(String path) {
        OpenCVImpl cv = new OpenCVImpl();
        cv.init();
        Mat img = cv.loadImage(path, Imgcodecs.IMREAD_ANYCOLOR);
        List<Chars> l = getSingleChar(img);
        return prepareTestDataForChars(img, l);
    }

    @Override
    public Mat zipMatToSize(Mat src, int width, int height) {
        OpenCVImpl o = new OpenCVImpl();
        BufferedImage originalImage = o.convertMatToBuffImg(src);
        BufferedImage destImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = destImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        Mat dest = o.convertBuffImgToMat(destImage);
        return dest;
    }

    private IBook prepareTestDataForChars(Mat img, List<Chars> points) {
        OpenCVImpl o = new OpenCVImpl();
        IBook book = new Book();
        o.init();
        List<double[]> testSet = new ArrayList<>();
        List<double[]> answers = new ArrayList<>();

        for (int i = 0; i < 33; i++){
            double[] a = new double[33];
            a[i] = 1;
            answers.add(a);
        }
        int indexAnswer = 0;
        for (int k = 0; k < points.size(); k++) {
            int rows = (int) (points.get(k).getRightDown().y - points.get(k).getLeftUp().y);
            int cols = (int) (points.get(k).getRightUp().x - points.get(k).getLeftUp().x);
            Mat src = new Mat(rows, cols, CvType.CV_8U);
            int x = 0;
            int y = 0;
            int index = 0;
            if (k == 33) {
                indexAnswer = 0;
            }
            double[] test = new double[100];
            for (int i = (int) points.get(k).getLeftUp().x; i < points.get(k).getRightUp().x; i++) {
                for (int j = (int) points.get(k).getLeftUp().y; j < (int) points.get(k).getRightDown().y; j++) {
                    double[] pixel = img.get(j, i);
                    src.put(y, x, pixel);
                    y++;
                }
                y = 0;
                x++;
            }
            Mat result = zipMatToSize(src, 10, 10);
            for (int row = 0; row < result.rows(); row++) {
                for (int col =0; col < result.cols(); col++) {
                    double[] pixel = result.get(row, col);
                    test[index] = pixel[0];
                    index++;
                }
            }
            testSet.add(test);
            book.addData(test, answers.get(indexAnswer));
            indexAnswer++;
        }
        return book;
    }

    private IBook prepareTestDataForNums(Mat img, List<Chars> points) {
        OpenCVImpl o = new OpenCVImpl();
        IBook book = new Book();
        o.init();
        List<double[]> answers = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            double[] a = new double[10];
            a[i] = 1;
            answers.add(a);
        }
        int indexAnswer = 0;
        for (int k = 0; k < points.size(); k++) {
            int rows = (int) (points.get(k).getRightDown().y - points.get(k).getLeftUp().y);
            int cols = (int) (points.get(k).getRightUp().x - points.get(k).getLeftUp().x);
            Mat src = new Mat(rows, cols, CvType.CV_8U);
            int x = 0;
            int y = 0;
            int index = 0;
            double[] test = new double[100];
            for (int i = (int) points.get(k).getLeftUp().x; i < points.get(k).getRightUp().x; i++) {
                for (int j = (int) points.get(k).getLeftUp().y; j < (int) points.get(k).getRightDown().y; j++) {
                    double[] pixel = img.get(j, i);
                    src.put(y, x, pixel);
                    y++;
                }
                y = 0;
                x++;
            }
            Mat result = zipMatToSize(src, 10, 10);
            for (int row = 0; row < result.rows(); row++) {
                for (int col =0; col < result.cols(); col++) {
                    double[] pixel = result.get(row, col);
                    //test[index] = pixel[0];
                    test[index] = 1 / (pixel[0]+1);
                    index++;
                }
            }
            book.addData(test, answers.get(indexAnswer));
            indexAnswer++;
        }
        return book;
    }

    //TODO: Сделать метод, для мелкого добавления шума, чтобы генерировать изображения разные.
}