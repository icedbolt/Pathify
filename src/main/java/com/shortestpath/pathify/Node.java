package com.shortestpath.pathify;

import java.util.*;

public class Node {
    private final String name;
    private final Map<Node, Integer> neighbours = new HashMap<>();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addNeighbour(Node neighbour, int weight) {
        neighbours.put(neighbour, weight);
    }

    public Map<Node, Integer> getNeighbours() {
        return neighbours;
    }
}
