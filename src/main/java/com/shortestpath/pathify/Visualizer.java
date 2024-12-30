package com.shortestpath.pathify;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.*;

public class Visualizer {
    private final Pane root;
    private final Graph graph;
    private final Map<String, Circle> circlesNodes = new HashMap<>();
    private final Map<String, Text> labelNodes = new HashMap<>();
    private Circle firstSelectedNode = null;
    private Circle secondSelectedNode = null;
    private final List<Line> highlightedEdges = new ArrayList<>(); // Keep track of highlighted edges
    private final List<Circle> highlightedNodes = new ArrayList<>(); // Keep track of highlighted nodes

    public Visualizer(Pane root, Graph graph) {
        this.root = root;
        this.graph = graph;
    }

    // Set up nodes & edges on the visualization pane
    public void setupCitySelection() {
        // Create & place nodes
        double centerX = 400;
        double centerY = 300;
        double radius = 200;
        int index = 0;
        int totalNodes = graph.getNodes().size();

        for (String nodeName : graph.getNodes().keySet()) {
            // Calculate position in circular layout
            double angle = (2 * Math.PI / totalNodes) * index++;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            // Create circle for nodes
            Circle nodeCircle = new Circle(x, y, 20, Color.LAVENDER);
            nodeCircle.setStroke(Color.BLACK);
            nodeCircle.setId(nodeName); // Assign the city name as the ID
            nodeCircle.setOnMouseClicked(mouseEvent -> handleNodeClick(nodeCircle)); // Pass the Circle object

            // Create label for the node
            Text nodeLabel = new Text(nodeName);
            nodeLabel.setFill(Color.BLACK);
            nodeLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

            // Center the text inside the circle
            javafx.geometry.Bounds textBounds = nodeLabel.getBoundsInLocal();
            nodeLabel.setX(x - textBounds.getWidth() / 2); // Center horizontally
            nodeLabel.setY(y + textBounds.getHeight() / 4); // Center vertically (adjusted for font height)

            // Add to root Pane & store references
            root.getChildren().addAll(nodeCircle, nodeLabel);
            circlesNodes.put(nodeName, nodeCircle);
            labelNodes.put(nodeName, nodeLabel);
        }

        // Create edges
        for (Node node : graph.getNodes().values()) {
            for (Map.Entry<Node, Integer> entry : node.getNeighbours().entrySet()) {
                createEdge(node.getName(), entry.getKey().getName());
            }
        }
    }

    // Highlight the shortest path
    public void highlightShortestPath(List<String> path) {
        resetHighlight(); // Reset previous highlights

        // Highlight edges in the shortest path
        for (int i = 0; i < path.size() - 1; i++) {
            String from = path.get(i);
            String to = path.get(i + 1);
            Line edge = findEdge(from, to);
            if (edge == null) {
                edge = findEdge(to, from); // Try the reverse direction
            }

            if (edge != null) {
                edge.setStroke(Color.BLUE);
                edge.setStrokeWidth(3);
                highlightedEdges.add(edge);
            }

            Circle fromCircle = circlesNodes.get(from);
            if (fromCircle != null) {
                fromCircle.setFill(Color.DARKOLIVEGREEN);
                highlightedNodes.add(fromCircle);
            }
        }

        // Highlight the last node
        String lastNode = path.get(path.size() - 1);
        Circle lastCircle = circlesNodes.get(lastNode);
        if (lastCircle != null) {
            lastCircle.setFill(Color.GOLD);
            highlightedNodes.add(lastCircle);
        }
    }

    // Handle node click events
    private void handleNodeClick(Circle nodeCircle) {
        if (firstSelectedNode == null) {
            firstSelectedNode = nodeCircle;
            firstSelectedNode.setFill(Color.DARKOLIVEGREEN);
            System.out.println("First city selected: " + firstSelectedNode.getId());
        } else if (secondSelectedNode == null) {
            secondSelectedNode = nodeCircle;
            secondSelectedNode.setFill(Color.DARKOLIVEGREEN);
            System.out.println("Second city selected: " + secondSelectedNode.getId());

            // Get city names
            String start = firstSelectedNode.getId();
            String end = secondSelectedNode.getId();

            // Validate that both cities exist in the graph
            if (!graph.getNodes().containsKey(start) || !graph.getNodes().containsKey(end)) {
                System.out.println("Error: Invalid cities selected!");
                resetSelection();
                return;
            }

            // Compute shortest path
            List<String> shortestPath = DijkstraAlgorithm.findShortestPath(graph.getNodes(), start, end);

            if (shortestPath == null) {
                System.out.println("No path found between " + start + " and " + end);
                System.out.println("--All set for new selection--");
            } else {
                System.out.println("Shortest Path: " + shortestPath);
                highlightShortestPath(shortestPath);
                System.out.println("--All set for new selection--");
            }
        } else {
            // If both cities are selected, reset and start fresh
            resetSelection();
            firstSelectedNode = nodeCircle;
            firstSelectedNode.setFill(Color.DARKOLIVEGREEN);
            System.out.println("First city selected: " + firstSelectedNode.getId());
        }
    }

    private void resetHighlight() {
        for (Line edge : highlightedEdges) {
            edge.setStroke(Color.GRAY);
            edge.setStrokeWidth(1);
        }

        for (Circle node : highlightedNodes) {
            node.setFill(Color.LAVENDER);
        }
        highlightedEdges.clear();
        highlightedNodes.clear();
    }

    // Reset the selection state
    private void resetSelection() {
        resetHighlight();
        if (firstSelectedNode != null) {
            firstSelectedNode.setFill(Color.LAVENDER);
        }
        if (secondSelectedNode != null) {
            secondSelectedNode.setFill(Color.LAVENDER);
        }
        firstSelectedNode = null;
        secondSelectedNode = null;
    }

    // Create an edge (line) between two nodes in the visualization
    private void createEdge(String from, String to) {
        Circle fromCircle = circlesNodes.get(from);
        Circle toCircle = circlesNodes.get(to);

        if (fromCircle != null && toCircle != null) {
            // Ensure edges are only created once (undirected graph logic)
            String edgeKey = getEdgeKey(from, to);
            if (edgesSet.contains(edgeKey)) {
                return; // Skip if the edge has already been created
            }
            edgesSet.add(edgeKey);

            Line edge = new Line(
                    fromCircle.getCenterX(), fromCircle.getCenterY(),
                    toCircle.getCenterX(), toCircle.getCenterY()
            );
            edge.setStroke(Color.GRAY);
            //root.getChildren().add(edge);

            // Display edge weight
            Node fromNode = graph.getNodes().get(from);
            Node toNode = graph.getNodes().get(to);
            if (fromNode != null && toNode != null) {
                Integer weight = fromNode.getNeighbours().get(toNode);
                if (weight != null) {
                    double midX = (fromCircle.getCenterX() + toCircle.getCenterX()) / 2;
                    double midY = (fromCircle.getCenterY() + toCircle.getCenterY()) / 2;
                    double angle = Math.atan2(
                            toCircle.getCenterY() - fromCircle.getCenterY(),
                            toCircle.getCenterX() - fromCircle.getCenterX()
                    );
                    double offsetX = 10 * Math.sin(angle);
                    double offsetY = -10 * Math.cos(angle);
                    Text weightText = new Text(midX + offsetX, midY + offsetY, String.valueOf(weight));
                    weightText.setFill(Color.DARKBLUE);
                    weightText.setStyle("-fx-font-size: 14;");
                    root.getChildren().add(weightText);
                }
            }

            // Add the edge line to the root pane
            root.getChildren().add(edge);
        }
    }

    private String getEdgeKey(String from, String to) {
        if (from.compareTo(to) < 0) {
            return from + "-" + to;
        } else {
            return to + "-" + from;
        }
    }

    // Set to track edges that have already been created
    private final Set<String> edgesSet = new HashSet<>();

    // Find an edge between two nodes
    private Line findEdge(String from, String to) {
        Circle fromCircle = circlesNodes.get(from);
        Circle toCircle = circlesNodes.get(to);

        if (fromCircle != null && toCircle != null) {
            return root.getChildren().stream()
                    .filter(node -> node instanceof Line)
                    .map(node -> (Line) node)
                    .filter(line ->
                            line.getStartX() == fromCircle.getCenterX() &&
                                    line.getStartY() == fromCircle.getCenterY() &&
                                    line.getEndX() == toCircle.getCenterX() &&
                                    line.getEndY() == toCircle.getCenterY())
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
