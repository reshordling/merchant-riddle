package org.dutch.merchant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class App {

    private static final String EXAMPLE = "example.txt";

    public static void main(String[] args) {
        System.out.println("Hello Timboektoe!");
    }

    private static Stream<String> readFilename(String filename) {
        try {
            return Files.lines(Paths.get(filename));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
