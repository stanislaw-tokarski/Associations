package pl.edu.agh.semantics;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class SpellChecker {

    private static final int LEVENSHTEIN_IDENTITY_CONDITION = 1;

    private SpellChecker() {
    }

    static boolean shouldAssociationsBeConsideredIdentical(String first, String second) {
        Integer levenshteinDistance = LevenshteinDistance.getDefaultInstance()
                .apply(first.toLowerCase(), second.toLowerCase());
        if (levenshteinDistance <= LEVENSHTEIN_IDENTITY_CONDITION) {
            System.out.println("Words considered identical: " + first + " " + second);
            return true;
        }
        return false;
    }

    static Map<String, Integer> removeDuplicatesFromAssociationsMap(Map<String, Integer> associations) {
        Map<String, Integer> associationsWithoutDuplicates = new ConcurrentHashMap<>();
        associations.keySet().forEach(keyToBeAdded -> {
            boolean mapUpdated = false;
            for (String existingKey : associationsWithoutDuplicates.keySet()) {
                if (!existingKey.equals(keyToBeAdded)
                        && shouldAssociationsBeConsideredIdentical(existingKey, keyToBeAdded)) {
                    if (associationsWithoutDuplicates.get(existingKey) >= associations.get(keyToBeAdded)) {
                        associationsWithoutDuplicates.compute(existingKey, (k, v) -> v + associations.get(keyToBeAdded));
                    } else {
                        associationsWithoutDuplicates.put(keyToBeAdded,
                                associationsWithoutDuplicates.get(existingKey) + associations.get(keyToBeAdded));
                        associationsWithoutDuplicates.remove(existingKey);
                    }
                    mapUpdated = true;
                }
            }
            if (!mapUpdated) {
                associationsWithoutDuplicates.put(keyToBeAdded, associations.get(keyToBeAdded));
            }
        });
        assert associations.values().stream().mapToInt(Integer::intValue).sum() ==
                associationsWithoutDuplicates.values().stream().mapToInt(Integer::intValue).sum();
        return associationsWithoutDuplicates;
    }
}