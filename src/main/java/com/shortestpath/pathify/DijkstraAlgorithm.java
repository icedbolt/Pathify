package com.shortestpath.pathify;

import java.util.*;

public class DijkstraAlgorithm {
    public static List<String> findShortestPath(Map<String, Node> nodes, String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distance));

        // Initialize distances
        for (String nodeName : nodes.keySet()) {
            distances.put(nodeName, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.add(new NodeDistance(start, 0));

        // Dijkstra's algorithm
        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            Node currentNode = nodes.get(current.name);

            if (current.name.equals(end)) break;

            for (Map.Entry<Node, Integer> neighbor : currentNode.getNeighbours().entrySet()) {
                int newDist = distances.get(current.name) + neighbor.getValue();
                if (newDist < distances.get(neighbor.getKey().getName())) {
                    distances.put(neighbor.getKey().getName(), newDist);
                    previousNodes.put(neighbor.getKey().getName(), current.name);
                    queue.add(new NodeDistance(neighbor.getKey().getName(), newDist));
                }
            }
        }
        // Build the path
        List<String> path = new ArrayList<>();
        String currentNode = end;
        while (currentNode != null) {
            path.addFirst(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        return path;
    }

    private static class NodeDistance {
        String name;
        int distance;

        NodeDistance(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}
