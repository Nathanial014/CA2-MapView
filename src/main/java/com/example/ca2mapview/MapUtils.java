package com.example.ca2mapview;

public class MapUtils {

    private static final double SCALE = 1000.0; // Scale pixels per map unit, adjust as needed

    public static double[] mapToPixel(double x, double y) {
        // Convert map coordinates to pixel coordinates
        return new double[]{x * SCALE, y * SCALE};
    }

    public static double[] pixelToMap(double px, double py) {
        // Convert pixel coordinates to map coordinates
        return new double[]{px / SCALE, py / SCALE};
    }

    public static double calculateDistance(double x1, double y1, double x2, double y2) {
        // Calculate Euclidean distance between two points
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
