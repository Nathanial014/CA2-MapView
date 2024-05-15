package com.example.ca2mapview;

import javafx.scene.shape.Line;

public class MapUtils {

    private double scale;
    private static final double SCALE = 1000.0; // Scale pixels per map unit, adjust as needed

    public static double[] pixelToMap(double px, double py) {
        // Convert pixel coordinates to map coordinates by dividing by the SCALE.
        double mapX = px / SCALE;
        double mapY = py / SCALE;
        return new double[]{mapX, mapY};
    }

    public static Line createLine(double x1, double y1, double x2, double y2) {
        double[] start = mapToPixel(x1, y1);
        double[] end = mapToPixel(x2, y2);
        return new Line(start[0], start[1], end[0], end[1]);
    }
    public MapUtils(double scale) {
        this.scale = scale;
    }
    public static double[] mapToPixel(double x, double y) {
        // Convert map coordinates to pixel coordinates by multiplying with the SCALE.
        double pixelX = x * SCALE;
        double pixelY = y * SCALE;
        return new double[]{pixelX, pixelY};
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        // Calculate Euclidean distance between two points
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }
}
