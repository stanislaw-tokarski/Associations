package pl.edu.agh.semantics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InputDataParser {

    private static final Logger log = LoggerFactory.getLogger(InputDataParser.class);

    private InputDataParser() {
    }

    static Map<String, Integer> readAssociationsFromFile(String fileName) {
        Map<String, Integer> associations = new HashMap<>();
        try (Stream<String> csvLines = Files.lines(Paths.get(fileName))) {
            csvLines.forEach(line -> {
                String[] payload = line.split(",");
                payload[1] = payload[1].replaceAll("\\P{javaAlphabetic}", "");
                if (associations.containsKey(payload[1])) {
                    associations.compute(payload[1], (s, i) -> i + Integer.valueOf(payload[0]));
                } else {
                    associations.put(payload[1], Integer.valueOf(payload[0]));
                }
            });
        } catch (IOException e) {
            log.error("Unable to read from file, {}", e);
        }
        return associations;
    }
}
