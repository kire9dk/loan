package com.loan.calculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileProcessor {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(FileProcessor.class.getName());
        LOGGER.setLevel(Level.WARNING);
    }

    private final File file;

    public FileProcessor(ArgumentParser argumentParser) {
        file = argumentParser.getMarketFile();
        if (!file.isFile()) {
            LOGGER.log(Level.SEVERE, () -> String.format("Illegal file location %s", file.getPath()));
            throw new IllegalArgumentException("Illegal file location");
        }
    }

    public List<String> getFileLines() {
        Stream.Builder<String> builder = Stream.builder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                builder.add(line);
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "File not found", e);
            throw new QuoteRuntimeException("File not found", e);
        }
        return builder.build().collect(Collectors.toList());
    }
}
