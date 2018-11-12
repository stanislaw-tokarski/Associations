package pl.edu.agh.semantics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Graph {

    private static final Logger log = LoggerFactory.getLogger(Graph.class);

    private final Map<String, Vertex> verticesByName;
    private final Map<String, Double> distancesByName;
    private final Set<String> allWords;

    private Graph(Set<String> allWords) {
        this.allWords = allWords;
        this.verticesByName = allWords.stream()
                .map(Vertex::new)
                .collect(Collectors.toMap(Vertex::getWord, Function.identity()));
        distancesByName = new HashMap<>();
    }

    static Graph ofMaps(List<Map<String, Integer>> associations, List<String> words) {
        assert associations.size() == words.size();

        Set<String> allWords = associations.stream()
                .flatMap(m -> m.keySet().stream())
                .collect(Collectors.toSet());
        allWords.addAll(words);
        Graph graph = new Graph(allWords);
        for (int i = 0; i < words.size(); i++) {
            Vertex name = graph.verticesByName.get(words.get(i));
            for (Map.Entry<String, Integer> association : associations.get(i).entrySet()) {
                name.addNeighbor(graph.verticesByName.get(association.getKey()),
                        calculateWeight(association.getValue())
                );
            }
        }
        return graph;
    }

    private static double calculateWeight(int occurrenceCount) {
        assert occurrenceCount > 0;
        return 1.0 / (1.0 + Math.log(occurrenceCount));
    }

    List<WeightPair<String>> getShortestPath(String source, String target) {
        for (String word : allWords) {
            distancesByName.put(word, Double.POSITIVE_INFINITY);
        }
        distancesByName.put(source, 0.0);
        PriorityQueue<WeightPair<Vertex>> queue = new PriorityQueue<>();
        queue.add(new WeightPair<>(verticesByName.get(source), 0.0));
        while (!queue.isEmpty()) {
            WeightPair<Vertex> poll = queue.poll();
            if (distancesByName.get(poll.getTarget().getWord()) < poll.getWeight()) {
                continue;
            }
            if (poll.getTarget().getWord().equals(target)) {
                break;
            }
            for (WeightPair<Vertex> neighbor : poll.getTarget().getNeighbors()) {
                double newDist = poll.getWeight() + neighbor.getWeight();
                if (distancesByName.get(neighbor.getTarget().getWord()) > newDist) {
                    distancesByName.put(neighbor.getTarget().getWord(), newDist);
                    queue.add(new WeightPair<>(neighbor.getTarget(), newDist));
                }
            }
        }
        if (distancesByName.get(target) == Double.POSITIVE_INFINITY) {
            log.info("Cannot find route from source to target!");
            return Collections.emptyList();
        }
        ArrayList<WeightPair<String>> result = new ArrayList<>();
        retrievePath(verticesByName.get(source), target, result);
        Collections.reverse(result);
        return result;
    }

    private boolean retrievePath(Vertex from, String target, List<WeightPair<String>> appendable) {
        if (from.getWord().equals(target)) {
            appendable.add(new WeightPair<>(target, 0.0));
            return true;
        }
        double myDist = distancesByName.get(from.getWord());
        for (WeightPair<Vertex> neighbor : from.getNeighbors()) {
            if (distancesByName.get(neighbor.getTarget().getWord()) == myDist + neighbor.getWeight()) {
                if (retrievePath(neighbor.getTarget(), target, appendable)) {
                    appendable.add(new WeightPair<>(from.getWord(), neighbor.getWeight()));
                    return true;
                }
            }
        }
        return false;
    }
}
