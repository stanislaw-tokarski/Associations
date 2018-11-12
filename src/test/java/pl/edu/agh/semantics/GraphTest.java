package pl.edu.agh.semantics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GraphTest {

    private static final Logger log = LoggerFactory.getLogger(Graph.class);

    public static void main(String[] args) {
        //given
        List<String> words = new ArrayList<>();

        words.add("bread");
        Map<String, Integer> bread = new HashMap<>();
        bread.put("food", 102);
        bread.put("baking", 73);
        bread.put("eating", 22);
        bread.put("good", 13);

        words.add("baking");
        Map<String, Integer> baking = new HashMap<>();
        baking.put("roll", 94);
        baking.put("money", 55);
        baking.put("hungry", 40);
        baking.put("good", 9);
        baking.put("eating", 4);
        baking.put("evil", 1);

        words.add("good");
        Map<String, Integer> good = new HashMap<>();
        good.put("mood", 42);
        good.put("evil", 34);
        good.put("root", 22);

        words.add("root");
        Map<String, Integer> root = new HashMap<>();
        root.put("bread", 113);
        root.put("roll", 11);
        root.put("baking", 2);

        //when
        Graph graph = Graph.ofMaps(Arrays.asList(bread, baking, good, root), words);
        log.info(graph.getShortestPath("bread", "root").toString());
        log.info(graph.getShortestPath("bread", "evil").toString());
        log.info(graph.getShortestPath("mood", "food").toString());
        log.info(graph.getShortestPath("baking", "food").toString());
    }
}
