package com.project.RecognitionImage.backend.OpenCV;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static org.opencv.core.Core.minMaxLoc;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class OpenCVImpl implements OpenCV {
    private Logger logger = LoggerFactory.getLogger(OpenCVImpl.class);
    public static void main(String[] args) {
        OpenCVImpl cv = new OpenCVImpl();
        cv.init();
        Mat img = cv.loadImage("C:/captcha_kino.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        //Mat img = cv.loadImage("C:/c.png", Imgcodecs.IMREAD_GRAYSCALE);
        Mat result = cv.processingImage(img);
        boolean saveFile = imwrite("C:/test.jpg", result);
        System.out.println(saveFile);

    }

    @Override
    public void init() {
        logger.info("Start init() in OpenCVImpl.class");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public Mat loadImage(String filePath) {
        logger.info("Start loadImage() in OpenCVImpl.class");
        Mat img = imread(filePath);
        if (img.empty()) {
            logger.info("loadImage() return null in OpenCVImpl.class");
            return null;
        }
        return img;
    }

    @Override
    public Mat loadImage(String filePath, int params) {
        logger.info("Start loadImage() in OpenCVImpl.class");
        Mat img = imread(filePath, params);
        if (img.empty()) {
            logger.info("loadImage() return null in OpenCVImpl.class");
            return null;
        }
        return img;
    }

    @Override
    public Mat processingImage(Mat img) {
        Mat dstMat = new Mat(img.rows(), img.cols(), CvType.CV_8U);
        if (img != null) {
            Mat blackAndWhiteMat = blackAndWhiteMat(img);
            Mat blackGreayAndWhiteMat = blacGreyAndWhiteMat(img);

            for (int i = 0; i < img.rows(); i++) {
                for (int j = 0; j < img.cols(); j++) {
                    int delta;
                    if (j < img.cols()/2) {
                        delta = 135;
                    } else {
                        delta = 125;
                    }
                    if (Math.abs(blackGreayAndWhiteMat.get(i, j)[0] - blackAndWhiteMat.get(i, j)[0]) < delta) {
                        dstMat.put(i, j, 225);
                    } else {
                        dstMat.put(i, j, 0);
                    }
                }
            }

            return dstMat;
        } else {
            return new Mat(0, 0, CvType.CV_8U);
        }
    }

    private Mat blackAndWhiteMat(Mat srcMat) {
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

    private Mat blacGreyAndWhiteMat(Mat img) {
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

                int counterOfSimilar = (int) Arrays.stream(arr).filter(elem -> Math.abs(avgArr - elem) > delta).count();
                if (counterOfSimilar > 4) {
                    if (currentPixel > 170) {
                        dstMat.put(i, j, 255.0);
                        System.out.print(" ");
                    } else {
                        dstMat.put(i, j, 0.0);
                        System.out.print(" ");
                    }
                } else {
                    dstMat.put(i, j, 128);
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        return dstMat;
    }

    private double avgArray(double[] arr) {
        double avg = 0;
        double sum = Arrays.stream(arr).sum();
        avg = sum / arr.length;
        return avg;
    }

    @Override
    public BufferedImage convertMatToBuffImg(Mat m) {
        logger.info("Start convertMatToBuffImg() in OpenCVImpl.class");
        if (m == null || m.empty()) return null;
        if (m.depth() == CvType.CV_8U) {
        } else if (m.depth() == CvType.CV_16U) { // CV_16U => CV_8U
            Mat m_16 = new Mat();
            m.convertTo(m_16, CvType.CV_8U, 255.0 / 65535);
            m = m_16;
        } else if (m.depth() == CvType.CV_32F) { // CV_32F => CV_8U
            Mat m_32 = new Mat();
            m.convertTo(m_32, CvType.CV_8U, 255);
            m = m_32;
        } else {
            return null;
        }
        int type = 0;
        if (m.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else if (m.channels() == 3)
            type = BufferedImage.TYPE_3BYTE_BGR;
        else if (m.channels() == 4)
            type = BufferedImage.TYPE_4BYTE_ABGR;
        else
            return null;
        byte[] buf = new byte[m.channels() * m.cols() * m.rows()];
        m.get(0, 0, buf);
        byte tmp = 0;
        if (m.channels() == 4) { // BGRA => ABGR
            for (int i = 0; i < buf.length; i += 4) {
                tmp = buf[i + 3];
                buf[i + 3] = buf[i + 2];
                buf[i + 2] = buf[i + 1];
                buf[i + 1] = buf[i];
                buf[i] = tmp;
            }
        }
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buf, 0, data, 0, buf.length);
        logger.info("End of convertMatToBuffImg() in OpenCVImpl.class");
        return image;
    }

    @Override
    public Mat convertBuffImgToMat(BufferedImage img) {
        logger.info("Start convertBuffImgToMat() in OpenCVImpl.class");
        if (img == null) return new Mat();
        int type = 0;
        if (img.getType() == BufferedImage.TYPE_BYTE_GRAY) {
            type = CvType.CV_8UC1;
        } else if (img.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            type = CvType.CV_8UC3;
        } else if (img.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
            type = CvType.CV_8UC4;
        } else {
            return new Mat();
        }
        Mat m = new Mat(img.getHeight(), img.getWidth(), type);
        byte[] data =
                ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        if (type == CvType.CV_8UC1 || type == CvType.CV_8UC3) {
            m.put(0, 0, data);
            return m;
        }
        byte[] buf = Arrays.copyOf(data, data.length);
        byte tmp = 0;
        for (int i = 0; i < buf.length; i += 4) { // ABGR => BGRA
            tmp = buf[i];
            buf[i] = buf[i + 1];
            buf[i + 1] = buf[i + 2];
            buf[i + 2] = buf[i + 3];
            buf[i + 3] = tmp;
        }
        m.put(0, 0, buf);
        logger.info("End of convertBuffImgToMat() in OpenCVImpl.class");
        return m;
    }

    @Override
    public Scalar colorRGBFromScalar(double red, double green, double blue) {
        return new Scalar(blue, green, red);
    }

    @Override
    public Scalar colorRGBFromScalar(double red, double green, double blue, double alpha) {
        return new Scalar(blue, green, red, alpha);
    }

    //----------------------------WORK WITH FILES (SAVE AND LOAD)----------------------------//

    @Override
    public boolean saveMatToFile(Mat m, String filePath) {
        logger.info("Start saveMatToFile() in OpenCVImpl.class");
        if (m == null || m.empty()) {
            return false;
        }
        if (filePath == null || filePath.length() < 5 || !filePath.endsWith(".mat")) {
            return false;
        }
        if (m.depth() == CvType.CV_8U) {
            //do nothing
        } else if (m.depth() == CvType.CV_16U) {
            Mat m_16 = new Mat();
            m.convertTo(m_16, CvType.CV_8U, 255.0 / 65535);
            m = m_16;
        } else if (m.depth() == CvType.CV_32F) {
            Mat m_32 = new Mat();
            m.convertTo(m_32, CvType.CV_8U, 255);
            m = m_32;
        } else {
            return false;
        }
        if (m.channels() == 2 || m.channels() > 4) {
            return false;
        }

        byte[] buf = new byte[m.channels() * m.cols() * m.rows()];
        m.get(0, 0, buf);
        try (
                OutputStream out = new FileOutputStream(filePath);
                BufferedOutputStream bout = new BufferedOutputStream(out);
                DataOutputStream dout = new DataOutputStream(bout);
        ) {
            dout.writeInt(m.rows());
            dout.writeInt(m.cols());
            dout.writeInt(m.channels());
            dout.write(buf);
            dout.flush();
        } catch (Exception e) {
            return false;
        }
        logger.info("Success of saveMatToFile() in OpenCVImpl.class");
        return true;
    }

    @Override
    public Mat loadMatFRomFile(String filePath) {
        logger.info("Start loadMatFRomFile() in OpenCVImpl.class");
        if (filePath == null || filePath.length() < 5 || !filePath.endsWith(".mat")) {
            return new Mat();
        }
        File f = new File(filePath);
        if (!f.exists() || !f.isFile()) {
            return new Mat();
        }
        try (
                InputStream in = new FileInputStream(filePath);
                BufferedInputStream bin = new BufferedInputStream(in);
                DataInputStream din = new DataInputStream(bin);
        ) {
            int rows = din.readInt();
            if (rows < 1) {
                return new Mat();
            }
            int cols = din.readInt();
            if (cols < 1) {
                return new Mat();
            }
            int ch = din.readInt();
            int type = 0;
            if (ch == 1) {
                type = CvType.CV_8UC1;
            } else if (ch == 3) {
                type = CvType.CV_8UC3;
            } else if (ch == 4) {
                type = CvType.CV_8UC4;
            } else {
                return new Mat();
            }
            int size = ch * cols * rows;
            byte[] buf = new byte[size];
            int rsize = din.read(buf);
            if (size != rsize) {
                return new Mat();
            }
            Mat m = new Mat(rows, cols, type);
            m.put(0, 0, buf);
            logger.info("Success of loadMatFRomFile() in OpenCVImpl.class");
            return m;
        } catch (Exception e) {
        }
        logger.info("return empty Map after loadMatFRomFile() in OpenCVImpl.class");
        return new Mat();
    }

    //----------------------------WORK WITH FILES (SAVE AND LOAD)----------------------------//

    public static void showImage(Mat img, String title) {
        OpenCV openCV = new OpenCVImpl();
        openCV.init();
        BufferedImage im = openCV.convertMatToBuffImg(img);
        if (im == null) return;
        int w = 1000, h = 600;
        JFrame window = new JFrame(title);
        window.setSize(w, h);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(im);
        JLabel label = new JLabel(imageIcon);
        JScrollPane pane = new JScrollPane(label);
        window.setContentPane(pane);
        if (im.getWidth() < w && im.getHeight() < h) {
            window.pack();
        }
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}
