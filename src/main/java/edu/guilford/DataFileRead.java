package edu.guilford;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataFileRead {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Scanner scanFile = null;
        Path dataLocation = null; // what's our data file?
        boolean validData = false; // did we get valid data from the file?
        double[][] values = null; // where are we putting the data when we get it?
        String fileName = null;

        System.out.println("Enter the data file name: ");
        fileName = scan.next();

        try {
            dataLocation = Paths.get(DataFileRead.class.getResource("/" + fileName).toURI());
            // FileReader is a stream that reads a file one character (Unicode) at a time
            // It's meant for text files
            // If we had binary file, we would use FileInputStream that reads a file one
            // byte at a time
            FileReader dataFile = new FileReader(dataLocation.toString());
            BufferedReader dataBuffer = new BufferedReader(dataFile); // so that we are reading the data efficiently
            scanFile = new Scanner(dataBuffer); // so that we can read the data line by line
            values = readData(scanFile);
        } catch (URISyntaxException | FileNotFoundException | NullPointerException e) { // | allow us to catch multiple exception
            // types and do the same basic thing with any of them
            e.printStackTrace();
        }

    }

    public static double[][] readData(Scanner scan) {
        // returns a double array of the data in the file
        double[][]inputValues = null;
        // get the number of rows and columns from the first row of the file
        int rows = scan.nextInt();
        int columns = scan.nextInt();

        // instantiate the appropriate sized array
        inputValues = new double[rows][columns];
        int i = 0;
        int j = 0;
        try {
        for ( i = 0; i < rows; i++) {
            for ( j = 0; j < columns; j++) {
                inputValues[i][j] = scan.nextDouble();
            }
        }
    } catch (InputMismatchException ex) {
        System.out.println(i + " " + j);
    }
        return inputValues;
    }

    public static class ScannerException extends Exception {
        public ScannerException(String message) {
            super(message);
        }
    }

}
