package com.project.RecognitionImage.backend.SplitterImage;

import org.opencv.core.Point;

public interface Chars {
    public Point getLeftUp();

    public void setLeftUp(Point leftUp);

    public Point getRightUp();

    public void setRightUp(Point rightUp);

    public Point getRightDown();

    public void setRightDown(Point rightDown);

    public Point getLeftDown();

    public void setLeftDown(Point leftDown);
}
