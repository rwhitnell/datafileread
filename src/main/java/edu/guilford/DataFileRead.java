package edu.guilford;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DataFileRead {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Scanner scanFile = null;
        Path dataLocation = null; // what's our data file?
        boolean validData = false; // did we get valid data from the file?
        double[][] values = null; // where are we putting the data when we get it?
        String fileName = null;
        // let's get a file name from the user and try to open the associated file
        System.out.println("Enter the data file name: ");
        fileName = scan.next();
        // open the file and read the data
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
        } catch (URISyntaxException | FileNotFoundException | NullPointerException e) { // | allow us to catch multiple
                                                                                        // exception
            // types and do the same basic thing with any of them
            e.printStackTrace();
        }

        // write the data to a file
        try {
            writeData(values, "output.txt");
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            System.exit(1); // 1 because it ended with an error
        }

    }

    public static double[][] readData(Scanner scan) {
        // returns a double array of the data in the file
        double[][] inputValues = null;
        // get the number of rows and columns from the first row of the file
        int rows = scan.nextInt();
        int columns = scan.nextInt();

        // instantiate the appropriate sized array
        inputValues = new double[rows][columns];
        int i = 0;
        int j = 0;
        // try reading the data from the file, catching any exceptions that take place
        try {
            for (i = 0; i < rows; i++) {
                for (j = 0; j < columns; j++) {
                    inputValues[i][j] = scan.nextDouble();
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Non-double value at row " + i + ", column " + j);
        } catch (NoSuchElementException ex) {
            System.out.println("Ran out of data");
        }
        return inputValues;
    }

    // write values to a file
    public static void writeData(double[][] values, String location) throws URISyntaxException, IOException {
        // "throws" means "not our problem", it's the problem of whoever asked us to run
        // this method
        // Get the path of the right folder
        Path locationPath = Paths.get(DataFileRead.class.getResource("/edu/guilford/").toURI());
        // Open a file in that folder
        FileWriter fileLocation = new FileWriter(locationPath.toString() + "/" + location);
        BufferedWriter bufferWrite = new BufferedWriter(fileLocation);
        // Write the data to the file
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[0].length; j++) {
                bufferWrite.write(values[i][j] + " ");
            }
            bufferWrite.newLine();
        }
        // Always close your files when you're done so that you flush the buffer
        bufferWrite.close();
    }

    public static class ScannerException extends Exception {
        public ScannerException(String message) {
            super(message);
        }
    }

}
