package com.project.RecognitionImage.backend.Entity;

public class PictureDot {
    private int x;
    private int y;
    private int height;
    private int width;
    private int color; //getRGB(x, y)
    private int red; //(color & 0xff0000) >> 16;
    private int green; //(color & 0xff00) >> 8;
    private int blue; //color & 0xff;

    public PictureDot(int x, int y, int height, int width, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.setRed();
        this.setGreen();
        this.setBlue();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRed() {
        return red;
    }

    public void setRed() {
        this.red = (color & 0xff0000) >> 16;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen() {
        this.green = (color & 0xff00) >> 8;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue() {
        this.blue = color & 0xff;
    }
}
