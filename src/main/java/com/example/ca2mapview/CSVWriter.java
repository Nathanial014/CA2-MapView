package com.example.ca2mapview;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVWriter {

    public static void main(String[] args) {
        try {
            writeLandmarks("./src/main/resources/landmarks.csv");
            writeRoads("./src/main/resources/roads.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLandmarks(String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("ID", "Name", "X", "Y", "CulturalValue"))) {
            csvPrinter.printRecord("1", "Eiffel Tower", "48.8584", "2.2945", "100");
            csvPrinter.printRecord("2", "Louvre Museum", "48.8606", "2.3376", "90");
            csvPrinter.printRecord("3", "Notre Dame", "48.8530", "2.3498", "85");
            csvPrinter.flush();
        }
    }

    public static void writeRoads(String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("StartID", "EndID", "Distance"))) {
            csvPrinter.printRecord("1", "2", "1.9");
            csvPrinter.printRecord("2", "3", "0.5");
            csvPrinter.printRecord("1", "3", "4.3");
            csvPrinter.flush();
        }
    }
}
