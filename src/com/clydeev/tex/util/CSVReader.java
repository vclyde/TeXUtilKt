package com.clydeev.tex.util;

import java.io.*;
import java.util.stream.Stream;

/**
 * A utility class for reading csv files
 *
 * @author Clyde M. Velasquez
 * @version 1.0
 * @since 12/13/2016
 */
public class CSVReader {
    private final BufferedReader lineReader;
    private String line = "";
    private File file;

    public CSVReader(File file) throws FileNotFoundException {
        this.file = file;
        lineReader = new BufferedReader(new FileReader(this.file));
    }

    public String[] lineSplit() throws IOException {
        return line.split(",");
    }

    public String line() throws IOException {
        return line;
    }

    public boolean hasNextLine() throws IOException {
        line = lineReader.readLine();
        return line != null;
    }

    public Stream<String> lines() {
        return lineReader.lines();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}