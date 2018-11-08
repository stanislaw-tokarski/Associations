package pl.edu.agh.semantics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InputDataParser {
    
    static Map<String, Integer> readAssociationsFromFile(String fileName) {
        Map<String, Integer> associations = new HashMap<>();
        try (Stream<String> csvLines = Files.lines(Paths.get(fileName))) {
            csvLines.forEach(line -> {
                String[] payload = line.split(",");
                if (associations.containsKey(payload[1])) {
                    associations.compute(payload[1], (s, i) -> i + Integer.valueOf(payload[0]));
                } else {
                    associations.put(payload[1], Integer.valueOf(payload[0]));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return associations;
    }
}
