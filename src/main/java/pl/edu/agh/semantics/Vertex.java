package pl.edu.agh.semantics;

import java.util.ArrayList;
import java.util.List;

final class Vertex {

    private final String word;
    private final List<WeightPair<Vertex>> neighbors;

    public List<WeightPair<Vertex>> getNeighbors() {
        return neighbors;
    }

    Vertex(String word) {
        this.word = word;
        neighbors = new ArrayList<>();
    }

    void addNeighbor(Vertex to, double weight) {
        neighbors.add(new WeightPair<>(to, weight));
    }

    public String getWord() {
        return word;
    }
}
