package org.dutch.merchant;

import org.dutch.merchant.model.Squad;
import org.dutch.merchant.warehouse.Audit;
import org.dutch.merchant.warehouse.Report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class App {

    private static final String EXAMPLE = "example.txt";
    private static final MerchantConfig CONF = new MerchantConfig();


    public static void main(String[] args) {
        if (args.length == 1) {
            Stream<Squad> squads = new Audit().lines(readFilename(args[0]));
            new Report().generate(squads, CONF);
        } else {
            printBanner();
        }
    }

    private static Stream<String> readFilename(String filename) {
        try {
            return Files.lines(Paths.get(filename));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void printBanner() {
        System.out.println("./gradlew run --args='/absolute/path/to/example.txt'");
    }
}
