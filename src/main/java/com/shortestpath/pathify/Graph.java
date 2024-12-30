package com.shortestpath.pathify;

import java.util.*;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();

    public void addNode(String name) {
        nodes.putIfAbsent(name, new Node(name));
    }

    public void addNode(Node node) {
        nodes.putIfAbsent(node.getName(), node);
    }

    public void addEdge(String from, String to, int weight) {
        Node nodeA = nodes.get(from);
        Node nodeB = nodes.get(to);
        if (nodeA != null && nodeB != null) {
            nodeA.addNeighbour(nodeB, weight);
        }
    }

    public List<String> findShortestPath(String start, String end) {
        return DijkstraAlgorithm.findShortestPath(nodes, start, end);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }
}
