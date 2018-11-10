package pl.edu.agh.semantics;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SpellCheckerTest {

    @Test
    void shouldReturnTrueForGivenWords() {
        assertThat(SpellChecker.shouldAssociationsBeConsideredIdentical("bird", "birt")).isTrue();
        assertThat(SpellChecker.shouldAssociationsBeConsideredIdentical("bird", "birld")).isTrue();
        assertThat(SpellChecker.shouldAssociationsBeConsideredIdentical("beach", "beah")).isTrue();
        assertThat(SpellChecker.shouldAssociationsBeConsideredIdentical("bird", "BIRD")).isTrue();
    }

    @Test
    void shouldRemoveDuplicatesFromMap() {
        Map<String, Integer> associations = new HashMap<>();
        associations.put("bird", 100);
        associations.put("birt", 5);
        associations.put("horse", 80);
        associations.put("hors[e", 5);
        associations.put("squirrel", 10);
        associations.put("HORSe", 5);
        Map<String, Integer> checkedAssociations = SpellChecker.removeDuplicatesFromAssociationsMap(associations);
        assertThat(checkedAssociations.size()).isEqualTo(3);
        assertThat(checkedAssociations.get("bird")).isEqualTo(105);
        assertThat(checkedAssociations.get("horse")).isEqualTo(90);
        assertThat(checkedAssociations.get("squirrel")).isEqualTo(10);
    }
}