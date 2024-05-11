package com.example.ca2mapview;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;

public class CsvReaderExample {
    public static void main(String[] args) {
        try (Reader in = new FileReader("path/to/your/data.csv")) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                String columnOne = record.get("Header1");
                String columnTwo = record.get("Header2");
                System.out.println(columnOne + " " + columnTwo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}