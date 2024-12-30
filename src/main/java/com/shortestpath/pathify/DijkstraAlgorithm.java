package com.shortestpath.pathify;

import java.util.*;

public class DijkstraAlgorithm {
    public static List<String> findShortestPath(Map<String, Node> nodes, String start, String end) {
        // Validate inputs
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            System.err.println("Start or end node does not exist in the graph.");
            return null;
        }
        if (start.equals(end)) {
            return Collections.singletonList(start); // Return path with a single node
        }

        // Initialize data structures
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

            if (currentNode == null) {
                System.err.println("Node not found: " + current.name);
                continue;
            }

            if (current.name.equals(end)) break;

            for (Map.Entry<Node, Integer> neighbor : currentNode.getNeighbours().entrySet()) {
                String neighborName = neighbor.getKey().getName();
                int newDist = distances.get(current.name) + neighbor.getValue();

                if (newDist < distances.get(neighborName)) {
                    distances.put(neighborName, newDist);
                    previousNodes.put(neighborName, current.name);
                    queue.add(new NodeDistance(neighborName, newDist));
                }
            }
        }
        // Build the path
        List<String> path = new LinkedList<>();
        String currentNode = end;
        while (currentNode != null) {
            path.add(0,  currentNode);
            currentNode = previousNodes.get(currentNode);
        }

        // If the path does not start at the 'start' node, there's no connection
        if (!path.get(0).equals(start)) {
            //System.out.println("No path exists between " + start + " and " + end);
            return null;
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
