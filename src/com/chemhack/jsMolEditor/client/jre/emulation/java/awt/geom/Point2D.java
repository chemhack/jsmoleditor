package com.chemhack.jsMolEditor.client.jre.emulation.java.awt.geom;

public class Point2D {
    public double x;
    public double y;

    public Point2D(){
        
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "{"+x+","+y+"}";
    }
}
