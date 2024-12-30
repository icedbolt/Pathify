package com.shortestpath.pathify;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class AppController {
    private final Graph graph;

    public AppController() {
        graph = new Graph();
        initializeGraph();
    }

    private void initializeGraph() {
        Node cityL = new Node("city L");
        Node cityM = new Node("city M");
        Node cityN = new Node("city N");
        Node cityO = new Node("city O");
        Node cityP = new Node("city P");
        Node cityQ = new Node("city Q");

        cityL.addNeighbour(cityN, 5);
        cityN.addNeighbour(cityL, 5);
        cityL.addNeighbour(cityP, 7);
        //cityP.addNeighbour(cityL, 7);
        cityM.addNeighbour(cityN, 3);
        //cityN.addNeighbour(cityM, 3);
        cityN.addNeighbour(cityO, 4);
        //cityO.addNeighbour(cityN, 4);
        cityO.addNeighbour(cityL, 2);
        //cityL.addNeighbour(cityO, 2);
        cityO.addNeighbour(cityP, 8);
        //cityP.addNeighbour(cityO, 8);
        cityQ.addNeighbour(cityM, 11);
        //cityM.addNeighbour(cityQ, 11);


        graph.addNode(cityL);
        graph.addNode(cityM);
        graph.addNode(cityN);
        graph.addNode(cityO);
        graph.addNode(cityP);
        graph.addNode(cityQ);

    }

    public Scene initializeUI() {
        Pane root = new Pane();
        Visualizer visualizer = new Visualizer(root, graph);

        // Adding event handlers for user input
        visualizer.setupCitySelection();

        return new Scene(root, 800, 600);
    }
}
