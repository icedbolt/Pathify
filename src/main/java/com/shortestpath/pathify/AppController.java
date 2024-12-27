package com.shortestpath.pathify;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class AppController {
    private final Graph graph;

    public AppController() {
        graph = new Graph();
    }

    public Scene initializeUI() {
        Pane root = new Pane();
        Visualizer visualizer = new Visualizer(root, graph);

        // Adding event handlers for user input
        visualizer.setupCitySelection();

        return new Scene(root, 800, 600);
    }
}
