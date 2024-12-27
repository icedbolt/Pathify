package com.shortestpath.pathify;

import javafx.scene.layout.Pane;

public class Visualizer {
    private final Pane root;
    private final Graph graph;

    public Visualizer(Pane root, Graph graph) {
        this.root = root;
        this.graph = graph;
    }

    public void citySelection() {
        // event handlers for selecting cities
    }

    public void highlightShortestPath() {
        // Highlight edge in the path
    }
}
