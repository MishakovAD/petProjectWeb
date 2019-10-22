package com.project.RecognitionImage.backend.SplitterImage;

import org.opencv.core.Point;

public class AreaChar implements Chars {
    private Point leftUp;
    private Point rightUp;
    private Point rightDown;
    private Point leftDown;

    public AreaChar() {
    }

    @Override
    public Point getLeftUp() {
        return leftUp;
    }

    @Override
    public void setLeftUp(Point leftUp) {
        this.leftUp = leftUp;
    }

    @Override
    public Point getRightUp() {
        return rightUp;
    }

    @Override
    public void setRightUp(Point rightUp) {
        this.rightUp = rightUp;
    }

    @Override
    public Point getRightDown() {
        return rightDown;
    }

    @Override
    public void setRightDown(Point rightDown) {
        this.rightDown = rightDown;
    }

    @Override
    public Point getLeftDown() {
        return leftDown;
    }

    @Override
    public void setLeftDown(Point leftDown) {
        this.leftDown = leftDown;
    }
}
