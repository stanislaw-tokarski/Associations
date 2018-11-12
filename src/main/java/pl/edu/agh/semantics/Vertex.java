package pl.edu.agh.semantics;

import java.util.ArrayList;
import java.util.List;

final class Vertex {

    private final String word;
    private final List<WeightPair<Vertex>> neighbors;

    Vertex(String word) {
        this.word = word;
        neighbors = new ArrayList<>();
    }

    List<WeightPair<Vertex>> getNeighbors() {
        return neighbors;
    }

    void addNeighbor(Vertex to, double weight) {
        neighbors.add(new WeightPair<>(to, weight));
    }

    String getWord() {
        return word;
    }
}
