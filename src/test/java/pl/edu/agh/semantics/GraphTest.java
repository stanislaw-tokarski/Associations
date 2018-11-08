package pl.edu.agh.semantics;

import java.util.*;

public class GraphTest {

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
        System.out.println(graph.getShortestPath("bread", "root"));
        System.out.println(graph.getShortestPath("bread", "evil"));
        System.out.println(graph.getShortestPath("mood", "food"));
        System.out.println(graph.getShortestPath("baking", "food"));
    }
}
